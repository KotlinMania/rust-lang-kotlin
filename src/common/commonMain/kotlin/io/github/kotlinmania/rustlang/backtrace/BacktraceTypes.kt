package io.github.kotlinmania.rustlang.backtrace

import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.MutexGuard

/**
 * A "Maybe" LockGuard
 */
public open class LockGuard internal constructor(
    valueCtorArg: MutexGuard<Unit>?,
) {
    public var value: MutexGuard<Unit>? = valueCtorArg
        internal set

    public var field0: MutexGuard<Unit>?
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        // Don't do anything if we're a LockGuard(None)
        if (this.value != null) {
            LOCK_HELD.with({ slot ->
                // Immediately crash if we somehow aren't the thread holding this lock
                check(slot.get())
                // We are no longer the thread holding this lock
                slot.set(false)
            })
        }
        // lock implicitly released here, if we're a LockGuard(Some(..))
    }

    public companion object {
        public operator fun invoke(
            value: MutexGuard<Unit>?,
        ): LockGuard =
            LockGuard(
                valueCtorArg = value,
            )
    }
}

/**
 * The global lock
 */
public var LOCK: Mutex<Unit> = Mutex.new()

/**
 * Acquire a partially unsound(!!!) global re-entrant lock over
 * backtrace's internals.
 *
 * That is, this lock can be acquired as many times as you want
 * on a single thread without deadlocking, allowing one thread
 * to acquire exclusive access to the ability to make backtraces.
 * Calls to this locking function are freely sprinkled in every place
 * where that needs to be enforced.
 *
 * # Why
 *
 * This was first introduced to guard uses of Windows' dbghelp API,
 * which isn't threadsafe. It's unclear if other things now rely on
 * this locking.
 *
 * # How
 *
 * The basic idea is to have a single global mutex, and a threadLocal
 * boolean saying "yep this is the thread that acquired the mutex".
 *
 * The first time a thread acquires the lock, it is handed a
 * `LockGuard(Some(..))` that will actually release the lock on Drop.
 * All subsequence attempts to lock on the same thread will see
 * that their thread acquired the lock, and get `LockGuard(None)`
 * which will do nothing when dropped.
 *
 * # Safety
 *
 * As long as you only ever assign the returned LockGuard to a freshly
 * declared local variable, it will do its job correctly, as the "first"
 * LockGuard will strictly outlive all subsequent LockGuards and
 * properly release the lock when the thread is done with backtracing.
 *
 * However if you ever attempt to store a LockGuard beyond the scope
 * it was acquired in, it might actually be a `LockGuard(None)` that
 * doesn't actually hold the lock! In this case another thread might
 * acquire the lock and you'll get races this system was intended to
 * avoid!
 *
 * This is why this is "partially unsound". As a public API this would
 * be unacceptable, but this is crate-private, and if you use this in
 * the most obvious and simplistic way it Just Works™.
 *
 * Note however that std specifically bypasses this lock, and uses
 * the `* _unsynchronized` backtrace APIs. This is "fine" because
 * it wraps its own calls to backtrace in a non-reentrant Mutex
 * that prevents two backtraces from getting interleaved during printing.
 */
public fun lock(): LockGuard {
    // If we're the thread holding this lock, pretend to acquire the lock
    // again by returning a LockGuard(None)
    if (LOCK_HELD.with({ l -> l.get() })) {
        return LockGuard(null)
    }
    // Insist that we totally are the thread holding the lock
    // (our thread will block until we are)
    LOCK_HELD.with({ s -> s.set(true) })
    // ok *actually* try to acquire the lock, blocking as necessary
    return LockGuard(LOCK.lock().unwrap())
}
