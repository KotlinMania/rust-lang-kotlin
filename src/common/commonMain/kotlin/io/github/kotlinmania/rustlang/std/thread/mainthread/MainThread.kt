// port-lint: source thread/main_thread.rs
package io.github.kotlinmania.rustlang.std.thread.mainthread

import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU64
import io.github.kotlinmania.rustlang.std.thread.id.ThreadId
import io.github.kotlinmania.rustlang.sync.atomic.ordering.Relaxed

// Store the ID of the main thread.
//
// The thread handle for the main thread is created lazily, and this might even
// happen pre-main. Since not every platform has a way to identify the main
// thread when that happens – macOS's `pthreadMainNp` function being a notable
// exception – we cannot assign it the right name right then. Instead, in our
// runtime startup code, we remember the thread ID of the main thread (through
// this modules `set` function) and use it to identify the main thread from then
// on. This works reliably and has the additional advantage that we can report
// the right thread name on main even after the thread handle has been destroyed.
// Note however that this also means that the name reported in pre-main functions
// will be incorrect, but that's just something we have to live with.

public var MAIN: Atomic<ULong> = AtomicU64.new(0)

public fun get(): Any? = ThreadId.fromU64(MAIN.load(Relaxed))

/**
 * # Safety
 * May only be called once.
 */
public fun set(id: Any) {
    MAIN.store(id.asU64().get(), Relaxed)
}
