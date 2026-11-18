package io.github.kotlinmania.rustlang.std.sync.mpmc

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvError
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpsc.SendError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TryRecvError
import io.github.kotlinmania.rustlang.std.sync.mpsc.TrySendError
import io.github.kotlinmania.rustlang.std.time.Instant

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
 * The sending-half of Rust's synchronous [channel] type.
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
 * import std.thread
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = channel()
 *     val sender2 = sender.clone()
 *     // First thread owns sender
 *     thread.spawn({
 *         sender.send(1).unwrap()
 *     })
 *     // Second thread owns sender2
 *     thread.spawn({
 *         sender2.send(2).unwrap()
 *     })
 *     val msg = receiver.recv().unwrap()
 *     val msg2 = receiver.recv().unwrap()
 *     check(3 == msg + msg2)
 * }
 * ```
 */
public open class Sender<T>(
    public var flavor: SenderFlavor<T>,
) {
    /**
     * Attempts to send a message into the channel without blocking.
     *
     * This method will either send a message into the channel immediately or return an error if
     * the channel is full or disconnected. The returned error contains the original message.
     *
     * If called on a zero-capacity channel, this method will send the message only if there
     * happens to be a receive operation on the other side of the channel at the same time.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (sender: Sender<Int>, receiver: Receiver<Int>) = channel()
     *     check(sender.trySend(1).isOk())
     * }
     * ```
     */
    public open fun trySend(msg: T): Result<Unit, TrySendError<T>> =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.trySend(msg)
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.trySend(msg)
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.trySend(msg)
            }
        }

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
     * hang up immediately after this function returns [Ok]. However, if
     * the channel is zero-capacity, it acts as a rendezvous channel and a
     * return value of [Ok] means that the data has been received.
     *
     * If the channel is full and not disconnected, this call will block until
     * the send operation can proceed. If the channel becomes disconnected,
     * this call will wake up and return an error. The returned error contains
     * the original message.
     *
     * If called on a zero-capacity channel, this method will wait for a receive
     * operation to appear on the other side of the channel.
     *
     * If called on an unbounded channel, this method will never block in order to wait for space to
     * become available. (See [std.sync] for precise guarantees on blocking.)
     *
     * [std.sync]: ../index.html#blocking-guarantees
     *
     * # Examples
     *
     * ```kotlin
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel()
     *     // This send is always successful
     *     tx.send(1).unwrap()
     *     // This send will fail because the receiver is gone
     *     (rx).let { }
     *     check(tx.send(1).isErr())
     * }
     * ```
     */
    public open fun send(msg: T): Result<Unit, SendError<T>> =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.send(msg, null)
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.send(msg, null)
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.send(msg, null)
            }
        }.mapErr({ err -> when (err) {
            is SendTimeoutError.Disconnected<*> -> {
                val (msg1) = err
                SendError(msg1)
            }
            is SendTimeoutError.Timeout<*> -> error("unreachable")
        } })

    /**
     * Waits for a message to be sent into the channel, but only for a limited time.
     *
     * If the channel is full and not disconnected, this call will block until the send operation
     * can proceed or the operation times out. If the channel becomes disconnected, this call will
     * wake up and return an error. The returned error contains the original message.
     *
     * If called on a zero-capacity channel, this method will wait for a receive operation to
     * appear on the other side of the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import time.Duration
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel()
     *     tx.sendTimeout(1, Duration.fromMillis(400)).unwrap()
     * }
     * ```
     */
    public open fun sendTimeout(msg: T, timeout: Duration): Result<Unit, SendTimeoutError<T>> =
        when (val matchVal = Instant.now().checkedAdd(timeout)) {
            // So far in the future that it's practically the same as waiting indefinitely.
            null -> this.send(msg).mapErr(SendTimeoutError::from)
            else -> {
                val deadline = matchVal
                this.sendDeadline(msg, deadline)
            }
        }

    /**
     * Waits for a message to be sent into the channel, but only until a given deadline.
     *
     * If the channel is full and not disconnected, this call will block until the send operation
     * can proceed or the operation times out. If the channel becomes disconnected, this call will
     * wake up and return an error. The returned error contains the original message.
     *
     * If called on a zero-capacity channel, this method will wait for a receive operation to
     * appear on the other side of the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import time.Duration
     * import time.Instant
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel()
     *     val t = Instant.now() + Duration.fromMillis(400)
     *     tx.sendDeadline(1, t).unwrap()
     * }
     * ```
     */
    public open fun sendDeadline(msg: T, deadline: Instant): Result<Unit, SendTimeoutError<T>> =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.send(msg, deadline)
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.send(msg, deadline)
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.send(msg, deadline)
            }
        }

    /**
     * Returns `true` if the channel is empty.
     *
     * Note: Zero-capacity channels are always empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
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
    public open fun isEmpty(): Boolean =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isEmpty()
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isEmpty()
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isEmpty()
            }
        }

    /**
     * Returns `true` if the channel is full.
     *
     * Note: Zero-capacity channels are always full.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.syncChannel(1)
     *     val (tx1, tx2) = Pair(send.clone(), send.clone())
     *     check(!tx1.isFull())
     *     val handle = thread.spawn({
     *         tx2.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(tx1.isFull())
     * }
     * ```
     */
    public open fun isFull(): Boolean =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isFull()
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isFull()
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isFull()
            }
        }

    /**
     * Returns the number of messages in the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
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
    public open fun len(): Int =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.len()
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.len()
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.len()
            }
        }

    /**
     * If the channel is bounded, returns its capacity.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.syncChannel(3)
     *     val (tx1, tx2) = Pair(send.clone(), send.clone())
     *     check(tx1.capacity() == 3)
     *     val handle = thread.spawn({
     *         tx2.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(tx1.capacity() == 3)
     * }
     * ```
     */
    public open fun capacity(): Int? =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.capacity()
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.capacity()
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.capacity()
            }
        }

    /**
     * Returns `true` if senders belong to the same channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx1, _) = mpmc.channel<Int>()
     *     val (tx2, _) = mpmc.channel<Int>()
     *     check(tx1.sameChannel(tx1))
     *     check(!tx1.sameChannel(tx2))
     * }
     * ```
     */
    public open fun sameChannel(other: Sender<T>): Boolean =
        run {
            val matchVal = Pair(this.flavor, other.flavor)
            when {
                matchVal.first is SenderFlavor.Array<*> && matchVal.second is SenderFlavor.Array<*> -> {
                    val (a) = matchVal.first as SenderFlavor.Array<T>
                    val (b) = matchVal.second as SenderFlavor.Array<T>
                    a == b
                }
                matchVal.first is SenderFlavor.List<*> && matchVal.second is SenderFlavor.List<*> -> {
                    val (a1) = matchVal.first as SenderFlavor.List<T>
                    val (b1) = matchVal.second as SenderFlavor.List<T>
                    a1 == b1
                }
                matchVal.first is SenderFlavor.Zero<*> && matchVal.second is SenderFlavor.Zero<*> -> {
                    val (a2) = matchVal.first as SenderFlavor.Zero<T>
                    val (b2) = matchVal.second as SenderFlavor.Zero<T>
                    a2 == b2
                }
                else -> false
            }
        }

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
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel<Int>()
     *     check(!tx.isDisconnected())
     *     (rx).let { }
     *     check(tx.isDisconnected())
     * }
     * ```
     */
    public open fun isDisconnected(): Boolean =
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isDisconnected()
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isDisconnected()
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isDisconnected()
            }
        }

    public open fun drop() {
        when (val matchVal = this.flavor) {
            is SenderFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.release({ c -> c.disconnectSenders() })
            }
            is SenderFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.release({ c -> c.disconnectSenders() })
            }
            is SenderFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.release({ c -> c.disconnect() })
            }
        }
    }

    public open fun clone(): Sender<T> {
        val flavor =
            when (val matchVal = this.flavor) {
                is SenderFlavor.Array<*> -> {
                    val (chan) = matchVal
                    SenderFlavor.Array(chan.acquire())
                }
                is SenderFlavor.List<*> -> {
                    val (chan1) = matchVal
                    SenderFlavor.List(chan1.acquire())
                }
                is SenderFlavor.Zero<*> -> {
                    val (chan2) = matchVal
                    SenderFlavor.Zero(chan2.acquire())
                }
            }
        return Sender(flavor = flavor)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Sender").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
}

/**
 * The receiving half of Rust's [channel] (or [syncChannel]) type.
 * Different threads can share this [Receiver] by cloning it.
 *
 * Messages sent to the channel can be retrieved using [recv].
 *
 * [recv]: Receiver.recv
 *
 * # Examples
 *
 * ```kotlin
 * import std.thread
 * import time.Duration
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (send, recv) = channel()
 *     val txThread = thread.spawn({
 *         send.send("Hello world!").unwrap()
 *         thread.sleep(Duration.fromSecs(2))
 *         // block for two seconds
 *         send.send("Delayed for 2 seconds").unwrap()
 *     })
 *     val (rx1, rx2) = Pair(recv.clone(), recv.clone())
 *     val rxThread1 = thread.spawn({
 *         println("${rx1.recv().unwrap()}")
 *     })
 *     val rxThread2 = thread.spawn({
 *         println("${rx2.recv().unwrap()}")
 *     })
 *     txThread.join().unwrap()
 *     rxThread1.join().unwrap()
 *     rxThread2.join().unwrap()
 * }
 * ```
 */
public open class Receiver<T>(
    public var flavor: ReceiverFlavor<T>,
) : Iterable<T> {
    override fun iterator(): Iterator<T> = this.intoIter().iterator()

    public open fun intoIter(): Iter<T> = this.iter()

    public open fun intoIterIntoIterator(): IntoIter<T> = IntoIter(rx = this)

    /**
     * Attempts to receive a message from the channel without blocking.
     *
     * This method will never block the caller in order to wait for data to
     * become available. Instead, this will always return immediately with a
     * possible option of pending data on the channel. (See [std.sync] for precise
     * guarantees on blocking.)
     *
     * If called on a zero-capacity channel, this method will receive a message only if there
     * happens to be a send operation on the other side of the channel at the same time.
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
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (_, receiver: Receiver<Int>) = channel()
     *     check(receiver.tryRecv().isErr())
     * }
     * ```
     */
    public open fun tryRecv(): Result<T, TryRecvError> =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.tryRecv()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.tryRecv()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.tryRecv()
            }
        }

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent (at least one sender
     * still exists). Once a message is sent to the corresponding [Sender],
     * this receiver will wake up and return that message.
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
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(Result.success(1) == recv.recv())
     * }
     * ```
     *
     * Buffering behavior:
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.sync.mpmc.RecvError
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *         send.send(2).unwrap()
     *         send.send(3).unwrap()
     *         (send).let { }
     *     })
     *     // wait for the thread to join so we ensure the sender is dropped
     *     handle.join().unwrap()
     *     check(Result.success(1) == recv.recv())
     *     check(Result.success(2) == recv.recv())
     *     check(Result.success(3) == recv.recv())
     *     check(Result.failure(RecvError) == recv.recv())
     * }
     * ```
     */
    public open fun recv(): Result<T, RecvError> =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.recv(null)
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.recv(null)
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.recv(null)
            }
        }.mapErr({ RecvError })

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up, or if it waits more than `timeout`.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent (at least one sender
     * still exists). Once a message is sent to the corresponding [Sender],
     * this receiver will wake up and return that message.
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
     * import std.sync.mpmc
     * import std.thread
     * import time.Duration
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     thread.spawn({
     *         send.send('a').unwrap()
     *     })
     *     check(recv.recvTimeout(Duration.fromMillis(400)) == Result.success('a'))
     * }
     * ```
     *
     * Receiving an error upon reaching timeout:
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     * import time.Duration
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(800))
     *         send.send('a').unwrap()
     *     })
     *     check(recv.recvTimeout(Duration.fromMillis(400)) == Result.failure(mpmc.RecvTimeoutError.Timeout))
     * }
     * ```
     */
    public open fun recvTimeout(timeout: Duration): Result<T, RecvTimeoutError> =
        when (val matchVal = Instant.now().checkedAdd(timeout)) {
            // So far in the future that it's practically the same as waiting indefinitely.
            null -> this.recv().mapErr(RecvTimeoutError::from)
            else -> {
                val deadline = matchVal
                this.recvDeadline(deadline)
            }
        }

    /**
     * Attempts to wait for a value on this receiver, returning an error if the
     * corresponding channel has hung up, or if `deadline` is reached.
     *
     * This function will always block the current thread if there is no data
     * available and it's possible for more data to be sent. Once a message is
     * sent to the corresponding [Sender], then this receiver will wake up
     * and return that message.
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
     * import std.sync.mpmc
     * import std.thread
     * import time.Duration
     * import time.Instant
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
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
     * import std.sync.mpmc
     * import std.thread
     * import time.Duration
     * import time.Instant
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     thread.spawn({
     *         thread.sleep(Duration.fromMillis(800))
     *         send.send('a').unwrap()
     *     })
     *     check(recv.recvDeadline(Instant.now() + Duration.fromMillis(400)) == Result.failure(mpmc.RecvTimeoutError.Timeout))
     * }
     * ```
     */
    public open fun recvDeadline(deadline: Instant): Result<T, RecvTimeoutError> =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.recv(deadline)
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.recv(deadline)
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.recv(deadline)
            }
        }

    /**
     * Returns an iterator that will attempt to yield all pending values.
     * It will return `None` if there are no more pending values or if the
     * channel has hung up. The iterator will never [panic] or block the
     * user by waiting for values.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     * import time.Duration
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (sender, receiver) = channel()
     *     // nothing is in the buffer yet
     *     check(receiver.tryIter().next() == null)
     *     thread.spawn({
     *         thread.sleep(Duration.fromSecs(1))
     *         sender.send(1).unwrap()
     *         sender.send(2).unwrap()
     *         sender.send(3).unwrap()
     *     })
     *     // nothing is in the buffer yet
     *     check(receiver.tryIter().next() == null)
     *     // block for two seconds
     *     thread.sleep(Duration.fromSecs(2))
     *     var iter = receiver.tryIter()
     *     check(iter.next() == 1)
     *     check(iter.next() == 2)
     *     check(iter.next() == 3)
     *     check(iter.next() == null)
     * }
     * ```
     */
    public open fun tryIter(): TryIter<T> = TryIter(rx = this)

    /**
     * Returns `true` if the channel is empty.
     *
     * Note: Zero-capacity channels are always empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     check(recv.isEmpty())
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(!recv.isEmpty())
     * }
     * ```
     */
    public open fun isEmpty(): Boolean =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isEmpty()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isEmpty()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isEmpty()
            }
        }

    /**
     * Returns `true` if the channel is full.
     *
     * Note: Zero-capacity channels are always full.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.syncChannel(1)
     *     check(!recv.isFull())
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(recv.isFull())
     * }
     * ```
     */
    public open fun isFull(): Boolean =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isFull()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isFull()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isFull()
            }
        }

    /**
     * Returns the number of messages in the channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.channel()
     *     check(recv.len() == 0)
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(recv.len() == 1)
     * }
     * ```
     */
    public open fun len(): Int =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.len()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.len()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.len()
            }
        }

    /**
     * If the channel is bounded, returns its capacity.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = mpmc.syncChannel(3)
     *     check(recv.capacity() == 3)
     *     val handle = thread.spawn({
     *         send.send(1u.toUByte()).unwrap()
     *     })
     *     handle.join().unwrap()
     *     check(recv.capacity() == 3)
     * }
     * ```
     */
    public open fun capacity(): Int? =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.capacity()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.capacity()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.capacity()
            }
        }

    /**
     * Returns `true` if receivers belong to the same channel.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.mpmc
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (_, rx1) = mpmc.channel<Int>()
     *     val (_, rx2) = mpmc.channel<Int>()
     *     check(rx1.sameChannel(rx1))
     *     check(!rx1.sameChannel(rx2))
     * }
     * ```
     */
    public open fun sameChannel(other: Receiver<T>): Boolean =
        run {
            val matchVal = Pair(this.flavor, other.flavor)
            when {
                matchVal.first is ReceiverFlavor.Array<*> && matchVal.second is ReceiverFlavor.Array<*> -> {
                    val (a) = matchVal.first as ReceiverFlavor.Array<T>
                    val (b) = matchVal.second as ReceiverFlavor.Array<T>
                    a == b
                }
                matchVal.first is ReceiverFlavor.List<*> && matchVal.second is ReceiverFlavor.List<*> -> {
                    val (a1) = matchVal.first as ReceiverFlavor.List<T>
                    val (b1) = matchVal.second as ReceiverFlavor.List<T>
                    a1 == b1
                }
                matchVal.first is ReceiverFlavor.Zero<*> && matchVal.second is ReceiverFlavor.Zero<*> -> {
                    val (a2) = matchVal.first as ReceiverFlavor.Zero<T>
                    val (b2) = matchVal.second as ReceiverFlavor.Zero<T>
                    a2 == b2
                }
                else -> false
            }
        }

    /**
     * Returns an iterator that will block waiting for messages, but never
     * [panic]. It will return [None] when the channel has hung up.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (send, recv) = channel()
     *     thread.spawn({
     *         send.send(1).unwrap()
     *         send.send(2).unwrap()
     *         send.send(3).unwrap()
     *     })
     *     var iter = recv.iter()
     *     check(iter.next() == 1)
     *     check(iter.next() == 2)
     *     check(iter.next() == 3)
     *     check(iter.next() == null)
     * }
     * ```
     */
    public open fun iter(): Iter<T> = Iter(rx = this)

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
     * @feature(mpmcChannel)
     * public fun doctestEntry() {
     *     val (tx, rx) = channel<Int>()
     *     check(!rx.isDisconnected())
     *     (tx).let { }
     *     check(rx.isDisconnected())
     * }
     * ```
     */
    public open fun isDisconnected(): Boolean =
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.isDisconnected()
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.isDisconnected()
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.isDisconnected()
            }
        }

    public open fun drop() {
        when (val matchVal = this.flavor) {
            is ReceiverFlavor.Array<*> -> {
                val (chan) = matchVal
                chan.release({ c -> c.disconnectReceivers() })
            }
            is ReceiverFlavor.List<*> -> {
                val (chan1) = matchVal
                chan1.release({ c -> c.disconnectReceivers() })
            }
            is ReceiverFlavor.Zero<*> -> {
                val (chan2) = matchVal
                chan2.release({ c -> c.disconnect() })
            }
        }
    }

    public open fun clone(): Receiver<T> {
        val flavor =
            when (val matchVal = this.flavor) {
                is ReceiverFlavor.Array<*> -> {
                    val (chan) = matchVal
                    ReceiverFlavor.Array(chan.acquire())
                }
                is ReceiverFlavor.List<*> -> {
                    val (chan1) = matchVal
                    ReceiverFlavor.List(chan1.acquire())
                }
                is ReceiverFlavor.Zero<*> -> {
                    val (chan2) = matchVal
                    ReceiverFlavor.Zero(chan2.acquire())
                }
            }
        return Receiver(flavor = flavor)
    }

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
 * import std.thread
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (send, recv) = channel()
 *     thread.spawn({
 *         send.send(1u.toUByte()).unwrap()
 *         send.send(2u.toUByte()).unwrap()
 *         send.send(3u.toUByte()).unwrap()
 *     })
 *     for (x in recv.iter()) {
 *         println("Got: $x")
 *     }
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
 * import std.thread
 * import time.Duration
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = channel()
 *     // Nothing is in the buffer yet
 *     check(receiver.tryIter().next() == null)
 *     println("Nothing in the buffer...")
 *     thread.spawn({
 *         sender.send(1).unwrap()
 *         sender.send(2).unwrap()
 *         sender.send(3).unwrap()
 *     })
 *     println("Going to sleep...")
 *     thread.sleep(Duration.fromSecs(2))
 *     // block for two seconds
 *     for (x in receiver.tryIter()) {
 *         println("Got: $x")
 *     }
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
 * import std.thread
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (send, recv) = channel()
 *     thread.spawn({
 *         send.send(1u.toUByte()).unwrap()
 *         send.send(2u.toUByte()).unwrap()
 *         send.send(3u.toUByte()).unwrap()
 *     })
 *     for (x in recv.intoIter()) {
 *         println("Got: $x")
 *     }
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
