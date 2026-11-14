// port-lint: source sync/poison/mutex.rs
package io.github.kotlinmania.rustlang.std.sync.poison.mutex

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.nonpoison.TryLockResult
import io.github.kotlinmania.rustlang.std.sync.poison.Flag
import io.github.kotlinmania.rustlang.std.sync.poison.Guard
import io.github.kotlinmania.rustlang.std.sync.poison.LockResult
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sync.poison.TryLockError
import io.github.kotlinmania.rustlang.std.sync.poison.mapResult

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
 * A mutual exclusion primitive useful for protecting shared data
 *
 * This mutex will block threads waiting for the lock to become available. The
 * mutex can be created via a [new] constructor. Each mutex has a type parameter
 * which represents the data that it is protecting. The data can only be accessed
 * through the RAII guards returned from [lock] and [tryLock], which
 * guarantees that the data is only ever accessed when the mutex is locked.
 *
 * # Poisoning
 *
 * The mutexes in this module implement a strategy called "poisoning" where a
 * mutex becomes poisoned if it recognizes that the thread holding it has
 * panicked.
 *
 * Once a mutex is poisoned, all other threads are unable to access the data by
 * default as it is likely tainted (some invariant is not being upheld). For a
 * mutex, this means that the [lock] and [tryLock] methods return a
 * [Result] which indicates whether a mutex has been poisoned or not. Most
 * usage of a mutex will simply [unwrap] these results, propagating panics
 * among threads to ensure that a possibly invalid invariant is not witnessed.
 *
 * Poisoning is only advisory: the [PoisonError] type has an [intoInner]
 * method which will return the guard that would have otherwise been returned
 * on a successful lock. This allows access to the data, despite the lock being
 * poisoned.
 *
 * In addition, the panic detection is not ideal, so even unpoisoned mutexes
 * need to be handled with care, since certain panics may have been skipped.
 * Here is a non-exhaustive list of situations where this might occur:
 *
 * - If a mutex is locked while a panic is underway, e.g. within a [Drop]
 *   implementation or a [panic hook], panicking for the second time while the
 *   lock is held will leave the mutex unpoisoned. Note that while double panic
 *   usually aborts the program, [catchUnwind] can prevent this.
 *
 * - Locking and unlocking the mutex across different panic contexts, e.g. by
 *   storing the guard to a [Cell] within [Drop.drop] and accessing it
 *   outside, or vice versa, can affect poisoning status in an unexpected way.
 *
 * - Foreign exceptions do not currently trigger poisoning even in absence of
 *   other panics.
 *
 * While this rarely happens in realistic code, `unchecked` code cannot rely on
 * poisoning for soundness, since the behavior of poisoning can depend on
 * outside context. Here's an example of **incorrect** use of poisoning:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.sync.Mutex
 *
 * open class MutexBox<T>(public var data: Mutex<MutPtr>) {
 *     public open fun new(value: T): MutexBox<T> {
 *         return MutexBox(data = Mutex.new(Holder.intoRaw(value)))
 *     }
 *
 *     public open fun replaceWith(f: (T) -> T) {
 *         val ptr = this.data.lock().expect("`Mutex` should not be poisoned")
 *         // While `f` is running, the data is moved out of `* ptr`. If `f`
 *         // panics, `* ptr` keeps pointing at a dropped value. The intention
 *         // is that this will poison the mutex, so the following calls to
 *         // `replaceWith` will panic without reading `* ptr`. But since
 *         // poisoning is not guaranteed to occur if this is run from a panic
 *         // hook, this can lead to use-after-free.
 *         run {
 *             ptr.write(f(ptr.read()))
 *         }
 *     }
 * }
 * ```
 *
 * [new]: Self.new
 * [lock]: Self.lock
 * [tryLock]: Self.tryLock
 * [unwrap]: Result.unwrap
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Mutex
 * import std.sync.mpsc.channel
 * import std.thread
 *
 * val N: Int = 10
 * // Spawn a few threads to increment a shared variable (non-atomically), and
 * // let the main thread know once all increments are done.
 * //
 * // Here we're using an Arc to share memory among threads, and the data inside
 * // the Arc is protected with a mutex.
 * val data = Mutex.new(0)
 * val (tx, rx) = channel()
 * for (unused in 0 until N) {
 *     val (data1, tx1) = Pair(data, tx.clone())
 *     thread.spawn({
 *         // The shared state can only be accessed once the lock is held.
 *         // Our non-atomic increment is safe because we're the only thread
 *         // which can access the shared state when the lock is held.
 *         //
 *         // We unwrap() the return value to assert that we are not expecting
 *         // threads to ever fail while holding the lock.
 *         var data2 = data1.lock().unwrap()
 *         data2 += 1
 *         if (data2 == N) {
 *             tx1.send().unwrap()
 *         }
 *     })
 * }
 * rx.recv().unwrap()
 * ```
 *
 * To recover from a poisoned mutex:
 *
 * ```kotlin
 * import std.sync.Mutex
 * import std.thread
 *
 * val lock = Mutex.new(0u)
 * val lock2 = lock
 * thread.spawn({
 *     // This thread will acquire the mutex first, unwrapping the result of
 *     // `lock` because the lock has not been poisoned.
 *     val guard = lock2.lock().unwrap()
 *     // This panic while holding the lock (`_guard` is in scope) will poison
 *     // the mutex.
 *     error("panic")
 * }).join()
 * // The lock is poisoned by this point, but the returned result can be
 * // pattern matched on to return the underlying guard on both branches.
 * var guard1 = run {
 *     val matchVal = lock.lock()
 *     if (matchVal.isSuccess) {
 *         val guard = matchVal.getOrThrow()
 *         guard
 *     } else {
 *         val poisoned = matchVal.exceptionOrNull()!!
 *         poisoned.intoInner()
 *     }
 * }
 * guard1 += 1
 * ```
 *
 * To unlock a mutex guard sooner than the end of the enclosing scope,
 * either create an inner scope or drop the guard manually.
 *
 * ```kotlin
 * import std.sync.Mutex
 * import std.thread
 *
 * val N: Int = 3
 * val dataMutex = Mutex.new(mutableListOf(1, 2, 3, 4))
 * val resMutex = Mutex.new(0)
 * var threads = ArrayList(N)
 * (0 until N).forEach({
 *     val dataMutexClone = dataMutex
 *     val resMutexClone = resMutex
 *     threads.add(thread.spawn({
 *         // Here we use a block to limit the scope of the lock guard.
 *         val result1 = run {
 *             var data = dataMutexClone.lock().unwrap()
 *             // This is the result of some important and long-ish work.
 *             val result = data.iter().fold(0, { acc, x -> acc + x * 2 })
 *             data.push(result)
 *             result
 *             // The mutex guard gets dropped here, together with any other values
 *             // created in the critical section.
 *         }
 *         // The guard created here is a temporary dropped at the end of the statement, i.e.
 *         // the lock would not remain being held even if the thread did some additional work.
 *         resMutexClone.lock().unwrap() += result1
 *     }))
 * })
 * var data = dataMutex.lock().unwrap()
 * // This is the result of some important and long-ish work.
 * val result = data.iter().fold(0, { acc, x -> acc + x * 2 })
 * data.push(result)
 * // We drop the `data` explicitly because it's not necessary anymore and the
 * // thread still has work to do. This allows other threads to start working on
 * // the data immediately, without waiting for the rest of the unrelated work
 * // to be done here.
 * //
 * // It's even more important here than in the threads because we `.join` the
 * // threads after that. If we had not dropped the mutex guard, a thread could
 * // be waiting forever for it, causing a deadlock.
 * // As in the threads, a block could have been used instead of calling the
 * // `drop` function.
 * (data).let { }
 * // Here the mutex guard is not assigned to a variable and so, even if the
 * // scope does not end after this line, the mutex is still released: there is
 * // no deadlock.
 * resMutex.lock().unwrap() += result
 * threads.forEach({ thread ->
 *     thread.join().expect("Thread creation or execution should not fail")
 * })
 * check(resMutex.lock().unwrap() == 800)
 * ```
 */
public open class Mutex<T>(
    public var inner: io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex,
    public var poison: Flag,
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

    /**
     * Returns the contained value by cloning it.
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.getCloned().unwrap() == 7)
     * }
     * ```
     */
    public open fun getCloned(): Result<T, PoisonError<Unit>> =
        run {
            val matchVal = this.lock()
            when (matchVal) { is Result.Ok<*> -> {
                val guard = matchVal.value
                Result.Ok(guard.clone())
            }; is Result.Err<*> -> {
                Result.Err(PoisonError.new())
            } }
        }

    /**
     * Sets the contained value.
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error containing the provided `value` instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.getCloned().unwrap() == 7)
     *     mutex.set(11).unwrap()
     *     check(mutex.getCloned().unwrap() == 11)
     * }
     * ```
     */
    public open fun set(value: T): Result<Unit, PoisonError<T>> =
        if (needsDrop<T>()) {
            // If the contained value has non-trivial destructor, we
            // call that destructor after the lock being released.
            this.replace(value).map({ Unit })
        } else {
            run {
                val matchVal = this.lock()
                when (matchVal) { is Result.Ok<*> -> {
                    val guard = matchVal.value
                    guard = value
                    Result.Ok(Unit)
                }; is Result.Err<*> -> {
                    Result.Err(PoisonError.new(value))
                } }
            }
        }

    /**
     * Replaces the contained value with `value`, and returns the old contained value.
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error containing the provided `value` instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * @feature(lockValueAccessors)
     * public fun doctestEntry() {
     *     var mutex = Mutex.new(7)
     *     check(mutex.replace(11).unwrap() == 7)
     *     check(mutex.getCloned().unwrap() == 11)
     * }
     * ```
     */
    public open fun replace(value: T): LockResult<T> =
        run {
            val matchVal = this.lock()
            when (matchVal) { is Result.Ok<*> -> {
                val guard = matchVal.value
                Result.Ok(replace(guard, value))
            }; is Result.Err<*> -> {
                Result.Err(PoisonError.new(value))
            } }
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
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error once the mutex is acquired. The acquired
     * mutex guard will be contained in the returned error.
     *
     * # Panics
     *
     * This function might panic when called if the lock is already held by
     * the current thread.
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
     *     cMutex.lock().unwrap() = 10
     * }).join().expect("`thread.spawn` should not fail")
     * check(mutex.lock().unwrap() == 10)
     * ```
     */
    public open fun lock(): LockResult<MutexGuard<T>> =
        run {
            this.inner.lock()
            MutexGuard.new(this)
        }

    /**
     * Attempts to acquire this lock.
     *
     * If the lock could not be acquired at this time, then [Err] is returned.
     * Otherwise, an RAII guard is returned. The lock will be unlocked when the
     * guard is dropped.
     *
     * This function does not block.
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return the [Poisoned] error if the mutex would
     * otherwise be acquired. An acquired lock guard will be contained
     * in the returned error.
     *
     * If the mutex could not be acquired because it is already locked, then
     * this call will return the [WouldBlock] error.
     *
     * [Poisoned]: TryLockError.Poisoned
     * [WouldBlock]: TryLockError.WouldBlock
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
    public open fun tryLock(): TryLockResult<MutexGuard<T>> {
        return if (this.inner.tryLock()) {
            Result.Ok(when (val tryResult = MutexGuard.new(this)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
        } else {
            Result.Err(TryLockError.WouldBlock)
        }
    }

    /**
     * Determines whether the mutex is poisoned.
     *
     * If another thread is active, the mutex can still become poisoned at any
     * time. You should not trust a `false` value for program correctness
     * without additional synchronization.
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
     *     val lock = cMutex.lock().unwrap()
     *     error("panic")
     * }).join()
     * check(mutex.isPoisoned() == true)
     * ```
     */
    public open fun isPoisoned(): Boolean = this.poison.get()

    /**
     * Clear the poisoned state from a mutex.
     *
     * If the mutex is poisoned, it will remain poisoned until this function is called. This
     * allows recovering from a poisoned state and marking that it has recovered. For example, if
     * the value is overwritten by a known-good value, then the mutex can be marked as
     * un-poisoned. Or possibly, the value could be inspected to determine if it is in a
     * consistent state, and if so the poison is removed.
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
     *     val lock = cMutex.lock().unwrap()
     *     error("panic")
     * }).join()
     * check(mutex.isPoisoned() == true)
     * val x = mutex.lock().unwrapOrElse({ e ->
     *     e.getMut() = 1
     *     mutex.clearPoison()
     *     e.intoInner()
     * })
     * check(mutex.isPoisoned() == false)
     * check(x == 1)
     * ```
     */
    public open fun clearPoison() {
        this.poison.clear()
    }

    /**
     * Consumes this mutex, returning the underlying data.
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error containing the underlying data
     * instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * val mutex = Mutex.new(0)
     * check(mutex.intoInner().unwrap() == 0)
     * ```
     */
    public open fun intoInner(): LockResult<T> {
        val data = this.data.intoInner()
        return mapResult(this.poison.borrow(), { data })
    }

    /**
     * Returns a mutable reference to the underlying data.
     *
     * Since this call borrows the `Mutex` mutably, no actual locking needs to
     * take place -- the mutable borrow statically guarantees no new locks can be acquired
     * while this reference exists. Note that this method does not clear any previous abandoned locks
     * (e.g., via [forget] on a [MutexGuard]).
     *
     * # Errors
     *
     * If another user of this mutex panicked while holding the mutex, then
     * this call will return an error containing a mutable reference to the
     * underlying data instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     *
     * var mutex = Mutex.new(0)
     * mutex.getMut()!! = 10
     * check(mutex.lock().unwrap() == 10)
     * ```
     *
     * [forget]: mem.forget
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
     * or written through after the mutex is dropped.
     */
    public open fun dataPtr(): MutPtr = this.data.get()

    public open fun fmt(f: StringBuilder) {
        val d = DebugStruct(f, "Mutex")
        run {
            val matchVal = this.tryLock()
            when {
                matchVal is Result.Ok<*> -> {
                    val guard = (matchVal as Result.Ok<*>).value
                    d.field("data", guard)
                }
                matchVal is Result.Err<*> && matchVal.error is TryLockError.Poisoned -> {
                    d.field("data", err.getRef())
                }
                else -> {
                    d.field("data", "<locked>")
                }
            }
        }
        d.field("poisoned", this.poison.get())
        d.finishNonExhaustive()
    }

    public companion object {
        /**
         * Creates a new mutex in an unlocked state ready for use.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.Mutex
         *
         * val mutex = Mutex.new(0)
         * ```
         */
        public fun <T> new(t: T): Mutex<T> = Mutex(inner = io.github.kotlinmania.rustlang.std.sys.sync.mutex.new(), poison = Flag.new(), data = UnsafeCell.new(t))

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
    public var poison: Guard,
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
            this.lock.poison.done(this.poison)
            this.lock.inner.unlock()
        }
    }

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

    public companion object {
        public fun <T> new(lock: Mutex<T>): LockResult<MutexGuard<T>> = mapResult(lock.poison.guard(), { guard -> MutexGuard(lock = lock, poison = guard) })

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
            return MappedMutexGuard(data = data, inner = orig1.lock.inner, poisonFlag = orig1.lock.poison, poison = orig1.poison.clone(), variance = Unit)
        }

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
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedMutexGuard(data = data1, inner = orig1.lock.inner, poisonFlag = orig1.lock.poison, poison = orig1.poison.clone(), variance = Unit))
                }
            }
        }
    }
}

/**
 * An RAII mutex guard returned by `MutexGuard.map`, which can point to a
 * subfield of the protected data. When this structure is dropped (falls out
 * of scope), the lock will be unlocked.
 *
 * The main difference between `MappedMutexGuard` and [MutexGuard] is that the
 * former cannot be used with [Condvar], since that
 * could introduce soundness issues if the locked object is modified by another
 * thread while the `Mutex` is unlocked.
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
    public var poisonFlag: Flag,
    public var poison: Guard,
    public var variance: Unit,
) {
    public open fun deref(): T = this.data.asRef()

    public open fun derefMut(): T = this.data.asMut()

    public open fun drop() {
        run {
            this.poisonFlag.done(this.poison)
            this.inner.unlock()
        }
    }

    public open fun fmt(f: StringBuilder) {
        f.append((this).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.fmt(f)
    }

    public companion object {
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
            return MappedMutexGuard(data = data, inner = orig1.inner, poisonFlag = orig1.poisonFlag, poison = orig1.poison.clone(), variance = Unit)
        }

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
                null -> Result.Err(orig)
                else -> {
                    val data = matchVal
                    val data1 = NonNull.from(data)
                    val orig1 = orig
                    Result.Ok(MappedMutexGuard(data = data1, inner = orig1.inner, poisonFlag = orig1.poisonFlag, poison = orig1.poison.clone(), variance = Unit))
                }
            }
        }
    }
}

/**
 * For use in [`nonpoison.condvar`](condvar).
 */
public fun <T> guardLock(guard: MutexGuard<T>): io.github.kotlinmania.rustlang.std.sys.sync.mutex.futex.Mutex = guard.lock.inner

/**
 * For use in [`nonpoison.condvar`](condvar).
 */
public fun <T> guardPoison(guard: MutexGuard<T>): Flag = guard.lock.poison
