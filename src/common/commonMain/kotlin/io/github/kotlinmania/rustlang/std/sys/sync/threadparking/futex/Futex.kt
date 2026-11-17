// port-lint: source sys/sync/thread_parking/futex.rs
package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.futex

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.SmallFutex
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.SmallPrimitive
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWake

public typealias Futex = SmallFutex

public typealias State = SmallPrimitive

public val PARKED: State = State.MAX_VALUE

public val EMPTY: State = 0u

public val NOTIFIED: State = 1u

public open class Parker(
    public var state: Futex,
) {
    // Notes about memory ordering:
    //
    // Memory ordering is only relevant for the relative ordering of operations
    // between different variables. Even Ordering.Relaxed guarantees a
    // monotonic/consistent order when looking at just a single atomic variable.
    //
    // So, since this parker is just a single atomic variable, we only need to look
    // at the ordering guarantees we need to provide to the outside world'.
    //
    // The only memory ordering guarantee that parking and unparking provide, is
    // that things which happened before unpark() are visible on the thread
    // returning from park() afterwards. Otherwise, it was effectively unparked
    // before unpark() was called while still consuming the `token`.
    //
    // In other words, unpark() needs to synchronize with the part of park() that
    // consumes the token and returns.
    //
    // This is done with a release-acquire synchronization, by using
    // Ordering.Release when writing NOTIFIED (the `token`) in unpark(), and using
    // Ordering.Acquire when checking for this state in park().
    // Assumes this is only called by the thread that owns the Parker,
    // which means that `self.state != PARKED`.
    public open fun park() {
        // Change NOTIFIED=>EMPTY or EMPTY=>PARKED, and directly return in the
        // first case.
        if (this.state.fetchSub(1, Acquire) == NOTIFIED) {
            return
        }
        while (true) {
            // Wait for something to happen, assuming it's still set to PARKED.
            futexWait(this.state, PARKED, null)
            // Change NOTIFIED=>EMPTY and return in that case.
            if (this.state.compareExchange(NOTIFIED, EMPTY, Acquire, Acquire).isOk()) {
                return
            } else {
                // Spurious wake up. We loop to try again.
            }
        }
    }

    // Assumes this is only called by the thread that owns the Parker,
    // which means that `self.state != PARKED`. This implementation doesn't
    // require `Pin`, but other implementations do.
    public open fun parkTimeout(timeout: Duration) {
        // Change NOTIFIED=>EMPTY or EMPTY=>PARKED, and directly return in the
        // first case.
        if (this.state.fetchSub(1, Acquire) == NOTIFIED) {
            return
        }
        // Wait for something to happen, assuming it's still set to PARKED.
        futexWait(this.state, PARKED, timeout)
        // This is not just a store, because we need to establish a
        // release-acquire ordering with unpark().
        if (this.state.swap(EMPTY, Acquire) == NOTIFIED) {
            // Woke up because of unpark().
        } else {
            // Timeout or spurious wake up.
            // We return either way, because we can't easily tell if it was the
            // timeout or not.
        }
    }

    // This implementation doesn't require `Pin`, but other implementations do.
    public open fun unpark() {
        // Change PARKED=>NOTIFIED, EMPTY=>NOTIFIED, or NOTIFIED=>NOTIFIED, and
        // wake the thread in the first case.
        //
        // Note that even NOTIFIED=>NOTIFIED results in a write. This is on
        // purpose, to make sure every unpark() has a release-acquire ordering
        // with park().
        if (this.state.swap(NOTIFIED, Release) == PARKED) {
            futexWake(this.state)
        }
    }

    public companion object {
        /**
         * Constructs the futex parker. The UNIX parker implementation
         * requires this to happen in-place.
         */
        public fun newInPlace(parker: MutPtr) {
            (UnresolvedTypeLayout<Parker>()).writeNative((parker).rawAddress, Parker(state = Futex.new(EMPTY)))
        }
    }
}
