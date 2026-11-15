// port-lint: source cell/once.rs
package io.github.kotlinmania.rustlang.core.cell.once

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.result.Result

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
 * A cell which can nominally be written to only once.
 *
 * This allows obtaining a shared `T` reference to its inner value without copying or replacing
 * it (unlike [Cell]), and without runtime borrow checks (unlike [RefCell]). However,
 * only immutable references can be obtained unless one has a mutable reference to the cell
 * itself. In the same vein, the cell can only be re-initialized with such a mutable reference.
 *
 * A `OnceCell` can be thought of as a safe abstraction over uninitialized data that becomes
 * initialized once written.
 *
 * For a thread-safe version of this struct, see [std.sync.OnceLock].
 *
 * [std.sync.OnceLock]: ../../std/sync/struct.OnceLock.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.OnceCell
 *
 * val cell = OnceCell.new()
 * check(cell.get() == null)
 * val value: String = cell.getOrInit({
 *     "Hello, World!".toString()
 * })
 * check(value == "Hello, World!")
 * check(cell.get() != null)
 * ```
 */
public open class OnceCell<T>(
    // Invariant: written to at most once.
    public var inner: UnsafeCell<T?>,
) {
    /**
     * Gets the reference to the underlying value.
     *
     * Returns `None` if the cell is uninitialized.
     */
    public open fun get(): T? {
        // SAFETY: Safe due to `inner`s invariant
        return this.inner.get().asRef()
    }

    /**
     * Gets the mutable reference to the underlying value.
     *
     * Returns `None` if the cell is uninitialized.
     */
    public open fun getMut(): T? = this.inner.getMut()

    /**
     * Initializes the contents of the cell to `value`.
     *
     * # Errors
     *
     * This method returns `Ok(())` if the cell was uninitialized
     * and `Err(value)` if it was already initialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * val cell = OnceCell.new()
     * check(cell.get() == null)
     * check(cell.set(92) == Unit)
     * check(cell.set(62) == Result.failure(62))
     * check(cell.get() != null)
     * ```
     */
    public open fun set(value: T): Result<Unit, T> =
        run {
            val matchVal = this.tryInsert(value)
            when (matchVal) {
                is Result.Ok<*, *> -> {
                    Result.Ok(Unit)
                }
                is Result.Err<*, *> -> {
                    val value1 = matchVal.error.second
                    Result.Err(value1)
                }
            }
        }

    /**
     * Initializes the contents of the cell to `value` if the cell was
     * uninitialized, then returns a reference to it.
     *
     * # Errors
     *
     * This method returns `Ok(value)` if the cell was uninitialized
     * and `Err((currentValue, value))` if it was already initialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * @feature(onceCellTryInsert)
     * public fun doctestEntry() {
     *     val cell = OnceCell.new()
     *     check(cell.get() == null)
     *     check(cell.tryInsert(92) == Result.success(92))
     *     check(cell.tryInsert(62) == Result.failure(Pair(92, 62)))
     *     check(cell.get() != null)
     * }
     * ```
     */
    public open fun tryInsert(value: T): Result<T, Pair<T, T>> {
        when (val matchVal = this.get()) {
            null -> {}
            else -> {
                val old = matchVal
                return Result.Err(Pair(old, value))
            }
        }
        // SAFETY: This is the only place where we set the slot, no races
        // due to reentrancy/concurrency are possible, and we've
        // checked that slot is currently `None`, so this write
        // maintains the `inner`s invariant.
        val slot = this.inner.get()
        return Result.Ok(slot.insert(value))
    }

    /**
     * Gets the contents of the cell, initializing it to `f()`
     * if the cell was uninitialized.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * It is an error to reentrantly initialize the cell from `f`. Doing
     * so results in a panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * val cell = OnceCell.new()
     * val value = cell.getOrInit({ 92 })
     * check(value == 92)
     * val value1 = cell.getOrInit({ error("unreachable") })
     * check(value1 == 92)
     * ```
     */
    public open fun getOrInit(f: () -> T): T =
        when (val matchVal = this.getOrTryInit({ Result.Ok(f()) })) {
            is Result.Ok<*, *> -> {
                val value = (matchVal as Result.Ok<*, *>).value
                value
            }
        }

    /**
     * Gets the mutable reference of the contents of the cell,
     * initializing it to `f()` if the cell was uninitialized.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * @feature(onceCellGetMut)
     * public fun doctestEntry() {
     *     var cell = OnceCell.new()
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
            is Result.Ok<*, *> -> {
                val value = (matchVal as Result.Ok<*, *>).value
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
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * It is an error to reentrantly initialize the cell from `f`. Doing
     * so results in a panic.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * @feature(onceCellTry)
     * public fun doctestEntry() {
     *     val cell = OnceCell.new()
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
        when (val matchVal = this.get()) {
            null -> {}
            else -> {
                val value = matchVal
                return Result.Ok(value)
            }
        }
        return this.tryInit(f)
    }

    /**
     * Gets the mutable reference of the contents of the cell, initializing
     * it to `f()` if the cell was uninitialized. If the cell was uninitialized
     * and `f()` failed, an error is returned.
     *
     * # Panics
     *
     * If `f()` panics, the panic is propagated to the caller, and the cell
     * remains uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     * import std.cell.OnceCell
     *
     * @feature(onceCellGetMut)
     * public fun doctestEntry() {
     *     var cell: OnceCell<UInt> = OnceCell.new()
     *     // Failed attempts to initialize the cell do not change its contents
     *     check(cell.getMutOrTryInit({ "not a number!".parse() }).isErr())
     *     check(cell.get() == null)
     *     val value = cell.getMutOrTryInit({ "1234".parse() })
     *     check(value == Result.success(RustMutableReference(allocateNativeCell(1234, PrimitiveLayouts.I32), PrimitiveLayouts.I32)))
     *     val value1 = value ?: return
     *     value1 += 2
     *     check(cell.get() == 1236)
     * }
     * ```
     */
    public open fun <E> getMutOrTryInit(f: () -> Result<T, E>): Result<T, E> {
        if (this.get() == null) {
            when (val tryResult = this.tryInit(f)) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
        }
        return Result.Ok(this.getMut()!!)
    }

    // Avoid inlining the initialization closure into the common path that fetches
    // the already initialized value
    public open fun <E> tryInit(f: () -> Result<T, E>): Result<T, E> {
        val value =
            when (val tryResult = f()) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
        // Note that *some* forms of reentrant initialization might lead to
        // UB (see `reentrantInit` test). I believe that just removing this
        // `panic`, while keeping `tryInsert` would be sound, but it seems
        // better to panic, rather than to silently use an old value.
        return when (val matchVal = this.tryInsert(value)) {
            is Ok -> {
                val value1 = matchVal.value
                Result.Ok(value1)
            }
            else -> {
                error("reentrant init")
            }
        }
    }

    /**
     * Consumes the cell, returning the wrapped value.
     *
     * Returns `None` if the cell was uninitialized.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * val cell: OnceCell<String> = OnceCell.new()
     * check(cell.intoInner() == null)
     * val cell1 = OnceCell.new()
     * cell1.set("hello".toOwned())
     * check(cell1.intoInner() == "hello".toOwned())
     * ```
     */
    public open fun intoInner(): T? {
        // Because `intoInner` takes `self` by value, the compiler statically verifies
        // that it is not currently borrowed. So it is safe to move out `Option<T>`.
        return this.inner.intoInner()
    }

    /**
     * Takes the value out of this `OnceCell`, moving it back to an uninitialized state.
     *
     * Has no effect and returns `None` if the `OnceCell` is uninitialized.
     *
     * Safety is guaranteed by requiring a mutable reference.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cell.OnceCell
     *
     * var cell: OnceCell<String> = OnceCell.new()
     * check(cell.take() == null)
     * var cell1 = OnceCell.new()
     * cell1.set("hello".toOwned())
     * check(cell1.also { cell1 = null } == "hello".toOwned())
     * check(cell1.get() == null)
     * ```
     */
    public open fun take(): T? =
        io.github.kotlinmania.rustlang.core.mem
            .take(this)
            .intoInner()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val d = DebugTuple(f, "OnceCell")
        when (val matchVal = this.get()) {
            null -> d.field("<uninit>")
            else -> {
                val v = matchVal
                d.field(v)
            }
        }
        return d.finish()
    }

    public open fun clone(): OnceCell<T> =
        when (val matchVal = this.get()) {
            null -> OnceCell.new()
            else -> {
                val value = matchVal
                OnceCell.from(value.clone())
            }
        }

    public open fun eq(other: OnceCell<T>): Boolean = this.get() == other.get()

    // Just like for `Cell<T>` this isn't needed, but results in nicer error messages.

    public companion object {
        /**
         * Creates a new uninitialized cell.
         */
        public fun <T> new(): OnceCell<T> = OnceCell(inner = UnsafeCell.new(null))

        /**
         * Creates a new initialized cell.
         *
         * This is equivalent to `OnceCell.from(value)`, but can be used in
         * const contexts, unlike the `From` implementation.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.OnceCell
         *
         * @feature(onceCellNewInit)
         * public fun doctestEntry() {
         *     val CELL: OnceCell<Int> = OnceCell.newInit(1)
         *     check(CELL.get() == 1)
         *     val cell = OnceCell.newInit("kitty")
         *     check(cell.set("puppy") == Result.failure("puppy"))
         *     check(cell.get() == "kitty".toString())
         * }
         * ```
         */
        public fun <T> newInit(initValue: T): OnceCell<T> = OnceCell(inner = UnsafeCell.new(initValue))

        public fun <T> default(): OnceCell<T> = OnceCell.new()

        /**
         * Creates a new `OnceCell<T>` which already contains the given `value`.
         */
        public fun <T> from(value: T): OnceCell<T> = OnceCell(inner = UnsafeCell.new(value))
    }
}
