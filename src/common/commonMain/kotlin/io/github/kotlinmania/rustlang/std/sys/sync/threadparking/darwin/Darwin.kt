// port-lint: source sys/sync/thread_parking/darwin.rs
package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.swap
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicI8
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Release
import kotlin.experimental.inv

// Thread parking for Darwin-based systems.
//
// Darwin actually has futex syscalls (`__ulock_wait`/`__ulock_wake`), but they
// cannot be used in `std` because they are non-public (their use will lead to
// rejection from the App Store).
//
// Therefore, we need to look for other synchronization primitives. Luckily, Darwin
// supports semaphores, which allow us to implement the behavior we need with
// only one primitive (as opposed to a mutex-condvar pair). We use the semaphore
// provided by libdispatch, as the underlying Mach semaphore is only dubiously
// public.

public typealias DispatchSemaphoreT = MutPtr

public typealias DispatchTimeT = ULong

public val DISPATCH_TIME_NOW: DispatchTimeT = 0uL

public val DISPATCH_TIME_FOREVER: DispatchTimeT = 0uL.inv()

// Contained in libSystem.dylib, which is linked by default.
public expect fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT

public expect fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT

public expect fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long

public expect fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long

public expect fun dispatchRelease(obj: MutPtr)

public val EMPTY: Byte = 0

public val NOTIFIED: Byte = 1

public val PARKED: Byte = (-1).toByte()

public open class Parker internal constructor(
    semaphoreCtorArg: DispatchSemaphoreT,
    stateCtorArg: Atomic<Byte, Align1<Byte>>,
) {
    public var semaphore: DispatchSemaphoreT = semaphoreCtorArg
        internal set

    public var state: Atomic<Byte, Align1<Byte>> = stateCtorArg
        internal set

    // Does not need `Pin`, but other implementation do.
    public open fun park() {
        // The semaphore counter must be zero at this point, because unparking
        // threads will not actually increase it until we signalled that we
        // are waiting.
        // Change NOTIFIED to EMPTY and EMPTY to PARKED.
        if (this.state.fetchSub(1, Acquire) == NOTIFIED) {
            return
        }
        // Another thread may increase the semaphore counter from this point on.
        // If it is faster than us, we will decrement it again immediately below.
        // If we are faster, we wait.
        // Ensure that the semaphore counter has actually been decremented, even
        // if the call timed out for some reason.
        while (dispatchSemaphoreWait(this.semaphore, DISPATCH_TIME_FOREVER) != 0L) {}
        // At this point, the semaphore counter is zero again.
        // We were definitely woken up, so we don't need to check the state.
        // Still, we need to reset the state using a swap to observe the state
        // change with acquire ordering.
        this.state.swap(EMPTY, Acquire)
    }

    // Does not need `Pin`, but other implementation do.
    public open fun parkTimeout(dur: Duration) {
        if (this.state.fetchSub(1, Acquire) == NOTIFIED) {
            return
        }
        val nanos = dur.asNanos().tryInto().unwrapOr(Long.MAX_VALUE)
        val timeout = dispatchTime(DISPATCH_TIME_NOW, nanos)
        val timeout1 = dispatchSemaphoreWait(this.semaphore, timeout) != 0L
        val state = this.state.swap(EMPTY, Acquire)
        if (state == NOTIFIED.toInt() && timeout1) {
            // If the state was NOTIFIED but semaphoreWait returned without
            // decrementing the count because of a timeout, it means another
            // thread is about to call semaphoreSignal. We must wait for that
            // to happen to ensure the semaphore count is reset.
            while (dispatchSemaphoreWait(this.semaphore, DISPATCH_TIME_FOREVER) != 0L) {}
        } else {
            // Either a timeout occurred and we reset the state before any thread
            // tried to wake us up, or we were woken up and reset the state,
            // making sure to observe the state change with acquire ordering.
            // Either way, the semaphore counter is now zero again.
        }
    }

    // Does not need `Pin`, but other implementation do.
    public open fun unpark() {
        val state = this.state.swap(NOTIFIED, Release)
        if (state == PARKED.toInt()) {
            run {
                dispatchSemaphoreSignal(this.semaphore)
            }
        }
    }

    public open fun drop() {
        // SAFETY:
        // We always ensure that the semaphore count is reset, so this will
        // never cause an exception.
        run {
            dispatchRelease(this.semaphore)
        }
    }

    public companion object {
        public operator fun invoke(
            semaphore: DispatchSemaphoreT,
            state: Atomic<Byte, Align1<Byte>>,
        ): Parker = Parker(
            semaphoreCtorArg = semaphore,
            stateCtorArg = state,
        )

        public fun newInPlace(parker: MutPtr) {
            val semaphore = dispatchSemaphoreCreate(0L)
            check(!semaphore.isNull()) { "failed to create dispatch semaphore for thread synchronization" }
            (UnresolvedTypeLayout<Parker>()).writeNative((parker).rawAddress, Parker(semaphore = semaphore, state = AtomicI8.new(EMPTY)))
        }
    }
}
