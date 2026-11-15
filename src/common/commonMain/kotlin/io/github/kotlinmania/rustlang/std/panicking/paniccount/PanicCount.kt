// port-lint: source panicking.rs
package io.github.kotlinmania.rustlang.std.panicking.paniccount

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.thread.LocalKey
import io.github.kotlinmania.rustlang.std.thread.localimpl.threadLocalInner

public val ALWAYS_ABORT_FLAG: Int = 1 shl (32u - 1u).toInt()

/**
 * A reason for forcing an immediate abort on panic.
 */
public sealed class MustAbort {
    public data object AlwaysAbort : MustAbort()
    public data object PanicInHook : MustAbort()
}

// Panic count for the current thread and whether a panic hook is currently
// being executed..
public val LOCAL_PANIC_COUNT: LocalKey<Cell<Pair<Int, Boolean>>> = threadLocalInner(key, Cell, Cell.new(Pair(0, false)))

// Sum of panic counts from all threads. The purpose of this is to have
// a fast path in `countIsZero` (which is used by `panicking`). In any particular
// thread, if that thread currently views `GLOBAL_PANIC_COUNT` as being zero,
// then `LOCAL_PANIC_COUNT` in that thread is zero. This invariant holds before
// and after increase and decrease, but not necessarily during their execution.
//
// Additionally, the top bit of GLOBAL_PANIC_COUNT (GLOBAL_ALWAYS_ABORT_FLAG)
// records whether panic.alwaysAbort() has been called. This can only be
// set, never cleared.
// panic.alwaysAbort() is usually called to prevent memory allocations done by
// the panic handling in the child created by `libc.fork`.
// Memory allocations performed in a child created with `libc.fork` are undefined
// behavior in most operating systems.
// Accessing LOCAL_PANIC_COUNT in a child created by `libc.fork` would lead to a memory
// allocation. Only GLOBAL_PANIC_COUNT can be accessed in this situation. This is
// sufficient because a child process will always have exactly one thread only.
// See also #85261 for details.
//
// This could be viewed as a struct containing a single bit and an n-1-bit
// value, but if we wrote it like that it would be more than a single word,
// and even a newtype around Int would be clumsy because we need atomics.
// But we use such a tuple for the return type of increase().
//
// Stealing a bit is fine because it just amounts to assuming that each
// panicking thread consumes at least 2 bytes of address space.
public var GLOBAL_PANIC_COUNT: Atomic<Int> = AtomicUsize.new(0)

// Increases the global and local panic count, and returns whether an
// immediate abort is required.
//
// This also updates thread-local state to keep track of whether a panic
// hook is currently executing.
public fun increase(runPanicHook: Boolean): MustAbort? {
    val globalCount = GLOBAL_PANIC_COUNT.fetchAdd(1, Ordering.Relaxed)
    if (globalCount and ALWAYS_ABORT_FLAG != 0) {
        // Do *not* access thread-local state, we might be after a `fork`.
        return MustAbort.AlwaysAbort
    }
    return LOCAL_PANIC_COUNT.with(closure@{ c ->
        val (count, inPanicHook) = c.get()
        if (inPanicHook) {
            return@closure MustAbort.PanicInHook
        }
        c.set(Pair(count + 1, runPanicHook))
        null
    })
}

public fun finishedPanicHook() {
    LOCAL_PANIC_COUNT.with({ c ->
        val (count, _) = c.get()
        c.set(Pair(count, false))
    })
}

public fun decrease() {
    GLOBAL_PANIC_COUNT.fetchSub(1, Ordering.Relaxed)
    LOCAL_PANIC_COUNT.with({ c ->
        val (count, _) = c.get()
        c.set(Pair(count - 1, false))
    })
}

public fun setAlwaysAbort() {
    GLOBAL_PANIC_COUNT.fetchOr(ALWAYS_ABORT_FLAG, Ordering.Relaxed)
}

// Disregards ALWAYS_ABORT_FLAG
public fun getCount(): Int = LOCAL_PANIC_COUNT.with({ c -> c.get().first })

// Disregards ALWAYS_ABORT_FLAG
public fun countIsZero(): Boolean =
    if (GLOBAL_PANIC_COUNT.load(Ordering.Relaxed) and ALWAYS_ABORT_FLAG.inv() == 0) {
        // Fast path: if `GLOBAL_PANIC_COUNT` is zero, all threads
        // (including the current one) will have `LOCAL_PANIC_COUNT`
        // equal to zero, so TLS access can be avoided.
        //
        // In terms of performance, a relaxed atomic load is similar to a normal
        // aligned memory read (e.g., a mov instruction in x86), but with some
        // compiler optimization restrictions. On the other hand, a TLS access
        // might require calling a non-inlinable function (such as `__tls_get_addr`
        // when using the GD TLS model).
        true
    } else {
        isZeroSlowPath()
    }

// Slow path is in a separate function to reduce the amount of code
// inlined from `countIsZero`.
public fun isZeroSlowPath(): Boolean = LOCAL_PANIC_COUNT.with({ c -> c.get().first == 0 })
