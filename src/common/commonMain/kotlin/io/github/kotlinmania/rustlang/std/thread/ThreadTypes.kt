package io.github.kotlinmania.rustlang.std.thread

import io.github.kotlinmania.rustlang.core.RustAny

/**
 * A specialized [Result] type for threads.
 *
 * Indicates the manner in which a thread exited.
 *
 * The value contained in the `Result.Err` variant
 * is the value the thread panicked with;
 * that is, the argument the `panic` macro was called with.
 * Unlike with normal errors, this value doesn't implement
 * the [`Error`](error.Error) trait.
 *
 * Thus, a sensible way to handle a thread panic is to either:
 *
 * 1. propagate the panic with [std.panic.resumeUnwind]
 * 2. or in case the thread is intended to be a subsystem boundary
 * that is supposed to isolate system-level failures,
 * match on the `Err` variant and handle the panic in an appropriate way
 *
 * A thread that completes without panicking is considered to exit successfully.
 *
 * # Examples
 *
 * Matching on the result of a joined thread:
 *
 * ```kotlin
 * import std.panic
 * import std.thread
 *
 * fun copyInThread(): thread.Result<Unit> {
 *     return thread.spawn({
 *         copy("foo.txt", "bar.txt").unwrap()
 *     }).join()
 * }
 * fun main() {
 *     run {
 *         val matchVal = copyInThread()
 *         if (matchVal.isSuccess) {
 *             println("copy succeeded")
 *         } else {
 *             val e = matchVal.exceptionOrNull()!!
 *             panic.resumeUnwind(e)
 *         }
 *     }
 * }
 * ```
 */
public typealias Result<T> = io.github.kotlinmania.rustlang.core.result.Result<T, RustAny>
