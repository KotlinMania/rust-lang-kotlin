// port-lint: source sys/exit.rs
package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.libc.pause
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.errnoLocation

/**
 * Mitigation for <https://github.com/rust-lang/rust/issues/126600>
 *
 * On glibc, `libc.exit` has been observed to not always be thread-safe.
 * It is currently unclear whether that is a glibc bug or allowed by the standard.
 * To mitigate this problem, we ensure that only one
 * Rust thread calls `libc.exit` (or returns from `main`) by calling this function before
 * calling `libc.exit` (or returning from `main`).
 *
 * Technically, this is not enough to ensure soundness, since other code directly calling
 * `libc.exit` will still race with this.
 *
 * *This function does not itself call `libc.exit`.* This is so it can also be used
 * to guard returning from `main`.
 *
 * This function will return only the first time it is called in a process.
 *
 * * If it is called again on the same thread as the first call, it will abort.
 * * If it is called again on a different thread, it will wait in a loop
 *   (waiting for the process to exit).
 */
public fun uniqueThreadExit() {
    val EXITING_THREAD_ID: AtomicPtr  = AtomicPtr.new(MutPtr(0L))
    // We use the address of `errno` as a cheap and safe way to identify
    // threads. As the C standard mandates that `errno` must have thread
    // storage duration, we can rely on its address not changing over the
    // scope of the thread. Additionally, accesses to `errno` are
    // async-signal-safe, so this function is available in all imaginable
    // circumstances.
    val thisThreadId = errnoLocation()
    run {
        val matchVal = EXITING_THREAD_ID.compareExchange(MutPtr(0L), thisThreadId, Acquire, Relaxed)
        when {
            matchVal is Result.Ok<*, *> -> {
                // This is the first thread to call `uniqueThreadExit`,
                // and this is the first time it is called. Continue exiting.
            }
            matchVal is Result.Err<*, *> && ((matchVal.error).rawAddress.toULong() == (thisThreadId).rawAddress.toULong()) -> {
                val exitingThreadId = matchVal.error
                // This is the first thread to call `uniqueThreadExit`,
                // but this is the second time it is called.
                // Abort the process.
                panicNounwind("std::process::exit called re-entrantly")
            }
            else -> {
                // This is not the first thread to call `uniqueThreadExit`.
                // Pause until the process exits.
                while (true) {
                    // Safety: libc.pause is safe to call.
                    run {
                        pause()
                    }
                }
            }
        }
    }
}

public expect fun exit(status: CInt): Nothing

public fun exit(code: Int): Nothing =
    run {
        uniqueThreadExit()
        io.github.kotlinmania.rustlang.libc.exit(code)
    }
