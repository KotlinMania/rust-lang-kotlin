// port-lint: source task.rs
package io.github.kotlinmania.rustlang.alloc.task

import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.task.wake.LocalWaker
import io.github.kotlinmania.rustlang.core.task.wake.RawWaker
import io.github.kotlinmania.rustlang.core.task.wake.RawWakerVTable
import io.github.kotlinmania.rustlang.core.task.wake.Waker

// Types and Traits for working with asynchronous tasks.
//
// **Note**: Some of the types in this module are only available
// on platforms that support atomic loads and stores of pointers.
// This may be detected at compile time using
// `@cfg(targetHasAtomic = "ptr")`.

/**
 * The implementation of waking a task on an executor.
 *
 * This trait can be used to create a [Waker]. An executor can define an
 * implementation of this trait, and use that to construct a [Waker] to pass
 * to the tasks that are executed on that executor.
 *
 * This trait is a memory-safe and ergonomic alternative to constructing a
 * [RawWaker]. It supports the common executor design in which the data used
 * to wake up a task is stored in an [Arc]. Some executors (especially
 * those for embedded systems) cannot use this API, which is why [RawWaker]
 * exists as an alternative for those systems.
 *
 * To construct a [Waker] from some type `W` implementing this trait,
 * wrap it in an [`Arc.W`](Arc) and call `Waker.from()` on that.
 * It is also possible to convert to [RawWaker] in the same way.
 *
 * <!-- Ideally we'd link to the `From` impl, but rustdoc doesn't generate any page for it within
 *      `alloc` because `alloc` neither defines nor re-exports `From` or `Waker`, and we can't
 *      link ../../std/task/struct.Waker.html#impl-From%3CArc%3CW,+Global%3E%3E-for-Waker
 *      without getting a link-checking error in CI. -->
 *
 * # Memory Ordering
 *
 * To avoid missed wakeups, all executors must adhere to the requirement described for [Waker.wake].
 *
 * # Examples
 *
 * A basic `blockOn` function that takes a future and runs it to completion on
 * the current thread.
 *
 * **Note:** This example trades correctness for simplicity. In order to prevent
 * deadlocks, production-grade implementations will also need to handle
 * intermediate calls to `thread.unpark` as well as nested invocations.
 *
 * ```kotlin
 * import core.pin.pin
 * import std.future.Future
 * import std.task.Context
 * import std.task.Poll
 * import std.task.Wake
 * import std.thread.Thread
 *
 * / **
 *  * A waker that wakes up the current thread when called.
 *  * /
 * open class ThreadWaker(public var value: Thread) : Wake {
 *     public var field0: Thread
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun wake() {
 *         this.value.unpark()
 *     }
 * }
 * / **
 *  * Run a future to completion on the current thread.
 *  * /
 * fun <T> blockOn(fut: Future<T>): T {
 *     // Pin the future so it can be polled.
 *     var fut1 = pin(fut)
 *     // Create a new context to be passed to the future.
 *     val t = thread.current()
 *     val waker = ThreadWaker(t).into()
 *     var cx = Context.fromWaker(waker)
 *     // Run the future to completion.
 *     while (true) {
 *         when (val matchVal = fut1.asMut().poll(cx)) {
 *             is Poll.Ready -> {
 *                 val (res) = matchVal
 *                 return res
 *             }
 *             Poll.Pending -> thread.park()
 *         }
 *     }
 * }
 * blockOn(run {
 *     println("Hi from inside a future!")
 * })
 * ```
 */
public interface Wake {
    /**
     * Wake this task.
     */
    public fun wake()

    /**
     * Wake this task without consuming the waker.
     *
     * If an executor supports a cheaper way to wake without consuming the
     * waker, it should override this method. By default, it clones the
     * [Arc] and calls [wake] on the clone.
     *
     * [wake]: Wake.wake
     */
    public fun wakeByRef() {
        this.clone().wake()
    }
}

/**
 * Use a [Wake]-able type as a `Waker`.
 *
 * No heap allocations or atomic operations are used for this conversion.
 */
public fun <W : Wake> fromWaker(waker: W): Waker {
    // SAFETY: This is safe because rawWaker safely constructs
    // a RawWaker from Arc<W>.
    return Waker.fromRaw(rawWaker(waker))
}

/**
 * Use a `Wake`-able type as a `RawWaker`.
 *
 * No heap allocations or atomic operations are used for this conversion.
 */
public fun <W : Wake> fromRawWaker(waker: W): RawWaker = rawWaker(waker)

/**
 * Converts a closure into a [Waker].
 *
 * The closure gets called every time the waker is woken.
 *
 * # Examples
 *
 * ```kotlin
 * import std.task.wakerFn
 *
 * @feature(wakerFn)
 * public fun doctestEntry() {
 *     val waker = wakerFn({ println("woken") })
 *     waker.wakeByRef()
 *     // Prints "woken".
 *     waker.wake()
 *     // Prints "woken".
 * }
 * ```
 */
public fun wakerFn(f: () -> Unit): Waker {
    open class WakeFn<F>(
        public var f: () -> Unit,
    ) : Wake {
        public override fun wake() {
            (this.f)()
        }

        public override fun wakeByRef() {
            (this.f)()
        }
    }
    return Waker.from(WakeFn(f = f))
}

// NB: This private function for constructing a RawWaker is used, rather than
// inlining this into the `From<Arc<W>> for RawWaker` impl, to ensure that
// the safety of `From<Arc<W>> for Waker` does not depend on the correct
// trait dispatch - instead both impls call this function directly and
// explicitly.
public fun <W : Wake> rawWaker(waker: W): RawWaker {
    // Wake by value, moving the Arc into the Wake.wake function
    fun <W : Wake> wake(waker1: ConstPtr, wakeWFn: (W) -> Unit) {
        // ignore-tidy-undocumented-unchecked
        val waker2 = Arc.fromRaw(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        wakeWFn(waker2)
    }

    // Wake by reference, wrap the waker in ManuallyDrop to avoid dropping it
    fun <W : Wake> wakeByRef(waker1: ConstPtr, wakeByRefWFn: (W) -> Unit) {
        // ignore-tidy-undocumented-unchecked
        val waker2 = Arc.fromRaw(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        wakeByRefWFn(waker2)
    }

    // Decrement the reference count of the Arc on drop
    fun dropWaker(waker1: ConstPtr) {
        // ignore-tidy-undocumented-unchecked
        Arc.decrementStrongCount(ConstPtr(waker1.rawAddress, waker1.rawMeta))
    }

    // Increment the reference count of the arc to clone it.
    //
    // The @inline(always) is to ensure that rawWaker and cloneWaker are
    // always generated in the same code generation unit as one another, and
    // therefore that the structurally identical const-promoted RawWakerVTable
    // within both functions is deduplicated at LLVM IR code generation time.
    // This allows optimizing Waker.willWake to a single pointer comparison of
    // the vtable pointers, rather than comparing all four function pointers
    // within the vtables.
    fun <W : Wake> cloneWaker(waker1: ConstPtr): RawWaker {
        // ignore-tidy-undocumented-unchecked
        Arc.incrementStrongCount(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        return RawWaker.new(waker1, RawWakerVTable.new(cloneWaker<W>, wake<W>, wakeByRef<W>, dropWaker<W>))
    }
    return RawWaker.new(constPtrFromRustCast(Arc.intoRaw(waker)), RawWakerVTable.new(cloneWaker<W>, wake<W>, wakeByRef<W>, dropWaker<W>))
}

/**
 * An analogous trait to `Wake` but used to construct a `LocalWaker`.
 *
 * This API works in exactly the same way as `Wake`,
 * except that it uses an `Rc` instead of an `Arc`,
 * and the result is a `LocalWaker` instead of a `Waker`.
 *
 * The benefits of using `LocalWaker` over `Waker` are that it allows the local waker
 * to hold data that does not implement `Send` and `Sync`. Additionally, it saves calls
 * to `Arc.clone`, which requires atomic synchronization.
 *
 * # Examples
 *
 * This is a simplified example of a `spawn` and a `blockOn` function. The `spawn` function
 * is used to push new tasks onto the run queue, while the block on function will remove them
 * and poll them. When a task is woken, it will put itself back on the run queue to be polled
 * by the executor.
 *
 * **Note:** This example trades correctness for simplicity. A real world example would interleave
 * poll calls with calls to an io reactor to wait for events instead of spinning on a loop.
 *
 * ```kotlin
 * import std.cell.RefCell
 * import std.future.Future
 * import std.pin.Pin
 * import std.task.ContextBuilder
 * import std.task.LocalWake
 * import std.task.LocalWaker
 * import std.task.Waker
 *
 * public typealias BoxedFuture = Pin<Future<Unit>>
 *
 * @feature(localWaker)
 * public fun doctestEntry() {
 *     open class Task(public var value: RefCell<BoxedFuture>) : LocalWake {
 *         public var field0: RefCell<BoxedFuture>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         public open fun wake() {
 *             RUN_QUEUE.withBorrowMut({ queue ->
 *                 queue.addLast(this)
 *             })
 *         }
 *     }
 *     fun <F> spawn(future: F) where F : Future<Unit> {
 *         val task = RefCell.new(Holder.pin(future))
 *         RUN_QUEUE.withBorrowMut({ queue ->
 *             queue.addLast(Task(task))
 *         })
 *     }
 *     fun <F> blockOn(future: F) where F : Future<Unit> {
 *         spawn(future)
 *         while (true) {
 *             val task = RUN_QUEUE.withBorrowMut({ queue -> queue.removeFirstOrNull() }) ?: // we exit, since there are no more tasks remaining on the queue
 * return
 *             // cast the Rc<Task> into a `LocalWaker`
 *             val localWaker: LocalWaker = task.clone().into()
 *             // Build the context using `ContextBuilder`
 *             var cx = ContextBuilder.fromWaker(Waker.noop()).localWaker(localWaker).build()
 *             // Poll the task
 *             task.first.borrowMut().asMut().poll(cx)
 *         }
 *     }
 *     threadLocal(RUN_QUEUE, RefCell, VecDeque, Rc, Task, RefCell)
 *     blockOn(run {
 *         println("hello world")
 *     })
 * }
 * ```
 */
public interface LocalWake {
    /**
     * Wake this task.
     */
    public fun wake()

    /**
     * Wake this task without consuming the local waker.
     *
     * If an executor supports a cheaper way to wake without consuming the
     * waker, it should override this method. By default, it clones the
     * [Rc] and calls [wake] on the clone.
     *
     * [wake]: LocalWaker.wake
     */
    public fun wakeByRef() {
        this.clone().wake()
    }
}

/**
 * Use a `Wake`-able type as a `LocalWaker`.
 *
 * No heap allocations or atomic operations are used for this conversion.
 */
public fun <W : LocalWake> fromLocalWaker(waker: W): LocalWaker {
    // SAFETY: This is safe because rawWaker safely constructs
    // a RawWaker from Rc<W>.
    return LocalWaker.fromRaw(localRawWaker(waker))
}

// @unstable(feature = "localWaker", issue = "118959")

/**
 * Converts a closure into a [LocalWaker].
 *
 * The closure gets called every time the local waker is woken.
 *
 * # Examples
 *
 * ```kotlin
 * import std.task.localWakerFn
 *
 * @feature(localWaker)
 * @feature(wakerFn)
 * public fun doctestEntry() {
 *     val waker = localWakerFn({ println("woken") })
 *     waker.wakeByRef()
 *     // Prints "woken".
 *     waker.wake()
 *     // Prints "woken".
 * }
 * ```
 */
public fun localWakerFn(f: () -> Unit): LocalWaker {
    open class LocalWakeFn<F>(
        public var f: () -> Unit,
    ) : LocalWake {
        public override fun wake() {
            (this.f)()
        }

        public override fun wakeByRef() {
            (this.f)()
        }
    }
    return LocalWaker.from(LocalWakeFn(f = f))
}

// NB: This private function for constructing a RawWaker is used, rather than
// inlining this into the `From<Rc<W>> for RawWaker` impl, to ensure that
// the safety of `From<Rc<W>> for Waker` does not depend on the correct
// trait dispatch - instead both impls call this function directly and
// explicitly.
public fun <W : LocalWake> localRawWaker(waker: W): RawWaker {
    // Wake by value, moving the Rc into the LocalWake.wake function
    fun <W : LocalWake> wake(waker1: ConstPtr, wakeWFn: (W) -> Unit) {
        // ignore-tidy-undocumented-unchecked
        val waker2 = Rc.fromRaw(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        wakeWFn(waker2)
    }

    // Wake by reference, wrap the waker in ManuallyDrop to avoid dropping it
    fun <W : LocalWake> wakeByRef(waker1: ConstPtr, wakeByRefWFn: (W) -> Unit) {
        // ignore-tidy-undocumented-unchecked
        val waker2 = Rc.fromRaw(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        wakeByRefWFn(waker2)
    }

    // Decrement the reference count of the Rc on drop
    fun dropWaker(waker1: ConstPtr) {
        // ignore-tidy-undocumented-unchecked
        Rc.decrementStrongCount(ConstPtr(waker1.rawAddress, waker1.rawMeta))
    }

    // Increment the reference count of the Rc to clone it.
    //
    // Refer to the comment on rawWaker's cloneWaker regarding why this is
    // always inline.
    fun <W : LocalWake> cloneWaker(waker1: ConstPtr): RawWaker {
        // ignore-tidy-undocumented-unchecked
        Rc.incrementStrongCount(ConstPtr(waker1.rawAddress, waker1.rawMeta))
        return RawWaker.new(waker1, RawWakerVTable.new(cloneWaker<W>, wake<W>, wakeByRef<W>, dropWaker<W>))
    }
    return RawWaker.new(constPtrFromRustCast(Rc.intoRaw(waker)), RawWakerVTable.new(cloneWaker<W>, wake<W>, wakeByRef<W>, dropWaker<W>))
}
