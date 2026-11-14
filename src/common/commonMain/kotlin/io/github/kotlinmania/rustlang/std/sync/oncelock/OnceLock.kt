// port-lint: source sync/once_lock.rs
package io.github.kotlinmania.rustlang.std.sync.oncelock

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.sync.once.Once
import io.github.kotlinmania.rustlang.std.sync.once.OnceExclusiveState

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

/**
 * A synchronization primitive which can nominally be written to only once.
 *
 * This type is a thread-safe [OnceCell], and can be used in statics.
 * In many simple cases, you can use [LazyLock.T.F] instead to get the benefits of this type
 * with less effort: `LazyLock<T, F>` "looks like" `T` because it initializes with `F` on deref!
 * Where OnceLock shines is when LazyLock is too simple to support a given case, as LazyLock
 * doesn't allow additional inputs to its function after you call [LazyLock.new].
 *
 * A `OnceLock` can be thought of as a safe abstraction over uninitialized data that becomes
 * initialized once written.
 *
 * Unlike [`Mutex`](sync.Mutex), `OnceLock` is never poisoned on panic.
 *
 * # Examples
 *
 * Writing to a `OnceLock` from a separate thread:
 *
 * ```kotlin
 * import std.sync.OnceLock
 *
 * var CELL: OnceLock<Int> = OnceLock.new()
 * // `OnceLock` has not been written to yet.
 * check(CELL.get() == null)
 * // Spawn a thread and write to `OnceLock`.
 * spawn({
 *     val value = CELL.getOrInit({ 12345 })
 *     check(value == 12345)
 * }).join().unwrap()
 * // `OnceLock` now contains the value.
 * check(CELL.get() == 12345)
 * ```
 *
 * You can use `OnceLock` to implement a type that requires "append-only" logic:
 *
 * ```kotlin
 * import std.sync.OnceLock
 * import std.sync.atomic.AtomicU32
 * import std.sync.atomic.Ordering
 *
 * open class OnceList<T>(public var data: OnceLock<T>, public var next: OnceLock<OnceList<T>>) {
 *     public open fun new(): OnceList<T> {
 *         return OnceList(data = OnceLock.new(), next = OnceLock.new())
 *     }
 *
 *     public open fun push(value: T) {
 *         // Note: this impl is concise, but is also slow for long lists or many threads.
 *         // as an exercise, consider how you might improve on it while preserving the behavior
 *         when (val matchVal = this.data.set(value)) {
 *             is Err -> {
 *                 val (value1) = matchVal
 *                 val next = this.next.getOrInit({ OnceList.new() })
 *                 next.push(value1)
 *             }
 *             else -> {}
 *         }
 *     }
 *
 *     public open fun contains(example: T): Boolean {
 *         return (this.data.get()?.let({ item -> item == example }).filter({ v -> v }) ?: {
 *             (this.next.get()?.let({ next -> next.contains(example) }) ?: false)
 *         }())
 *     }
 * }
 * // Let's exercise this new Sync append-only list by doing a little counting
 * var LIST: OnceList<UInt> = OnceList.new()
 * var COUNTER: AtomicU32 = AtomicU32.new(0)
 * val LEN: UInt = 1000u
 * thread.scope({ s ->
 *     for (unused in 0 until thread.availableParallelism().unwrap().get()) {
 *         s.spawn({
 *             while (true) {
 *                 val matchVal = COUNTER.fetchAdd(1, Ordering.Relaxed)
 *                 if (!(matchVal in 0 until LEN)) break
 *                 val i = matchVal
 *                 LIST.push(i)
 *             }
 *         })
 *     }
 * })
 * for (i in 0 until LEN) {
 *     check(LIST.contains(i))
 * }
 * ```
 */
public open class OnceLock<T> internal constructor(
    // Note(nonpoisonOnce): switch to nonpoison version once it is available
    public var once: Once,
    valueCtorArg: UnsafeCell<MaybeUninit<T>>,
    /**
     * `PhantomData` to make sure dropck understands we're dropping T in our Drop impl.
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * open class A(public var value: String) {
     *     public var field0: String
     *         get() = value
     *         set(v) {
     *             value = v
     *         }
     *
     *     public open fun drop() {}
     * }
     * val cell = OnceLock.new()
     * run {
     *     val s = ""
     *     cell.set(A(s))
     * }
     * ```
     */
    public var marker: Unit,
) {
    // Whether or not the value is initialized is tracked by `once.isCompleted()`.
    public var value: UnsafeCell<MaybeUninit<T>> = valueCtorArg
        internal set

    /**
     * Gets the reference to the underlying value.
     *
     * Returns `None` if the cell is uninitialized, or being initialized.
     * This method never blocks.
     */
    public open fun get(): T? =
        if (this.initialized()) {
            // Safe b/c checked initialized
            this.getUnchecked()
        } else {
            null
        }

    /**
     * Gets the mutable reference to the underlying value.
     *
     * Returns `None` if the cell is uninitialized.
     *
     * This method never blocks. Since it borrows the `OnceLock` mutably,
     * it is statically guaranteed that no active borrows to the `OnceLock`
     * exist, including from other threads.
     */
    public open fun getMut(): T? =
        if (this.initializedMut()) {
            // Safe b/c checked initialized and we have a unique access
            this.getUncheckedMut()
        } else {
            null
        }

    /**
     * Blocks the current thread until the cell is initialized.
     *
     * # Example
     *
     * Waiting for a computation on another thread to finish:
     * ```kotlin
     * import std.sync.OnceLock
     *
     * val value = OnceLock.new()
     * thread.scope({ s ->
     *     s.spawn({ value.set(1 + 1) })
     *     val result = value.wait()
     *     check(result == 2)
     * })
     * ```
     */
    public open fun wait(): T {
        this.once.waitForce()
        return this.getUnchecked()
    }

    /**
     * Initializes the contents of the cell to `value`.
     *
     * May block if another thread is currently attempting to initialize the cell. The cell is
     * guaranteed to contain a value when `set` returns, though not necessarily the one provided.
     *
     * Returns `Ok(())` if the cell was uninitialized and
     * `Err(value)` if the cell was already initialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * var CELL: OnceLock<Int> = OnceLock.new()
     * fun main() {
     *     check(CELL.get() == null)
     *     spawn({
     *         check(CELL.set(92) == Result.success(Unit))
     *     }).join().unwrap()
     *     check(CELL.set(62) == Result.failure(62))
     *     check(CELL.get() == 92)
     * }
     * ```
     */
    public open fun set(value: T): Result<Unit, T> =
        run {
            val matchVal = this.tryInsert(value)
            when (matchVal) { is Result.Ok<*> -> {
                Result.Ok(Unit)
            }; is Result.Err<*> -> {
                val value1 = matchVal.error.second
                Result.Err(value1)
            } }
        }

    /**
     * Initializes the contents of the cell to `value` if the cell was uninitialized,
     * then returns a reference to it.
     *
     * May block if another thread is currently attempting to initialize the cell. The cell is
     * guaranteed to contain a value when `tryInsert` returns, though not necessarily the
     * one provided.
     *
     * Returns `Ok(value)` if the cell was uninitialized and
     * `Err((currentValue, value))` if it was already initialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * @feature(onceCellTryInsert)
     * public fun doctestEntry() {
     *     var CELL: OnceLock<Int> = OnceLock.new()
     *     fun main() {
     *         check(CELL.get() == null)
     *         spawn({
     *             check(CELL.tryInsert(92) == Result.success(92))
     *         }).join().unwrap()
     *         check(CELL.tryInsert(62) == Result.failure(Pair(92, 62)))
     *         check(CELL.get() == 92)
     *     }
     * }
     * ```
     */
    public open fun tryInsert(value: T): Result<T, Pair<T, T>> {
        var value1 = value
        val res = this.getOrInit({ (value1.also { value1 = null })!! })
        return when (value1) {
            null -> Result.Ok(res)
            else -> {
                val value2 = value1
                Result.Err(Pair(res, value2))
            }
        }
    }

    /**
     * Gets the contents of the cell, initializing it to `f()` if the cell
     * was uninitialized.
     *
     * Many threads may call `getOrInit` concurrently with different
     * initializing functions, but it is guaranteed that only one function
     * will be executed if the function doesn't panic.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * It is an error to reentrantly initialize the cell from `f`. The
     * exact outcome is unspecified. Current implementation deadlocks, but
     * this may be changed to a panic in the future.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * val cell = OnceLock.new()
     * val value = cell.getOrInit({ 92 })
     * check(value == 92)
     * val value1 = cell.getOrInit({ error("unreachable") })
     * check(value1 == 92)
     * ```
     */
    public open fun getOrInit(f: () -> T): T =
        when (val matchVal = this.getOrTryInit({ Result.Ok(f()) })) {
            is Result.Ok<*> -> {
                val value = matchVal.value
                value
            }
        }

    /**
     * Gets the mutable reference of the contents of the cell, initializing
     * it to `f()` if the cell was uninitialized.
     *
     * This method never blocks. Since it borrows the `OnceLock` mutably,
     * it is statically guaranteed that no active borrows to the `OnceLock`
     * exist, including from other threads.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * @feature(onceCellGetMut)
     * public fun doctestEntry() {
     *     var cell = OnceLock.new()
     *     val value = cell.getMutOrInit({ 92 })
     *     check(value == 92)
     *     value += 2
     *     check(value == 94)
     *     val value1 = cell.getMutOrInit({ error("unreachable") })
     *     check(value1 == 94)
     * }
     * ```
     */
    public open fun getMutOrInit(f: () -> T): T =
        when (val matchVal = this.getMutOrTryInit({ Result.Ok(f()) })) {
            is Result.Ok<*> -> {
                val value = matchVal.value
                value
            }
        }

    /**
     * Gets the contents of the cell, initializing it to `f()` if
     * the cell was uninitialized. If the cell was uninitialized
     * and `f()` failed, an error is returned.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and
     * the cell remains uninitialized.
     *
     * It is an error to reentrantly initialize the cell from `f`.
     * The exact outcome is unspecified. Current implementation
     * deadlocks, but this may be changed to a panic in the future.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * @feature(onceCellTry)
     * public fun doctestEntry() {
     *     val cell = OnceLock.new()
     *     check(cell.getOrTryInit({ Result.failure(Unit) }) == Result.failure(Unit))
     *     check(cell.get() == null)
     *     val value = cell.getOrTryInit({
     *         Result.success(92)
     *     })
     *     check(value == Result.success(92))
     *     check(cell.get() == 92)
     * }
     * ```
     */
    public open fun <E> getOrTryInit(f: () -> Result<T, E>): Result<T, E> {
        // Fast path check
        // NOTE: We need to perform an acquire on the state in this method
        // in order to correctly synchronize `LazyLock.force`. This is
        // currently done by calling `self.get()`, which in turn calls
        // `self.initialized()`, which in turn performs the acquire.
        when (val matchVal = this.get()) {
            null -> {}
            else -> {
                val value = matchVal
                return Result.Ok(value)
            }
        }
        when (val tryResult = this.initialize(f)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // SAFETY: The inner value has been initialized
        return Result.Ok(this.getUnchecked())
    }

    /**
     * Gets the mutable reference of the contents of the cell, initializing
     * it to `f()` if the cell was uninitialized. If the cell was uninitialized
     * and `f()` failed, an error is returned.
     *
     * This method never blocks. Since it borrows the `OnceLock` mutably,
     * it is statically guaranteed that no active borrows to the `OnceLock`
     * exist, including from other threads.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and
     * the cell remains uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     * import std.sync.OnceLock
     *
     * @feature(onceCellGetMut)
     * public fun doctestEntry() {
     *     var cell: OnceLock<UInt> = OnceLock.new()
     *     // Failed attempts to initialize the cell do not change its contents
     *     check(cell.getMutOrTryInit({ "not a number!".parse() }).isErr())
     *     check(cell.get() == null)
     *     val value = cell.getMutOrTryInit({ "1234".parse() })
     *     check(value == Result.success(RustMutableReference(allocateNativeCell(1234, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     *     value.unwrap() += 2
     *     check(cell.get() == 1236)
     * }
     * ```
     */
    public open fun <E> getMutOrTryInit(f: () -> Result<T, E>): Result<T, E> {
        if (this.getMut() == null) {
            when (val tryResult = this.initialize(f)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        }
        // SAFETY: The inner value has been initialized
        return Result.Ok(this.getUncheckedMut())
    }

    /**
     * Consumes the `OnceLock`, returning the wrapped value. Returns
     * `None` if the cell was uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * val cell: OnceLock<String> = OnceLock.new()
     * check(cell.intoInner() == null)
     * val cell1 = OnceLock.new()
     * cell1.set("hello".toString()).unwrap()
     * check(cell1.intoInner() == "hello".toString())
     * ```
     */
    public open fun intoInner(): T? = this.also { this = null }

    /**
     * Takes the value out of this `OnceLock`, moving it back to an uninitialized state.
     *
     * Has no effect and returns `None` if the `OnceLock` was uninitialized.
     *
     * Since this method borrows the `OnceLock` mutably, it is statically guaranteed that
     * no active borrows to the `OnceLock` exist, including from other threads.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * var cell: OnceLock<String> = OnceLock.new()
     * check(cell.take() == null)
     * var cell1 = OnceLock.new()
     * cell1.set("hello".toString()).unwrap()
     * check(cell1.also { cell1 = null } == "hello".toString())
     * check(cell1.get() == null)
     * ```
     */
    public open fun take(): T? =
        if (this.initializedMut()) {
            this.once = Once.new()
            // SAFETY: `self.value` is initialized and contains a valid `T`.
            // `self.once` is reset, so `initialized()` will be false again
            // which prevents the value from being read twice.
            this.value.getMut().assumeInitRead()
        } else {
            null
        }

    public open fun initialized(): Boolean = this.once.isCompleted()

    public open fun initializedMut(): Boolean {
        // `state()` does not perform an atomic load, so prefer it over `isComplete()`.
        val state = this.once.state()
        return when (state) {
            OnceExclusiveState.Complete -> true
            else -> false
        }
    }

    public open fun <E> initialize(f: () -> Result<T, E>): Result<Unit, E> {
        var res: Result<Unit, E> = Result.Ok(Unit)
        val slot = this.value
        // Ignore poisoning from other threads
        // If another thread panics, then we'll be able to run our closure
        this.once.callOnceForce({ p ->
            run {
                val matchVal = f()
                when (matchVal) { is Result.Ok<*> -> {
                    val value = matchVal.value
                    (slot.get()).write(value)
                }; is Result.Err<*> -> {
                    val e = matchVal.error
                    res = Result.Err(e)
                    // Treat the underlying `Once` as poisoned since we
                    // failed to initialize our value.
                    p.poison()
                } }
            }
        })
        return res
    }

    /**
     * Returns a reference to the value in the `OnceLock` without checking
     * whether it has been initialized.
     *
     * # Safety
     *
     * The `OnceLock` must be initialized before calling this function.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * @feature(onceLazyLockGetUnchecked)
     * public fun doctestEntry() {
     *     val cell = OnceLock.new()
     *     cell.set(42).unwrap()
     *     val value = cell.getUnchecked()
     *     check(value == 42)
     * }
     * ```
     */
    public open fun getUnchecked(): T {
        check(this.initialized())
        return (this.value.get()).assumeInitRef()
    }

    /**
     * Returns a mutable reference to the value in the `OnceLock` without
     * checking whether it has been initialized.
     *
     * # Safety
     *
     * The `OnceLock` must be initialized before calling this function.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * @feature(onceLazyLockGetUnchecked)
     * public fun doctestEntry() {
     *     var cell = OnceLock.new()
     *     cell.set(42).unwrap()
     *     val value = cell.getUncheckedMut()
     *     value = 100
     *     check(cell.get()!! == 100)
     * }
     * ```
     */
    public open fun getUncheckedMut(): T {
        check(this.initializedMut())
        return this.value.getMut().assumeInitMut()
    }

    // Why do we need `T: Send`?
    // Thread A creates a `OnceLock` and shares it with
    // scoped thread B, which fills the cell, which is
    // then destroyed by A. That is, destructor observes
    // a sent value.

    public open fun fmt(f: StringBuilder) {
        val d = DebugTuple(f, "OnceLock")
        when (val matchVal = this.get()) {
            null -> d.field("<uninit>")
            else -> {
                val v = matchVal
                d.field(v)
            }
        }
        d.finish()
    }

    public open fun clone(): OnceLock<T> = this.get().mapOrDefault(OnceLock::from)

    /**
     * Equality for two `OnceLock`s.
     *
     * Two `OnceLock`s are equal if they either both contain values and their
     * values are equal, or if neither contains a value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.OnceLock
     *
     * val five = OnceLock.new()
     * five.set(5).unwrap()
     * val alsoFive = OnceLock.new()
     * alsoFive.set(5).unwrap()
     * check(five == alsoFive)
     * check(OnceLock.new() == OnceLock.new())
     * ```
     */
    public open fun eq(other: OnceLock<T>): Boolean = (this.get() == other.get()).toUByte()

    public open fun drop() {
        if (this.initializedMut()) {
            // SAFETY: The cell is initialized and being dropped, so it can't
            // be accessed again. We also don't touch the `T` other than
            // dropping it, which validates our usage of @mayDangle.
            this.value.getMut().assumeInitDrop()
        }
    }

    public companion object {
        public operator fun <T> invoke(
            once: Once,
            value: UnsafeCell<MaybeUninit<T>>,
            marker: Unit,
        ): OnceLock<T> = OnceLock<T>(
            once = once,
            valueCtorArg = value,
            marker = marker,
        )

        /**
         * Creates a new uninitialized cell.
         */
        public fun <T> new(): OnceLock<T> = OnceLock(once = Once.new(), value = UnsafeCell.new(MaybeUninit.uninit(PrimitiveLayouts.U8)), marker = Unit)

        /**
         * Creates a new initialized cell.
         *
         * This is equivalent to `OnceLock.from(value)`, but can be used in
         * const contexts, unlike the `From` implementation.
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.OnceLock
         *
         * @feature(onceLockNewInit)
         * public fun doctestEntry() {
         *     var CELL: OnceLock<Int> = OnceLock.newInit(1)
         *     check(CELL.get() == 1)
         *     // Already initialized, so this closure never runs.
         *     check(CELL.getOrInit({ error("Kaboom!") }) == 1)
         * }
         * ```
         */
        public fun <T> newInit(initValue: T): OnceLock<T> = OnceLock(once = Once.newComplete(), value = UnsafeCell.new(MaybeUninit.new(initValue, ObjectSlotLayout<T>())), marker = Unit)

        /**
         * Creates a new uninitialized cell.
         *
         * # Example
         *
         * ```kotlin
         * import std.sync.OnceLock
         *
         * fun main() {
         *     check(OnceLock.new() == OnceLock.default())
         * }
         * ```
         */
        public fun <T> default(): OnceLock<T> = OnceLock.new()

        /**
         * Creates a new cell with its contents set to `value`.
         *
         * # Example
         *
         * ```kotlin
         * import std.sync.OnceLock
         *
         * fun main(): Result<Unit> {
         *     val a = OnceLock.from(3)
         *     val b = OnceLock.new()
         *     b.set(3).getOrElse { return Result.failure(it) }
         *     check(a == b)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <T> from(value: T): OnceLock<T> = OnceLock(once = Once.newComplete(), value = UnsafeCell.new(MaybeUninit.new(value, ObjectSlotLayout<T>())), marker = Unit)
    }
}
