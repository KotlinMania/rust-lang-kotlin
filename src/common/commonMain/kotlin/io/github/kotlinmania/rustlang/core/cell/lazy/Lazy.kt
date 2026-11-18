// port-lint: source cell/lazy.rs
package io.github.kotlinmania.rustlang.core.cell.lazy

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.Read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.write
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

public sealed class State<T, F> {
    public data class Uninit<T, F>(public var value: F) : State<T, F>()
    public data class Init<T, F>(public var value: T) : State<T, F>()
    public class Poisoned<T, F>() : State<T, F>()
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
        return d.finish()
    }

    public companion object {
        public operator fun <T, F> invoke(
            state: UnsafeCell<State<T, F>>,
        ): LazyCell<T, F> = LazyCell<T, F>(
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
        public fun <T> intoInner(thisRef: LazyCell<T, F>): Result<T, () -> T> =
            when (val matchVal = thisRef.state.intoInner()) {
                is State.Init<*, *> -> {
                    val (data) = matchVal
                    Result.Ok<T, () -> T>(data)
                }
                is State.Uninit<*, *> -> {
                    val (f) = matchVal
                    Result.Err<T, () -> T>(f)
                }
                is State.Poisoned<*, *> -> panicPoisoned()
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
        public fun <T> force(thisRef: LazyCell<T, () -> T>): T {
            // SAFETY:
            // This invalidates any mutable references to the data. The resulting
            // reference lives either until the end of the borrow of `this` (in the
            // initialized case) or is invalidated in `reallyInit` (in the
            // uninitialized case; `reallyInit` will create and return a fresh reference).
            val state = thisRef.state.get()
            return when (state) {
                is State.Init<*, *> -> {
                    val (data) = state
                    data
                }
                // SAFETY: The state is uninitialized.
                is State.Uninit<*, *> -> LazyCell.reallyInit(thisRef)
                is State.Poisoned<*, *> -> panicPoisoned()
            }
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
        public fun <T> forceMut(thisRef: LazyCell<T, () -> T>): T {
            /**
             * # Safety
             * May only be called when the state is `Uninit`.
             */
            fun <T> reallyInitMut(state: State<T, () -> T>): T {
                // INVARIANT: Always valid, but the value may not be dropped.
                open class PoisonOnPanic<T, F>(
                    public var value: MutPtr,
                ) {
                    public var field0: MutPtr
                        get() = value
                        set(v) {
                            value = v
                        }

                    public open fun drop() {
                        // SAFETY: Invariant states it is valid, and we don't drop the old value.
                        run {
                            (UnresolvedTypeLayout<State<T, () -> T>>()).writeNative((this.value).rawAddress, State.Poisoned())
                        }
                    }
                }
                val f = state ?: // `unreachable()` here won't optimize out because the function is cold.
// SAFETY: Precondition.
unreachableUnchecked()
                // SAFETY: We never drop the state after we read `f`, and we write a valid value back
                // in any case, panic or success. `f` can't access the `LazyCell` because it is mutably
                // borrowed.
                val f1 = Read(f, typeLayoutT = UnresolvedTypeLayout<State<T, () -> T>>())
                // INVARIANT: Initiated from mutable reference, don't drop because we read it.
                val guard = PoisonOnPanic(state)
                val data = f1()
                // SAFETY: `PoisonOnPanic` invariant, and we don't drop the old value.
                run {
                    write(guard.first, State.Init(data))
                }
                forget(guard)
                val data1 = state ?: error("unreachable")
                return data1
            }
            val state = thisRef.state.getMut()
            return when (state) {
                is State.Init<*, *> -> {
                    val (data) = state
                    data
                }
                // SAFETY: `state` is `Uninit`.
                is State.Uninit<*, *> -> reallyInitMut(state)
                is State.Poisoned<*, *> -> panicPoisoned()
            }
        }

        /**
         * # Safety
         * May only be called when the state is `Uninit`.
         */
        public fun <T> reallyInit(thisRef: LazyCell<T, () -> T>): T {
            // SAFETY:
            // This function is only called when the state is uninitialized,
            // so no references to `state` can exist except for the reference
            // in `force`, which is invalidated here and not accessed again.
            val state = thisRef.state.get()
            // Temporarily mark the state as poisoned. This prevents reentrant
            // accesses and correctly poisons the cell if the closure panicked.
            val f = replace(state, State.Poisoned()) ?: error("unreachable")
            val data = f()
            // SAFETY:
            // If the closure accessed the cell through something like a reentrant
            // mutex, but caught the panic resulting from the state being poisoned,
            // the mutable borrow for `state` will be invalidated, so we need to
            // go through the `UnsafeCell` pointer here. The state can only be
            // poisoned at this point, so using `write` to skip the destructor
            // of `State` should help the optimizer.
            thisRef.state.get().write(State.Init(data))
            // SAFETY:
            // The previous references were invalidated by the `write` call above,
            // so do a new shared borrow of the state instead.
            val state1 = thisRef.state.get()
            val data1 = state1 ?: error("unreachable")
            return data1
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
        public fun <T, F> getMut(thisRef: LazyCell<T, F>): T? {
            val state = thisRef.state.getMut()
            return when (state) {
                is State.Init<*, *> -> {
                    val (data) = state
                    data
                }
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
         * import std.cell.LazyCell
         *
         * val lazy = LazyCell.new({ 92 })
         * check(LazyCell.get(lazy) == null)
         * LazyCell.force(lazy)
         * check(LazyCell.get(lazy) == 92)
         * ```
         */
        public fun <T, F> get(thisRef: LazyCell<T, F>): T? {
            // SAFETY:
            // This is sound for the same reason as in `force`: once the state is
            // initialized, it will not be mutably accessed again, so this reference
            // will stay valid for the duration of the borrow to `self`.
            val state = thisRef.state.get()
            return when (state) {
                is State.Init<*, *> -> {
                    val (data) = state
                    data
                }
                else -> null
            }
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
public fun <T> LazyCell<T, F>.deref(): T = LazyCell.force(this)

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
public fun <T> LazyCell<T, F>.derefMut(): T = LazyCell.forceMut(this)

/**
 * Creates a new lazy value using `Default` as the initializing function.
 */
public fun <T> defaultLazyCellT(defaultTFn: () -> T): LazyCell<T, () -> T> = LazyCell.new(defaultTFn)

public fun panicPoisoned(): Nothing {
    error("LazyCell instance has previously been poisoned")
}
