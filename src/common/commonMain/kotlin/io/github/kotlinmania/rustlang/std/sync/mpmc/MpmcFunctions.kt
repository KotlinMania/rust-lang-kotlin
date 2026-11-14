package io.github.kotlinmania.rustlang.std.sync.mpmc

import io.github.kotlinmania.rustlang.std.sync.mpmc.counter.new
import io.github.kotlinmania.rustlang.std.sync.mpmc.list.Channel

/**
 * Creates a new asynchronous channel, returning the sender/receiver halves.
 *
 * All data sent on the [Sender] will become available on the [Receiver] in
 * the same order as it was sent, and no [send] will block the calling thread
 * (this channel has an "infinite buffer", unlike [syncChannel], which will
 * block after its buffer limit is reached). [recv] will block until a message
 * is available while there is at least one [Sender] alive (including clones).
 *
 * The [Sender] can be cloned to [send] to the same channel multiple times.
 * The [Receiver] also can be cloned to have multi receivers.
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
 * import std.thread
 *
 * @feature(mpmcChannel)
 * public fun doctestEntry() {
 *     val (sender, receiver) = channel()
 *     // Spawn off an expensive computation
 *     thread.spawn({
 *         sender.send(expensiveComputation()).unwrap()
 *     })
 *     // Do some useful work for a while
 *     // Let's see what that answer was
 *     println("${receiver.recv().unwrap()}")
 * }
 * ```
 */
public fun <T> channel(): Pair<Sender<T>, Receiver<T>> {
    val (s, r) = new(Channel.new())
    val s1 = Sender(flavor = SenderFlavor.List(s))
    val r1 = Receiver(flavor = ReceiverFlavor.List(r))
    return Pair(s1, r1)
}

/**
 * Creates a new synchronous, bounded channel.
 *
 * All data sent on the [Sender] will become available on the [Receiver]
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
 * The [Sender] can be cloned to [send] to the same channel multiple
 * times. The [Receiver] also can be cloned to have multi receivers.
 *
 * Like asynchronous channels, if the [Receiver] is disconnected while trying
 * to [send] with the [Sender], the [send] method will return a
 * [SendError]. Similarly, If the [Sender] is disconnected while trying
 * to [recv], the [recv] method will return a [RecvError].
 *
 * [send]: Sender.send
 * [recv]: Receiver.recv
 *
 * # Examples
 *
 * ```kotlin
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
public fun <T> syncChannel(cap: Int): Pair<Sender<T>, Receiver<T>> =
    if (cap == 0) {
        val (s, r) = new(io.github.kotlinmania.rustlang.std.sync.mpmc.zero.Channel.new())
        val s1 = Sender(flavor = SenderFlavor.Zero(s))
        val r1 = Receiver(flavor = ReceiverFlavor.Zero(r))
        Pair(s1, r1)
    } else {
        val (s2, r2) = new(io.github.kotlinmania.rustlang.std.sync.mpmc.array.Channel.withCapacity(cap))
        val s3 = Sender(flavor = SenderFlavor.Array(s2))
        val r3 = Receiver(flavor = ReceiverFlavor.Array(r2))
        Pair(s3, r3)
    }
