package io.github.kotlinmania.rustlang.std.sync.mpmc

// Multi-producer, multi-consumer FIFO queue communication primitives.
//
// This module provides message-based communication over channels, concretely
// defined by two types:
//
// * [Sender]
// * [Receiver]
//
// [Sender]s are used to send data to a set of [Receiver]s where each item
// sent is delivered to (at most) one receiver. Both sender and receiver are
// cloneable (multi-producer) such that many threads can send simultaneously
// to receivers (multi-consumer).
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
//    return a `(Sender, Receiver)` tuple where the storage for pending
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
// import std.thread
//
// @feature(mpmcChannel)
// public fun doctestEntry() {
//     // Create a simple streaming channel
//     val (tx, rx) = channel()
//     thread.spawn({
//         tx.send(10).unwrap()
//     })
//     check(rx.recv().unwrap() == 10)
// }
// ```
//
// Shared usage:
//
// ```kotlin
// import std.thread
//
// @feature(mpmcChannel)
// public fun doctestEntry() {
//     thread.scope({ s ->
//         // Create a shared channel that can be sent along from many threads
//         // where tx is the sending half (tx for transmission), and rx is the receiving
//         // half (rx for receiving).
//         val (tx, rx) = channel()
//         for (i in 0 until 10) {
//             val tx1 = tx.clone()
//             s.spawn({
//                 tx1.send(i).unwrap()
//             })
//         }
//         for (unused in 0 until 5) {
//             val rx1 = rx.clone()
//             val rx2 = rx.clone()
//             s.spawn({
//                 val j = rx1.recv().unwrap()
//                 check(0 <= j && j < 10)
//             })
//             s.spawn({
//                 val j = rx2.recv().unwrap()
//                 check(0 <= j && j < 10)
//             })
//         }
//     })
// }
// ```
//
// Propagating panics:
//
// ```kotlin
// @feature(mpmcChannel)
// public fun doctestEntry() {
//     // The call to recv() will return an error because the channel has already
//     // hung up (or been deallocated)
//     val (tx, rx) = channel<Int>()
//     (tx).let { }
//     check(rx.recv().isErr())
// }
// ```

