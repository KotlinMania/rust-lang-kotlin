// port-lint: source sys/sync/once/futex.rs
package io.github.kotlinmania.rustlang.std.sys.sync.once.futex

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Acquire
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Relaxed
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Release
import io.github.kotlinmania.rustlang.std.sync.once.OnceExclusiveState
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.Futex
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.Primitive
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWakeAll

// On some platforms, the OS is very nice and handles the waiter queue for us.
// This means we only need one atomic value with 4 states:

/**
 * No initialization has run yet, and no thread is currently using the Once.
 */
public val INCOMPLETE: Primitive = 3u

/**
 * Some thread has previously attempted to initialize the Once, but it panicked,
 * so the Once is now poisoned. There are no other threads currently accessing
 * this Once.
 */
public val POISONED: Primitive = 2u

/**
 * Some thread is currently attempting to run initialization. It may succeed,
 * so all future threads need to wait for it to finish.
 */
public val RUNNING: Primitive = 1u

/**
 * Initialization has completed and all future calls should finish immediately.
 * By choosing this state as the all-zero state the `isCompleted` check can be
 * a bit faster on some platforms.
 */
public val COMPLETE: Primitive = 0u

// An additional bit indicates whether there are waiting threads:

/**
 * May only be set if the state is not COMPLETE.
 */
public val QUEUED: Primitive = 4u

// Threads wait by setting the QUEUED bit and calling `futexWait` on the state
// variable. When the running thread finishes, it will wake all waiting threads using
// `futexWakeAll`.
public val STATE_MASK: Primitive = 0x3u

public open class OnceState internal constructor(
    public var poisoned: Boolean,
    setStateToCtorArg: Cell<Primitive>,
) {
    public var setStateTo: Cell<Primitive> = setStateToCtorArg
        internal set

    public open fun isPoisoned(): Boolean = this.poisoned

    public open fun poison() {
        this.setStateTo.set(POISONED)
    }

    public companion object {
        public operator fun invoke(
            poisoned: Boolean,
            setStateTo: Cell<Primitive>,
        ): OnceState = OnceState(
            poisoned = poisoned,
            setStateToCtorArg = setStateTo,
        )
    }
}

public open class CompletionGuard(
    public var stateAndQueued: Futex,
    public var setStateOnDropTo: Primitive,
) {
    public open fun drop() {
        // Use release ordering to propagate changes to all threads checking
        // up on the Once. `futexWakeAll` does its own synchronization, hence
        // we do not need `AcqRel`.
        if (this.stateAndQueued.swap(this.setStateOnDropTo, Release) and QUEUED != 0u) {
            futexWakeAll(this.stateAndQueued)
        }
    }
}

public open class Once(
    public var stateAndQueued: Futex,
) {
    public open fun isCompleted(): Boolean {
        // Use acquire ordering to make all initialization changes visible to the
        // current thread.
        return (this.stateAndQueued.load(Acquire) == COMPLETE).toUByte()
    }

    public open fun state(): OnceExclusiveState =
        when (this.stateAndQueued.getMut()) {
            INCOMPLETE -> OnceExclusiveState.Incomplete
            POISONED -> OnceExclusiveState.Poisoned
            COMPLETE -> OnceExclusiveState.Complete
            else -> error("invalid Once state")
        }

    public open fun setState(newState: OnceExclusiveState) {
        this.stateAndQueued.getMut() = when (newState) {
            OnceExclusiveState.Incomplete -> INCOMPLETE
            OnceExclusiveState.Poisoned -> POISONED
            OnceExclusiveState.Complete -> COMPLETE
        }
    }

    public open fun wait(ignorePoisoning: Boolean) {
        var stateAndQueued = this.stateAndQueued.load(Acquire)
        while (true) {
            val state = stateAndQueued and STATE_MASK
            val queued = stateAndQueued and QUEUED != 0u
            when {
                state == COMPLETE -> { return }
                state == POISONED && !ignorePoisoning -> {
                    // Panic to propagate the poison.
                    error("Once instance has previously been poisoned")
                }
                else -> {
                    // Set the QUEUED bit if it has not already been set.
                    if (queued.inv()) {
                        stateAndQueued += QUEUED
                        when (val matchVal = this.stateAndQueued.compareExchangeWeak(state, stateAndQueued, Relaxed, Acquire)) {
                            is Result.Err<*, *> -> {
                                val new = matchVal.errValue()
                                stateAndQueued = new
                                continue
                            }
                            else -> {}
                        }
                    }
                    futexWait(this.stateAndQueued, stateAndQueued, null)
                    stateAndQueued = this.stateAndQueued.load(Acquire)
                }
            }
        }
    }

    public open fun call(ignorePoisoning: Boolean, f: (io.github.kotlinmania.rustlang.std.sync.once.OnceState) -> Unit) {
        var stateAndQueued = this.stateAndQueued.load(Acquire)
        while (true) {
            val state = stateAndQueued and STATE_MASK
            val queued = stateAndQueued and QUEUED != 0u
            when {
                state == COMPLETE -> { return }
                state == POISONED && !ignorePoisoning -> {
                    // Panic to propagate the poison.
                    error("Once instance has previously been poisoned")
                }
                state == INCOMPLETE || state == POISONED -> {
                    // Try to register the current thread as the one running.
                    val next =
                        RUNNING +
                            if (queued) {
                                QUEUED
                            } else {
                                0u
                            }
                    when (val matchVal = this.stateAndQueued.compareExchangeWeak(stateAndQueued, next, Acquire, Acquire)) {
                        is Result.Err<*, *> -> {
                            val new = matchVal.errValue()
                            stateAndQueued = new
                            continue
                        }
                        else -> {}
                    }
                    // `waiterQueue` will manage other waiting threads, and
                    // wake them up on drop.
                    val waiterQueue = CompletionGuard(stateAndQueued = this.stateAndQueued, setStateOnDropTo = POISONED)
                    // Run the function, letting it know if we're poisoned or not.
                    val fState = io.github.kotlinmania.rustlang.std.sync.once.OnceState(inner = OnceState(poisoned = (state == POISONED).toUByte(), setStateTo = Cell.new(COMPLETE)))
                    f(fState)
                    waiterQueue.setStateOnDropTo = fState.inner.setStateTo.get()
                    return
                }
                else -> {
                    // All other values must be RUNNING.
                    check(state == RUNNING)
                    // Set the QUEUED bit if it is not already set.
                    if (queued.inv()) {
                        stateAndQueued += QUEUED
                        when (val matchVal1 = this.stateAndQueued.compareExchangeWeak(state, stateAndQueued, Relaxed, Acquire)) {
                            is Result.Err<*, *> -> {
                                val new1 = matchVal1.errValue()
                                stateAndQueued = new1
                                continue
                            }
                            else -> {}
                        }
                    }
                    futexWait(this.stateAndQueued, stateAndQueued, null)
                    stateAndQueued = this.stateAndQueued.load(Acquire)
                }
            }
        }
    }

    public companion object {
        public fun new(): Once = Once(stateAndQueued = Futex.new(INCOMPLETE))

        public fun newComplete(): Once = Once(stateAndQueued = Futex.new(COMPLETE))
    }
}
