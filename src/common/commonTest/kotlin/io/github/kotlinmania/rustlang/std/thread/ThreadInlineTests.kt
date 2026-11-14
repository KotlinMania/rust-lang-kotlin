package io.github.kotlinmania.rustlang.std.thread

// Native threads.
//
// ## The threading model
//
// An executing Rust program consists of a collection of native OS threads,
// each with their own stack and local state. Threads can be named, and
// provide some built-in support for low-level synchronization.
//
// Communication between threads can be done through
// [channels], Rust's message-passing types, along with [other forms of thread
// synchronization](../../std/sync/index.html) and shared-memory data
// structures. In particular, types that are guaranteed to be
// threadsafe are easily shared between threads using the
// atomically-reference-counted container, [Arc].
//
// Fatal logic errors in Rust cause *thread panic*, during which
// a thread will unwind the stack, running destructors and freeing
// owned resources. While not meant as a try/catch' mechanism, panics
// in Rust can nonetheless be caught (unless compiling with `panic = abort`) with
// [`catchUnwind`](../../std/panic/fn.catchUnwind.html) and recovered
// from, or alternatively be resumed with
// [`resumeUnwind`](../../std/panic/fn.resumeUnwind.html). If the panic
// is not caught the thread will exit, but the panic may optionally be
// detected from a different thread with [join]. If the main thread panics
// without the panic being caught, the application will exit with a
// non-zero exit code.
//
// When the main thread of a Rust program terminates, the entire program shuts
// down, even if other threads are still running, and any destructors on the
// remaining threads' stacks may not be executed.  However, this module
// provides convenient facilities for automatically waiting for the
// termination of a thread (i.e., join).
//
// ## Spawning a thread
//
// A new thread can be spawned using the [`thread.spawn`][spawn] function:
//
// ```kotlin
// import std.thread
//
// thread.spawn({})
// ```
//
// In this example, the spawned thread is "detached," which means that there is
// no way for the program to learn when the spawned thread completes or otherwise
// terminates.
//
// To learn when a thread completes, it is necessary to capture the [JoinHandle]
// object that is returned by the call to [spawn], which provides
// a `join` method that allows the caller to wait for the completion of the
// spawned thread:
//
// ```kotlin
// import std.thread
//
// val threadJoinHandle = thread.spawn({})
// // some work here
// val res = threadJoinHandle.join()
// ```
//
// The [join] method returns a [thread.Result] containing [Ok] of the final
// value produced by the spawned thread, or [Err] of the value given to
// a call to [panic] if the thread panicked.
//
// Note that there is no parent/child relationship between a thread that spawns a
// new thread and the thread being spawned.  In particular, the spawned thread may or
// may not outlive the spawning thread, unless the spawning thread is the main thread.
//
// ## Configuring threads
//
// A new thread can be configured before it is spawned via the [Builder] type,
// which currently allows you to set the name and stack size for the thread:
//
// ```kotlin
// import std.thread
//
// thread.Builder.new().name("thread1".toString()).spawn({
//     println("Hello, world!")
// })
// ```
//
// ## The `Thread` type
//
// Threads are represented via the [Thread] type, which you can get in one of
// two ways:
//
// * By spawning a new thread, e.g., using the [`thread.spawn`][spawn]
//   function, and calling [`thread`][JoinHandle.thread] on the [JoinHandle].
// * By requesting the current thread, using the [thread.current] function.
//
// The [thread.current] function is available even for threads not spawned
// by the APIs of this module.
//
// ## Thread-local storage
//
// This module also provides an implementation of thread-local storage for Rust
// programs. Thread-local storage is a method of storing data into a global
// variable that each thread in the program will have its own copy of.
// Threads do not share this data, so accesses do not need to be synchronized.
//
// A thread-local key owns the value it contains and will destroy the value when the
// thread exits. It is created with the [threadLocal] macro and can contain any
// value that is `static` (no borrowed pointers). It provides an accessor function,
// [with], that yields a shared reference to the value to the specified
// closure. Thread-local keys allow only shared access to values, as there would be no
// way to guarantee uniqueness if mutable borrows were allowed. Most values
// will want to make use of some form of **interior mutability** through the
// [Cell] or [RefCell] types.
//
// ## Naming threads
//
// Threads are able to have associated names for identification purposes. By default, spawned
// threads are unnamed. To specify a name for a thread, build the thread with [Builder] and pass
// the desired thread name to [Builder.name]. To retrieve the thread name from within the
// thread, use [Thread.name]. A couple of examples where the name of a thread gets used:
//
// * If a panic occurs in a named thread, the thread name will be printed in the panic message.
// * The thread name is provided to the OS where applicable (e.g., `pthreadSetnameNp` in
//   unix-like platforms).
//
// ## Stack size
//
// The default stack size is platform-dependent and subject to change.
// Currently, it is 2 MiB on all Tier-1 platforms.
//
// There are two ways to manually specify the stack size for spawned threads:
//
// * Build the thread with [Builder] and pass the desired stack size to [Builder.stackSize].
// * Set the `RUST_MIN_STACK` environment variable to an integer representing the desired stack
//   size (in bytes). Note that setting [Builder.stackSize] will override this. Be aware that
//   changes to `RUST_MIN_STACK` may be ignored after program start.
//
// Note that the stack size of the main thread is *not* determined by Rust.
//
// [join]: JoinHandle.join
// [thread.current]: current.current
// [thread.Result]: Result
// [unpark]: Thread.unpark
// [thread.parkTimeout]: parkTimeout
// [with]: LocalKey.with

