// port-lint: source sync/poison/rwlock.rs
package io.github.kotlinmania.rustlang.std.sync.poison.rwlock

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.needsDrop
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.replace
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.poison.Flag
import io.github.kotlinmania.rustlang.std.sync.poison.Guard
import io.github.kotlinmania.rustlang.std.sync.poison.LockResult
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sync.poison.TryLockError
import io.github.kotlinmania.rustlang.std.sync.poison.TryLockResult
import io.github.kotlinmania.rustlang.std.sync.poison.mapResult

private class DebugStruct(
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

// // current active reader locks.

/**
 * A reader-writer lock
 *
 * This type of lock allows a number of readers or at most one writer at any
 * point in time. The write portion of this lock typically allows modification
 * of the underlying data (exclusive access) and the read portion of this lock
 * typically allows for read-only access (shared access).
 *
 * In comparison, a [Mutex] does not distinguish between readers or writers
 * that acquire the lock, therefore blocking any threads waiting for the lock to
 * become available. An `RwLock` will allow multiple readers to acquire the
 * lock as long as a writer is not holding the lock.
 *
 * The priority policy of the lock is dependent on the underlying operating
 * system's implementation, and this type does not guarantee that any
 * particular policy will be used. In particular, a writer which is waiting to
 * acquire the lock in `write` might or might not block concurrent calls to
 * `read`, e.g.:
 *
 * <details><summary>Potential deadlock example</summary>
 *
 * ```text
 * // Thread 1              |  // Thread 2
 * let _rg1 = lock.read();  |
 *                          |  // will block
 *                          |  let _wg = lock.write();
 * // may deadlock          |
 * let _rg2 = lock.read();  |
 * ```
 *
 * </details>
 *
 * The type parameter `T` represents the data that this lock protects. It is
 * required that `T` satisfies [Send] to be shared across threads and
 * [Sync] to allow concurrent access through readers. The RAII guards
 * returned from the locking methods implement [Deref] (and [DerefMut]
 * for the `write` methods) to allow access to the content of the lock.
 *
 * # Poisoning
 *
 * An `RwLock`, like [Mutex], will [usually] become poisoned on a panic. Note,
 * however, that an `RwLock` may only be poisoned if a panic occurs while it is
 * locked exclusively (write mode). If a panic occurs in any reader, then the
 * lock will not be poisoned.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.RwLock
 * import std.thread
 * import std.time.Duration
 *
 * val data = RwLock.new(5)
 * // Multiple readers can access in parallel.
 * for (i in 0 until 3) {
 *     val lockClone = data
 *     thread.spawn({
 *         val value = lockClone.read().unwrap()
 *         println("Reader $i: Read value $value, now holding lock...")
 *         // Simulating a long read operation
 *         thread.sleep(Duration.fromSecs(1))
 *         println("Reader $i: Dropping lock.")
 *     })
 * }
 * thread.sleep(Duration.fromMillis(100))
 * // Wait for readers to start
 * // While all readers can proceed, a call to .write() has to wait for
 * var writableData = data.write().unwrap()
 * println("Writer proceeds...")
 * writableData += 1
 * ```
 */
public open class RwLock<T>(
    /**
     * The inner [sys.RwLock] that synchronizes thread access to the protected data.
     */
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock,
    /**
     * A flag denoting if this `RwLock` has been poisoned.
     */
    public var poison: Flag,
    /**
     * The lock-protected data.
     */
    public var data: UnsafeCell<T>,
) {
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    // Implementations
    // ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the contained value by cloning it.
     *
     * # Errors
     *
     * This function will return an error if the `RwLock` is poisoned. An
     * `RwLock` is poisoned whenever a writer panics while holding an exclusive
     * lock.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.getCloned().unwrap() == 7)
     * }
     * ```
     */
    public open fun getCloned(): Result<T, PoisonError<Unit>> =
        run {
            val matchVal = this.read()
            when (matchVal) { is Result.Ok<*, *> -> {
                val guard = matchVal.okValue()
                Result.Ok<T, PoisonError<Unit>>(guard.clone())
            }; is Result.Err<*, *> -> {
                Result.Err<T, PoisonError<Unit>>(PoisonError.new(Unit))
            } }
        }

    /**
     * Sets the contained value.
     *
     * # Errors
     *
     * This function will return an error containing the provided `value` if
     * the `RwLock` is poisoned. An `RwLock` is poisoned whenever a writer
     * panics while holding an exclusive lock.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.getCloned().unwrap() == 7)
     *     lock.set(11).unwrap()
     *     check(lock.getCloned().unwrap() == 11)
     * }
     * ```
     */
    public open fun set(value: T): Result<Unit, PoisonError<T>> =
        if (needsDrop<T>()) {
            // If the contained value has non-trivial destructor, we
            // call that destructor after the lock being released.
            this.replace(value).map({ drop(it) })
        } else {
            run {
                val matchVal = this.write()
                when (matchVal) { is Result.Ok<*, *> -> {
                    val guard = matchVal.okValue()
                    guard = value
                    Result.Ok<Unit, PoisonError<T>>(Unit)
                }; is Result.Err<*, *> -> {
                    Result.Err<Unit, PoisonError<T>>(PoisonError.new(value))
                } }
            }
        }

    /**
     * Replaces the contained value with `value`, and returns the old contained value.
     *
     * # Errors
     *
     * This function will return an error containing the provided `value` if
     * the `RwLock` is poisoned. An `RwLock` is poisoned whenever a writer
     * panics while holding an exclusive lock.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.replace(11).unwrap() == 7)
     *     check(lock.getCloned().unwrap() == 11)
     * }
     * ```
     */
    public open fun replace(value: T): LockResult<T> =
        run {
            val matchVal = this.write()
            when (matchVal) { is Result.Ok<*, *> -> {
                val guard = matchVal.okValue()
                Result.Ok(io.github.kotlinmania.rustlang.core.mem.replace(guard, value))
            }; is Result.Err<*, *> -> {
                Result.Err(PoisonError.new(value))
            } }
        }

    /**
     * Locks this `RwLock` with shared read access, blocking the current thread
     * until it can be acquired.
     *
     * The calling thread will be blocked until there are no more writers which
     * hold the lock. There may be other readers currently inside the lock when
     * this method returns. This method does not provide any guarantees with
     * respect to the ordering of whether contentious readers or writers will
     * acquire the lock first.
     *
     * Returns an RAII guard which will release this thread's shared access
     * once it is dropped.
     *
     * # Errors
     *
     * This function will return an error if the `RwLock` is poisoned. An
     * `RwLock` is poisoned whenever a writer panics while holding an exclusive
     * lock. The failure will occur immediately after the lock has been
     * acquired. The acquired lock guard will be contained in the returned
     * error.
     *
     * # Panics
     *
     * This function might panic when called if the lock is already held by the current thread
     * in read or write mode.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     * import std.thread
     *
     * val lock = RwLock.new(1)
     * val cLock = lock
     * val n = lock.read().unwrap()
     * check(n == 1)
     * thread.spawn({
     *     val r = cLock.read()
     *     check(r.isOk())
     * }).join().unwrap()
     * ```
     */
    public open fun read(): LockResult<RwLockReadGuard<T>> =
        run {
            this.inner.read()
            RwLockReadGuard.new(this)
        }

    /**
     * Attempts to acquire this `RwLock` with shared read access.
     *
     * If the access could not be granted at this time, then `Err` is returned.
     * Otherwise, an RAII guard is returned which will release the shared access
     * when it is dropped.
     *
     * This function does not block.
     *
     * This function does not provide any guarantees with respect to the ordering
     * of whether contentious readers or writers will acquire the lock first.
     *
     * # Errors
     *
     * This function will return the [Poisoned] error if the `RwLock` is
     * poisoned. An `RwLock` is poisoned whenever a writer panics while holding
     * an exclusive lock. `Poisoned` will only be returned if the lock would
     * have otherwise been acquired. An acquired lock guard will be contained
     * in the returned error.
     *
     * This function will return the [WouldBlock] error if the `RwLock` could
     * not be acquired because it was already locked exclusively.
     *
     * [Poisoned]: TryLockError.Poisoned
     * [WouldBlock]: TryLockError.WouldBlock
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * val lock = RwLock.new(1)
     * run {
     *     val matchVal = lock.tryRead()
     *     if (matchVal.isSuccess) {
     *         val n = matchVal.getOrThrow()
     *         check(n == 1)
     *     } else {
     *         error("unreachable")
     *     }
     * }
     * ```
     */
    public open fun tryRead(): TryLockResult<RwLockReadGuard<T>> {
        return if (this.inner.tryRead()) {
            Result.Ok(when (val tryResult = RwLockReadGuard.new(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
        } else {
            Result.Err(TryLockError.WouldBlock())
        }
    }

    /**
     * Locks this `RwLock` with exclusive write access, blocking the current
     * thread until it can be acquired.
     *
     * This function will not return while other writers or other readers
     * currently have access to the lock.
     *
     * Returns an RAII guard which will drop the write access of this `RwLock`
     * when dropped.
     *
     * # Errors
     *
     * This function will return an error if the `RwLock` is poisoned. An
     * `RwLock` is poisoned whenever a writer panics while holding an exclusive
     * lock. An error will be returned when the lock is acquired. The acquired
     * lock guard will be contained in the returned error.
     *
     * # Panics
     *
     * This function might panic when called if the lock is already held by the current thread
     * in read or write mode.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * val lock = RwLock.new(1)
     * var n = lock.write().unwrap()
     * n = 2
     * check(lock.tryRead().isErr())
     * ```
     */
    public open fun write(): LockResult<RwLockWriteGuard<T>> =
        run {
            this.inner.write()
            RwLockWriteGuard.new(this)
        }

    /**
     * Attempts to lock this `RwLock` with exclusive write access.
     *
     * If the lock could not be acquired at this time, then `Err` is returned.
     * Otherwise, an RAII guard is returned which will release the lock when
     * it is dropped.
     *
     * This function does not block.
     *
     * This function does not provide any guarantees with respect to the ordering
     * of whether contentious readers or writers will acquire the lock first.
     *
     * # Errors
     *
     * This function will return the [Poisoned] error if the `RwLock` is
     * poisoned. An `RwLock` is poisoned whenever a writer panics while holding
     * an exclusive lock. `Poisoned` will only be returned if the lock would
     * have otherwise been acquired. An acquired lock guard will be contained
     * in the returned error.
     *
     * This function will return the [WouldBlock] error if the `RwLock` could
     * not be acquired because it was already locked.
     *
     * [Poisoned]: TryLockError.Poisoned
     * [WouldBlock]: TryLockError.WouldBlock
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * val lock = RwLock.new(1)
     * val n = lock.read().unwrap()
     * check(n == 1)
     * check(lock.tryWrite().isErr())
     * ```
     */
    public open fun tryWrite(): TryLockResult<RwLockWriteGuard<T>> {
        return if (this.inner.tryWrite()) {
            Result.Ok(when (val tryResult = RwLockWriteGuard.new(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") })
        } else {
            Result.Err(TryLockError.WouldBlock())
        }
    }

    /**
     * Determines whether the lock is poisoned.
     *
     * If another thread is active, the lock can still become poisoned at any
     * time. You should not trust a `false` value for program correctness
     * without additional synchronization.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     * import std.thread
     *
     * val lock = RwLock.new(0)
     * val cLock = lock
     * thread.spawn({
     *     val lock = cLock.write().unwrap()
     *     error("panic")
     * }).join()
     * check(lock.isPoisoned() == true)
     * ```
     */
    public open fun isPoisoned(): Boolean = this.poison.get()

    /**
     * Clear the poisoned state from a lock.
     *
     * If the lock is poisoned, it will remain poisoned until this function is called. This allows
     * recovering from a poisoned state and marking that it has recovered. For example, if the
     * value is overwritten by a known-good value, then the lock can be marked as un-poisoned. Or
     * possibly, the value could be inspected to determine if it is in a consistent state, and if
     * so the poison is removed.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     * import std.thread
     *
     * val lock = RwLock.new(0)
     * val cLock = lock
     * thread.spawn({
     *     val lock = cLock.write().unwrap()
     *     error("panic")
     * }).join()
     * check(lock.isPoisoned() == true)
     * val guard = lock.write().unwrapOrElse({ e ->
     *     e.getMut() = 1
     *     lock.clearPoison()
     *     e.intoInner()
     * })
     * check(lock.isPoisoned() == false)
     * check(guard == 1)
     * ```
     */
    public open fun clearPoison() {
        this.poison.clear()
    }

    /**
     * Consumes this `RwLock`, returning the underlying data.
     *
     * # Errors
     *
     * This function will return an error containing the underlying data if
     * the `RwLock` is poisoned. An `RwLock` is poisoned whenever a writer
     * panics while holding an exclusive lock. An error will only be returned
     * if the lock would have otherwise been acquired.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * val lock = RwLock.new("")
     * run {
     *     var s = lock.write().unwrap()
     *     s = "modified".toOwned()
     * }
     * check(lock.intoInner().unwrap() == "modified")
     * ```
     */
    public open fun intoInner(): LockResult<T> {
        val data = this.data.intoInner()
        return mapResult(this.poison.borrow(), { data })
    }

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this call borrows the `RwLock` mutably, no actual locking needs to
     * take place -- the mutable borrow statically guarantees no new locks can be acquired
     * while this reference exists. Note that this method does not clear any previously abandoned
     * locks (e.g., via [forget] on a [RwLockReadGuard] or [RwLockWriteGuard]).
     *
     * # Errors
     *
     * This function will return an error containing a mutable reference to
     * the underlying data if the `RwLock` is poisoned. An `RwLock` is
     * poisoned whenever a writer panics while holding an exclusive lock.
     * An error will only be returned if the lock would have otherwise been
     * acquired.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.RwLock
     *
     * var lock = RwLock.new(0)
     * lock.getMut()!! = 10
     * check(lock.read().unwrap() == 10)
     * ```
     */
    public open fun getMut(): LockResult<T> {
        val data = this.data.getMut()
        return mapResult(this.poison.borrow(), { data })
    }

    /**
     * Returns a raw pointer to the underlying data.
     *
     * The returned pointer is always non-null and properly aligned, but it is
     * the user's responsibility to ensure that any reads and writes through it
     * are properly synchronized to avoid data races, and that it is not read
     * or written through after the lock is dropped.
     */
    public open fun dataPtr(): MutPtr = this.data.get()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        val d = DebugStruct(f, "RwLock")
        run {
            val matchVal = this.tryRead()
            when {
                matchVal is Result.Ok<*, *> -> {
                    val guard = matchVal.okValue()
                    d.field("data", guard)
                }
                matchVal is Result.Err<*, *> && matchVal.errValue() is TryLockError.Poisoned<*> -> {
                    val errValueVal = matchVal.errValue()
                    val (err) = errValueVal as TryLockError.Poisoned<*>
                    d.field("data", err.getRef())
                }
                else -> {
                    val errValueVal1 = (matchVal as Result.Err<*, *>).errValue()
                    d.field("data", "<locked>")
                }
            }
        }
        d.field("poisoned", this.poison.get())
        return run { d.finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }
    }

    public companion object {
        /**
         * Creates a new instance of an `RwLock<T>` which is unlocked.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.RwLock
         *
         * val lock = RwLock.new(5)
         * ```
         */
        public fun <T> new(t: T): RwLock<T> = RwLock(inner = io.github.kotlinmania.rustlang.std.sys.sync.rwlock.new(), poison = Flag.new(), data = UnsafeCell.new(t))

        /**
         * Creates a new `RwLock<T>`, with the `Default` value for T.
         */
        public fun <T> default(defaultTFn: () -> T): RwLock<T> = RwLock.new(defaultTFn())

        /**
         * Creates a new instance of an `RwLock<T>` which is unlocked.
         * This is equivalent to [RwLock.new].
         */
        public fun <T> from(t: T): RwLock<T> = RwLock.new(t)
    }
}

// ///////////////////////////////////////////////////////////////////////////////////////////////
// Guards
// ///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * RAII structure used to release the shared read access of a lock when
 * dropped.
 *
 * This structure is created by the [read] and [tryRead] methods on
 * [RwLock].
 *
 * [read]: RwLock.read
 * [tryRead]: RwLock.tryRead
 */
public open class RwLockReadGuard<T>(
    /**
     * A pointer to the data protected by the `RwLock`. Note that we use a pointer here instead of
     * `T` to avoid `noalias` violations, because a `RwLockReadGuard` instance only holds
     * immutability until it drops, not for its whole scope.
     * `NonNull` is preferable over `* const T` to allow for niche optimizations. `NonNull` is also
     * covariant over `T`, just like we would have with `T`.
     */
    public var data: NonNull,
    /**
     * A reference to the internal [sys.RwLock] that we have read-locked.
     */
    public var innerLock: io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock,
) : AutoCloseable {
    override fun close() {
        this.drop()
    }

    public open fun drop() {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when created.
        run {
            this.innerLock.readUnlock()
        }
    }

    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when created.
        return this.data.asRef()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        /**
         * Creates a new instance of `RwLockReadGuard<T>` from a `RwLock<T>`.
         *
         * # Safety
         *
         * This function is safe if and only if the same thread has successfully and safely called
         * `lock.inner.read()`, `lock.inner.tryRead()`, or `lock.inner.downgrade()` before
         * instantiating this object.
         */
        public fun <T> new(lock: RwLock<T>): LockResult<RwLockReadGuard<T>> = mapResult(lock.poison.borrow(), { RwLockReadGuard(data = NonNull.newUnchecked(lock.data.get()), innerLock = lock.inner) })

        /**
         * Makes a [MappedRwLockReadGuard] for a component of the borrowed data, e.g.
         * an enum variant.
         *
         * The `RwLock` is already locked for reading, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RwLockReadGuard.map(...)`. A method would interfere with methods of
         * the same name on the contents of the `RwLockReadGuard` used through
         * `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will not be
         * poisoned.
         */
        public fun <T, U> map(orig: RwLockReadGuard<T>, f: (T) -> U): MappedRwLockReadGuard<U> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.data.asRef()))
            val orig1 = orig
            return MappedRwLockReadGuard(data = data, innerLock = orig1.innerLock)
        }

        /**
         * Makes a [MappedRwLockReadGuard] for a component of the borrowed data. The
         * original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `RwLock` is already locked for reading, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RwLockReadGuard.filterMap(...)`. A method would interfere with methods
         * of the same name on the contents of the `RwLockReadGuard` used through
         * `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will not be
         * poisoned.
         */
        public fun <T, U> filterMap(orig: RwLockReadGuard<T>, f: (T) -> U?): Result<MappedRwLockReadGuard<U>, RwLockReadGuard<T>> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asRef())) {
                null -> Result.Err<MappedRwLockReadGuard<U>, RwLockReadGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedRwLockReadGuard<U>, RwLockReadGuard<T>>(MappedRwLockReadGuard(data = data1, innerLock = orig1.innerLock))
                }
            }
        }
    }
}

/**
 * RAII structure used to release the exclusive write access of a lock when
 * dropped.
 *
 * This structure is created by the [write] and [tryWrite] methods
 * on [RwLock].
 *
 * [write]: RwLock.write
 * [tryWrite]: RwLock.tryWrite
 */
public open class RwLockWriteGuard<T>(
    /**
     * A reference to the [RwLock] that we have write-locked.
     */
    public var lock: RwLock<T>,
    /**
     * The poison guard. See the [poison] module for more information.
     */
    public var poison: Guard,
) : AutoCloseable {
    override fun close() {
        this.drop()
    }

    public open fun drop() {
        this.lock.poison.done(this.poison)
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when created.
        run {
            this.lock.inner.writeUnlock()
        }
    }

    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when created.
        return this.lock.data.get()
    }

    public open fun derefMut(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when created.
        return this.lock.data.get()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        /**
         * Creates a new instance of `RwLockWriteGuard<T>` from a `RwLock<T>`.
         *
         * # Safety
         *
         * This function is safe if and only if the same thread has successfully and safely called
         * `lock.inner.write()`, `lock.inner.tryWrite()`, or `lock.inner.tryUpgrade` before
         * instantiating this object.
         */
        public fun <T> new(lock: RwLock<T>): LockResult<RwLockWriteGuard<T>> = mapResult(lock.poison.guard(), { guard -> RwLockWriteGuard(lock = lock, poison = guard) })

        /**
         * Downgrades a write-locked `RwLockWriteGuard` into a read-locked [RwLockReadGuard].
         *
         * Since we have the `RwLockWriteGuard`, the [RwLock] must already be locked for writing, so
         * this method cannot fail.
         *
         * After downgrading, other readers will be allowed to read the protected data.
         *
         * # Examples
         *
         * `downgrade` takes ownership of the `RwLockWriteGuard` and returns a [RwLockReadGuard].
         *
         * ```kotlin
         * import std.sync.RwLock
         * import std.sync.RwLockWriteGuard
         *
         * val rw = RwLock.new(0)
         * var writeGuard = rw.write().unwrap()
         * writeGuard = 42
         * val readGuard = RwLockWriteGuard.downgrade(writeGuard)
         * check(42 == readGuard)
         * ```
         *
         * `downgrade` will _atomically_ change the state of the [RwLock] from exclusive mode into
         * shared mode. This means that it is impossible for another writing thread to get in between a
         * thread calling `downgrade` and any reads it performs after downgrading.
         *
         * ```kotlin
         * import std.sync.RwLock
         * import std.sync.RwLockWriteGuard
         *
         * val rw = RwLock.new(1)
         * // Put the lock in write mode.
         * var mainWriteGuard = rw.write().unwrap()
         * val rwClone = rw.clone()
         * val evilHandle = spawn({
         *     // This will not return until the main thread drops the `mainReadGuard`.
         *     var evilGuard = rwClone.write().unwrap()
         *     check(evilGuard == 2)
         *     evilGuard = 3
         * })
         * mainWriteGuard = 2
         * // Atomically downgrade the write guard into a read guard.
         * val mainReadGuard = RwLockWriteGuard.downgrade(mainWriteGuard)
         * // Since `downgrade` is atomic, the writer thread cannot have changed the protected data.
         * check(mainReadGuard == 2)
         * ```
         */
        public fun <T> downgrade(s: RwLockWriteGuard<T>): RwLockReadGuard<T> {
            val lock = s.lock
            // We don't want to call the destructor since that calls `writeUnlock`.
            forget(s)
            // SAFETY: We take ownership of a write guard, so we must already have the `RwLock` in write
            // mode, satisfying the `downgrade` contract.
            lock.inner.downgrade()
            // SAFETY: We have just successfully called `downgrade`, so we fulfill the safety contract.
            return RwLockReadGuard.new(lock).unwrapOrElse({ it.intoInner() })
        }

        /**
         * Makes a [MappedRwLockWriteGuard] for a component of the borrowed data, e.g.
         * an enum variant.
         *
         * The `RwLock` is already locked for writing, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RwLockWriteGuard.map(...)`. A method would interfere with methods of
         * the same name on the contents of the `RwLockWriteGuard` used through
         * `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will be poisoned.
         */
        public fun <T, U> map(orig: RwLockWriteGuard<T>, f: (T) -> U): MappedRwLockWriteGuard<U> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.lock.data.get()))
            val orig1 = orig
            return MappedRwLockWriteGuard(data = data, innerLock = orig1.lock.inner, poisonFlag = orig1.lock.poison, poisonGuard = orig1.poison.clone(), variance = Unit)
        }

        /**
         * Makes a [MappedRwLockWriteGuard] for a component of the borrowed data. The
         * original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `RwLock` is already locked for writing, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `RwLockWriteGuard.filterMap(...)`. A method would interfere with methods
         * of the same name on the contents of the `RwLockWriteGuard` used through
         * `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will be poisoned.
         */
        public fun <T, U> filterMap(orig: RwLockWriteGuard<T>, f: (T) -> U?): Result<MappedRwLockWriteGuard<U>, RwLockWriteGuard<T>> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.lock.data.get())) {
                null -> Result.Err<MappedRwLockWriteGuard<U>, RwLockWriteGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedRwLockWriteGuard<U>, RwLockWriteGuard<T>>(MappedRwLockWriteGuard(data = data1, innerLock = orig1.lock.inner, poisonFlag = orig1.lock.poison, poisonGuard = orig1.poison.clone(), variance = Unit))
                }
            }
        }
    }
}

/**
 * RAII structure used to release the shared read access of a lock when
 * dropped, which can point to a subfield of the protected data.
 *
 * This structure is created by the [map] and [filterMap] methods
 * on [RwLockReadGuard].
 *
 * [map]: RwLockReadGuard.map
 * [filterMap]: RwLockReadGuard.filterMap
 */
public open class MappedRwLockReadGuard<T>(
    /**
     * A pointer to the data protected by the `RwLock`. Note that we use a pointer here instead of
     * `T` to avoid `noalias` violations, because a `MappedRwLockReadGuard` instance only
     * holds immutability until it drops, not for its whole scope.
     * `NonNull` is preferable over `* const T` to allow for niche optimizations. `NonNull` is also
     * covariant over `T`, just like we would have with `T`.
     */
    public var data: NonNull,
    /**
     * A reference to the internal [sys.RwLock] that we have read-locked.
     */
    public var innerLock: io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock,
) : AutoCloseable {
    override fun close() {
        this.drop()
    }

    public open fun drop() {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        run {
            this.innerLock.readUnlock()
        }
    }

    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asRef()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        /**
         * Makes a [MappedRwLockReadGuard] for a component of the borrowed data,
         * e.g. an enum variant.
         *
         * The `RwLock` is already locked for reading, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedRwLockReadGuard.map(...)`. A method would interfere with
         * methods of the same name on the contents of the `MappedRwLockReadGuard`
         * used through `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will not be
         * poisoned.
         */
        public fun <T, U> map(orig: MappedRwLockReadGuard<T>, f: (T) -> U): MappedRwLockReadGuard<U> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.data.asRef()))
            val orig1 = orig
            return MappedRwLockReadGuard(data = data, innerLock = orig1.innerLock)
        }

        /**
         * Makes a [MappedRwLockReadGuard] for a component of the borrowed data.
         * The original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `RwLock` is already locked for reading, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedRwLockReadGuard.filterMap(...)`. A method would interfere with
         * methods of the same name on the contents of the `MappedRwLockReadGuard`
         * used through `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will not be
         * poisoned.
         */
        public fun <T, U> filterMap(orig: MappedRwLockReadGuard<T>, f: (T) -> U?): Result<MappedRwLockReadGuard<U>, MappedRwLockReadGuard<T>> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asRef())) {
                null -> Result.Err<MappedRwLockReadGuard<U>, MappedRwLockReadGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedRwLockReadGuard<U>, MappedRwLockReadGuard<T>>(MappedRwLockReadGuard(data = data1, innerLock = orig1.innerLock))
                }
            }
        }
    }
}

/**
 * RAII structure used to release the exclusive write access of a lock when
 * dropped, which can point to a subfield of the protected data.
 *
 * This structure is created by the [map] and [filterMap] methods
 * on [RwLockWriteGuard].
 *
 * [map]: RwLockWriteGuard.map
 * [filterMap]: RwLockWriteGuard.filterMap
 */
public open class MappedRwLockWriteGuard<T>(
    /**
     * A pointer to the data protected by the `RwLock`. Note that we use a pointer here instead of
     * `T` to avoid `noalias` violations, because a `MappedRwLockWriteGuard` instance only
     * holds uniquneness until it drops, not for its whole scope.
     * `NonNull` is preferable over `* const T` to allow for niche optimizations.
     */
    public var data: NonNull,
    /**
     * `NonNull` is covariant over `T`, so we add a `PhantomData<T>` field here to
     * enforce the correct invariance over `T`.
     */
    public var variance: Unit,
    /**
     * A reference to the internal [sys.RwLock] that we have write-locked.
     */
    public var innerLock: io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock,
    /**
     * A reference to the original `RwLock`s poison state.
     */
    public var poisonFlag: Flag,
    /**
     * The poison guard. See the [poison] module for more information.
     */
    public var poisonGuard: Guard,
) : AutoCloseable {
    override fun close() {
        this.drop()
    }

    public open fun drop() {
        this.poisonFlag.done(this.poisonGuard)
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        run {
            this.innerLock.writeUnlock()
        }
    }

    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asRef()
    }

    public open fun derefMut(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asMut()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        /**
         * Makes a [MappedRwLockWriteGuard] for a component of the borrowed data,
         * e.g. an enum variant.
         *
         * The `RwLock` is already locked for writing, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedRwLockWriteGuard.map(...)`. A method would interfere with
         * methods of the same name on the contents of the `MappedRwLockWriteGuard`
         * used through `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will be poisoned.
         */
        public fun <T, U> map(orig: MappedRwLockWriteGuard<T>, f: (T) -> U): MappedRwLockWriteGuard<U> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.data.asMut()))
            val orig1 = orig
            return MappedRwLockWriteGuard(data = data, innerLock = orig1.innerLock, poisonFlag = orig1.poisonFlag, poisonGuard = orig1.poisonGuard.clone(), variance = Unit)
        }

        /**
         * Makes a [MappedRwLockWriteGuard] for a component of the borrowed data.
         * The original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `RwLock` is already locked for writing, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedRwLockWriteGuard.filterMap(...)`. A method would interfere with
         * methods of the same name on the contents of the `MappedRwLockWriteGuard`
         * used through `Deref`.
         *
         * # Panics
         *
         * If the closure panics, the guard will be dropped (unlocked) and the RwLock will be poisoned.
         */
        public fun <T, U> filterMap(orig: MappedRwLockWriteGuard<T>, f: (T) -> U?): Result<MappedRwLockWriteGuard<U>, MappedRwLockWriteGuard<T>> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asMut())) {
                null -> Result.Err<MappedRwLockWriteGuard<U>, MappedRwLockWriteGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedRwLockWriteGuard<U>, MappedRwLockWriteGuard<T>>(MappedRwLockWriteGuard(data = data1, innerLock = orig1.innerLock, poisonFlag = orig1.poisonFlag, poisonGuard = orig1.poisonGuard.clone(), variance = Unit))
                }
            }
        }
    }
}
