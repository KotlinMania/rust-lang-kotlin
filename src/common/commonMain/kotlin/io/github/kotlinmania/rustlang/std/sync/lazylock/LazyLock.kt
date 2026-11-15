// port-lint: source sync/lazy_lock.rs
package io.github.kotlinmania.rustlang.std.sync.lazylock

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.once.OnceExclusiveState
import io.github.kotlinmania.rustlang.sync.once.Once

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

// We use the state of a Once as discriminant value. Upon creation, the state is
// "incomplete" and `f` contains the initialization closure. In the first call to
// `callOnce`, `f` is taken and run. If it succeeds, `value` is set and the state
// is changed to "complete". If it panics, the Once is poisoned, so none of the
// two fields is initialized.
public open class Data<T, F>(
    public var value: T? = null,
    public var f: F? = null,
)

/**
 * A value which is initialized on the first access.
 *
 * This type is a thread-safe [LazyCell], and can be used in statics.
 * Since initialization may be called from multiple threads, any
 * dereferencing call will block the calling thread if another
 * initialization routine is currently running.
 *
 * # Poisoning
 *
 * If the initialization closure passed to [LazyLock.new] panics, the lock will be poisoned.
 * Once the lock is poisoned, any threads that attempt to access this lock (via a dereference
 * or via an explicit call to [force]) will panic.
 *
 * This concept is similar to that of poisoning in the [std.sync.poison] module. A key
 * difference, however, is that poisoning in `LazyLock` is _unrecoverable_. All future accesses of
 * the lock from other threads will panic, whereas a type in [std.sync.poison] like
 * [std.sync.poison.Mutex] allows recovery via [PoisonError.intoInner].
 *
 * [force]: LazyLock.force
 *
 * # Examples
 *
 * Initialize static variables with `LazyLock`.
 * ```kotlin
 * import std.sync.LazyLock
 *
 * // Note: static items do not call [Drop] on program termination, so this won't be deallocated.
 * // this is fine, as the OS can deallocate the terminated program faster than we can free memory
 * // but tools like valgrind might report "memory leaks" as it isn't obvious this is intentional.
 * var DEEP_THOUGHT: LazyLock<String> = LazyLock.new({
 *     // M3 Ultra takes about 16 million years in --release config
 *     greatQuestion()
 * })
 * // The `String` is built, stored in the `LazyLock`, and returned as `String`.
 * DEEP_THOUGHT
 * ```
 *
 * Initialize fields with `LazyLock`.
 * ```kotlin
 * import std.sync.LazyLock
 *
 * open class UseCellLock(public var number: LazyLock<UInt>)
 * fun main() {
 *     val lock: LazyLock<UInt> = LazyLock.new({ 0u })
 *     val data = UseCellLock(number = lock)
 *     println("${data.number}")
 * }
 * ```
 */
public open class LazyLock<T, F> internal constructor(
    // Note(nonpoisonOnce): if possible, switch to nonpoison version once it is available
    public var once: Once,
    dataCtorArg: UnsafeCell<Data<T, F>>,
) {
    public var data: UnsafeCell<Data<T, F>> = dataCtorArg
        internal set

    public open fun drop() {
        when (this.once.state()) {
            OnceExclusiveState.Incomplete -> ManuallyDrop.drop(this.data.getMut().f)
            OnceExclusiveState.Complete -> ManuallyDrop.drop(this.data.getMut().value)
            OnceExclusiveState.Poisoned -> {}
        }
    }

    /**
     * Dereferences the value.
     *
     * This method will block the calling thread if another initialization
     * routine is currently running.
     *
     * # Panics
     *
     * If the initialization closure panics (the one that is passed to the [new] method), the
     * panic is propagated to the caller, and the lock becomes poisoned. This will cause all future
     * accesses of the lock (via [force] or a dereference) to panic.
     *
     * [new]: LazyLock.new
     * [force]: LazyLock.force
     */
    public open fun deref(): T = LazyLock.force(this)

    /**
     * # Panics
     *
     * If the initialization closure panics (the one that is passed to the [new] method), the
     * panic is propagated to the caller, and the lock becomes poisoned. This will cause all future
     * accesses of the lock (via [force] or a dereference) to panic.
     *
     * [new]: LazyLock.new
     * [force]: LazyLock.force
     */
    public open fun derefMut(): T = LazyLock.forceMut(this)

    public open fun fmt(f: StringBuilder) {
        val d = DebugTuple(f, "LazyLock")
        when (val matchVal = LazyLock.get(this)) {
            null -> d.field("<uninit>")
            else -> {
                val v = matchVal
                d.field(v)
            }
        }
        d.finish()
    }

    // We never create a `F` from a `LazyLock<T, F>` so it is fine
    // to not impl `Sync` for `F`.

    // auto-derived `Send` impl is OK.

    public companion object {
        public operator fun <T, F> invoke(
            once: Once,
            data: UnsafeCell<Data<T, F>>,
        ): LazyLock<T, F> = LazyLock<T, F>(
            once = once,
            dataCtorArg = data,
        )

        /**
         * Creates a new lazy value with the given initializing function.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * val hello = "Hello, World!".toString()
         * val lazy = LazyLock.new({ hello.uppercase() })
         * check(lazy == "HELLO, WORLD!")
         * ```
         */
        public fun <T> new(f: () -> T): LazyLock<T, () -> T> = LazyLock(once = Once.new(), data = UnsafeCell.new(Data(f = f)))

        /**
         * Consumes this `LazyLock` returning the stored value.
         *
         * Returns `Ok(value)` if `Lazy` is initialized and `Err(f)` otherwise.
         *
         * # Panics
         *
         * Panics if the lock is poisoned.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * @feature(lazyCellIntoInner)
         * public fun doctestEntry() {
         *     val hello = "Hello, World!".toString()
         *     val lazy = LazyLock.new({ hello.uppercase() })
         *     check(lazy == "HELLO, WORLD!")
         *     check(LazyLock.intoInner(lazy).ok() == "HELLO, WORLD!".toString())
         * }
         * ```
         */
        public fun <T> intoInner(thisRef: LazyLock<T, F>): Result<T, () -> T> {
            val state = thisRef.once.state()
            return when (state) {
                OnceExclusiveState.Poisoned -> panicPoisoned()
                else -> {
                    val state1 = state
                    val thisRef1 = thisRef
                    val data = ptr.read(thisRef1.data).intoInner()
                    when (state1) {
                        OnceExclusiveState.Incomplete -> {
                            Result.Err(ManuallyDrop.intoInner(data.f))
                        }
                        OnceExclusiveState.Complete -> {
                            Result.Ok(ManuallyDrop.intoInner(data.value))
                        }
                        OnceExclusiveState.Poisoned -> error("unreachable")
                    }
                }
            }
        }

        /**
         * Forces the evaluation of this lazy value and returns a mutable reference to
         * the result.
         *
         * # Panics
         *
         * If the initialization closure panics (the one that is passed to the [new] method), the
         * panic is propagated to the caller, and the lock becomes poisoned. This will cause all future
         * accesses of the lock (via [force] or a dereference) to panic.
         *
         * [new]: LazyLock.new
         * [force]: LazyLock.force
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * var lazy = LazyLock.new({ 92 })
         * val p = LazyLock.forceMut(lazy)
         * check(p == 92)
         * p = 44
         * check(lazy == 44)
         * ```
         */
        public fun <T> forceMut(thisRef: LazyLock<T, () -> T>): T {
            /**
             * # Safety
             * May only be called when the state is `Incomplete`.
             */
            fun <T> reallyInitMut(thisRef1: LazyLock<T, () -> T>): T {
                open class PoisonOnPanic<T, F>(
                    public var value: LazyLock<T, () -> T>,
                ) {
                    public var field0: LazyLock<T, () -> T>
                        get() = value
                        set(v) {
                            value = v
                        }

                    public open fun drop() {
                        this.value.once.setState(OnceExclusiveState.Poisoned)
                    }
                }
                // SAFETY: We always poison if the initializer panics (then we never check the data),
                // or set the data on success.
                val f = ManuallyDrop.take(thisRef1.data.getMut().f)
                // INVARIANT: Initiated from mutable reference, don't drop because we read it.
                val guard = PoisonOnPanic(thisRef1)
                val data = f()
                guard.first.data.getMut().value = data
                guard.first.once.setState(OnceExclusiveState.Complete)
                forget(guard)
                // SAFETY: We put the value there above.
                return LazyLock.getUncheckedMut(thisRef1)
            }
            val state = thisRef.once.state()
            return when (state) {
                OnceExclusiveState.Poisoned -> panicPoisoned()
                // SAFETY: The `Once` states we completed the initialization.
                OnceExclusiveState.Complete -> LazyLock.getUncheckedMut(thisRef)
                // SAFETY: The state is `Incomplete`.
                OnceExclusiveState.Incomplete -> reallyInitMut(thisRef)
            }
        }

        /**
         * Forces the evaluation of this lazy value and returns a reference to
         * result. This is equivalent to the `Deref` impl, but is explicit.
         *
         * This method will block the calling thread if another initialization
         * routine is currently running.
         *
         * # Panics
         *
         * If the initialization closure panics (the one that is passed to the [new] method), the
         * panic is propagated to the caller, and the lock becomes poisoned. This will cause all future
         * accesses of the lock (via [force] or a dereference) to panic.
         *
         * [new]: LazyLock.new
         * [force]: LazyLock.force
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * val lazy = LazyLock.new({ 92 })
         * check(LazyLock.force(lazy) == 92)
         * check(lazy == 92)
         * ```
         */
        public fun <T> force(thisRef: LazyLock<T, () -> T>): T {
            thisRef.once.callOnceForce({ state ->
                if (state.isPoisoned()) {
                    panicPoisoned()
                }
                // SAFETY: `callOnce` only runs this closure once, ever.
                val data = thisRef.data.get()
                val f = ManuallyDrop.take(data.f)
                val value = f()
                data.value = value
            })
            // SAFETY:
            // There are four possible scenarios:
            // * the closure was called and initialized `value`.
            // * the closure was called and panicked, so this point is never reached.
            // * the closure was not called, but a previous call initialized `value`.
            // * the closure was not called because the Once is poisoned, which we handled above.
            // So `value` has definitely been initialized and will not be modified again.
            return LazyLock.getUnchecked(thisRef)
        }

        /**
         * Returns a mutable reference to the value if initialized. Otherwise (if uninitialized or
         * poisoned), returns `None`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * var lazy = LazyLock.new({ 92 })
         * check(LazyLock.getMut(lazy) == null)
         * LazyLock.force(lazy)
         * LazyLock.getMut(lazy).unwrap() = 44
         * check(lazy == 44)
         * ```
         */
        public fun <T, F> getMut(thisRef: LazyLock<T, F>): T? {
            // `state()` does not perform an atomic load, so prefer it over `isComplete()`.
            val state = thisRef.once.state()
            return when (state) {
                // SAFETY:
                // The closure has been run successfully, so `value` has been initialized.
                OnceExclusiveState.Complete -> LazyLock.getUncheckedMut(thisRef)
                else -> null
            }
        }

        /**
         * Returns a reference to the value if initialized. Otherwise (if uninitialized or poisoned),
         * returns `None`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * val lazy = LazyLock.new({ 92 })
         * check(LazyLock.get(lazy) == null)
         * LazyLock.force(lazy)
         * check(LazyLock.get(lazy) == 92)
         * ```
         */
        public fun <T, F> get(thisRef: LazyLock<T, F>): T? =
            if (thisRef.once.isCompleted()) {
                // SAFETY:
                // The closure has been run successfully, so `value` has been initialized
                // and will not be modified again.
                LazyLock.getUnchecked(thisRef)
            } else {
                null
            }

        /**
         * Returns a shared reference to the value stored in the `LazyLock` without
         * checking whether it has been initialized.
         *
         * # Safety
         *
         * The lazy value must be initialized before calling this function.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * @feature(onceLazyLockGetUnchecked)
         * public fun doctestEntry() {
         *     val lazy = LazyLock.new({ 42 })
         *     // Initialize the lazy.
         *     lazy
         *     val value = LazyLock.getUnchecked(lazy)
         *     check(value == 42)
         * }
         * ```
         */
        public fun <T, F> getUnchecked(thisRef: LazyLock<T, F>): T {
            check(thisRef.once.isCompleted())
            return thisRef.data.get().value
        }

        /**
         * Returns a mutable reference to the value stored in the `LazyLock` without
         * checking whether it has been initialized.
         *
         * # Safety
         *
         * The lazy value must be initialized before calling this function.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.LazyLock
         *
         * @feature(onceLazyLockGetUnchecked)
         * public fun doctestEntry() {
         *     var lazy = LazyLock.new({ 42 })
         *     // Initialize the lazy.
         *     lazy
         *     val value = LazyLock.getUncheckedMut(lazy)
         *     value = 100
         *     check(lazy == 100)
         * }
         * ```
         */
        public fun <T, F> getUncheckedMut(thisRef: LazyLock<T, F>): T {
            check(thisRef.once.isCompleted())
            return thisRef.data.getMut().value
        }

        /**
         * Constructs a `LazyLock` that starts already initialized
         * with the provided value.
         */
        public fun <T, F> from(value: T): LazyLock<T, F> = LazyLock(once = Once.newComplete(), data = UnsafeCell.new(Data(value = value)))
    }
}

/**
 * Creates a new lazy value using `Default` as the initializing function.
 */
public fun <T> defaultLazyLockT(defaultTFn: () -> T): LazyLock<T> = LazyLock.new(defaultTFn)

public fun panicPoisoned(): Nothing {
    error("LazyLock instance has previously been poisoned")
}
