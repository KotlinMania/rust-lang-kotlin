// port-lint: source thread/current.rs
package io.github.kotlinmania.rustlang.std.thread.current

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.enable
import io.github.kotlinmania.rustlang.std.thread.id.ThreadId
import io.github.kotlinmania.rustlang.std.thread.mainthread.main
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

public val NONE: MutPtr = MutPtr(0L)

public val BUSY: MutPtr = ptr.withoutProvenanceMut(1)

public val DESTROYED: MutPtr = ptr.withoutProvenanceMut(2)

public var ID: Cell<Any?> = Cell.new(null)

public val CHEAP: Boolean = true

public fun get(): Any? = ID.get()

public fun set(id: Any) {
    ID.set(id)
}

public fun getOrInit(): Any =
    (get() ?: run {
        val id = ThreadId.new()
        set(id)
        id
    })

/**
 * Tries to set the thread handle for the current thread. Fails if a handle was
 * already set or if the thread ID of `thread` would change an already-set ID.
 */
public fun setCurrent(thread: Thread): Result<Unit, Thread> {
    if (((CURRENT.get()).rawAddress.toULong() != (NONE).rawAddress.toULong())) {
        return Result.Err<Unit, Thread>(thread)
    }
    run {
        val matchVal = get()
        when {
            matchVal != null && matchVal == thread.id() -> {
                val id = matchVal
            }
            matchVal == null -> set(thread.id())
            else -> { return Result.Err<Unit, Thread>(thread) }
        }
    }
    // Make sure that `rt.threadCleanup` will be run, which will
    // call `dropCurrent`.
    enable()
    CURRENT.set(MutPtr((thread.intoRaw()).rawAddress))
    return Result.Ok<Unit, Thread>(Unit)
}

/**
 * Gets the unique identifier of the thread which invokes it.
 *
 * Calling this function may be more efficient than accessing the current
 * thread id through the current thread handle. i.e. `thread.current().id()`.
 *
 * This function will always succeed, will always return the same value for
 * one thread and is guaranteed not to call the global allocator.
 *
 * # Examples
 *
 * ```kotlin
 * import std.thread
 *
 * @feature(currentThreadId)
 * public fun doctestEntry() {
 *     val otherThread = thread.spawn({
 *         thread.currentId()
 *     })
 *     val otherThreadId = otherThread.join().unwrap()
 *     check(thread.currentId() != otherThreadId)
 * }
 * ```
 */
public fun currentId(): Any {
    // If accessing the persistent thread ID takes multiple TLS accesses, try
    // to retrieve it from the current thread handle, which will only take one
    // TLS access.
    if (CHEAP.inv()) {
        when (val matchVal = tryWithCurrent({ t -> t.map({ t1 -> t1.id() }) })) {
            null -> {}
            else -> {
                val id = matchVal
                return id
            }
        }
    }
    return getOrInit()
}

/**
 * Gets the OS thread ID of the thread that invokes it, if available. If not, return the Rust
 * thread ID.
 *
 * We use a `ULong` to all possible platform IDs without excess `cfg`; most use `int`, some use a
 * pointer, and Apple uses `uint64T`. This is a "best effort" approach for diagnostics and is
 * allowed to fall back to a non-OS ID (such as the Rust thread ID) or a non-unique ID (such as a
 * PID) if the thread ID cannot be retrieved.
 */
public fun currentOsId(): ULong = io.github.kotlinmania.rustlang.std.sys.thread.unix.currentOsId().unwrapOrElse({ currentId().asU64().get() })

/**
 * Gets a reference to the handle of the thread that invokes it, if the handle
 * has been initialized.
 */
public fun <R> tryWithCurrent(f: (Thread?) -> R): R {
    val current = CURRENT.get()
    return if (((current).rawAddress.toULong() > (DESTROYED).rawAddress.toULong())) {
        // SAFETY: `Arc` does not contain interior mutability, so it does not
        // matter that the address of the handle might be different depending
        // on where this is called.
        run {
            val current1 = Thread.fromRaw(current)
            f(current1)
        }
    } else {
        f(null)
    }
}

/**
 * Run a function with the current thread's name.
 *
 * Modulo thread local accesses, this function is safe to call from signal
 * handlers and in similar circumstances where allocations are not possible.
 */
public fun <R> withCurrentName(f: (String?) -> R): R =
    tryWithCurrent({ thread ->
        val name =
            if (thread != null) {
                val thread1 = thread
                // If there is a current thread handle, try to use the name stored
                // there.
                thread1.name()
            } else if (io.github.kotlinmania.rustlang.std.thread.mainthread.get() != null && get() != null && id == main) {
                // The main thread doesn't always have a thread handle, we must
                // identify it through its ID instead. The checks are ordered so
                // that the current ID is only loaded if it is actually needed,
                // since loading it from TLS might need multiple expensive accesses.
                "main"
            } else {
                null
            }
        f(name)
    })

/**
 * Gets a handle to the thread that invokes it. If the handle stored in thread-
 * local storage was already destroyed, this creates a new unnamed temporary
 * handle to allow thread parking in nearly all situations.
 */
public fun currentOrUnnamed(): Thread {
    val current = CURRENT.get()
    return if (((current).rawAddress.toULong() > (DESTROYED).rawAddress.toULong())) {
        run {
            val current1 = Thread.fromRaw(current)
            current1.clone()
        }
    } else if (((current).rawAddress.toULong() == (DESTROYED).rawAddress.toULong())) {
        val id = getOrInit()
        Thread.newCurrent(id)
    } else {
        initCurrent(current)
    }
}

/**
 * Gets a handle to the thread that invokes it.
 *
 * # Examples
 *
 * Getting a handle to the current thread with `thread.current()`:
 *
 * ```kotlin
 * import std.thread
 *
 * val handler = thread.Builder.new().name("named thread".into()).spawn({
 *     val handle = thread.current()
 *     check(handle.name() == "named thread")
 * }).unwrap()
 * handler.join().unwrap()
 * ```
 */
public fun current(): Thread {
    val current = CURRENT.get()
    return if (((current).rawAddress.toULong() > (DESTROYED).rawAddress.toULong())) {
        run {
            val current1 = Thread.fromRaw(current)
            current1.clone()
        }
    } else {
        initCurrent(current)
    }
}

public fun initCurrent(current: MutPtr): Thread =
    if (((current).rawAddress.toULong() == (NONE).rawAddress.toULong())) {
        CURRENT.set(BUSY)
        // If the thread ID was initialized already, use it.
        val id = getOrInit()
        val thread = Thread.newCurrent(id)
        // Make sure that `rt.threadCleanup` will be run, which will
        // call `dropCurrent`.
        enable()
        CURRENT.set(MutPtr((thread.clone().intoRaw()).rawAddress))
        thread
    } else if (((current).rawAddress.toULong() == (BUSY).rawAddress.toULong())) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("init_current() was re-entrant, which indicates a bug in the Rust threading implementation").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("init_current() was re-entrant, which indicates a bug in the Rust threading implementation").toString() + ", aborting\n"
            }
            abort()
        }
    } else {
        check(current == DESTROYED)
        error("use of std::thread::current() is not possible after the thread's local data has been destroyed")
    }

/**
 * This should be run in [rt.threadCleanup] to reset the thread
 * handle.
 */
public fun dropCurrent() {
    val current = CURRENT.get()
    if (((current).rawAddress.toULong() > (DESTROYED).rawAddress.toULong())) {
        run {
            CURRENT.set(DESTROYED)
            (Thread.fromRaw(current)).let { }
        }
    }
}
