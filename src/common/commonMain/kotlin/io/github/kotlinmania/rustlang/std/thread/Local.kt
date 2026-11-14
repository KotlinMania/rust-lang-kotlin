// port-lint: source thread/local.rs
package io.github.kotlinmania.rustlang.std.thread

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.error

// Thread local storage

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
 * A thread local storage (TLS) key which owns its contents.
 *
 * This key uses the fastest implementation available on the target platform.
 * It is instantiated with the [threadLocal] macro and the
 * primary method is the [with] method, though there are helpers to make
 * working with [Cell] types easier.
 *
 * The [with] method yields a reference to the contained value which cannot
 * outlive the current thread or escape the given closure.
 *
 * # Initialization and Destruction
 *
 * Initialization is dynamically performed on the first call to a setter (e.g.
 * [with]) within a thread, and values that implement [Drop] get
 * destructed when a thread exits. Some platform-specific caveats apply, which
 * are explained below.
 * Note that, should the destructor panic, the whole process will be [aborted].
 * On platforms where initialization requires memory allocation, this is
 * performed directly through [System], allowing the [global allocator]
 * to make use of thread local storage.
 *
 * A `LocalKey`s initializer cannot recursively depend on itself. Using a
 * `LocalKey` in this way may cause panics, aborts, or infinite recursion on
 * the first call to `with`.
 *
 * # Single-thread Synchronization
 *
 * Though there is no potential race with other threads, it is still possible to
 * obtain multiple references to the thread-local data in different places on
 * the call stack. For this reason, only shared (`T`) references may be obtained.
 *
 * To allow obtaining an exclusive mutable reference (`T`), typically a
 * [Cell] or [RefCell] is used (see the [std.cell] for more information
 * on how exactly this works). To make this easier there are specialized
 * implementations for [LocalKey.Cell.T] and [LocalKey.RefCell.T].
 *
 * [std.cell]: `cell`
 * [LocalKey.Cell.T]: struct.LocalKey.html#impl-LocalKey<Cell<T>>
 * [LocalKey.RefCell.T]: struct.LocalKey.html#impl-LocalKey<RefCell<T>>
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 * import std.thread
 *
 * // explicit `const {}` block enables more efficient initialization
 * threadLocal(FOO, Cell, Cell, new, 1)
 * check(FOO.get() == 1)
 * FOO.set(2)
 * // each thread starts out with the initial value of 1
 * val t = thread.spawn({
 *     check(FOO.get() == 1)
 *     FOO.set(3)
 * })
 * // wait for the thread to complete and bail out on panic
 * t.join().unwrap()
 * // we retain our original value of 2 despite the child thread
 * check(FOO.get() == 2)
 * ```
 *
 * # Platform-specific behavior
 *
 * Note that a "best effort" is made to ensure that destructors for types
 * stored in thread local storage are run, but not all platforms can guarantee
 * that destructors will be run for all types in thread local storage. For
 * example, there are a number of known caveats where destructors are not run:
 *
 * 1. On Unix systems when pthread-based TLS is being used, destructors will
 *    not be run for TLS values on the main thread when it exits. Note that the
 *    application will exit immediately after the main thread exits as well.
 * 2. On all platforms it's possible for TLS to re-initialize other TLS slots
 *    during destruction. Some platforms ensure that this cannot happen
 *    infinitely by preventing re-initialization of any slot that has been
 *    destroyed, but not all platforms have this guard. Those platforms that do
 *    not guard typically have a synthetic limit after which point no more
 *    destructors are run.
 * 3. When the process exits on Windows systems, TLS destructors may only be
 *    run on the thread that causes the process to exit. This is because the
 *    other threads may be forcibly terminated.
 *
 *    TLS destructors may be leaked if a thread exits while [converted into a fiber],
 *    or if Rust TLS destructor support is first needed while running in a fiber.
 *
 *    If a process loads a Rust `cdylib`, it must not cause the Rust TLS destructor support
 *    to be initialized for the first time during process shutdown.
 *
 *    When dynamically unloading a Rust `cdylib`, pending TLS destructors may run
 *    during the unload or may be leaked.
 *
 * [converted into a fiber]: https://learn.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-convertthreadtofiber
 * [loader lock]: https://docs.microsoft.com/en-us/windows/win32/dlls/dynamic-link-library-best-practices
 * [with]: LocalKey.with
 */
public open class LocalKey<T>(
    // This outer `LocalKey<T>` type is what's going to be stored in statics,
    // but actual data inside will sometimes be tagged with @threadLocal.
    // It's not valid for a true static to reference a @threadLocal static,
    // so we get around that by exposing an accessor through a layer of function
    // indirection (this thunk).
    //
    // Note that the thunk is itself unchecked because the returned scope of the
    // slot where data lives, `static`, is not actually valid. The scope
    // here is actually slightly shorter than the currently running thread!
    //
    // Although this is an extra layer of indirection, it should in theory be
    // trivially devirtualizable by LLVM because the value of `inner` never
    // changes and the constant should be readonly within a crate. This mainly
    // only runs into problems when TLS statics are exported across crates.
    //
    // The argument of the function, `init`, can be used to initialize the key for the current
    // thread: if it is not yet initialized, and if the argument is `Some(Some(val))`, the
    // inner `val` should be `take`en out and used as the initial value instead of the default.
    // Also, in that case `inner` *must not* panic. However, it is okay (but potentially
    // inefficient) for `inner` to ignore `init` and use a default value as long as that is
    // guaranteed not to panic.
    public var inner: (T?) -> ConstPtr,
) {
    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "LocalKey").finishNonExhaustive()
    }

    /**
     * Acquires a reference to the value in this TLS key.
     *
     * This will lazily initialize the value if this thread has not referenced
     * this key yet.
     *
     * # Panics
     *
     * This function will `panic()` if the key currently has its
     * destructor running, and it **may** panic if the destructor has
     * previously been run for this thread.
     *
     * # Examples
     *
     * ```kotlin
     * threadLocal(STATIC, String, String, from, "I am")
     * check(STATIC.with({ originalValue -> "$originalValue initialized" }) == "I am initialized")
     * ```
     */
    public open fun <R> with(f: (T) -> R): R =
        run {
            val matchVal = this.tryWith(f)
            when (matchVal) { is Result.Ok<*> -> {
                val r = matchVal.value
                r
            }; is Result.Err<*> -> {
                val err = matchVal.error
                panicAccessError(err)
            } }
        }

    /**
     * Acquires a reference to the value in this TLS key.
     *
     * This will lazily initialize the value if this thread has not referenced
     * this key yet. If the key has been destroyed (which may happen if this is called
     * in a destructor), this function may return an [AccessError].
     *
     * # Panics
     *
     * This function will still `panic()` if the key is uninitialized and the
     * key's initializer panics.
     *
     * # Examples
     *
     * ```kotlin
     * threadLocal(STATIC, String, String, from, "I am")
     * check(STATIC.tryWith({ originalValue -> "$originalValue initialized" }) == Result.success("I am initialized"))
     * ```
     */
    public open fun <R> tryWith(f: (T) -> R): Result<R, AccessError> {
        val threadLocal = when (val tryResult = (this.inner)(null).asRef().okOr(AccessError())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(f(threadLocal))
    }

    /**
     * Acquires a reference to the value in this TLS key, initializing it with
     * `init` if it wasn't already initialized on this thread.
     *
     * If `init` was used to initialize the thread local variable, `None` is
     * passed as the first argument to `f`. If it was already initialized,
     * `Some(init)` is passed to `f`.
     *
     * # Panics
     *
     * This function will panic if the key currently has its destructor
     * running, and it **may** panic if the destructor has previously been run
     * for this thread.
     */
    public open fun <R> initializeWith(init: T, f: (T?, T) -> R): R {
        val init1 = init
        val reference =
            when (val matchVal = (this.inner)(init1).asRef()) {
                null -> panicAccessError(AccessError())
                else -> {
                    val r = matchVal
                    r
                }
            }
        return f(init1, reference)
    }

    public companion object {
        public fun <T> new(inner: (T?) -> ConstPtr): LocalKey<T> = LocalKey(inner = inner)
    }
}

/**
 * An error returned by [`LocalKey.tryWith`](struct.LocalKey.html#method.tryWith).
 */
public open class AccessError {
    public open fun clone(): AccessError = AccessError()

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "AccessError").finish()
    }

    public open fun fmtDisplay(f: StringBuilder) {
        f.append(("already destroyed").toString())
    }

    public companion object {
        public fun typeLayout(): TypeLayout<AccessError> =
            object : TypeLayout<AccessError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): AccessError =
                    AccessError()
                override fun writeNative(addr: Long, value: AccessError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): AccessError =
                    AccessError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: AccessError) {
                }
            }
    }
}

// This ensures the panicking code is outlined from `with` for `LocalKey`.
public fun panicAccessError(err: AccessError): Nothing {
    error("cannot access a Thread Local Storage value during or after destruction: $err")
}

/**
 * Sets or initializes the contained value.
 *
 * Unlike the other methods, this will *not* run the lazy initializer of
 * the thread local. Instead, it will be directly initialized with the
 * given value if it wasn't initialized yet.
 *
 * # Panics
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * threadLocal(X, Cell, panic, "!")
 * // Calling X.get() here would result in a panic.
 * X.set(123)
 * // But X.set() is fine, as it skips the initializer above.
 * check(X.get() == 123)
 * ```
 */
public fun <T> LocalKey<Cell<T>>.set(value: T) {
    this.initializeWith(Cell.new(value), { value1, cell ->
        if (value1 != null) {
            val value2 = value1
            // The cell was already initialized, so `value` wasn't used to
            // initialize it. So we overwrite the current value with the
            // new one instead.
            cell.set(value2.intoInner())
        }
    })
}

/**
 * Returns a copy of the contained value.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * threadLocal(X, Cell, Cell, new, 1)
 * check(X.get() == 1)
 * ```
 */
public fun <T> LocalKey<Cell<T>>.get(): T = this.with(Cell::get)

/**
 * Takes the contained value, leaving `Default.default()` in its place.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * threadLocal(X, Cell, Option, Cell, new, Some, 1)
 * check(X.also { X = null } == 1)
 * check(X.also { X = null } == null)
 * ```
 */
public fun <T> LocalKey<Cell<T>>.take(): T = this.with(Cell::take)

/**
 * Replaces the contained value, returning the old value.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * threadLocal(X, Cell, Cell, new, 1)
 * check(X.replace(2) == 1)
 * check(X.replace(3) == 2)
 * ```
 */
public fun <T> LocalKey<Cell<T>>.replace(value: T): T = this.with({ cell -> cell.replace(value) })

/**
 * Updates the contained value using a function.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * threadLocal(X, Cell, Cell, new, 5)
 * X.update({ x -> x + 1 })
 * check(X.get() == 6)
 * ```
 */
public fun <T> LocalKey<Cell<T>>.update(f: (T) -> T) {
    this.with({ cell -> cell.update(f) })
}

/**
 * Acquires a reference to the contained value.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the value is currently mutably borrowed.
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.RefCell
 *
 * threadLocal(X, RefCell, Vec, RefCell, new, Vec, new)
 * X.withBorrow({ v -> check(v.isEmpty()) })
 * ```
 */
public fun <T, R> LocalKey<RefCell<T>>.withBorrow(f: (T) -> R): R = this.with({ cell -> f(cell.borrow()) })

/**
 * Acquires a mutable reference to the contained value.
 *
 * This will lazily initialize the value if this thread has not referenced
 * this key yet.
 *
 * # Panics
 *
 * Panics if the value is currently borrowed.
 *
 * Panics if the key currently has its destructor running,
 * and it **may** panic if the destructor has previously been run for this thread.
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.RefCell
 *
 * threadLocal(X, RefCell, Vec, RefCell, new, Vec, new)
 * X.withBorrowMut({ v -> v.push(1) })
 * X.withBorrow({ v -> check(v == mutableListOf(1)) })
 * ```
 */
public fun <T, R> LocalKey<RefCell<T>>.withBorrowMut(f: (T) -> R): R = this.with({ cell -> f(cell.borrowMut()) })

private const val THREAD_LOCAL_TEMPLATE_1: String = "thread.localImpl.threadLocalProcessAttrs([][]; \$(\$tt)+);"

/**
 * Declare a new thread local storage key of type [std.thread.LocalKey].
 *
 * # Syntax
 *
 * The macro wraps any number of static declarations and makes them thread local.
 * Publicity and attributes for each static are allowed. Example:
 *
 * ```kotlin
 * import std.cell.Cell
 * import std.cell.RefCell
 *
 * threadLocal(FOO, Cell, Cell, new, 1, BAR, RefCell, Vec, RefCell, new, vec, 1.0, 2.0)
 * check(FOO.get() == 1)
 * BAR.withBorrow({ v -> check(v[1] == 2.0) })
 * ```
 *
 * Note that only shared references (`T`) to the inner data may be obtained, so a
 * type such as [Cell] or [RefCell] is typically used to allow mutating access.
 *
 * This macro supports a special `const {}` syntax that can be used
 * when the initialization expression can be evaluated as a constant.
 * This can enable a more efficient thread local implementation that
 * can avoid lazy initialization. For types that do not
 * [need to be dropped][mem.needsDrop], this can enable an
 * even more efficient implementation that does not need to
 * track any additional state.
 *
 * ```kotlin
 * import std.cell.RefCell
 *
 * threadLocal(FOO, RefCell, Vec, RefCell, new, Vec, new)
 * FOO.withBorrow({ v -> check(v.len() == 0) })
 * ```
 *
 * See [`LocalKey` documentation][std.thread.LocalKey] for more
 * information.
 */

public fun threadLocal(tt: String = ""): String =
    when {
        tt.isNotEmpty() -> THREAD_LOCAL_TEMPLATE_1.replace("\$tt", tt)
        else -> ""
    }
