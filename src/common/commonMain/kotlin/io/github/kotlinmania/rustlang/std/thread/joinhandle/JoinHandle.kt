// port-lint: source thread/join_handle.rs
package io.github.kotlinmania.rustlang.std.thread.joinhandle

import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.thread.Result
import io.github.kotlinmania.rustlang.std.thread.lifecycle.JoinInner
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

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
 * An owned permission to join on a thread (block on its termination).
 *
 * A `JoinHandle` *detaches* the associated thread when it is dropped, which
 * means that there is no longer any handle to the thread and no way to `join`
 * on it.
 *
 * Due to platform restrictions, it is not possible to [Clone] this
 * handle: the ability to join a thread is a uniquely-owned permission.
 *
 * This `class` is created by the [thread.spawn] function and the
 * [thread.Builder.spawn] method.
 *
 * # Examples
 *
 * Creation from [thread.spawn]:
 *
 * ```kotlin
 * import std.thread
 *
 * val joinHandle: thread.JoinHandle = thread.spawn({})
 * ```
 *
 * Creation from [thread.Builder.spawn]:
 *
 * ```kotlin
 * import std.thread
 *
 * val builder = thread.Builder.new()
 * val joinHandle: thread.JoinHandle = builder.spawn({}).unwrap()
 * ```
 *
 * A thread being detached and outliving the thread that spawned it:
 *
 * ```kotlin
 * import std.thread
 * import std.time.Duration
 *
 * val originalThread = thread.spawn({
 *     val detachedThread = thread.spawn({
 *         // Here we sleep to make sure that the first thread returns before.
 *         thread.sleep(Duration.fromMillis(10))
 *         // This will be called, even though the JoinHandle is dropped.
 *         println("♫ Still alive ♫")
 *     })
 * })
 * originalThread.join().expect("The thread being joined has panicked")
 * println("Original thread is joined.")
 * // We make sure that the new thread has time to run, before the main
 * // thread returns.
 * thread.sleep(Duration.fromMillis(1000))
 * ```
 */
public open class JoinHandle<T>(
    internal var value: JoinInner<T>,
) : AsInner<Thread>, IntoInner<Thread> {
    public var field0: JoinInner<T>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Extracts a handle to the underlying thread.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new()
     * val joinHandle: thread.JoinHandle = builder.spawn({}).unwrap()
     * val thread = joinHandle.thread()
     * println("thread id: ${thread.id()}")
     * ```
     */
    public open fun thread(): Thread = this.value.thread()

    /**
     * Waits for the associated thread to finish.
     *
     * This function will return immediately if the associated thread has already finished.
     * Otherwise, it fully waits for the thread to finish, including all destructors
     * for thread-local variables that might be running after the main function of the thread.
     *
     * In terms of [atomic memory orderings], the completion of the associated
     * thread synchronizes with this function returning. In other words, all
     * operations performed by that thread [happen
     * before](https://doc.rust-lang.org/nomicon/atomics.html#data-accesses) all
     * operations that happen after `join` returns.
     *
     * If the associated thread panics, [Err] is returned with the parameter given
     * to [panic] (though see the Notes below).
     *
     * # Panics
     *
     * This function may panic on some platforms if a thread attempts to join
     * itself or otherwise may create a deadlock with joining threads.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new()
     * val joinHandle: thread.JoinHandle = builder.spawn({}).unwrap()
     * joinHandle.join().expect("Couldn't join on the associated thread")
     * ```
     *
     * # Notes
     *
     * If a "foreign" unwinding operation (e.g. an exception thrown from C++
     * code, or a `panic` in Rust code compiled or linked with a different
     * runtime) unwinds all the way to the thread root, the process may be
     * aborted; see the Notes on [thread.spawn]. If the process is not
     * aborted, this function will return a `Result.Err` containing an opaque
     * type.
     *
     * [catchUnwind]: ../../std/panic/fn.catchUnwind.html
     */
    public open fun join(): Result<T> = this.value.join()

    /**
     * Checks if the associated thread has finished running its main function.
     *
     * `isFinished` supports implementing a non-blocking join operation, by checking
     * `isFinished`, and calling `join` if it returns `true`. This function does not block. To
     * block while waiting on the thread to finish, use [`join`][Self.join].
     *
     * This might return `true` for a brief moment after the thread's main
     * function has returned, but before the thread itself has stopped running.
     * However, once this returns `true`, [`join`][Self.join] can be expected
     * to return quickly, without blocking for any significant amount of time.
     */
    public open fun isFinished(): Boolean = this.value.isFinished()

    public override fun asInner(): Thread = this.value.asInner()

    public override fun intoInner(): Thread = this.value.intoInner()

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "JoinHandle").finishNonExhaustive()
    }
}
