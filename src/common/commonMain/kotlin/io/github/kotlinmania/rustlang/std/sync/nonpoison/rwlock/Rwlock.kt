// port-lint: source sync/nonpoison/rwlock.rs
package io.github.kotlinmania.rustlang.std.sync.nonpoison.rwlock

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.sync.nonpoison.TryLockResult
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock

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

/**
 * A reader-writer lock that does not keep track of lock poisoning.
 *
 * For more information about reader-writer locks, check out the documentation for the poisoning
 * variant of this lock (which can be found at [poison.RwLock]).
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.nonpoison.RwLock
 *
 * @feature(nonpoisonRwlock)
 * public fun doctestEntry() {
 *     val lock = RwLock.new(5)
 *     // many reader locks can be held at once
 *     run {
 *         val r1 = lock.read()
 *         val r2 = lock.read()
 *         check(r1 == 5)
 *         check(r2 == 5)
 *     }
 *     // read locks are dropped at this point
 *     // only one write lock may be held, however
 *     run {
 *         var w = lock.write()
 *         w += 1
 *         check(w == 6)
 *     }
 *     // write lock is dropped here
 * }
 * ```
 */
public open class RwLock<T>(
    /**
     * The inner [sys.RwLock] that synchronizes thread access to the protected data.
     */
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock,
    /**
     * The lock-protected data.
     */
    public var data: UnsafeCell<T>,
) {
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    // Implementations
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Returns the contained value by cloning it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.getCloned() == 7)
     * }
     * ```
     */
    public open fun getCloned(): T = this.read().clone()

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Sets the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.getCloned() == 7)
     *     lock.set(11)
     *     check(lock.getCloned() == 11)
     * }
     * ```
     */
    public open fun set(value: T) {
        if (mem.needsDrop<T>()) {
            // If the contained value has a non-trivial destructor, we
            // call that destructor after the lock has been released.
            drop(this.replace(value))
        } else {
            this.write() = value
        }
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Replaces the contained value with `value`, and returns the old contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(7)
     *     check(lock.replace(11) == 7)
     *     check(lock.getCloned() == 11)
     * }
     * ```
     */
    public open fun replace(value: T): T {
        val guard = this.write()
        return mem.replace(guard, value)
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
     * # Panics
     *
     * This function might panic when called if the lock is already held by the current thread.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val lock = RwLock.new(1)
     *     val cLock = lock
     *     val n = lock.read()
     *     check(n == 1)
     *     thread.spawn({
     *         val r = cLock.read()
     *     }).join().unwrap()
     * }
     * ```
     */
    public open fun read(): RwLockReadGuard<T> =
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
     * This function will return the [WouldBlock] error if the `RwLock` could
     * not be acquired because it was already locked exclusively.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val lock = RwLock.new(1)
     *     run {
     *         val matchVal = lock.tryRead()
     *         if (matchVal.isSuccess) {
     *             val n = matchVal.getOrThrow()
     *             check(n == 1)
     *         } else {
     *             error("unreachable")
     *         }
     *     }
     * }
     * ```
     */
    public open fun tryRead(): TryLockResult<RwLockReadGuard<T>> =
        if (this.inner.tryRead()) {
            Result.Ok(RwLockReadGuard.new(this))
        } else {
            Result.Err(WouldBlock)
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
     * # Panics
     *
     * This function might panic when called if the lock is already held by the current thread.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val lock = RwLock.new(1)
     *     var n = lock.write()
     *     n = 2
     *     check(lock.tryRead().isErr())
     * }
     * ```
     */
    public open fun write(): RwLockWriteGuard<T> =
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
     * This function will return the [WouldBlock] error if the `RwLock` could
     * not be acquired because it was already locked.
     *
     * [WouldBlock]: WouldBlock
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val lock = RwLock.new(1)
     *     val n = lock.read()
     *     check(n == 1)
     *     check(lock.tryWrite().isErr())
     * }
     * ```
     */
    public open fun tryWrite(): TryLockResult<RwLockWriteGuard<T>> =
        if (this.inner.tryWrite()) {
            Result.Ok(RwLockWriteGuard.new(this))
        } else {
            Result.Err(WouldBlock)
        }

    /**
     * Consumes this `RwLock`, returning the underlying data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val lock = RwLock.new("")
     *     run {
     *         var s = lock.write()
     *         s = "modified".toOwned()
     *     }
     *     check(lock.intoInner() == "modified")
     * }
     * ```
     */
    public open fun intoInner(): T = this.data.intoInner()

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this call borrows the `RwLock` mutably, no actual locking needs to
     * take place -- the mutable borrow statically guarantees no new locks can be acquired
     * while this reference exists. Note that this method does not clear any previously abandoned
     * locks (e.g., via [forget] on a [RwLockReadGuard] or [RwLockWriteGuard]).
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(nonpoisonRwlock)
     * public fun doctestEntry() {
     *     var lock = RwLock.new(0)
     *     lock.getMut() = 10
     *     check(lock.read() == 10)
     * }
     * ```
     */
    public open fun getMut(): T = this.data.getMut()

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Returns a raw pointer to the underlying data.
     *
     * The returned pointer is always non-null and properly aligned, but it is
     * the user's responsibility to ensure that any reads and writes through it
     * are properly synchronized to avoid data races, and that it is not read
     * or written through after the lock is dropped.
     */
    public open fun dataPtr(): MutPtr = this.data.get()

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Locks this `RwLock` with shared read access to the underlying data by passing
     * a reference to the given closure.
     *
     * This method acquires the lock, calls the provided closure with a reference
     * to the data, and returns the result of the closure. The lock is released after
     * the closure completes, even if it panics.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(lockValueAccessors, nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val rwlock = RwLock.new(2)
     *     val result = rwlock.with({ data -> data + 3 })
     *     check(result == 5)
     * }
     * ```
     */
    public open fun <R> with(f: (T) -> R): R = f(this.read())

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    /**
     * Locks this `RwLock` with exclusive write access to the underlying data by passing
     * a mutable reference to the given closure.
     *
     * This method acquires the lock, calls the provided closure with a mutable reference
     * to the data, and returns the result of the closure. The lock is released after
     * the closure completes, even if it panics.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.RwLock
     *
     * @feature(lockValueAccessors, nonpoisonRwlock)
     * public fun doctestEntry() {
     *     val rwlock = RwLock.new(2)
     *     val result = rwlock.withMut({ data ->
     *         data += 3
     *         data + 5
     *     })
     *     check(rwlock.read() == 5)
     *     check(result == 10)
     * }
     * ```
     */
    public open fun <R> withMut(f: (T) -> R): R = f(this.write())

    public open fun fmt(f: StringBuilder) {
        val d = DebugStruct(f, "RwLock")
        run {
            val matchVal = this.tryRead()
            when {
                matchVal is Result.Ok<*> -> {
                    val guard = (matchVal as Result.Ok<*>).value
                    d.field("data", guard)
                }
                else -> {
                    val WouldBlock = (matchVal as Result.Err<*>).error
                    d.field("data", "<locked>")
                }
            }
        }
        d.finishNonExhaustive()
    }

    public companion object {
        /**
         * Creates a new instance of an `RwLock<T>` which is unlocked.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.nonpoison.RwLock
         *
         * @feature(nonpoisonRwlock)
         * public fun doctestEntry() {
         *     val lock = RwLock.new(5)
         * }
         * ```
         */
        public fun <T> new(t: T): RwLock<T> = RwLock(inner = io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex.RwLock.new(), data = UnsafeCell.new(t))

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
) {
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

    public open fun fmt(f: StringBuilder) {
        this.fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

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
        public fun <T> new(lock: RwLock<T>): RwLockReadGuard<T> = RwLockReadGuard(data = NonNull.newUnchecked(lock.data.get()), innerLock = lock.inner)

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
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

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> filterMap(orig: RwLockReadGuard<T>, f: (T) -> U?): Result<MappedRwLockReadGuard<U>, RwLockReadGuard<T>> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asRef())) {
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedRwLockReadGuard(data = data1, innerLock = orig1.innerLock))
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
) {
    public open fun drop() {
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

    public open fun fmt(f: StringBuilder) {
        this.fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

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
        public fun <T> new(lock: RwLock<T>): RwLockWriteGuard<T> = RwLockWriteGuard(lock = lock)

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
         * import std.sync.nonpoison.RwLock
         * import std.sync.nonpoison.RwLockWriteGuard
         *
         * @feature(nonpoisonRwlock)
         * public fun doctestEntry() {
         *     val rw = RwLock.new(0)
         *     var writeGuard = rw.write()
         *     writeGuard = 42
         *     val readGuard = RwLockWriteGuard.downgrade(writeGuard)
         *     check(42 == readGuard)
         * }
         * ```
         *
         * `downgrade` will _atomically_ change the state of the [RwLock] from exclusive mode into
         * shared mode. This means that it is impossible for another writing thread to get in between a
         * thread calling `downgrade` and any reads it performs after downgrading.
         *
         * ```kotlin
         * import std.sync.nonpoison.RwLock
         * import std.sync.nonpoison.RwLockWriteGuard
         *
         * @feature(nonpoisonRwlock)
         * public fun doctestEntry() {
         *     val rw = RwLock.new(1)
         *     // Put the lock in write mode.
         *     var mainWriteGuard = rw.write()
         *     val rwClone = rw.clone()
         *     val evilHandle = spawn({
         *         // This will not return until the main thread drops the `mainReadGuard`.
         *         var evilGuard = rwClone.write()
         *         check(evilGuard == 2)
         *         evilGuard = 3
         *     })
         *     mainWriteGuard = 2
         *     // Atomically downgrade the write guard into a read guard.
         *     val mainReadGuard = RwLockWriteGuard.downgrade(mainWriteGuard)
         *     // Since `downgrade` is atomic, the writer thread cannot have changed the protected data.
         *     check(mainReadGuard == 2)
         * }
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
            return RwLockReadGuard.new(lock)
        }

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> map(orig: RwLockWriteGuard<T>, f: (T) -> U): MappedRwLockWriteGuard<U> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.lock.data.get()))
            val orig1 = orig
            return MappedRwLockWriteGuard(data = data, innerLock = orig1.lock.inner, variance = Unit)
        }

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> filterMap(orig: RwLockWriteGuard<T>, f: (T) -> U?): Result<MappedRwLockWriteGuard<U>, RwLockWriteGuard<T>> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.lock.data.get())) {
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedRwLockWriteGuard(data = data1, innerLock = orig1.lock.inner, variance = Unit))
                }
            }
        }
    }
}

// @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
) {
    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun drop() {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        run {
            this.innerLock.readUnlock()
        }
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asRef()
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun fmt(f: StringBuilder) {
        this.fmt(f)
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

    public companion object {
        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
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

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> filterMap(orig: MappedRwLockReadGuard<T>, f: (T) -> U?): Result<MappedRwLockReadGuard<U>, MappedRwLockReadGuard<T>> {
            // SAFETY: the conditions of `RwLockReadGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asRef())) {
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedRwLockReadGuard(data = data1, innerLock = orig1.innerLock))
                }
            }
        }
    }
}

// @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
) {
    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun drop() {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        run {
            this.innerLock.writeUnlock()
        }
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun deref(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asRef()
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun derefMut(): T {
        // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
        // was created, and have been upheld throughout `map` and/or `filterMap`.
        return this.data.asMut()
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun fmt(f: StringBuilder) {
        this.fmt(f)
    }

    // @unstable(feature = "nonpoisonRwlock", issue = "134645")
    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

    public companion object {
        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> map(orig: MappedRwLockWriteGuard<T>, f: (T) -> U): MappedRwLockWriteGuard<U> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.data.asMut()))
            val orig1 = orig
            return MappedRwLockWriteGuard(data = data, innerLock = orig1.innerLock, variance = Unit)
        }

        // @unstable(feature = "nonpoisonRwlock", issue = "134645")

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
         * If the closure panics, the guard will be dropped (unlocked).
         */
        public fun <T, U> filterMap(orig: MappedRwLockWriteGuard<T>, f: (T) -> U?): Result<MappedRwLockWriteGuard<U>, MappedRwLockWriteGuard<T>> {
            // SAFETY: the conditions of `RwLockWriteGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the
            // reference passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asMut())) {
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedRwLockWriteGuard(data = data1, innerLock = orig1.innerLock, variance = Unit))
                }
            }
        }
    }
}
