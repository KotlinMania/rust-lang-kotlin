// port-lint: source sys/thread_local/guard/retained.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.retained

import io.github.kotlinmania.rustlang.process.abort
import io.github.kotlinmania.rustlang.sys.stdio.unix.panicOutput

// Guards which run all TLS destructors themselves, and only then
// [rt.threadCleanup], never free the values of keys without a
// destructor before the cleanup. Hence, local pointers need no destructor.

public val LOCAL_POINTER_NEEDS_DTOR: Boolean = false

/**
 * Only referenced by the `keep` destructor of `localPointer`, which is
 * never registered here (`LOCAL_POINTER_NEEDS_DTOR` is `false`).
 */
public fun cleanupPending(): Boolean =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("`cleanup_pending` called although `LOCAL_POINTER_NEEDS_DTOR` is false").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("`cleanup_pending` called although `LOCAL_POINTER_NEEDS_DTOR` is false").toString() + ", aborting\n"
        }
        abort()
    }
