// port-lint: source sync/nonpoison/mutex.rs
package io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.replace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
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
 * A mutual exclusion primitive useful for protecting shared data that does not keep track of
 * lock poisoning.
 *
 * For more information about mutexes, check out the documentation for the poisoning variant of
 * this lock at [poison.Mutex].
 *
 * # Examples
 *
 * Note that this `Mutex` does **not** propagate threads that panic while holding the lock via
 * poisoning. If you need this functionality, see [poison.Mutex].
 *
 * ```kotlin
 * import std.sync.nonpoison.Mutex
 * import std.thread
 *
 * @feature(nonpoisonMutex)
 * public fun doctestEntry() {
 *     val mutex = Mutex.new(0u)
 *     var handles = mutableListOf()
 *     for (n in 0 until 10) {
 *         val m = mutex
 *         val handle = thread.spawn({
 *             var guard = m.lock()
 *             guard += 1
 *             error("panic from thread $n $guard")
 *         })
 *         handles.add(handle)
 *     }
 *     for (h in handles) {
 *         h.join()
 *     }
 *     println("Finished, locked ${mutex.lock()} times")
 * }
 * ```
 */
public open class Mutex<T>(
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex,
    public var data: UnsafeCell<T>,
) {
    /**
     * `T` must be `Send` for a [Mutex] to be `Send` because it is possible to acquire
     * the owned `T` from the `Mutex` via [intoInner].
     *
     * [intoInner]: Mutex.intoInner
     */

    /**
     * `T` must be `Send` for [Mutex] to be `Sync`.
     * This ensures that the protected data can be accessed safely from multiple threads
     * without causing data races or other unchecked behavior.
     *
     * [Mutex.T] provides mutable access to `T` to one thread at a time. However, it's essential
     * for `T` to be `Send` because it's not safe for non-`Send` structures to be accessed in
     * this manner. For instance, consider [Rc], a non-atomic reference counted smart pointer,
     * which is not `Send`. With `Rc`, we can have multiple copies pointing to the same heap
     * allocation with a non-atomic reference count. If we were to use `Mutex<Rc<_>>`, it would
     * only protect one instance of `Rc` from shared access, leaving other copies vulnerable
     * to potential data races.
     *
     * Also note that it is not necessary for `T` to be `Sync` as `T` is only made available
     * to one thread at a time if `T` is not `Sync`.
     */

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    /**
     * Returns the contained value by cloning it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(nonpoisonMutex)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.getCloned() == 7)
     * }
     * ```
     */
    public open fun getCloned(): T = this.lock().clone()

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    /**
     * Sets the contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(nonpoisonMutex)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.getCloned() == 7)
     *     mutex.set(11)
     *     check(mutex.getCloned() == 11)
     * }
     * ```
     */
    public open fun set(value: T) {
        if (needsDrop<T>()) {
            // If the contained value has a non-trivial destructor, we
            // call that destructor after the lock has been released.
            (this.replace(value)).let { }
        } else {
            this.lock() = value
        }
    }

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    /**
     * Replaces the contained value with `value`, and returns the old contained value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(nonpoisonMutex)
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.replace(11) == 7)
     *     check(mutex.getCloned() == 11)
     * }
     * ```
     */
    public open fun replace(value: T): T {
        val guard = this.lock()
        return replace(guard, value)
    }

    /**
     * Acquires a mutex, blocking the current thread until it is able to do so.
     *
     * This function will block the local thread until it is available to acquire
     * the mutex. Upon returning, the thread is the only thread with the lock
     * held. An RAII guard is returned to allow scoped unlock of the lock. When
     * the guard goes out of scope, the mutex will be unlocked.
     *
     * The exact behavior on locking a mutex in the thread which already holds
     * the lock is left unspecified. However, this function will not return on
     * the second call (it might panic or deadlock, for example).
     *
     * # Panics
     *
     * This function might panic when called if the lock is already held by
     * the current thread.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     * import std.thread
     *
     * @feature(nonpoisonMutex)
     * public fun doctestEntry() {
     *     val mutex = Mutex.new(0)
     *     val cMutex = mutex
     *     thread.spawn({
     *         cMutex.lock() = 10
     *     }).join().expect("`thread.spawn` should not fail")
     *     check(mutex.lock() == 10)
     * }
     * ```
     */
    public open fun lock(): MutexGuard<T> =
        run {
            this.inner.lock()
            MutexGuard.new(this)
        }

    /**
     * Attempts to acquire this lock.
     *
     * This function does not block. If the lock could not be acquired at this time, then
     * [WouldBlock] is returned. Otherwise, an RAII guard is returned.
     *
     * The lock will be unlocked when the guard is dropped.
     *
     * # Errors
     *
     * If the mutex could not be acquired because it is already locked, then this call will return
     * the [WouldBlock] error.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     * import std.thread
     *
     * val mutex = Mutex.new(0)
     * val cMutex = mutex
     * thread.spawn({
     *     var lock = cMutex.tryLock()
     *     if (lock is Ok) {
     *         val (mutex1) = lock
     *         mutex1 = 10
     *     } else {
     *         println("tryLock failed")
     *     }
     * }).join().expect("`thread.spawn` should not fail")
     * check(mutex.lock().unwrap() == 10)
     * ```
     */
    public open fun tryLock(): TryLockResult<MutexGuard<T>> =
        if (this.inner.tryLock()) {
            Result.Ok(MutexGuard.new(this))
        } else {
            Result.Err(WouldBlock)
        }

    /**
     * Consumes this mutex, returning the underlying data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(nonpoisonMutex)
     * public fun doctestEntry() {
     *     val mutex = Mutex.new(0)
     *     check(mutex.intoInner() == 0)
     * }
     * ```
     */
    public open fun intoInner(): T = this.data.intoInner()

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this call borrows the `Mutex` mutably, no actual locking needs to
     * take place -- the mutable borrow statically guarantees no locks exist.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(nonpoisonMutex)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(0)
     *     mutex.getMut() = 10
     *     check(mutex.lock() == 10)
     * }
     * ```
     */
    public open fun getMut(): T = this.data.getMut()

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    /**
     * Returns a raw pointer to the underlying data.
     *
     * The returned pointer is always non-null and properly aligned, but it is
     * the user's responsibility to ensure that any reads and writes through it
     * are properly synchronized to avoid data races, and that it is not read
     * or written through after the mutex is dropped.
     */
    public open fun dataPtr(): MutPtr = this.data.get()

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    /**
     * Acquires the mutex and provides mutable access to the underlying data by passing
     * a mutable reference to the given closure.
     *
     * This method acquires the lock, calls the provided closure with a mutable reference
     * to the data, and returns the result of the closure. The lock is released after
     * the closure completes, even if it panics.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.nonpoison.Mutex
     *
     * @feature(lockValueAccessors, nonpoisonMutex)
     * public fun doctestEntry() {
     *     val mutex = Mutex.new(2)
     *     val result = mutex.withMut({ data ->
     *         data += 3
     *         data + 5
     *     })
     *     check(mutex.lock() == 5)
     *     check(result == 10)
     * }
     * ```
     */
    public open fun <R> withMut(f: (T) -> R): R = f(this.lock())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        val d = DebugStruct(f, "Mutex")
        run {
            val matchVal = this.tryLock()
            when {
                matchVal is Result.Ok<*, *> -> {
                    val guard = matchVal.okValue()
                    d.field("data", guard)
                }
                else -> {
                    val WouldBlock = (matchVal as Result.Err<*, *>).errValue()
                    d.field("data", "<locked>")
                }
            }
        }
        return d.finishNonExhaustive()
    }

    public companion object {
        /**
         * Creates a new mutex in an unlocked state ready for use.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.nonpoison.Mutex
         *
         * @feature(nonpoisonMutex)
         * public fun doctestEntry() {
         *     val mutex = Mutex.new(0)
         * }
         * ```
         */
        public fun <T> new(t: T): Mutex<T> = Mutex(inner = io.github.kotlinmania.rustlang.std.sys.sync.mutex.new(), data = UnsafeCell.new(t))

        /**
         * Creates a new mutex in an unlocked state ready for use.
         * This is equivalent to [Mutex.new].
         */
        public fun <T> from(t: T): Mutex<T> = Mutex.new(t)

        /**
         * Creates a `Mutex<T>`, with the `Default` value for T.
         */
        public fun <T> default(defaultTFn: () -> T): Mutex<T> = Mutex.new(defaultTFn())
    }
}

/**
 * An RAII implementation of a "scoped lock" of a mutex. When this structure is
 * dropped (falls out of scope), the lock will be unlocked.
 *
 * The data protected by the mutex can be accessed through this guard via its
 * [Deref] and [DerefMut] implementations.
 *
 * This structure is created by the [lock] and [tryLock] methods on
 * [Mutex].
 *
 * [lock]: Mutex.lock
 * [tryLock]: Mutex.tryLock
 */
public open class MutexGuard<T>(
    public var lock: Mutex<T>,
) {
    /**
     * A [MutexGuard] is not `Send` to maximize platform portability.
     *
     * On platforms that use POSIX threads (commonly referred to as pthreads) there is a requirement to
     * release mutex locks on the same thread they were acquired.
     * For this reason, [MutexGuard] must not implement `Send` to prevent it being dropped from
     * another thread.
     */

    /**
     * `T` must be `Sync` for a [MutexGuard.T] to be `Sync`
     * because it is possible to get a `T` from `MutexGuard` (via `Deref`).
     */

    public open fun deref(): T = this.lock.data.get()

    public open fun derefMut(): T = this.lock.data.get()

    public open fun drop() {
        run {
            this.lock.inner.unlock()
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        public fun <T> new(lock: Mutex<T>): MutexGuard<T> = MutexGuard(lock = lock)

        // @unstable(feature = "nonpoisonMutex", issue = "134645")

        /**
         * Makes a [MappedMutexGuard] for a component of the borrowed data, e.g.
         * an enum variant.
         *
         * The `Mutex` is already locked, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MutexGuard.map(...)`. A method would interfere with methods of the
         * same name on the contents of the `MutexGuard` used through `Deref`.
         */
        public fun <T, U> map(orig: MutexGuard<T>, f: (T) -> U): MappedMutexGuard<U> {
            // SAFETY: the conditions of `MutexGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the reference
            // passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.lock.data.get()))
            val orig1 = orig
            return MappedMutexGuard(data = data, inner = orig1.lock.inner, variance = Unit)
        }

        // @unstable(feature = "nonpoisonMutex", issue = "134645")

        /**
         * Makes a [MappedMutexGuard] for a component of the borrowed data. The
         * original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `Mutex` is already locked, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MutexGuard.filterMap(...)`. A method would interfere with methods of the
         * same name on the contents of the `MutexGuard` used through `Deref`.
         */
        public fun <T, U> filterMap(orig: MutexGuard<T>, f: (T) -> U?): Result<MappedMutexGuard<U>, MutexGuard<T>> {
            // SAFETY: the conditions of `MutexGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the reference
            // passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.lock.data.get())) {
                null -> Result.Err<MappedMutexGuard<U>, MutexGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedMutexGuard<U>, MutexGuard<T>>(MappedMutexGuard(data = data1, inner = orig1.lock.inner, variance = Unit))
                }
            }
        }
    }
}

// @unstable(feature = "nonpoisonMutex", issue = "134645")

/**
 * An RAII mutex guard returned by `MutexGuard.map`, which can point to a
 * subfield of the protected data. When this structure is dropped (falls out
 * of scope), the lock will be unlocked.
 *
 * The main difference between `MappedMutexGuard` and [MutexGuard] is that the
 * former cannot be used with [Condvar], since that could introduce soundness issues if the
 * locked object is modified by another thread while the `Mutex` is unlocked.
 *
 * The data protected by the mutex can be accessed through this guard via its
 * [Deref] and [DerefMut] implementations.
 *
 * This structure is created by the [map] and [filterMap] methods on
 * [MutexGuard].
 *
 * [map]: MutexGuard.map
 * [filterMap]: MutexGuard.filterMap
 */
public open class MappedMutexGuard<T>(
    // NB: we use a pointer instead of `T` to avoid `noalias` violations, because a
    // `MappedMutexGuard` argument doesn't hold uniqueness for its whole scope, only until it drops.
    // `NonNull` is covariant over `T`, so we add a `PhantomData<T>` field
    // below for the correct variance over `T` (invariance).
    public var data: NonNull,
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex,
    public var variance: Unit,
) {
    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    // @unstable(feature = "nonpoisonMutex", issue = "134645")

    public open fun deref(): T = this.data.asRef()

    public open fun derefMut(): T = this.data.asMut()

    public open fun drop() {
        run {
            this.inner.unlock()
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = this.fmt(f)

    public companion object {
        // @unstable(feature = "nonpoisonMutex", issue = "134645")

        /**
         * Makes a [MappedMutexGuard] for a component of the borrowed data, e.g.
         * an enum variant.
         *
         * The `Mutex` is already locked, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedMutexGuard.map(...)`. A method would interfere with methods of the
         * same name on the contents of the `MutexGuard` used through `Deref`.
         */
        public fun <T, U> map(orig: MappedMutexGuard<T>, f: (T) -> U): MappedMutexGuard<U> {
            // SAFETY: the conditions of `MutexGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the reference
            // passed to it. If the closure panics, the guard will be dropped.
            val data = NonNull.from(f(orig.data.asMut()))
            val orig1 = orig
            return MappedMutexGuard(data = data, inner = orig1.inner, variance = Unit)
        }

        // @unstable(feature = "nonpoisonMutex", issue = "134645")

        /**
         * Makes a [MappedMutexGuard] for a component of the borrowed data. The
         * original guard is returned as an `Err(...)` if the closure returns
         * `None`.
         *
         * The `Mutex` is already locked, so this cannot fail.
         *
         * This is an associated function that needs to be used as
         * `MappedMutexGuard.filterMap(...)`. A method would interfere with methods of the
         * same name on the contents of the `MutexGuard` used through `Deref`.
         */
        public fun <T, U> filterMap(orig: MappedMutexGuard<T>, f: (T) -> U?): Result<MappedMutexGuard<U>, MappedMutexGuard<T>> {
            // SAFETY: the conditions of `MutexGuard.new` were satisfied when the original guard
            // was created, and have been upheld throughout `map` and/or `filterMap`.
            // The signature of the closure guarantees that it will not "leak" the scope of the reference
            // passed to it. If the closure panics, the guard will be dropped.
            return when (val matchVal = f(orig.data.asMut())) {
                null -> Result.Err<MappedMutexGuard<U>, MappedMutexGuard<T>>(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok<MappedMutexGuard<U>, MappedMutexGuard<T>>(MappedMutexGuard(data = data1, inner = orig1.inner, variance = Unit))
                }
            }
        }
    }
}

/**
 * For use in [`nonpoison.condvar`](condvar).
 */
public fun <T> guardLock(guard: MutexGuard<T>): io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex = guard.lock.inner
