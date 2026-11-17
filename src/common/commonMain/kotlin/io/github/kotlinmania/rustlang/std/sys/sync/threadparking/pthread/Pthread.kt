// port-lint: source sys/sync/thread_parking/pthread.rs
package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.pthread

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release
import io.github.kotlinmania.rustlang.std.sys.sync.condvar.pthread.Condvar
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.pthread.Mutex

// Thread parking without `futex` using the `pthread` synchronization primitives.

public val EMPTY: Int = 0

public val PARKED: Int = 1

public val NOTIFIED: Int = 2

public open class Parker internal constructor(
    stateCtorArg: Atomic<Int, Align8<Int>>,
    public var lock: Mutex,
    public var cvar: Condvar,
) {
    public var state: Atomic<Int, Align8<Int>> = stateCtorArg
        internal set

    public open fun lock(): Pin<Mutex> = this.mapUnchecked({ p -> p.lock })

    public open fun cvar(): Pin<Condvar> = this.mapUnchecked({ p -> p.cvar })

    // This implementation doesn't require `unchecked`, but other implementations
    // may assume this is only called by the thread that owns the Parker.
    //
    // For memory ordering, see futex.rs
    public open fun park() {
        // If we were previously notified then we consume this notification and
        // return quickly.
        if (this.state.compareExchange(NOTIFIED, EMPTY, Acquire, Relaxed).isOk()) {
            return
        }
        // Otherwise we need to coordinate going to sleep
        this.lock().lock()
        run {
            val matchVal = this.state.compareExchange(EMPTY, PARKED, Relaxed, Relaxed)
            when {
                matchVal is Result.Ok<*, *> -> {}
                matchVal is Result.Err<*, *> && matchVal.errValue() == NOTIFIED -> {
                    val Notified = matchVal.errValue()
                    // We must read here, even though we know it will be `NOTIFIED`.
                    // This is because `unpark` may have been called again since we read
                    // `NOTIFIED` in the `compareExchange` above. We must perform an
                    // acquire operation that synchronizes with that `unpark` to observe
                    // any writes it made before the call to unpark. To do that we must
                    // read from the write it made to `state`.
                    val old = this.state.swap(EMPTY, Acquire)
                    this.lock().unlock()
                    check(old == NOTIFIED)
                    return
                }
                // should consume this notification, so prohibit spurious wakeups in next park.
                else -> {
                    this.lock().unlock()
                    error("inconsistent park state")
                }
            }
        }
        while (true) {
            this.cvar().wait(this.lock())
            run {
                val matchVal1 = this.state.compareExchange(NOTIFIED, EMPTY, Acquire, Relaxed)
                when (matchVal1) { is Result.Ok<*, *> -> {
                    break
                }; is Result.Err<*, *> -> {} }
            }
        }
        this.lock().unlock()
    }

    // This implementation doesn't require `unchecked`, but other implementations
    // may assume this is only called by the thread that owns the Parker. Use
    // `Pin` to guarantee a stable address for the mutex and condition variable.
    public open fun parkTimeout(dur: Duration) {
        // Like `park` above we have a fast path for an already-notified thread, and
        // afterwards we start coordinating for a sleep.
        // return quickly.
        if (this.state.compareExchange(NOTIFIED, EMPTY, Acquire, Relaxed).isOk()) {
            return
        }
        this.lock().lock()
        run {
            val matchVal = this.state.compareExchange(EMPTY, PARKED, Relaxed, Relaxed)
            when {
                matchVal is Result.Ok<*, *> -> {}
                matchVal is Result.Err<*, *> && matchVal.errValue() == NOTIFIED -> {
                    val Notified = matchVal.errValue()
                    // We must read again here, see `park`.
                    val old = this.state.swap(EMPTY, Acquire)
                    this.lock().unlock()
                    check(old == NOTIFIED)
                    return
                }
                // should consume this notification, so prohibit spurious wakeups in next park.
                else -> {
                    this.lock().unlock()
                    error("inconsistent park_timeout state")
                }
            }
        }
        // Wait with a timeout, and if we spuriously wake up or otherwise wake up
        // from a notification we just want to unconditionally set the state back to
        // empty, either consuming a notification or un-flagging ourselves as
        // parked.
        this.cvar().waitTimeout(this.lock(), dur)
        when (val matchVal1 = this.state.swap(EMPTY, Acquire)) {
            NOTIFIED -> this.lock().unlock()
            // got a notification, hurray!
            PARKED -> this.lock().unlock()
            // no notification, alas
            else -> {
                val n = matchVal1
                this.lock().unlock()
                error("inconsistent park_timeout state: $n")
            }
        }
    }

    public open fun unpark() {
        // To ensure the unparked thread will observe any writes we made
        // before this call, we must perform a release operation that `park`
        // can synchronize with. To do that we must write `NOTIFIED` even if
        // `state` is already `NOTIFIED`. That is why this must be a swap
        // rather than a compare-and-swap that returns if it reads `NOTIFIED`
        // on failure.
        when (this.state.swap(NOTIFIED, Release)) {
            EMPTY -> { return }
            // no one was waiting
            NOTIFIED -> { return }
            // already unparked
            PARKED -> {}
            // gotta go wake someone up
            else -> error("inconsistent state in unpark")
        }
        // There is a period between when the parked thread sets `state` to
        // `PARKED` (or last checked `state` in the case of a spurious wake
        // up) and when it actually waits on `cvar`. If we were to notify
        // during this period it would be ignored and then when the parked
        // thread went to sleep it would never wake up. Fortunately, it has
        // `lock` locked at this stage so we can acquire `lock` to wait until
        // it is ready to receive the notification.
        //
        // Releasing `lock` before the call to `notifyOne` means that when the
        // parked thread wakes it doesn't get woken only to have to wait for us
        // to release `lock`.
        run {
            this.lock().lock()
            this.lock().unlock()
            this.cvar().notifyOne()
        }
    }

    public companion object {
        public operator fun invoke(
            state: Atomic<Int, Align8<Int>>,
            lock: Mutex,
            cvar: Condvar,
        ): Parker = Parker(
            stateCtorArg = state,
            lock = lock,
            cvar = cvar,
        )

        /**
         * Constructs the UNIX parker in-place.
         *
         * # Safety
         * The constructed parker must never be moved.
         */
        public fun newInPlace(parker: MutPtr) {
            (UnresolvedTypeLayout<Parker>()).writeNative((parker).rawAddress, Parker(state = AtomicUsize.new(EMPTY), lock = Mutex.new(), cvar = Condvar.new()))
            Pin.newUnchecked((UnresolvedTypeLayout<Parker>()).readNative((parker).rawAddress).cvar).init()
        }
    }
}
