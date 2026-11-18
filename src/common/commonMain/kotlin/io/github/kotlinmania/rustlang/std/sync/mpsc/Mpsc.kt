// port-lint: source sync/mpsc.rs
package io.github.kotlinmania.rustlang.std.sync.mpsc

import io.github.kotlinmania.rustlang.core.clone.Share
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.mpmc.SendTimeoutError
import io.github.kotlinmania.rustlang.std.time.Instant

// Multi-producer, single-consumer FIFO queue communication primitives.
//
// This module provides message-based communication over channels, concretely
// defined among three types:
//
// * [Sender]
// * [SyncSender]
// * [Receiver]
//
// A [Sender] or [SyncSender] is used to send data to a [Receiver]. Both
// senders are clone-able (multi-producer) such that many threads can send
// simultaneously to one receiver (single-consumer).
//
// These channels come in two flavors:
//
// 1. An asynchronous, infinitely buffered channel. The [channel] function
//    will return a `(Sender, Receiver)` tuple where all sends will be
//    **asynchronous** (they never block for space to become available; see
//    [std.sync] for precise guarantees on blocking.) The channel
//    conceptually has an infinite buffer.
//
// 2. A synchronous, bounded channel. The [syncChannel] function will
//    return a `(SyncSender, Receiver)` tuple where the storage for pending
//    messages is a pre-allocated buffer of a fixed size. All sends will be
//    **synchronous** by blocking until there is buffer space available. Note
//    that a bound of 0 is allowed, causing the channel to become a "rendezvous"
//    channel where each sender atomically hands off a message to a receiver.
//
// [send]: Sender.send
// [std.sync]: ../index.html#blocking-guarantees
//
// ## Disconnection
//
// The send and receive operations on channels will all return a [Result]
// indicating whether the operation succeeded or not. An unsuccessful operation
// is normally indicative of the other half of a channel having "hung up" by
// being dropped in its corresponding thread.
//
// Once half of a channel has been deallocated, most operations can no longer
// continue to make progress, so [Err] will be returned. Many applications
// will continue to [unwrap] the results returned from this module,
// instigating a propagation of failure among threads if one unexpectedly dies.
//
// [unwrap]: Result.unwrap
//
// # Examples
//
// Simple usage:
//
// ```kotlin
// import std.sync.mpsc.channel
// import std.thread
//
// // Create a simple streaming channel
// val (tx, rx) = channel()
// thread.spawn({
//     tx.send(10).unwrap()
// })
// check(rx.recv().unwrap() == 10)
// ```
//
// Shared usage:
//
// ```kotlin
// import std.sync.mpsc.channel
// import std.thread
//
// // Create a shared channel that can be sent along from many threads
// // where tx is the sending half (tx for transmission), and rx is the receiving
// // half (rx for receiving).
// val (tx, rx) = channel()
// for (i in 0 until 10) {
//     val tx1 = tx.clone()
//     thread.spawn({
//         tx1.send(i).unwrap()
//     })
// }
// for (unused in 0 until 10) {
//     val j = rx.recv().unwrap()
//     check(0 <= j && j < 10)
// }
// ```
//
// Propagating panics:
//
// ```kotlin
// import std.sync.mpsc.channel
//
// // The call to recv() will return an error because the channel has already
// // hung up (or been deallocated)
// val (tx, rx) = channel<Int>()
// (tx).let { }
// check(rx.recv().isErr())
// ```
//
// Synchronous channels:
//
// ```kotlin
// import std.sync.mpsc.syncChannel
// import std.thread
//
// val (tx, rx) = syncChannel<Int>(0)
// thread.spawn({
//     // This will wait for the parent thread to start receiving
//     tx.send(53).unwrap()
// })
// rx.recv().unwrap()
// ```
//
// Unbounded receive loop:
//
// ```kotlin
// import std.sync.mpsc.syncChannel
// import std.thread
//
// val (tx, rx) = syncChannel(3)
// for (unused in 0 until 3) {
//     // It would be the same without thread and clone here
//     // since there will still be one `tx` left.
//     val tx1 = tx.clone()
//     // cloned tx dropped within thread
//     thread.spawn({ tx1.send("ok").unwrap() })
// }
// // Drop the last sender to stop `rx` waiting for message.
// // The program will not complete if we comment this out.
// // **All** `tx` needs to be dropped for `rx` to have `Err`.
// (tx).let { }
// // Unbounded receiver waiting for all senders to complete.
// while (true) {
//     val matchVal = rx.recv()
//     if (matchVal !is Ok) break
//     val (msg) = matchVal
//     println("$msg")
// }
// println("completed")
// ```

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
 * The receiving half of Rust's [channel] (or [syncChannel]) type.
 * This half can only be owned by one thread.
 *
 * Messages sent to the channel can be retrieved using [recv].
 *
 * [recv]: Receiver.recv
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 * import std.time.Duration
 *
 * val (send, recv) = channel()
 * thread.spawn({
 *     send.send("Hello world!").unwrap()
 *     thread.sleep(Duration.fromSecs(2))
 *     // block for two seconds
 *     send.send("Delayed for 2 seconds").unwrap()
 * })
 * println("${recv.recv().unwrap()}")
 * // Received immediately
 * println("Waiting...")
 * println("${recv.recv().unwrap()}")
 * // Received after 2 seconds
 * ```
 */
public open class Receiver<T>(
    public var inner: io.github.kotlinmania.rustlang.std.sync.mpmc.Receiver<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    // The receiver port can be sent from place to place, so long as it
    // is not used to receive non-sendable things.

    // ///////////////////////////////////////////////////////////////////////////
    // Receiver
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Attempts to return a pending value on this receiver without blocking.
     *
     * This method will never block the caller in order to wait for data to
     * become available. Instead, this will always return immediately with a
     * possible option of pending data on the channel. (See [std.sync] for
     * precise guarantees on blocking.)
     *
     * This is useful for a flavor of "optimistic check" before deciding to
     * block on a receiver.
     *
     * Compared with [recv], this function has two failure cases instead of one
     * (one for disconnection, one for an empty buffer).
     *
     * [recv]: Self.recv
     * [std.sync]: ../index.html#blocking-guarantees
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.Receiver
     * import std.sync.mpsc.channel
     *
     * val (_, receiver: Receiver<Int>) = channel()
     * check(receiver.tryRecv().isErr())
     * ```
     */
    public open fun tryRecv(): Result<T, TryRecvError> = this.inner.tryRecv()

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent (at least one sender
     * still exists). Once a message is sent to the corresponding [Sender]
     * (or [SyncSender]), this receiver will wake up and return that
     * message.
     *
     * If the corresponding [Sender] has disconnected, or it disconnects while
     * this call is blocking, this call will wake up and return [Err] to
     * indicate that no more messages can ever be received on this channel.
     * However, since channels are buffered, messages sent before the disconnect
     * will still be properly received.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     *
     * val (send, recv) = mpsc.channel()
     * val handle = thread.spawn({
     *     send.send(1u.toUByte()).unwrap()
     * })
     * handle.join().unwrap()
     * check(Result.success(1) == recv.recv())
     * ```
     *
     * Buffering behavior:
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.sync.mpsc.RecvError
     * import std.thread
     *
     * val (send, recv) = mpsc.channel()
     * val handle = thread.spawn({
     *     send.send(1u.toUByte()).unwrap()
     *     send.send(2).unwrap()
     *     send.send(3).unwrap()
     *     (send).let { }
     * })
     * // wait for the thread to join so we ensure the sender is dropped
     * handle.join().unwrap()
     * check(Result.success(1) == recv.recv())
     * check(Result.success(2) == recv.recv())
     * check(Result.success(3) == recv.recv())
     * check(Result.failure(RecvError) == recv.recv())
     * ```
     */
    public open fun recv(): Result<T, RecvError> = this.inner.recv()

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up, or if it waits more than `timeout`.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent (at least one sender
     * still exists). Once a message is sent to the corresponding [Sender]
     * (or [SyncSender]), this receiver will wake up and return that
     * message.
     *
     * If the corresponding [Sender] has disconnected, or it disconnects while
     * this call is blocking, this call will wake up and return [Err] to
     * indicate that no more messages can ever be received on this channel.
     * However, since channels are buffered, messages sent before the disconnect
     * will still be properly received.
     *
     * # Examples
     *
     * Successfully receiving value before encountering timeout:
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     * import std.time.Duration
     *
     * val (send, recv) = mpsc.channel()
     * thread.spawn({
     *     send.send('a').unwrap()
     * })
     * check(recv.recvTimeout(Duration.fromMillis(400)) == Result.success('a'))
     * ```
     *
     * Receiving an error upon reaching timeout:
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     * import std.time.Duration
     *
     * val (send, recv) = mpsc.channel()
     * thread.spawn({
     *     thread.sleep(Duration.fromMillis(800))
     *     send.send('a').unwrap()
     * })
     * check(recv.recvTimeout(Duration.fromMillis(400)) == Result.failure(mpsc.RecvTimeoutError.Timeout))
     * ```
     */
    public open fun recvTimeout(timeout: Duration): Result<T, RecvTimeoutError> = this.inner.recvTimeout(timeout)

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up, or if `deadline` is reached.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent. Once a message is
     * sent to the corresponding [Sender] (or [SyncSender]), then this
     * receiver will wake up and return that message.
     *
     * If the corresponding [Sender] has disconnected, or it disconnects while
     * this call is blocking, this call will wake up and return [Err] to
     * indicate that no more messages can ever be received on this channel.
     * However, since channels are buffered, messages sent before the disconnect
     * will still be properly received.
     *
     * # Examples
     *
     * Successfully receiving value before reaching deadline:
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     * import std.time.Duration
     * import std.time.Instant
     *
     * @feature(deadlineApi)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.channel()
     *     thread.spawn({
     *         send.send('a').unwrap()
     *     })
     *     check(recv.recvDeadline(Instant.now() + Duration.fromMillis(400)) == Result.success('a'))
     * }
     * ```
     *
     * Receiving an error upon reaching deadline:
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     * import std.time.Duration
     * import std.time.Instant
     *
     * @feature(deadlineApi)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(800))
     *         send.send('a').unwrap()
     *     })
     *     check(recv.recvDeadline(Instant.now() + Duration.fromMillis(400)) == Result.failure(mpsc.RecvTimeoutError.Timeout))
     * }
     * ```
     */
    public open fun recvDeadline(deadline: Instant): Result<T, RecvTimeoutError> = this.inner.recvDeadline(deadline)

    /**
     * Returns an iterator that will block waiting for messages, but never
     * [panic]. It will return [None] when the channel has hung up.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     * import std.thread
     *
     * val (send, recv) = channel()
     * thread.spawn({
     *     send.send(1).unwrap()
     *     send.send(2).unwrap()
     *     send.send(3).unwrap()
     * })
     * var iter = recv.iter()
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == 3)
     * check(iter.next() == null)
     * ```
     */
    public open fun iter(): Iter<T> = Iter(rx = this)

    /**
     * Returns an iterator that will attempt to yield all pending values.
     * It will return `None` if there are no more pending values or if the
     * channel has hung up. The iterator will never [panic] or block the
     * user by waiting for values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     * import std.thread
     * import std.time.Duration
     *
     * val (sender, receiver) = channel()
     * // nothing is in the buffer yet
     * check(receiver.tryIter().next() == null)
     * thread.spawn({
     *     thread.sleep(Duration.fromSecs(1))
     *     sender.send(1).unwrap()
     *     sender.send(2).unwrap()
     *     sender.send(3).unwrap()
     * })
     * // nothing is in the buffer yet
     * check(receiver.tryIter().next() == null)
     * // block for two seconds
     * thread.sleep(Duration.fromSecs(2))
     * var iter = receiver.tryIter()
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == 3)
     * check(iter.next() == null)
     * ```
     */
    public open fun tryIter(): TryIter<T> = TryIter(rx = this)

    /**
     * Returns `true` if the channel is disconnected.
     *
     * Note that a return value of `false` does not guarantee the channel will
     * remain connected. The channel may be disconnected immediately after this method
     * returns, so a subsequent [Receiver.recv] may still fail with [RecvError].
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     *
     * @feature(mpscIsDisconnected)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel<Int>()
     *     check(!rx.isDisconnected())
     *     (tx).let { }
     *     check(rx.isDisconnected())
     * }
     * ```
     */
    public open fun isDisconnected(): Boolean = this.inner.isDisconnected()

    /**
     * Returns `true` if the channel is empty.
     *
     * Note: Zero-capacity channels are always empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.channel()
     *     check(recv.isEmpty())
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(!recv.isEmpty())
     * }
     * ```
     */
    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    /**
     * Returns the number of messages in the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (tx, rx) = mpsc.channel()
     *     check(rx.len() == 0)
     *     tx.send(1u.toUByte()).unwrap()
     *     tx.send(2u.toUByte()).unwrap()
     *     check(rx.len() == 2)
     *     rx.recv().unwrap()
     *     check(rx.len() == 1)
     * }
     * ```
     */
    public open fun len(): Int = this.inner.len()

    public open fun intoIter(): Iter<T> = this.iter()

    public open fun intoIterIntoIterator(): IntoIter<T> = IntoIter(rx = this)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Receiver").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

/**
 * An iterator over messages on a [Receiver], created by [iter].
 *
 * This iterator will block whenever [next] is called,
 * waiting for a new message, and [None] will be returned
 * when the corresponding channel has hung up.
 *
 * [iter]: Receiver.iter
 * [next]: Iterator.next
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 *
 * val (send, recv) = channel()
 * thread.spawn({
 *     send.send(1u.toUByte()).unwrap()
 *     send.send(2u.toUByte()).unwrap()
 *     send.send(3u.toUByte()).unwrap()
 * })
 * for (x in recv.iter()) {
 *     println("Got: $x")
 * }
 * ```
 */
public open class Iter<T>(
    public var rx: Receiver<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? = this.rx.recv().ok()
}

/**
 * An iterator that attempts to yield all pending values for a [Receiver],
 * created by [tryIter].
 *
 * [None] will be returned when there are no pending values remaining or
 * if the corresponding channel has hung up.
 *
 * This iterator will never block the caller in order to wait for data to
 * become available. Instead, it will return [None]. (See [std.sync] for
 * precise guarantees on blocking.)
 *
 * [tryIter]: Receiver.tryIter
 * [std.sync]: ../index.html#blocking-guarantees
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 * import std.time.Duration
 *
 * val (sender, receiver) = channel()
 * // Nothing is in the buffer yet
 * check(receiver.tryIter().next() == null)
 * println("Nothing in the buffer...")
 * thread.spawn({
 *     sender.send(1).unwrap()
 *     sender.send(2).unwrap()
 *     sender.send(3).unwrap()
 * })
 * println("Going to sleep...")
 * thread.sleep(Duration.fromSecs(2))
 * // block for two seconds
 * for (x in receiver.tryIter()) {
 *     println("Got: $x")
 * }
 * ```
 */
public open class TryIter<T>(
    public var rx: Receiver<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@TryIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? = this.rx.tryRecv().ok()
}

/**
 * An owning iterator over messages on a [Receiver],
 * created by [intoIter].
 *
 * This iterator will block whenever [next]
 * is called, waiting for a new message, and [None] will be
 * returned if the corresponding channel has hung up.
 *
 * [intoIter]: Receiver.intoIter
 * [next]: Iterator.next
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 *
 * val (send, recv) = channel()
 * thread.spawn({
 *     send.send(1u.toUByte()).unwrap()
 *     send.send(2u.toUByte()).unwrap()
 *     send.send(3u.toUByte()).unwrap()
 * })
 * for (x in recv.intoIter()) {
 *     println("Got: $x")
 * }
 * ```
 */
public open class IntoIter<T>(
    public var rx: Receiver<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@IntoIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): T? = this.rx.recv().ok()
}

/**
 * The sending-half of Rust's asynchronous [channel] type.
 *
 * Messages can be sent through this channel with [send].
 *
 * Note: all senders (the original and its clones) need to be dropped for the receiver
 * to stop blocking to receive messages with [Receiver.recv].
 *
 * [send]: Sender.send
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 *
 * val (sender, receiver) = channel()
 * val sender2 = sender.clone()
 * // First thread owns sender
 * thread.spawn({
 *     sender.send(1).unwrap()
 * })
 * // Second thread owns sender2
 * thread.spawn({
 *     sender2.send(2).unwrap()
 * })
 * val msg = receiver.recv().unwrap()
 * val msg2 = receiver.recv().unwrap()
 * check(3 == msg + msg2)
 * ```
 */
public open class Sender<T>(
    public var inner: io.github.kotlinmania.rustlang.std.sync.mpmc.Sender<T>,
) : Share {
    // The send port can be sent from place to place, so long as it
    // is not used to send non-sendable things.

    // ///////////////////////////////////////////////////////////////////////////
    // Sender
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Attempts to send a value on this channel, returning it back if it could
     * not be sent.
     *
     * A successful send occurs when it is determined that the other end of
     * the channel has not hung up already. An unsuccessful send would be one
     * where the corresponding receiver has already been deallocated. Note
     * that a return value of [Err] means that the data will never be
     * received, but a return value of [Ok] does *not* mean that the data
     * will be received. It is possible for the corresponding receiver to
     * hang up immediately after this function returns [Ok].
     *
     * This method will never block the caller in order to wait for space to
     * become available. (See [std.sync] for precise guarantees on blocking.)
     *
     * [std.sync]: ../index.html#blocking-guarantees
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     *
     * val (tx, rx) = channel()
     * // This send is always successful
     * tx.send(1).unwrap()
     * // This send will fail because the receiver is gone
     * (rx).let { }
     * check(tx.send(1).unwrapErr().first == 1)
     * ```
     */
    public open fun send(t: T): Result<Unit, SendError<T>> = this.inner.send(t)

    /**
     * Returns `true` if the channel is disconnected.
     *
     * Note that a return value of `false` does not guarantee the channel will
     * remain connected. The channel may be disconnected immediately after this method
     * returns, so a subsequent [Sender.send] may still fail with [SendError].
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     *
     * @feature(mpscIsDisconnected)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel<Int>()
     *     check(!tx.isDisconnected())
     *     (rx).let { }
     *     check(tx.isDisconnected())
     * }
     * ```
     */
    public open fun isDisconnected(): Boolean = this.inner.isDisconnected()

    /**
     * Returns `true` if the channel is empty.
     *
     * Note: Zero-capacity channels are always empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.channel()
     *     val tx1 = send.clone()
     *     val tx2 = send.clone()
     *     check(tx1.isEmpty())
     *     val handle = thread.spawn({
     *         tx2.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(!tx1.isEmpty())
     * }
     * ```
     */
    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    /**
     * Returns the number of messages in the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.channel()
     *     val (tx1, tx2) = Pair(send.clone(), send.clone())
     *     check(tx1.len() == 0)
     *     val handle = thread.spawn({
     *         tx2.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(tx1.len() == 1)
     * }
     * ```
     */
    public open fun len(): Int = this.inner.len()

    /**
     * Clone a sender to send to other threads.
     *
     * Note, be aware of the scope of the sender because all senders
     * (including the original) need to be dropped in order for
     * [Receiver.recv] to stop blocking.
     */
    public open fun clone(): Sender<T> = Sender(inner = this.inner.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Sender").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

/**
 * The sending-half of Rust's synchronous [syncChannel] type.
 *
 * Messages can be sent through this channel with [send] or [trySend].
 *
 * [send] will block if there is no space in the internal buffer.
 *
 * [send]: SyncSender.send
 * [trySend]: SyncSender.trySend
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.syncChannel
 * import std.thread
 *
 * // Create a syncChannel with buffer size 2
 * val (syncSender, receiver) = syncChannel(2)
 * val syncSender2 = syncSender.clone()
 * // First thread owns syncSender
 * thread.spawn({
 *     syncSender.send(1).unwrap()
 *     syncSender.send(2).unwrap()
 * })
 * // Second thread owns syncSender2
 * thread.spawn({
 *     syncSender2.send(3).unwrap()
 *     // thread will now block since the buffer is full
 *     println("Thread unblocked!")
 * })
 * var msg: Any?
 * msg = receiver.recv().unwrap()
 * println("message $msg received")
 * // "Thread unblocked!" will be printed now
 * msg = receiver.recv().unwrap()
 * println("message $msg received")
 * msg = receiver.recv().unwrap()
 * println("message $msg received")
 * ```
 */
public open class SyncSender<T>(
    public var inner: io.github.kotlinmania.rustlang.std.sync.mpmc.Sender<T>,
) : Share {
    // ///////////////////////////////////////////////////////////////////////////
    // SyncSender
    // ///////////////////////////////////////////////////////////////////////////

    /**
     * Sends a value on this synchronous channel.
     *
     * This function will *block* until space in the internal buffer becomes
     * available or a receiver is available to hand off the message to.
     *
     * Note that a successful send does *not* guarantee that the receiver will
     * ever see the data if there is a buffer on this channel. Items may be
     * enqueued in the internal buffer for the receiver to receive at a later
     * time. If the buffer size is 0, however, the channel becomes a rendezvous
     * channel and it guarantees that the receiver has indeed received
     * the data if this function returns success.
     *
     * This function will never panic, but it may return [Err] if the
     * [Receiver] has disconnected and is no longer able to receive
     * information.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.syncChannel
     * import std.thread
     *
     * // Create a rendezvous syncChannel with buffer size 0
     * val (syncSender, receiver) = syncChannel(0)
     * thread.spawn({
     *     println("sending message...")
     *     syncSender.send(1).unwrap()
     *     // Thread is now blocked until the message is received
     *     println("...message received!")
     * })
     * val msg = receiver.recv().unwrap()
     * check(1 == msg)
     * ```
     */
    public open fun send(t: T): Result<Unit, SendError<T>> = this.inner.send(t)

    /**
     * Attempts to send a value on this channel without blocking.
     *
     * This method differs from [send] by returning immediately if the
     * channel's buffer is full or no receiver is waiting to acquire some
     * data. Compared with [send], this function has two failure cases
     * instead of one (one for disconnection, one for a full buffer).
     *
     * See [send] for notes about guarantees of whether the
     * receiver has received the data or not if this function is successful.
     *
     * [send]: Self.send
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.syncChannel
     * import std.thread
     *
     * // Create a syncChannel with buffer size 1
     * val (syncSender, receiver) = syncChannel(1)
     * val syncSender2 = syncSender.clone()
     * // First thread owns syncSender
     * val handle1 = thread.spawn({
     *     syncSender.send(1).unwrap()
     *     syncSender.send(2).unwrap()
     * })
     * // Second thread owns syncSender2
     * val handle2 = thread.spawn({
     *     // This will return an error and send
     *     // no message if the buffer is full
     *     syncSender2.trySend(3)
     * })
     * var msg: Any?
     * msg = receiver.recv().unwrap()
     * println("message $msg received")
     * msg = receiver.recv().unwrap()
     * println("message $msg received")
     * // Third message may have never been sent
     * run {
     *     val matchVal = receiver.tryRecv()
     *     if (matchVal.isSuccess) {
     *         val msg1 = matchVal.getOrThrow()
     *         println("message $msg received")
     *     } else {
     *         println("the third message was never sent")
     *     }
     * }
     * // Wait for threads to complete
     * handle1.join().unwrap()
     * handle2.join().unwrap()
     * ```
     */
    public open fun trySend(t: T): Result<Unit, TrySendError<T>> = this.inner.trySend(t)

    // Attempts to send for a value on this receiver, returning an error if the
    // corresponding channel has hung up, or if it waits more than `timeout`.
    //
    // This method is currently only used for tests.
    public open fun sendTimeout(t: T, timeout: Duration): Result<Unit, SendTimeoutError<T>> = this.inner.sendTimeout(t, timeout)

    /**
     * Returns `true` if the channel is empty.
     *
     * Note: Zero-capacity channels are always empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     * import std.thread
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (send, recv) = mpsc.syncChannel(1)
     *     val tx1 = send.clone()
     *     val tx2 = send.clone()
     *     check(tx1.isEmpty())
     *     val handle = thread.spawn({
     *         tx2.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(!tx1.isEmpty())
     * }
     * ```
     */
    public open fun isEmpty(): Boolean = this.inner.isEmpty()

    /**
     * Returns the number of messages in the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc
     *
     * @feature(mpscChannelLen)
     * public fun doctestEntry() {
     *     val (tx, rx) = mpsc.syncChannel(2)
     *     check(tx.len() == 0)
     *     tx.send(1u.toUByte()).unwrap()
     *     tx.send(2u.toUByte()).unwrap()
     *     check(tx.len() == 2)
     *     rx.recv().unwrap()
     *     check(tx.len() == 1)
     * }
     * ```
     */
    public open fun len(): Int = this.inner.len()

    public open fun clone(): SyncSender<T> = SyncSender(inner = this.inner.clone())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "SyncSender").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

/**
 * An error returned from the [Sender.send] or [SyncSender.send]
 * function on **channel**s.
 *
 * A **send** operation can only fail if the receiving end of a channel is
 * disconnected, implying that the data could never be received. The error
 * contains the data being sent as a payload so it can be recovered.
 */
public open class SendError<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): SendError<T> = SendError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "SendError").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = "sending on a closed channel".fmt(f)

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<SendError<T>> =
            object : TypeLayout<SendError<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): SendError<T> =
                    SendError(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SendError<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SendError<T> =
                    SendError(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SendError<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * An error returned from the [recv] function on a [Receiver].
 *
 * The [recv] operation can only fail if the sending half of a
 * [channel] (or [syncChannel]) is disconnected, implying that no further
 * messages will ever be received.
 *
 * [recv]: Receiver.recv
 */
public open class RecvError {
    public open fun clone(): RecvError = RecvError()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = "receiving on a closed channel".fmt(f)

    public companion object {
        public fun typeLayout(): TypeLayout<RecvError> =
            object : TypeLayout<RecvError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): RecvError =
                    RecvError()
                override fun writeNative(addr: Long, value: RecvError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): RecvError =
                    RecvError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: RecvError) {
                }
            }
    }
}

/**
 * This enumeration is the list of the possible reasons that [tryRecv] could
 * not return data when called. This can occur with both a [channel] and
 * a [syncChannel].
 *
 * [tryRecv]: Receiver.tryRecv
 */
public sealed class TryRecvError {
    /**
     * This **channel** is currently empty, but the **Sender**(s) have not yet
     * disconnected, so data may yet become available.
     */
    public data object Empty : TryRecvError()
    /**
     * The **channel**'s sending half has become disconnected, and there will
     * never be any more data received on it.
     */
    public data object Disconnected : TryRecvError()

    public fun rustEquals(other: TryRecvError): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Empty -> 0
        Disconnected -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            TryRecvError.Empty -> "receiving on an empty channel".fmt(f)
            TryRecvError.Disconnected -> "receiving on a closed channel".fmt(f)
        }

    public companion object {
        /**
         * Converts a `RecvError` into a `TryRecvError`.
         *
         * This conversion always returns `TryRecvError.Disconnected`.
         *
         * No data is allocated on the heap.
         */
        public fun from(err: RecvError): TryRecvError =
            when (err) {
                RecvError -> TryRecvError.Disconnected
            }
    }
}

/**
 * This enumeration is the list of possible errors that made [recvTimeout]
 * unable to return data when called. This can occur with both a [channel] and
 * a [syncChannel].
 *
 * [recvTimeout]: Receiver.recvTimeout
 */
public sealed class RecvTimeoutError {
    /**
     * This **channel** is currently empty, but the **Sender**(s) have not yet
     * disconnected, so data may yet become available.
     */
    public data object Timeout : RecvTimeoutError()
    /**
     * The **channel**'s sending half has become disconnected, and there will
     * never be any more data received on it.
     */
    public data object Disconnected : RecvTimeoutError()

    public fun rustEquals(other: RecvTimeoutError): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Timeout -> 0
        Disconnected -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            RecvTimeoutError.Timeout -> "timed out waiting on channel".fmt(f)
            RecvTimeoutError.Disconnected -> "channel is empty and sending half is closed".fmt(f)
        }

    public companion object {
        /**
         * Converts a `RecvError` into a `RecvTimeoutError`.
         *
         * This conversion always returns `RecvTimeoutError.Disconnected`.
         *
         * No data is allocated on the heap.
         */
        public fun from(err: RecvError): RecvTimeoutError =
            when (err) {
                RecvError -> RecvTimeoutError.Disconnected
            }
    }
}

/**
 * This enumeration is the list of the possible error outcomes for the
 * [trySend] method.
 *
 * [trySend]: SyncSender.trySend
 */
public sealed class TrySendError<T> {
    /**
     * The data could not be sent on the [syncChannel] because it would require that
     * the callee block to send the data.
     *
     * If this is a buffered channel, then the buffer is full at this time. If
     * this is not a buffered channel, then there is no [Receiver] available to
     * acquire the data.
     */
    public data class Full<T>(public var value: T) : TrySendError<T>()
    /**
     * This [syncChannel]s receiving half has disconnected, so the data could not be
     * sent. The data is returned back to the callee in this case.
     */
    public data class Disconnected<T>(public var value: T) : TrySendError<T>()

    public fun rustEquals(other: TrySendError<T>): Boolean = this == other

    /**
     * Consumes the error and returns the inner value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpsc.syncChannel
     *
     * @feature(trySendErrorIntoInner)
     * public fun doctestEntry() {
     *     val (tx, rx) = syncChannel(0)
     *     when (val matchVal = tx.trySend("message")) {
     *         is Err -> {
     *             val (err) = matchVal
     *             val msg: String = err.intoInner()
     *             check(msg == "message")
     *         }
     *         else -> {}
     *     }
     * }
     * ```
     */
    public open fun intoInner(): T =
        when (this) {
            is TrySendError.Full<*> -> {
                val (inner) = this as TrySendError.Full<T>
                inner
            }
            is TrySendError.Disconnected<*> -> {
                val (inner1) = this as TrySendError.Disconnected<T>
                inner1
            }
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TrySendError.Full<*> -> run { DebugTuple(f, "TrySendError::Full").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
            is TrySendError.Disconnected<*> -> {
                run { DebugTuple(f, "TrySendError::Disconnected").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
            }
        }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TrySendError.Full<*> -> "sending on a full channel".fmt(f)
            is TrySendError.Disconnected<*> -> "sending on a closed channel".fmt(f)
        }

    public companion object {
        /**
         * Converts a `SendError<T>` into a `TrySendError<T>`.
         *
         * This conversion always returns a `TrySendError.Disconnected` containing the data in the `SendError<T>`.
         *
         * No data is allocated on the heap.
         */
        public fun <T> from(err: SendError<T>): TrySendError<T> =
            when (err) {
                is SendError -> {
                    val (t) = err
                    TrySendError.Disconnected(t)
                }
            }
    }
}

/**
 * Creates a new asynchronous channel, returning the sender/receiver halves.
 *
 * All data sent on the [Sender] will become available on the [Receiver] in
 * the same order as it was sent, and no [send] will block the calling thread
 * (this channel has an "infinite buffer", unlike [syncChannel], which will
 * block after its buffer limit is reached). [recv] will block until a message
 * is available while there is at least one [Sender] alive (including clones).
 *
 * The [Sender] can be cloned to [send] to the same channel multiple times, but
 * only one [Receiver] is supported.
 *
 * If the [Receiver] is disconnected while trying to [send] with the
 * [Sender], the [send] method will return a [SendError]. Similarly, if the
 * [Sender] is disconnected while trying to [recv], the [recv] method will
 * return a [RecvError].
 *
 * [send]: Sender.send
 * [recv]: Receiver.recv
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.channel
 * import std.thread
 *
 * val (sender, receiver) = channel()
 * // Spawn off an expensive computation
 * thread.spawn({
 *     sender.send(expensiveComputation()).unwrap()
 * })
 * // Do some useful work for a while
 * // Let's see what that answer was
 * println("${receiver.recv().unwrap()}")
 * ```
 */
public fun <T> channel(): Pair<Sender<T>, Receiver<T>> {
    val (tx, rx) = io.github.kotlinmania.rustlang.std.sync.mpmc.channel()
    return Pair(Sender(inner = tx), Receiver(inner = rx))
}

/**
 * Creates a new synchronous, bounded channel.
 *
 * All data sent on the [SyncSender] will become available on the [Receiver]
 * in the same order as it was sent. Like asynchronous [channel]s, the
 * [Receiver] will block until a message becomes available. `syncChannel`
 * differs greatly in the semantics of the sender, however.
 *
 * This channel has an internal buffer on which messages will be queued.
 * `bound` specifies the buffer size. When the internal buffer becomes full,
 * future sends will *block* waiting for the buffer to open up. Note that a
 * buffer size of 0 is valid, in which case this becomes "rendezvous channel"
 * where each [send] will not return until a [recv] is paired with it.
 *
 * The [SyncSender] can be cloned to [send] to the same channel multiple
 * times, but only one [Receiver] is supported.
 *
 * Like asynchronous channels, if the [Receiver] is disconnected while trying
 * to [send] with the [SyncSender], the [send] method will return a
 * [SendError]. Similarly, If the [SyncSender] is disconnected while trying
 * to [recv], the [recv] method will return a [RecvError].
 *
 * [send]: SyncSender.send
 * [recv]: Receiver.recv
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.mpsc.syncChannel
 * import std.thread
 *
 * val (sender, receiver) = syncChannel(1)
 * // this returns immediately
 * sender.send(1).unwrap()
 * thread.spawn({
 *     // this will block until the previous message has been received
 *     sender.send(2).unwrap()
 * })
 * check(receiver.recv().unwrap() == 1)
 * check(receiver.recv().unwrap() == 2)
 * ```
 */
public fun <T> syncChannel(bound: Int): Pair<SyncSender<T>, Receiver<T>> {
    val (tx, rx) = io.github.kotlinmania.rustlang.std.sync.mpmc.syncChannel(bound)
    return Pair(SyncSender(inner = tx), Receiver(inner = rx))
}
