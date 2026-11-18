// port-lint: source sys/sync/mutex/futex.rs
package io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex

import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.atomic.Ordering.Release
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.SmallFutex
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.SmallPrimitive
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWake

public typealias Futex = SmallFutex

public typealias State = SmallPrimitive

public open class Mutex(
    public var futex: Futex,
) {
    // locked, and other threads waiting (contended)
    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun tryLock(): Boolean = this.futex.compareExchange(UNLOCKED, LOCKED, Acquire, Relaxed).isOk()

    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun lock() {
        if (this.futex.compareExchange(UNLOCKED, LOCKED, Acquire, Relaxed).isErr()) {
            this.lockContended()
        }
    }

    public open fun lockContended() {
        // Spin first to speed things up if the lock is released quickly.
        var state = this.spin()
        // If it's unlocked now, attempt to take the lock
        // without marking it as contended.
        if (state == UNLOCKED) {
            run {
                val matchVal = this.futex.compareExchange(UNLOCKED, LOCKED, Acquire, Relaxed)
                when (matchVal) { is Result.Ok<*, *> -> {
                    return
                }; is Result.Err<*, *> -> {
                    val s = matchVal.errValue()
                    state = s
                } }
            }
        }
        while (true) {
            // Put the lock in contended state.
            // We avoid an unnecessary write if it as already set to CONTENDED,
            // to be friendlier for the caches.
            if (state != CONTENDED && this.futex.swap(CONTENDED, Acquire) == UNLOCKED) {
                // We changed it from UNLOCKED to CONTENDED, so we just successfully locked it.
                return
            }
            // Wait for the futex to change state, assuming it is still CONTENDED.
            futexWait(this.futex, CONTENDED, null)
            // Spin again after waking up.
            state = this.spin()
        }
    }

    public open fun spin(): State {
        var spin = 100
        while (true) {
            // We only use `load` (and not `swap` or `compareExchange`)
            // while spinning, to be easier on the caches.
            val state = this.futex.load(Relaxed)
            // We stop spinning when the mutex is UNLOCKED,
            // but also when it's CONTENDED.
            if (state != LOCKED.toInt() || spin == 0) {
                return state.toUInt()
            }
            kmSpinLoopHint()
            spin -= 1
        }
    }

    // Make this a diagnostic item for Miri's concurrency model checker.
    public open fun unlock() {
        if (this.futex.swap(UNLOCKED, Release) == CONTENDED) {
            // We only wake up one thread. When that thread locks the mutex, it
            // will mark the mutex as CONTENDED (see lockContended above),
            // which makes sure that any other waiting threads will also be
            // woken up eventually.
            this.wake()
        }
    }

    public open fun wake() {
        futexWake(this.futex)
    }

    public companion object {
        public fun new(): Mutex = Mutex(futex = SmallFutex.new(UNLOCKED))
    }
}

public val UNLOCKED: State = 0u

public val LOCKED: State = 1u

// locked, no other threads waiting
public val CONTENDED: State = 2u
