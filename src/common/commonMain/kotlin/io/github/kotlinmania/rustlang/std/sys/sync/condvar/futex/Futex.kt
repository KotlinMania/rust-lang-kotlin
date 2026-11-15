// port-lint: source sys/sync/condvar/futex.rs
package io.github.kotlinmania.rustlang.std.sys.sync.condvar.futex

import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.Futex
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWake
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWakeAll
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex

public open class Condvar(
    // The value of this atomic is simply incremented on every notification.
    // This is used by `.wait()` to not miss any notifications after
    // unlocking the mutex and before waiting for notifications.
    public var futex: Futex,
) {
    // All the memory orderings here are `Relaxed`,
    // because synchronization is done by unlocking and locking the mutex.
    public open fun notifyOne() {
        this.futex.fetchAdd(1, Relaxed)
        futexWake(this.futex)
    }

    public open fun notifyAll() {
        this.futex.fetchAdd(1, Relaxed)
        futexWakeAll(this.futex)
    }

    public open fun wait(mutex: Mutex) {
        this.waitOptionalTimeout(mutex, null)
    }

    public open fun waitTimeout(mutex: Mutex, timeout: Duration): Boolean = this.waitOptionalTimeout(mutex, timeout)

    public open fun waitOptionalTimeout(mutex: Mutex, timeout: Duration?): Boolean {
        // Examine the notification counter _before_ we unlock the mutex.
        val futexValue = this.futex.load(Relaxed)
        // Unlock the mutex before going to sleep.
        mutex.unlock()
        // Wait, but only if there hasn't been any
        // notification since we unlocked the mutex.
        val r = futexWait(this.futex, futexValue, timeout)
        // Lock the mutex again.
        mutex.lock()
        return r
    }

    public companion object {
        public fun new(): Condvar = Condvar(futex = Futex.new(0))
    }
}
