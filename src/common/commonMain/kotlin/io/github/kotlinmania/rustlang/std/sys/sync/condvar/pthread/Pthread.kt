// port-lint: source sys/sync/condvar/pthread.rs
package io.github.kotlinmania.rustlang.std.sys.sync.condvar.pthread

import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex
import io.github.kotlinmania.rustlang.std.sys.sync.oncebox.OnceBox
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.std.sys.pal.sync as pal

public open class Condvar internal constructor(
    cvarCtorArg: OnceBox<pal.Condvar>,
    mutexCtorArg: Atomic<Int, Align8<Int>>,
) {
    public var cvar: OnceBox<pal.Condvar> = cvarCtorArg
        internal set

    public var mutex: Atomic<Int, Align8<Int>> = mutexCtorArg
        internal set

    public open fun get(): Pin<pal.Condvar> =
        this.cvar.getOrInit({
            var cvar = Box.pin(pal.Condvar.new())
            // SAFETY: we only call `init` once per `pal.Condvar`, namely here.
            cvar.asMut().init()
            cvar
        })

    public open fun verify(mutex: Pin<pal.Mutex>) {
        val addr = mutex.addr()
        // Relaxed is okay here because we never read through `self.mutex`, and only use it to
        // compare addresses.
        run {
            val matchVal = this.mutex.compareExchange(0, addr, Relaxed, Relaxed)
            when {
                matchVal is Result.Ok -> {}
                // Stored the address
                matchVal is Result.Err && (matchVal as Result.Err).error == addr -> {
                    val n = matchVal.error
                }
                // Lost a race to store the same address
                else -> error("attempted to use a condition variable with two mutexes")
            }
        }
    }

    public open fun notifyOne() {
        // SAFETY: we called `init` above.
        this.get().notifyOne()
    }

    public open fun notifyAll() {
        // SAFETY: we called `init` above.
        this.get().notifyAll()
    }

    public open fun wait(mutex: Mutex) {
        // SAFETY: the caller guarantees that the lock is owned, thus the mutex
        // must have been initialized already.
        val mutex1 = mutex.pal.getUnchecked()
        this.verify(mutex1)
        // SAFETY: we called `init` above, we verified that this condition
        // variable is only used with `mutex` and the caller guarantees that
        // `mutex` is locked by the current thread.
        this.get().wait(mutex1)
    }

    public open fun waitTimeout(mutex: Mutex, dur: Duration): Boolean {
        // SAFETY: the caller guarantees that the lock is owned, thus the mutex
        // must have been initialized already.
        val mutex1 = mutex.pal.getUnchecked()
        this.verify(mutex1)
        return if (pal.Condvar.PRECISE_TIMEOUT) {
            // SAFETY: we called `init` above, we verified that this condition
            // variable is only used with `mutex` and the caller guarantees that
            // `mutex` is locked by the current thread.
            this.get().waitTimeout(mutex1, dur)
        } else {
            // Timeout reports are not reliable, so do the check ourselves.
            val now = Instant.now()
            // SAFETY: we called `init` above, we verified that this condition
            // variable is only used with `mutex` and the caller guarantees that
            // `mutex` is locked by the current thread.
            val woken = this.get().waitTimeout(mutex1, dur)
            woken || now.elapsed() < dur
        }
    }

    public companion object {
        public operator fun invoke(
            cvar: OnceBox<pal.Condvar>,
            mutex: Atomic<Int, Align8<Int>>,
        ): Condvar = Condvar(
            cvarCtorArg = cvar,
            mutexCtorArg = mutex,
        )

        public fun new(): Condvar = Condvar(cvar = OnceBox.new(), mutex = AtomicUsize.new(0))
    }
}
