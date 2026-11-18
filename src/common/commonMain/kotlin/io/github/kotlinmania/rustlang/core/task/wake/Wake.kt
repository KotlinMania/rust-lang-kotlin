// port-lint: source task/wake.rs
package io.github.kotlinmania.rustlang.core.task.wake

import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.any.Any
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.result.Result

public fun interface FnCallback {
    public operator fun invoke(arg0: ConstPtr): RawWaker
}

public fun interface FnCallback1 {
    public operator fun invoke(arg0: ConstPtr)
}

public fun interface FromFnPtrFCallback {
    public operator fun invoke()
}

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

/**
 * A `RawWaker` allows the implementor of a task executor to create a [Waker]
 * or a [LocalWaker] which provides customized wakeup behavior.
 *
 * It consists of a data pointer and a [virtual function pointer table (vtable)][vtable]
 * that customizes the behavior of the `RawWaker`.
 *
 * `RawWaker`s are unchecked to use.
 * Implementing the [Wake] trait is a safe alternative that requires memory allocation.
 *
 * [vtable]: https://en.wikipedia.org/wiki/VirtualMethodTable
 * [Wake]: ../../alloc/task/trait.Wake.html
 */
public open class RawWaker internal constructor(
    dataCtorArg: ConstPtr,
    /**
     * Virtual function pointer table that customizes the behavior of this waker.
     */
    public var vtable: RawWakerVTable,
) {
    /**
     * A data pointer, which can be used to store arbitrary data as required
     * by the executor. This could be e.g. a type-erased pointer to an `Arc`
     * that is associated with the task.
     * The value of this field gets passed to all functions that are part of
     * the vtable as the first parameter.
     */
    public var data: ConstPtr = dataCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            data: ConstPtr,
            vtable: RawWakerVTable,
        ): RawWaker = RawWaker(
            dataCtorArg = data,
            vtable = vtable,
        )

        /**
         * Creates a new `RawWaker` from the provided `data` pointer and `vtable`.
         *
         * The `data` pointer can be used to store arbitrary data as required
         * by the executor. This could be e.g. a type-erased pointer to an `Arc`
         * that is associated with the task.
         * The value of this pointer will get passed to all functions that are part
         * of the `vtable` as the first parameter.
         *
         * It is important to consider that the `data` pointer must point to a
         * thread safe type such as an `Arc<T: ThreadSafe>`
         * when used to construct a [Waker]. This restriction is lifted when
         * constructing a [LocalWaker], which allows using types that do not implement
         * <code>[Send] + [Sync]</code> like `Rc<T>`.
         *
         * The `vtable` customizes the behavior of a `Waker` which gets created
         * from a `RawWaker`. For each operation on the `Waker`, the associated
         * function in the `vtable` of the underlying `RawWaker` will be called.
         */
        public fun new(data: ConstPtr, vtable: RawWakerVTable): RawWaker = RawWaker(data = data, vtable = vtable)

        public val NOOP: RawWaker = run {
            val VTABLE: RawWakerVTable = RawWakerVTable.new({ RawWaker.NOOP }, {}, {}, {})
            RawWaker.new(ConstPtr(0L), VTABLE)
        }
    }
}

// For bit-stuffing pointers we guarantee align >= 8.

/**
 * A virtual function pointer table (vtable) that specifies the behavior
 * of a [RawWaker].
 *
 * The pointer passed to all functions inside the vtable is the `data` pointer
 * from the enclosing [RawWaker] object.
 *
 * The functions inside this struct are only intended to be called on the `data`
 * pointer of a properly constructed [RawWaker] object from inside the
 * [RawWaker] implementation. Calling one of the contained functions using
 * any other `data` pointer will cause undefined behavior.
 *
 * Note that while this type implements `PartialEq`, comparing function pointers, and hence
 * comparing structs like this that contain function pointers, is unreliable: pointers to the same
 * function can compare inequal (because functions are duplicated in multiple codegen units), and
 * pointers to *different* functions can compare equal (since identical functions can be
 * deduplicated within a codegen unit).
 *
 * This struct is guaranteed to be aligned to at least 8 bytes.
 *
 * # Thread safety
 * If the [RawWaker] will be used to construct a [Waker] then
 * these functions must all be thread-safe (even though [RawWaker] is
 * <code>\![Send] + \![Sync]</code>). This is because [Waker] is <code>[Send] + [Sync]</code>,
 * and it may be moved to arbitrary threads or invoked by  reference. For example,
 * this means that if the `clone` and `drop` functions manage a reference count,
 * they must do so atomically.
 *
 * However, if the [RawWaker] will be used to construct a [LocalWaker] instead, then
 * these functions don't need to be thread safe. This means that <code>\![Send] + \![Sync]</code>
 *  data can be stored in the data pointer, and reference counting does not need any atomic
 * synchronization. This is because [LocalWaker] is not thread safe itself, so it cannot
 * be sent across threads.
 */
public open class RawWakerVTable(
    /**
     * This function will be called when the [RawWaker] gets cloned, e.g. when
     * the [Waker] in which the [RawWaker] is stored gets cloned.
     *
     * The implementation of this function must retain all resources that are
     * required for this additional instance of a [RawWaker] and associated
     * task. Calling `wake` on the resulting [RawWaker] should result in a wakeup
     * of the same task that would have been awoken by the original [RawWaker].
     */
    public var clone: FnCallback,
    /**
     * This function will be called when `wake` is called on the [Waker].
     * It must wake up the task associated with this [RawWaker].
     *
     * The implementation of this function must make sure to release any
     * resources that are associated with this instance of a [RawWaker] and
     * associated task.
     */
    public var wake: FnCallback1,
    /**
     * This function will be called when `wakeByRef` is called on the [Waker].
     * It must wake up the task associated with this [RawWaker].
     *
     * This function is similar to `wake`, but must not consume the provided data
     * pointer.
     */
    public var wakeByRef: FnCallback1,
    /**
     * This function will be called when a [Waker] gets dropped.
     *
     * The implementation of this function must make sure to release any
     * resources that are associated with this instance of a [RawWaker] and
     * associated task.
     */
    public var drop: FnCallback1,
) {
    public open fun clone(): RawWakerVTable = RawWakerVTable(clone, wake, wakeByRef, drop)

    public companion object {
        /**
         * Creates a new `RawWakerVTable` from the provided `clone`, `wake`,
         * `wakeByRef`, and `drop` functions.
         *
         * If the [RawWaker] will be used to construct a [Waker] then
         * these functions must all be thread-safe (even though [RawWaker] is
         * <code>\![Send] + \![Sync]</code>). This is because [Waker] is <code>[Send] + [Sync]</code>,
         * and it may be moved to arbitrary threads or invoked by  reference. For example,
         * this means that if the `clone` and `drop` functions manage a reference count,
         * they must do so atomically.
         *
         * However, if the [RawWaker] will be used to construct a [LocalWaker] instead, then
         * these functions don't need to be thread safe. This means that <code>\![Send] + \![Sync]</code>
         * data can be stored in the data pointer, and reference counting does not need any atomic
         * synchronization. This is because [LocalWaker] is not thread safe itself, so it cannot
         * be sent across threads.
         * # `clone`
         *
         * This function will be called when the [RawWaker] gets cloned, e.g. when
         * the [Waker]/[LocalWaker] in which the [RawWaker] is stored gets cloned.
         *
         * The implementation of this function must retain all resources that are
         * required for this additional instance of a [RawWaker] and associated
         * task. Calling `wake` on the resulting [RawWaker] should result in a wakeup
         * of the same task that would have been awoken by the original [RawWaker].
         *
         * # `wake`
         *
         * This function will be called when `wake` is called on the [Waker].
         * It must wake up the task associated with this [RawWaker].
         *
         * The implementation of this function must make sure to release any
         * resources that are associated with this instance of a [RawWaker] and
         * associated task.
         *
         * # `wakeByRef`
         *
         * This function will be called when `wakeByRef` is called on the [Waker].
         * It must wake up the task associated with this [RawWaker].
         *
         * This function is similar to `wake`, but must not consume the provided data
         * pointer.
         *
         * # `drop`
         *
         * This function will be called when a [Waker]/[LocalWaker] gets
         * dropped.
         *
         * The implementation of this function must make sure to release any
         * resources that are associated with this instance of a [RawWaker] and
         * associated task.
         */
        public fun new(clone: FnCallback, wake: FnCallback1, wakeByRef: FnCallback1, drop: FnCallback1): RawWakerVTable = RawWakerVTable(clone = clone, wake = wake, wakeByRef = wakeByRef, drop = drop)
    }
}

public sealed class ExtData {
    public data class Some(public var value: RustAny) : ExtData()
    public data class None(public var value: Unit) : ExtData()
}

/**
 * The context of an asynchronous task.
 *
 * Currently, `Context` only serves to provide access to a [`Waker`](Waker)
 * which can be used to wake the current task.
 */
public open class Context internal constructor(
    public var waker: Waker,
    public var localWaker: LocalWaker,
    extCtorArg: AssertUnwindSafe<ExtData>,
    // Ensure we future-proof against variance changes by forcing
    // the scope to be invariant (argument-position scopes
    // are contravariant while return-position scopes are
    // covariant).
    public var marker: Unit,
    // Ensure `Context` is `!Send` and `!Sync` in order to allow
    // for future `!Send` and / or `!Sync` fields.
    public var marker2: Unit,
) {
    public var ext: AssertUnwindSafe<ExtData> = extCtorArg
        internal set

    /**
     * Returns a reference to the [Waker] for the current task.
     */
    public open fun waker(): Waker = this.waker

    /**
     * Returns a reference to the [LocalWaker] for the current task.
     */
    public open fun localWaker(): LocalWaker = this.localWaker

    /**
     * Returns a reference to the extension data for the current task.
     */
    public open fun ext(): RustAny {
        // Note: this field makes Context extra-weird about unwind safety
        // can we justify AssertUnwindSafe if we stabilize this? do we care?
        return when (val matchVal = this.ext.value) {
            is ExtData.Some -> {
                val (data) = matchVal
                data
            }
            is ExtData.None -> {
                val (unit) = matchVal
                unit
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "Context").field("waker", this.waker).finish(); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public operator fun invoke(
            waker: Waker,
            localWaker: LocalWaker,
            ext: AssertUnwindSafe<ExtData>,
            marker: Unit,
            marker2: Unit,
        ): Context = Context(
            waker = waker,
            localWaker = localWaker,
            extCtorArg = ext,
            marker = marker,
            marker2 = marker2,
        )

        /**
         * Creates a new `Context` from a [`Waker`](Waker).
         */
        public fun fromWaker(waker: Waker): Context = ContextBuilder.fromWaker(waker).build()
    }
}

/**
 * A Builder used to construct a `Context` instance
 * with support for `LocalWaker`.
 *
 * # Examples
 * ```kotlin
 * import std.future.Future
 * import std.task.ContextBuilder
 * import std.task.LocalWaker
 * import std.task.Poll
 * import std.task.Waker
 *
 * @feature(localWaker)
 * public fun doctestEntry() {
 *     val localWaker = LocalWaker.noop()
 *     val waker = Waker.noop()
 *     var cx = ContextBuilder.fromWaker(waker).localWaker(localWaker).build()
 *     var future = pin(20)
 *     val poll = future.asMut().poll(cx)
 *     check(poll == Poll.Ready(20))
 * }
 * ```
 */
public open class ContextBuilder(
    public var waker: Waker,
    public var localWaker: LocalWaker,
    public var ext: ExtData,
    // Ensure we future-proof against variance changes by forcing
    // the scope to be invariant (argument-position scopes
    // are contravariant while return-position scopes are
    // covariant).
    public var marker: Unit,
    // Ensure `Context` is `!Send` and `!Sync` in order to allow
    // for future `!Send` and / or `!Sync` fields.
    public var marker2: Unit,
) {
    /**
     * Sets the value for the waker on `Context`.
     */
    public open fun waker(waker: Waker): ContextBuilder = this.copy(waker = waker)

    /**
     * Sets the value for the local waker on `Context`.
     */
    public open fun localWaker(localWaker: LocalWaker): ContextBuilder = this.copy(localWaker = localWaker)

    /**
     * Sets the value for the extension data on `Context`.
     */
    public open fun ext(data: RustAny): ContextBuilder = this.copy(ext = ExtData.Some(data))

    /**
     * Builds the `Context`.
     */
    public open fun build(): Context {
        val waker = this.waker
        val localWaker = this.localWaker
        val ext = this.ext
        val marker = this.marker
        val marker2 = this.marker2
        return Context(waker = waker, localWaker = localWaker, ext = AssertUnwindSafe(ext), marker = marker, marker2 = marker2)
    }

    public companion object {
        /**
         * Creates a ContextBuilder from a Waker.
         */
        public fun fromWaker(waker: Waker): ContextBuilder {
            // SAFETY: LocalWaker is just Waker without thread safety
            val localWaker = transmuteWithLayouts<Waker, ContextBuilder>(waker, UnresolvedTypeLayout<Waker>(), UnresolvedTypeLayout<ContextBuilder>())
            return ContextBuilder(waker = waker, localWaker = localWaker, ext = ExtData.None(Unit), marker = Unit, marker2 = Unit)
        }

        /**
         * Creates a ContextBuilder from an existing Context.
         */
        public fun from(cx: Context): ContextBuilder {
            val ext1 =
                when (val matchVal = cx.ext.value) {
                    is ExtData.Some -> {
                        val (ext) = matchVal
                        ExtData.Some(ext)
                    }
                    is ExtData.None -> ExtData.None(Unit)
                }
            return ContextBuilder(waker = cx.waker, localWaker = cx.localWaker, ext = ext1, marker = Unit, marker2 = Unit)
        }
    }
}

/**
 * A `Waker` is a handle for waking up a task by notifying its executor that it
 * is ready to be run.
 *
 * This handle encapsulates a [RawWaker] instance, which defines the
 * executor-specific wakeup behavior.
 *
 * The typical life of a `Waker` is that it is constructed by an executor, wrapped in a
 * [Context], then passed to [Future.poll]. Then, if the future chooses to return
 * [Poll.Pending], it must also store the waker somehow and call [Waker.wake] when
 * the future should be polled again.
 *
 * Implements [Clone], [Send], and [Sync]; therefore, a waker may be invoked
 * from any thread, including ones not in any way managed by the executor. For example,
 * this might be done to wake a future when a blocking function call completes on another
 * thread.
 *
 * Note that it is preferable to use `waker.cloneFrom(newWaker)` instead
 * of `* waker = newWaker.clone()`, as the former will avoid cloning the waker
 * unnecessarily if the two wakers [wake the same task](Self.willWake).
 *
 * Constructing a `Waker` from a [RawWaker] is unchecked.
 * Implementing the [Wake] trait is a safe alternative that requires memory allocation.
 *
 * [Wake]: ../../alloc/task/trait.Wake.html
 */
public open class Waker internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout(),
) : AsRef<LocalWaker> {
    public var waker: RawWaker
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()) : this(allocateNativeCell(waker, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Waker> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Waker(ptr, layout) }, { it.waker })

    /**
     * Wakes up the task associated with this `Waker`.
     *
     * As long as the executor keeps running and the task is not finished,
     * it is guaranteed that each invocation of [`wake`](Self.wake) (or
     * [`wakeByRef`](Self.wakeByRef)) will be followed by at least one
     * [poll] of the task to which this `Waker` belongs, such that the call to
     * [`wake`](Self.wake) (or [`wakeByRef`](Self.wakeByRef)) _happens-before_
     * the beginning of the invocation of [poll]. This makes it possible to temporarily
     * yield to other tasks while running potentially unbounded processing loops.
     *
     * Note that the above implies that multiple wake-ups may be coalesced into a
     * single [poll] invocation by the executor.
     *
     * Also note that yielding to competing tasks is not guaranteed: it is the
     * executor’s choice which task to run and the executor may choose to run the
     * current task again.
     */
    public open fun wake() {
        // The actual wakeup call is delegated through a virtual function call
        // to the implementation which is defined by the executor.
        // Don't call `drop` -- the waker will be consumed by `wake`.
        val thisRef = this
        // SAFETY: This is safe because `Waker.fromRaw` is the only way
        // to initialize `wake` and `data` requiring the user to acknowledge
        // that the contract of `RawWaker` is upheld.
        (thisRef.waker.vtable.wake)(thisRef.waker.data)
    }

    /**
     * Wakes up the task associated with this `Waker` without consuming the `Waker`.
     *
     * This is similar to [`wake`](Self.wake), but may be slightly less efficient in
     * the case where an owned `Waker` is available. This method should be preferred to
     * calling `waker.clone().wake()`.
     */
    public open fun wakeByRef() {
        // The actual wakeup call is delegated through a virtual function call
        // to the implementation which is defined by the executor.
        // SAFETY: see `wake`
        (this.waker.vtable.wakeByRef)(this.waker.data)
    }

    /**
     * Returns `true` if this `Waker` and another `Waker` would awake the same task.
     *
     * This function works on a best-effort basis, and may return false even
     * when the `Waker`s would awaken the same task. However, if this function
     * returns `true`, it is guaranteed that the `Waker`s will awaken the same task.
     *
     * This function is primarily used for optimization purposes — for example,
     * this type's [`cloneFrom`](Self.cloneFrom) implementation uses it to
     * avoid cloning the waker when they would wake the same task anyway.
     */
    public open fun willWake(other: Waker): Boolean {
        // We optimize this by comparing vtable addresses instead of vtable contents.
        // This is permitted since the function is documented as best-effort.
        val aData = this.waker.data
        val aVtable = this.waker.vtable
        val structVal = other.waker
        val bData = structVal.data
        val bVtable = structVal.vtable
        return ((aData).rawAddress.toULong() == (bData).rawAddress.toULong()) && eq(aVtable, bVtable)
    }

    /**
     * Gets the `data` pointer used to create this `Waker`.
     */
    public open fun data(): ConstPtr = this.waker.data

    /**
     * Gets the `vtable` pointer used to create this `Waker`.
     */
    public open fun vtable(): RawWakerVTable = this.waker.vtable

    public open fun clone(): Waker = Waker(waker = (this.waker.vtable.clone)(this.waker.data), fieldLayout = this.fieldLayout)

    /**
     * Assigns a clone of `source` to `self`, unless [`this.willWake`][Waker.willWake] anyway.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids cloning the waker if `self` is already the same waker.
     *
     * # Examples
     *
     * ```kotlin
     * import std.future.Future
     * import std.pin.Pin
     * import std.sync.Mutex
     * import std.task.Context
     * import std.task.Poll
     * import std.task.Waker
     *
     * open class Shared(public var waker: Waker)
     * open class Waiter(public var shared: Mutex<Shared>) : Future<Unit> {
     *     public open fun poll(cx: Context): Poll<Unit> {
     *         var shared = this.shared.lock().unwrap()
     *         // update the waker
     *         shared.waker = cx.waker()
     *         // readiness logic ...
     *     }
     * }
     * ```
     */
    public open fun cloneFrom(source: Waker) {
        var selfMut = this
        if (!selfMut.willWake(source)) {
            selfMut = source.clone()
        }
    }

    public open fun drop() {
        // SAFETY: This is safe because `Waker.fromRaw` is the only way
        // to initialize `drop` and `data` requiring the user to acknowledge
        // that the contract of `RawWaker` is upheld.
        (this.waker.vtable.drop)(this.waker.data)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val vtablePtr = constPtrFromRustCast(this.waker.vtable)
        return run { DebugStruct(f, "Waker").field("data", this.waker.data).field("vtable", vtablePtr).finish(); Result.Ok<Unit, Unit>(Unit) }
    }

    public override fun asRef(): LocalWaker {
        // SAFETY: LocalWaker is just Waker without thread safety
        return transmuteWithLayouts<Waker, LocalWaker>(this, UnresolvedTypeLayout<Waker>(), TransparentTypeLayout(this.fieldLayout, { ptr, layout -> LocalWaker(ptr, layout) }, { it.waker }))
    }

    public companion object {
        public operator fun invoke(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker = Waker(waker, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker = Waker(fieldStorage, fieldLayout)

        /**
         * Creates a new `Waker` from the provided `data` pointer and `vtable`.
         *
         * The `data` pointer can be used to store arbitrary data as required
         * by the executor. This could be e.g. a type-erased pointer to an `Arc`
         * that is associated with the task.
         * The value of this pointer will get passed to all functions that are part
         * of the `vtable` as the first parameter.
         *
         * It is important to consider that the `data` pointer must point to a
         * thread safe type such as an `Arc`.
         *
         * The `vtable` customizes the behavior of a `Waker`. For each operation
         * on the `Waker`, the associated function in the `vtable` will be called.
         *
         * # Safety
         *
         * The behavior of the returned `Waker` is undefined if the contract defined
         * in [RawWakerVTable]s documentation is not upheld.
         *
         * (Authors wishing to avoid unchecked code may implement the [Wake] trait instead, at the
         * cost of a required heap allocation.)
         *
         * [Wake]: ../../alloc/task/trait.Wake.html
         */
        public fun new(data: ConstPtr, vtable: RawWakerVTable, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker = Waker(waker = RawWaker(data = data, vtable = vtable), fieldLayout = fieldLayout)

        /**
         * Creates a new `Waker` from [RawWaker].
         *
         * # Safety
         *
         * The behavior of the returned `Waker` is undefined if the contract defined
         * in [RawWaker]s and [RawWakerVTable]s documentation is not upheld.
         *
         * (Authors wishing to avoid unchecked code may implement the [Wake] trait instead, at the
         * cost of a required heap allocation.)
         *
         * [Wake]: ../../alloc/task/trait.Wake.html
         */
        public fun fromRaw(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker = Waker(waker = waker, fieldLayout = fieldLayout)

        // Note!  Much of the documentation for this method is duplicated
        // in the docs for `LocalWaker.noop`.
        // If you edit it, consider editing the other copy too.
        //

        /**
         * Returns a reference to a `Waker` that does nothing when used.
         *
         * This is mostly useful for writing tests that need a [Context] to poll
         * some futures, but are not expecting those futures to wake the waker or
         * do not need to do anything specific if it happens.
         *
         * More generally, using `Waker.noop()` to poll a future
         * means discarding the notification of when the future should be polled again.
         * So it should only be used when such a notification will not be needed to make progress.
         *
         * If an owned `Waker` is needed, `clone()` this one.
         *
         * # Examples
         *
         * ```kotlin
         * import std.future.Future
         * import std.task
         *
         * var cx = task.Context.fromWaker(task.Waker.noop())
         * var future = Holder.pin(10)
         * check(future.asMut().poll(cx) == task.Poll.Ready(10))
         * ```
         */
        public fun noop(fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker {
            val WAKER: Waker = Waker(waker = RawWaker.NOOP, fieldLayout = fieldLayout)
            return WAKER
        }

        /**
         * Constructs a `Waker` from a function pointer.
         */
        public fun fromFnPtr(f: FromFnPtrFCallback, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): Waker {
            // SAFETY: Unsafe is used for transmutes, pointer came from `fun()` so it
            // is sound to reinterpret it back to `fun()`.
            val VTABLE: RawWakerVTable  = RawWakerVTable.new({ thisRef -> RawWaker.new(thisRef, VTABLE) }, { thisRef -> transmuteWithLayouts<ConstPtr, FromFnPtrFCallback>(thisRef, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<FromFnPtrFCallback>())() }, { thisRef -> transmuteWithLayouts<ConstPtr, FromFnPtrFCallback>(thisRef, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<FromFnPtrFCallback>())() }, {})
            val raw = RawWaker.new(constPtrFromRustCast(f), VTABLE)
            // SAFETY: `clone` is just a copy, `drop` is a no-op while `wake` and
            // `wakeByRef` just call the function pointer.
            return Waker.fromRaw(raw, fieldLayout = fieldLayout)
        }
    }
}

/**
 * A `LocalWaker` is analogous to a [Waker], but it does not implement [Send] or [Sync].
 *
 * This handle encapsulates a [RawWaker] instance, which defines the
 * executor-specific wakeup behavior.
 *
 * Local wakers can be requested from a `Context` with the [localWaker] method.
 *
 * The typical life of a `LocalWaker` is that it is constructed by an executor, wrapped in a
 * [Context] using [ContextBuilder], then passed to [Future.poll]. Then, if the future chooses to return
 * [Poll.Pending], it must also store the waker somehow and call [LocalWaker.wake] when
 * the future should be polled again.
 *
 * Implements [Clone], but neither [Send] nor [Sync]; therefore, a local waker may
 * not be moved to other threads. In general, when deciding to use wakers or local wakers,
 * local wakers are preferable unless the waker needs to be sent across threads. This is because
 * wakers can incur in additional cost related to memory synchronization.
 *
 * Note that it is preferable to use `localWaker.cloneFrom(newWaker)` instead
 * of `* localWaker = newWaker.clone()`, as the former will avoid cloning the waker
 * unnecessarily if the two wakers [wake the same task](Self.willWake).
 *
 * # Examples
 * Usage of a local waker to implement a future analogous to `std.thread.yieldNow()`.
 * ```kotlin
 * import std.future.Future
 * import std.future.pollFn
 * import std.task.Poll
 *
 * @feature(localWaker)
 * public fun doctestEntry() {
 *     // a future that returns pending once.
 *     fun yieldNow(): Future<Unit> {
 *         var yielded = false
 *         return pollFn(closure@{ cx ->
 *             if (!yielded) {
 *                 yielded = true
 *                 cx.localWaker().wakeByRef()
 *                 return@closure Poll.Pending
 *             }
 *             return@closure Poll.Ready(Unit)
 *         })
 *     }
 *     yieldNow()
 * }
 * ```
 */
public open class LocalWaker internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout(),
) {
    public var waker: RawWaker
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()) : this(allocateNativeCell(waker, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<LocalWaker> = TransparentTypeLayout(fieldLayout, { ptr, layout -> LocalWaker(ptr, layout) }, { it.waker })

    /**
     * Wakes up the task associated with this `LocalWaker`.
     *
     * As long as the executor keeps running and the task is not finished, it is
     * guaranteed that each invocation of [`wake`](Self.wake) (or
     * [`wakeByRef`](Self.wakeByRef)) will be followed by at least one
     * [poll] of the task to which this `LocalWaker` belongs. This makes
     * it possible to temporarily yield to other tasks while running potentially
     * unbounded processing loops.
     *
     * Note that the above implies that multiple wake-ups may be coalesced into a
     * single [poll] invocation by the runtime.
     *
     * Also note that yielding to competing tasks is not guaranteed: it is the
     * executor’s choice which task to run and the executor may choose to run the
     * current task again.
     */
    public open fun wake() {
        // The actual wakeup call is delegated through a virtual function call
        // to the implementation which is defined by the executor.
        // Don't call `drop` -- the waker will be consumed by `wake`.
        val thisRef = this
        // SAFETY: This is safe because `Waker.fromRaw` is the only way
        // to initialize `wake` and `data` requiring the user to acknowledge
        // that the contract of `RawWaker` is upheld.
        (thisRef.waker.vtable.wake)(thisRef.waker.data)
    }

    /**
     * Wakes up the task associated with this `LocalWaker` without consuming the `LocalWaker`.
     *
     * This is similar to [`wake`](Self.wake), but may be slightly less efficient in
     * the case where an owned `Waker` is available. This method should be preferred to
     * calling `waker.clone().wake()`.
     */
    public open fun wakeByRef() {
        // The actual wakeup call is delegated through a virtual function call
        // to the implementation which is defined by the executor.
        // SAFETY: see `wake`
        (this.waker.vtable.wakeByRef)(this.waker.data)
    }

    /**
     * Returns `true` if this `LocalWaker` and another `LocalWaker` would awake the same task.
     *
     * This function works on a best-effort basis, and may return false even
     * when the `Waker`s would awaken the same task. However, if this function
     * returns `true`, it is guaranteed that the `Waker`s will awaken the same task.
     *
     * This function is primarily used for optimization purposes — for example,
     * this type's [`cloneFrom`](Self.cloneFrom) implementation uses it to
     * avoid cloning the waker when they would wake the same task anyway.
     */
    public open fun willWake(other: LocalWaker): Boolean {
        // We optimize this by comparing vtable addresses instead of vtable contents.
        // This is permitted since the function is documented as best-effort.
        val aData = this.waker.data
        val aVtable = this.waker.vtable
        val structVal = other.waker
        val bData = structVal.data
        val bVtable = structVal.vtable
        return ((aData).rawAddress.toULong() == (bData).rawAddress.toULong()) && eq(aVtable, bVtable)
    }

    /**
     * Gets the `data` pointer used to create this `LocalWaker`.
     */
    public open fun data(): ConstPtr = this.waker.data

    /**
     * Gets the `vtable` pointer used to create this `LocalWaker`.
     */
    public open fun vtable(): RawWakerVTable = this.waker.vtable

    public open fun clone(): LocalWaker = LocalWaker(waker = (this.waker.vtable.clone)(this.waker.data), fieldLayout = this.fieldLayout)

    public open fun cloneFrom(source: LocalWaker) {
        var selfMut = this
        if (!selfMut.willWake(source)) {
            selfMut = source.clone()
        }
    }

    public open fun drop() {
        // SAFETY: This is safe because `LocalWaker.fromRaw` is the only way
        // to initialize `drop` and `data` requiring the user to acknowledge
        // that the contract of `RawWaker` is upheld.
        (this.waker.vtable.drop)(this.waker.data)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        val vtablePtr = constPtrFromRustCast(this.waker.vtable)
        return run { DebugStruct(f, "LocalWaker").field("data", this.waker.data).field("vtable", vtablePtr).finish(); Result.Ok<Unit, Unit>(Unit) }
    }

    public companion object {
        public operator fun invoke(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker = LocalWaker(waker, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker = LocalWaker(fieldStorage, fieldLayout)

        /**
         * Creates a new `LocalWaker` from the provided `data` pointer and `vtable`.
         *
         * The `data` pointer can be used to store arbitrary data as required
         * by the executor. This could be e.g. a type-erased pointer to an `Arc`
         * that is associated with the task.
         * The value of this pointer will get passed to all functions that are part
         * of the `vtable` as the first parameter.
         *
         * The `vtable` customizes the behavior of a `LocalWaker`. For each
         * operation on the `LocalWaker`, the associated function in the `vtable`
         * will be called.
         *
         * # Safety
         *
         * The behavior of the returned `Waker` is undefined if the contract defined
         * in [RawWakerVTable]s documentation is not upheld.
         */
        public fun new(data: ConstPtr, vtable: RawWakerVTable, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker = LocalWaker(waker = RawWaker(data = data, vtable = vtable), fieldLayout = fieldLayout)

        /**
         * Creates a new `LocalWaker` from [RawWaker].
         *
         * # Safety
         *
         * The behavior of the returned `LocalWaker` is undefined if the contract defined
         * in [RawWaker]s and [RawWakerVTable]s documentation is not upheld.
         */
        public fun fromRaw(waker: RawWaker, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker = LocalWaker(waker = waker, fieldLayout = fieldLayout)

        // Note!  Much of the documentation for this method is duplicated
        // in the docs for `Waker.noop`.
        // If you edit it, consider editing the other copy too.
        //

        /**
         * Returns a reference to a `LocalWaker` that does nothing when used.
         *
         * This is mostly useful for writing tests that need a [Context] to poll
         * some futures, but are not expecting those futures to wake the waker or
         * do not need to do anything specific if it happens.
         *
         * More generally, using `LocalWaker.noop()` to poll a future
         * means discarding the notification of when the future should be polled again,
         * So it should only be used when such a notification will not be needed to make progress.
         *
         * If an owned `LocalWaker` is needed, `clone()` this one.
         *
         * # Examples
         *
         * ```kotlin
         * import std.future.Future
         * import std.task.ContextBuilder
         * import std.task.LocalWaker
         * import std.task.Poll
         * import std.task.Waker
         *
         * @feature(localWaker)
         * public fun doctestEntry() {
         *     var cx = ContextBuilder.fromWaker(Waker.noop()).localWaker(LocalWaker.noop()).build()
         *     var future = Holder.pin(10)
         *     check(future.asMut().poll(cx) == Poll.Ready(10))
         * }
         * ```
         */
        public fun noop(fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker {
            val WAKER: LocalWaker = LocalWaker(waker = RawWaker.NOOP, fieldLayout = fieldLayout)
            return WAKER
        }

        /**
         * Constructs a `LocalWaker` from a function pointer.
         */
        public fun fromFnPtr(f: FromFnPtrFCallback, fieldLayout: TypeLayout<RawWaker> = UnresolvedTypeLayout()): LocalWaker {
            // SAFETY: Unsafe is used for transmutes, pointer came from `fun()` so it
            // is sound to reinterpret it back to `fun()`.
            val VTABLE: RawWakerVTable  = RawWakerVTable.new({ thisRef -> RawWaker.new(thisRef, VTABLE) }, { thisRef -> transmuteWithLayouts<ConstPtr, FromFnPtrFCallback>(thisRef, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<FromFnPtrFCallback>())() }, { thisRef -> transmuteWithLayouts<ConstPtr, FromFnPtrFCallback>(thisRef, PrimitiveLayouts.constPtrLayout(64L), UnresolvedTypeLayout<FromFnPtrFCallback>())() }, {})
            val raw = RawWaker.new(constPtrFromRustCast(f), VTABLE)
            // SAFETY: `clone` is just a copy, `drop` is a no-op while `wake` and
            // `wakeByRef` just call the function pointer.
            return LocalWaker.fromRaw(raw, fieldLayout = fieldLayout)
        }
    }
}
