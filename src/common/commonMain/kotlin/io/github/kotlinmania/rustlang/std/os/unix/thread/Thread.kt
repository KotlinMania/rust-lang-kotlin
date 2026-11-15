// port-lint: source os/unix/thread.rs
package io.github.kotlinmania.rustlang.std.os.unix.thread

import io.github.kotlinmania.rustlang.std.os.linux.raw.PthreadT
import io.github.kotlinmania.rustlang.std.thread.joinhandle.JoinHandle

// Unix-specific extensions to primitives in the [std.thread] module.

public typealias RawPthread = PthreadT

/**
 * Unix-specific extensions to [JoinHandle].
 */
public interface JoinHandleExt {
    /**
     * Extracts the raw pthreadT without taking ownership
     */
    public fun asPthreadT(): RawPthread

    /**
     * Consumes the thread, returning the raw pthreadT
     *
     * This function **transfers ownership** of the underlying pthreadT to
     * the caller. Callers are then the unique owners of the pthreadT and
     * must either detach or join the pthreadT once it's no longer needed.
     */
    public fun intoPthreadT(): RawPthread
}

// This is an int2ptr cast on some platforms (e.g., *-musl) where RawPthread
// is an integer but libc.pthreadT is a pointer. Exposed provenance is the
// safe choice here, but `as` also works when it's int2int or ptr2ptr.
public fun <T> JoinHandle<T>.asPthreadT(): RawPthread = this.asInner().id().toULong()

// see above for why
public fun <T> JoinHandle<T>.intoPthreadT(): RawPthread = this.intoInner().intoId().toULong()
