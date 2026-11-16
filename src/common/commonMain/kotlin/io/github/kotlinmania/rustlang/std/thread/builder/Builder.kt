// port-lint: source thread/builder.rs
package io.github.kotlinmania.rustlang.std.thread.builder

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.thread.joinhandle.JoinHandle
import io.github.kotlinmania.rustlang.std.thread.lifecycle.spawnUnchecked

/**
 * Thread factory, which can be used in order to configure the properties of
 * a new thread.
 *
 * Methods can be chained on it in order to configure it.
 *
 * The two configurations available are:
 *
 * - [name]: specifies an [associated name for the thread][naming-threads]
 * - [stackSize]: specifies the [desired stack size for the thread][stack-size]
 *
 * The [spawn] method will take ownership of the builder and create an
 * [io.Result] to the thread handle with the given configuration.
 *
 * The [thread.spawn] free function uses a `Builder` with default
 * configuration and [unwrap]s its return value.
 *
 * You may want to use [spawn] instead of [thread.spawn], when you want
 * to recover from a failure to launch a thread, indeed the free function will
 * panic where the `Builder` method will return a [io.Result].
 *
 * # Examples
 *
 * ```kotlin
 * import std.thread
 *
 * val builder = thread.Builder.new()
 * val handler = builder.spawn({}).unwrap()
 * handler.join().unwrap()
 * ```
 *
 * [stackSize]: Builder.stackSize
 * [name]: Builder.name
 * [spawn]: Builder.spawn
 * [naming-threads]: ./index.html#naming-threads
 * [stack-size]: ./index.html#stack-size
 */
public open class Builder(
    /**
     * A name for the thread-to-be, for identification in panic messages
     */
    public var name: String?,
    /**
     * The size of the stack for the spawned thread in bytes
     */
    public var stackSize: Int?,
    /**
     * Skip running and inheriting the thread spawn hooks
     */
    public var noHooks: Boolean,
) {
    /**
     * Names the thread-to-be. Currently the name is used for identification
     * only in panic messages.
     *
     * The name must not contain null bytes (`\0`).
     *
     * For more information about named threads, see
     * [this module-level documentation][naming-threads].
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new().name("foo".into())
     * val handler = builder.spawn({
     *     check(thread.current().name() == "foo")
     * }).unwrap()
     * handler.join().unwrap()
     * ```
     *
     * [naming-threads]: ./index.html#naming-threads
     */
    public open fun name(name: String): Builder {
        this.name = name
        return this
    }

    /**
     * Sets the size of the stack (in bytes) for the new thread.
     *
     * The actual stack size may be greater than this value if
     * the platform specifies a minimal stack size.
     *
     * For more information about the stack size for threads, see
     * [this module-level documentation][stack-size].
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new().stackSize(32 * 1024)
     * ```
     *
     * [stack-size]: ./index.html#stack-size
     */
    public open fun stackSize(size: Int): Builder {
        this.stackSize = size
        return this
    }

    /**
     * Disables running and inheriting [spawn hooks].
     *
     * Use this if the parent thread is in no way relevant for the child thread.
     * For example, when lazily spawning threads for a thread pool.
     */
    public open fun noHooks(): Builder {
        this.noHooks = true
        return this
    }

    // even without panics, this helps for Miri backtraces

    /**
     * Spawns a new thread by taking ownership of the `Builder`, and returns an
     * [io.Result] to its [JoinHandle].
     *
     * The spawned thread may outlive the caller (unless the caller thread
     * is the main thread; the whole process is terminated when the main
     * thread finishes). The join handle can be used to block on
     * termination of the spawned thread, including recovering its panics.
     *
     * For a more complete documentation see [thread.spawn].
     *
     * # Errors
     *
     * Unlike the [spawn] free function, this method yields an
     * [io.Result] to capture any failure to create the thread at
     * the OS level.
     *
     * # Panics
     *
     * Panics if a thread name was set and it contained null bytes.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new()
     * val handler = builder.spawn({}).unwrap()
     * handler.join().unwrap()
     * ```
     */
    public open fun <T> spawn(f: () -> T): Result<JoinHandle<T>, Error> = this.spawnUnchecked(f)

    // even without panics, this helps for Miri backtraces

    /**
     * Spawns a new thread without any scope restrictions by taking ownership
     * of the `Builder`, and returns an [io.Result] to its [JoinHandle].
     *
     * The spawned thread may outlive the caller (unless the caller thread
     * is the main thread; the whole process is terminated when the main
     * thread finishes). The join handle can be used to block on
     * termination of the spawned thread, including recovering its panics.
     *
     * This method is identical to [`thread.Builder.spawn`][Builder.spawn],
     * except for the relaxed scope bounds, which render it unchecked.
     * For a more complete documentation see [thread.spawn].
     *
     * # Errors
     *
     * Unlike the [spawn] free function, this method yields an
     * [io.Result] to capture any failure to create the thread at
     * the OS level.
     *
     * # Panics
     *
     * Panics if a thread name was set and it contained null bytes.
     *
     * # Safety
     *
     * The caller has to ensure that the spawned thread does not outlive any
     * references in the supplied thread closure and its return type.
     * This can be guaranteed in two ways:
     *
     * - ensure that [`join`][JoinHandle.join] is called before any referenced
     * data is dropped
     * - use only types with `static` scope bounds, i.e., those with no or only
     * `static` references (both [`thread.Builder.spawn`][Builder.spawn]
     * and [thread.spawn] enforce this property statically)
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new()
     * val x = 1
     * val threadX = x
     * val handler = builder.spawnUnchecked({
     *     println("x = $threadX")
     * }).unwrap()
     * // caller has to ensure `join()` is called, otherwise
     * // it is possible to access freed memory if `x` gets
     * // dropped before the thread closure is executed!
     * handler.join().unwrap()
     * ```
     */
    public open fun <T> spawnUnchecked(f: () -> T): Result<JoinHandle<T>, Error> {
        val name = this.name
        val stackSize = this.stackSize
        val noHooks = this.noHooks
        return Result.Ok<JoinHandle<T>, Error>(JoinHandle(when (val tryResult = spawnUnchecked(name, stackSize, noHooks, null, f)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<JoinHandle<T>, Error>(tryResult.error) }))
    }

    public companion object {
        /**
         * Generates the base configuration for spawning a thread, from which
         * configuration methods can be chained.
         *
         * # Examples
         *
         * ```kotlin
         * import std.thread
         *
         * val builder = thread.Builder.new().name("foo".into()).stackSize(32 * 1024)
         * val handler = builder.spawn({}).unwrap()
         * handler.join().unwrap()
         * ```
         */
        public fun new(): Builder = Builder(name = null, stackSize = null, noHooks = false)
    }
}
