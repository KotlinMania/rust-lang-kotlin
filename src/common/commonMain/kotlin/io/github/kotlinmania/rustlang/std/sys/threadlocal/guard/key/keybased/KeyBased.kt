// port-lint: source sys/thread_local/guard/key.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.key.keybased

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.rt.threadCleanup
import io.github.kotlinmania.rustlang.sys.threadlocal.key.racy.LazyKey
import io.github.kotlinmania.rustlang.sys.threadlocal.key.unix.get
import io.github.kotlinmania.rustlang.sys.threadlocal.key.unix.set

/**
 * The OS may free the values of the keys without a destructor **before**
 * [rt.threadCleanup] runs (e.g. ESP-IDF, glibc and musl free
 * them in the first round of TLS destruction). Hence, local pointers need
 * a destructor, which stores the value again for as long as
 * [cleanupPending] returns `true`.
 */
public val LOCAL_POINTER_NEEDS_DTOR: Boolean = true

public val DEFER: MutPtr = ptr.withoutProvenanceMut(1)

public var CLEANUP: LazyKey = LazyKey.new(::run)

/**
 * On platforms with key-based TLS, the system runs the destructors for us, so we don't have to
 * call `destructors.run`. We still have to make sure that [rt.threadCleanup] is
 * called, however. And we should make sure that it is called *last*, after dropping all
 * regular Rust thread-locals. This is done by deferring the execution of a TLS destructor to
 * the next round of destruction inside the TLS destructors.
 */
public fun enable() {
    set(CLEANUP.force(), DEFER)
}

public fun run(state: MutPtr) {
    if (((state).rawAddress.toULong() == (DEFER).rawAddress.toULong())) {
        // Make sure that this function is run again in the next round of
        // TLS destruction. If there is no further round, there will be leaks,
        // but that's okay, `threadCleanup` is not guaranteed to be called.
        set(CLEANUP.force(), RUN)
    } else {
        check(state == RUN)
        // If the state is still RUN in the next round of TLS destruction,
        // it means that no other TLS destructors defined by this runtime
        // have been run, as they would have set the state to DEFER.
        threadCleanup()
    }
}

/**
 * Whether [rt.threadCleanup] has yet to run on this thread.
 */
public fun cleanupPending(): Boolean = (!((get(CLEANUP.force())).rawAddress == 0L)).toUByte()

public val RUN: MutPtr = ptr.withoutProvenanceMut(2)
