// port-lint: source sys/sync/mutex/pthread.rs
package io.github.kotlinmania.rustlang.std.sys.sync.mutex.pthread

import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.std.sys.sync.oncebox.OnceBox
import io.github.kotlinmania.rustlang.std.sys.pal.sync as pal

public open class Mutex(
    internal var pal: OnceBox<Mutex>,
) {
    public open fun get(): Pin<Mutex> {
        // If the initialization race is lost, the new mutex is destroyed.
        // This is sound however, as it cannot have been locked.
        return this.pal.getOrInit({
            var pal = Box.pin(Mutex.new())
            // SAFETY: we only call `init` once per `pal.Mutex`, namely here.
            pal.asMut().init()
            pal
        })
    }

    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun lock() {
        // SAFETY: we call `init` above, therefore reentrant locking is safe.
        // In `drop` we ensure that the mutex is not destroyed while locked.
        this.get().lock()
    }

    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun unlock() {
        // SAFETY: the mutex can only be locked if it is already initialized
        // and we observed this initialization since we observed the locking.
        this.pal.getUnchecked().unlock()
    }

    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun tryLock(): Boolean {
        // SAFETY: we call `init` above, therefore reentrant locking is safe.
        // In `drop` we ensure that the mutex is not destroyed while locked.
        return this.get().tryLock()
    }

    public open fun drop() {
        val pal = this.pal.also { this.pal = null } ?: return
        // We're not allowed to pthreadMutexDestroy a locked mutex,
        // so check first if it's unlocked.
        if (pal.asRef().tryLock()) {
            pal.asRef().unlock()
            drop(pal)
        } else {
            // The mutex is locked. This happens if a MutexGuard is leaked.
            // In this case, we just leak the Mutex too.
            forget(pal)
        }
    }

    public companion object {
        public fun new(): Mutex = Mutex(pal = OnceBox.new())
    }
}
