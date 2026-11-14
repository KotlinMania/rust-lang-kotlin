// port-lint: source sync/reentrant_lock.rs
package io.github.kotlinmania.rustlang.std.sync.reentrantlock

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.fuchsia.Mutex
import io.github.kotlinmania.rustlang.std.sys.thread.hermit.Tid
import io.github.kotlinmania.rustlang.std.thread.current.currentId

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

// # Implementation details
//
// The 'owner' field tracks which thread has locked the mutex.
//
// We use thread.currentId() as the thread identifier, which is just the
// current thread's ThreadId, so it's unique across the process scope.
//
// If `owner` is set to the identifier of the current thread,
// we assume the mutex is already locked and instead of locking it again,
// we increment `lockCount`.
//
// When unlocking, we decrement `lockCount`, and only unlock the mutex when
// it reaches zero.
//
// `lockCount` is protected by the mutex and only accessed by the thread that has
// locked the mutex, so needs no synchronization.
//
// `owner` can be checked by other threads that want to see if they already
// hold the lock, so needs to be atomic. If it compares equal, we're on the
// same thread that holds the mutex and memory access can use relaxed ordering
// since we're not dealing with multiple threads. If it's not equal,
// synchronization is left to the mutex, making relaxed memory ordering for
// the `owner` field fine in all cases.
//
// On systems without 64 bit atomics we also store the address of a TLS variable
// along the 64-bit TID. We then first check that address against the address
// of that variable on the current thread, and only if they compare equal do we
// compare the actual TIDs. Because we only ever read the TID on the same thread
// that it was written on (or a thread sharing the TLS block with that writer thread),
// we don't need to further synchronize the TID accesses, so they can be regular 64-bit
// non-atomic accesses.

/**
 * A re-entrant mutual exclusion lock
 *
 * This lock will block *other* threads waiting for the lock to become
 * available. The thread which has already locked the mutex can lock it
 * multiple times without blocking, preventing a common source of deadlocks.
 *
 * # Examples
 *
 * Allow recursively calling a function needing synchronization from within
 * a callback (this is how [`StdoutLock`](io.StdoutLock) is currently
 * implemented):
 *
 * ```kotlin
 * import std.cell.RefCell
 * import std.sync.ReentrantLock
 *
 * @feature(reentrantLock)
 * public fun doctestEntry() {
 *     open class Log(public var data: RefCell<String>) {
 *         public open fun append(msg: String) {
 *             this.data.borrowMut().pushStr(msg)
 *         }
 *     }
 *     var LOG: ReentrantLock<Log> = ReentrantLock.new(Log(data = RefCell.new("")))
 *     fun <R> withLog(f: (Log) -> R): R {
 *         val log = LOG.lock()
 *         return f(log)
 *     }
 *     withLog({ log ->
 *         log.append("Hello")
 *         withLog({ log1 -> log1.append(" there!") })
 *     })
 * }
 * ```
 */
public open class ReentrantLock<T> internal constructor(
    public var mutex: Mutex,
    public var owner: Tid,
    lockCountCtorArg: UnsafeCell<UInt>,
    public var data: T,
) {
    public var lockCount: UnsafeCell<UInt> = lockCountCtorArg
        internal set

    // Because of the `UnsafeCell`, these traits are not implemented automatically

    /**
     * Consumes this lock, returning the underlying data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.ReentrantLock
     *
     * @feature(reentrantLock)
     * public fun doctestEntry() {
     *     val lock = ReentrantLock.new(0)
     *     check(lock.intoInner() == 0)
     * }
     * ```
     */
    public open fun intoInner(): T = this.data

    /**
     * Acquires the lock, blocking the current thread until it is able to do
     * so.
     *
     * This function will block the caller until it is available to acquire
     * the lock. Upon returning, the thread is the only thread with the lock
     * held. When the thread calling this method already holds the lock, the
     * call succeeds without blocking.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.Cell
     * import std.sync.ReentrantLock
     *
     * @feature(reentrantLock)
     * public fun doctestEntry() {
     *     val lock = ReentrantLock.new(Cell.new(0))
     *     val cLock = lock
     *     thread.spawn({
     *         cLock.lock().set(10)
     *     }).join().expect("`thread.spawn` should not fail")
     *     check(lock.lock().get() == 10)
     * }
     * ```
     */
    public open fun lock(): ReentrantLockGuard<T> {
        val thisThread = currentId()
        // Safety: We only touch lockCount when we own the inner mutex.
        // Additionally, we only call `self.owner.set()` while holding
        // the inner mutex, so no two threads can call it concurrently.
        if (this.owner.contains(thisThread)) {
            this.incrementLockCount().expect("lock count overflow in reentrant mutex")
        } else {
            this.mutex.lock()
            this.owner.set(thisThread)
            check(this.lockCount.get() == 0)
            this.lockCount.get() = 1
        }
        return ReentrantLockGuard(lock = this)
    }

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this call borrows the `ReentrantLock` mutably, no actual locking
     * needs to take place -- the mutable borrow statically guarantees no locks
     * exist.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.ReentrantLock
     *
     * @feature(reentrantLock)
     * public fun doctestEntry() {
     *     var lock = ReentrantLock.new(0)
     *     lock.getMut() = 10
     *     check(lock.lock() == 10)
     * }
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this::data
    }

    // Note maybe make it a public part of the API?

    /**
     * Attempts to acquire this lock.
     *
     * If the lock could not be acquired at this time, then `None` is returned.
     * Otherwise, an RAII guard is returned.
     *
     * This function does not block.
     */
    public open fun tryLock(): ReentrantLockGuard<T>? {
        val thisThread = currentId()
        // Safety: We only touch lockCount when we own the inner mutex.
        // Additionally, we only call `self.owner.set()` while holding
        // the inner mutex, so no two threads can call it concurrently.
        return if (this.owner.contains(thisThread)) {
            (this.incrementLockCount() ?: return null)
            ReentrantLockGuard(lock = this)
        } else if (this.mutex.tryLock()) {
            this.owner.set(thisThread)
            check(this.lockCount.get() == 0)
            this.lockCount.get() = 1
            ReentrantLockGuard(lock = this)
        } else {
            null
        }
    }

    /**
     * Returns a raw pointer to the underlying data.
     *
     * The returned pointer is always non-null and properly aligned, but it is
     * the user's responsibility to ensure that any reads through it are
     * properly synchronized to avoid data races, and that it is not read
     * through after the lock is dropped.
     */
    public open fun dataPtr(): ConstPtr = addrOf(this.data)

    public open fun incrementLockCount(): Unit? {
        run {
            this.lockCount.get() = (this.lockCount.get().checkedAdd(1) ?: return null)
        }
        return Unit
    }

    public open fun fmt(f: StringBuilder) {
        val d = DebugStruct(f, "ReentrantLock")
        when (val matchVal = this.tryLock()) {
            null -> d.field("data", "<locked>")
            else -> {
                val v = matchVal
                d.field("data", v)
            }
        }
        d.finishNonExhaustive()
    }

    public companion object {
        public operator fun <T> invoke(
            mutex: Mutex,
            owner: Tid,
            lockCount: UnsafeCell<UInt>,
            data: T,
        ): ReentrantLock<T> = ReentrantLock<T>(
            mutex = mutex,
            owner = owner,
            lockCountCtorArg = lockCount,
            data = data,
        )

        /**
         * Creates a new re-entrant lock in an unlocked state ready for use.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.ReentrantLock
         *
         * @feature(reentrantLock)
         * public fun doctestEntry() {
         *     val lock = ReentrantLock.new(0)
         * }
         * ```
         */
        public fun <T> new(t: T): ReentrantLock<T> = ReentrantLock(mutex = Mutex.new(), owner = Tid.new(), lockCount = UnsafeCell.new(0), data = t)

        public fun <T> default(defaultTFn: () -> T): ReentrantLock<T> = ReentrantLock.new(defaultTFn())

        public fun <T> from(t: T): ReentrantLock<T> = ReentrantLock.new(t)
    }
}

/**
 * An RAII implementation of a "scoped lock" of a re-entrant lock. When this
 * structure is dropped (falls out of scope), the lock will be unlocked.
 *
 * The data protected by the mutex can be accessed through this guard via its
 * [Deref] implementation.
 *
 * This structure is created by the [`lock`](ReentrantLock.lock) method on
 * [ReentrantLock].
 *
 * # Mutability
 *
 * Unlike [`MutexGuard`](MutexGuard), `ReentrantLockGuard` does not
 * implement [`DerefMut`](ops.DerefMut), because implementation of
 * the trait would violate Rust’s reference aliasing rules. Use interior
 * mutability (usually [`RefCell`](cell.RefCell)) in order to mutate
 * the guarded data.
 */
public open class ReentrantLockGuard<T>(
    public var lock: ReentrantLock<T>,
) {
    public open fun deref(): T = this.lock.data

    public open fun fmt(f: StringBuilder) {
        this.fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

    public open fun drop() {
        // Safety: We own the lock.
        run {
            this.lock.lockCount.get() -= 1
            if (this.lock.lockCount.get() == 0) {
                this.lock.owner.set(null)
                this.lock.mutex.unlock()
            }
        }
    }
}
