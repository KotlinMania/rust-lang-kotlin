// rust-kotlin: manual
// port-lint: source cell/lazy.rs
package io.github.kotlinmania.rustlang.core.cell.lazy

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.result.Result

private class DebugTuple(
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

public sealed class State<T, F> {
    public data class Uninit<T, F>(
        public var value: F,
    ) : State<T, F>()
    public data class Init<T, F>(
        public var value: T,
    ) : State<T, F>()
    public class Poisoned<T, F> : State<T, F>()
}

/**
 * A value which is initialized on the first access.
 *
 * For a thread-safe version of this struct, see [std.sync.LazyLock].
 *
 * [std.sync.LazyLock]: ../../std/sync/struct.LazyLock.html
 *
 * # Poisoning
 *
 * If the initialization closure passed to [LazyCell.new] panics, the cell will be poisoned.
 * Once the cell is poisoned, any threads that attempt to access this cell (via a dereference
 * or via an explicit call to [force]) will panic.
 *
 * This concept is similar to that of poisoning in the [std.sync.poison] module. A key
 * difference, however, is that poisoning in `LazyCell` is _unrecoverable_. All future accesses of
 * the cell from other threads will panic, whereas a type in [std.sync.poison] like
 * [std.sync.poison.Mutex] allows recovery via [PoisonError.intoInner].
 *
 * [force]: LazyCell.force
 * [std.sync.poison]: ../../std/sync/poison/index.html
 * [std.sync.poison.Mutex]: ../../std/sync/poison/struct.Mutex.html
 * [PoisonError.intoInner]: ../../std/sync/poison/struct.PoisonError.html#method.intoInner
 *
 * # Examples
 *
 * ```kotlin
 * import std.cell.LazyCell
 *
 * val lazy: LazyCell<Int> = LazyCell.new({
 *     println("initializing")
 *     92
 * })
 * println("ready")
 * println("$lazy")
 * println("$lazy")
 * // Prints:
 * // ready
 * // initializing
 * // 92
 * // 92
 * ```
 */
public open class LazyCell<T, F> internal constructor(
    stateCtorArg: UnsafeCell<State<T, F>>,
) {
    public var state: UnsafeCell<State<T, F>> = stateCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val d = DebugTuple(f, "LazyCell")
        when (val matchVal = LazyCell.get(this)) {
            null -> d.field("<uninit>")
            else -> {
                val data = matchVal
                d.field(data)
            }
        }
        return run {
            d.finish()
            Result.Ok<Unit, Unit>(Unit)
        }
    }

    public companion object {
        public operator fun <T, F> invoke(
            state: UnsafeCell<State<T, F>>,
        ): LazyCell<T, F> =
            LazyCell<T, F>(
                stateCtorArg = state,
            )

        /**
         * Creates a new lazy value with the given initializing function.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * val hello = "Hello, World!".toString()
         * val lazy = LazyCell.new({ hello.uppercase() })
         * check(lazy == "HELLO, WORLD!")
         * ```
         */
        public fun <T> new(f: () -> T): LazyCell<T, () -> T> = LazyCell(state = UnsafeCell.new(State.Uninit(f)))

        /**
         * Consumes this `LazyCell` returning the stored value.
         *
         * Returns `Ok(value)` if `Lazy` is initialized and `Err(f)` otherwise.
         *
         * # Panics
         *
         * Panics if the cell is poisoned.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * @feature(lazyCellIntoInner)
         * public fun doctestEntry() {
         *     val hello = "Hello, World!".toString()
         *     val lazy = LazyCell.new({ hello.uppercase() })
         *     check(lazy == "HELLO, WORLD!")
         *     check(LazyCell.intoInner(lazy).ok() == "HELLO, WORLD!".toString())
         * }
         * ```
         */
        public fun <T, F> intoInner(thisRef: LazyCell<T, F>): Result<T, F> =
            when (val matchVal = thisRef.state.intoInner()) {
                is State.Init -> Result.Ok(matchVal.value)
                is State.Uninit -> Result.Err(matchVal.value)
                is State.Poisoned -> panicPoisoned()
            }

        /**
         * Forces the evaluation of this lazy value and returns a reference to
         * the result.
         *
         * This is equivalent to the `Deref` impl, but is explicit.
         *
         * # Panics
         *
         * If the initialization closure panics (the one that is passed to the [new] method), the
         * panic is propagated to the caller, and the cell becomes poisoned. This will cause all future
         * accesses of the cell (via [force] or a dereference) to panic.
         *
         * [new]: LazyCell.new
         * [force]: LazyCell.force
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * val lazy = LazyCell.new({ 92 })
         * check(LazyCell.force(lazy) == 92)
         * check(lazy == 92)
         * ```
         */
        public fun <T> force(thisRef: LazyCell<T, () -> T>): T =
            when (val state = thisRef.state.value) {
                is State.Init -> state.value
                is State.Uninit -> LazyCell.reallyInit(thisRef)
                is State.Poisoned -> panicPoisoned()
            }

        /**
         * Forces the evaluation of this lazy value and returns a mutable reference to
         * the result.
         *
         * # Panics
         *
         * If the initialization closure panics (the one that is passed to the [new] method), the
         * panic is propagated to the caller, and the cell becomes poisoned. This will cause all future
         * accesses of the cell (via [force] or a dereference) to panic.
         *
         * [new]: LazyCell.new
         * [force]: LazyCell.force
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * var lazy = LazyCell.new({ 92 })
         * val p = LazyCell.forceMut(lazy)
         * check(p == 92)
         * p = 44
         * check(lazy == 44)
         * ```
         */
        public fun <T> forceMut(thisRef: LazyCell<T, () -> T>): T =
            when (val state = thisRef.state.value) {
                is State.Init -> state.value
                is State.Uninit -> LazyCell.reallyInit(thisRef)
                is State.Poisoned -> panicPoisoned()
            }

        /**
         * # Safety
         * May only be called when the state is `Uninit`.
         */
        public fun <T> reallyInit(thisRef: LazyCell<T, () -> T>): T {
            val prev = thisRef.state.value
            val f = (prev as? State.Uninit<T, () -> T>)?.value ?: error("unreachable")
            // Temporarily mark the state as poisoned before calling `f()`.
            // If `f()` panics and unwinds, the state remains poisoned without catching exceptions.
            thisRef.state.value = State.Poisoned()
            val data = f()
            thisRef.state.value = State.Init(data)
            return data
        }

        /**
         * Returns a mutable reference to the value if initialized. Otherwise (if uninitialized or
         * poisoned), returns `None`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * var lazy = LazyCell.new({ 92 })
         * check(LazyCell.getMut(lazy) == null)
         * LazyCell.force(lazy)
         * LazyCell.getMut(lazy).unwrap() = 44
         * check(lazy == 44)
         * ```
         */
        public fun <T, F> getMut(thisRef: LazyCell<T, F>): T? =
            when (val state = thisRef.state.value) {
                is State.Init -> state.value
                else -> null
            }

        /**
         * Returns a reference to the value if initialized. Otherwise (if uninitialized or poisoned),
         * returns `None`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.cell.LazyCell
         *
         * val lazy = LazyCell.new({ 92 })
         * check(LazyCell.get(lazy) == null)
         * LazyCell.force(lazy)
         * check(LazyCell.get(lazy) == 92)
         * ```
         */
        public fun <T, F> get(thisRef: LazyCell<T, F>): T? =
            when (val state = thisRef.state.value) {
                is State.Init -> state.value
                else -> null
            }

        /**
         * Constructs a `LazyCell` that starts already initialized
         * with the provided value.
         */
        public fun <T, F> from(value: T): LazyCell<T, F> = LazyCell(state = UnsafeCell.new(State.Init(value)))
    }
}

/**
 * # Panics
 *
 * If the initialization closure panics (the one that is passed to the [new] method), the
 * panic is propagated to the caller, and the cell becomes poisoned. This will cause all future
 * accesses of the cell (via [force] or a dereference) to panic.
 *
 * [new]: LazyCell.new
 * [force]: LazyCell.force
 */
public fun <T> LazyCell<T, () -> T>.deref(): T = LazyCell.force(this)

/**
 * # Panics
 *
 * If the initialization closure panics (the one that is passed to the [new] method), the
 * panic is propagated to the caller, and the cell becomes poisoned. This will cause all future
 * accesses of the cell (via [force] or a dereference) to panic.
 *
 * [new]: LazyCell.new
 * [force]: LazyCell.force
 */
public fun <T> LazyCell<T, () -> T>.derefMut(): T = LazyCell.forceMut(this)

/**
 * Creates a new lazy value using `Default` as the initializing function.
 */
public fun <T> defaultLazyCellT(defaultTFn: () -> T): LazyCell<T, () -> T> = LazyCell.new(defaultTFn)

public fun panicPoisoned(): Nothing {
    error("LazyCell instance has previously been poisoned")
}
