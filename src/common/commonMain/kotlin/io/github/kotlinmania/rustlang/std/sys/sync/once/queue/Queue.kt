// port-lint: source sys/sync/once/queue.rs
package io.github.kotlinmania.rustlang.std.sys.sync.once.queue

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.AcqRel
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release
import io.github.kotlinmania.rustlang.std.sync.once.OnceExclusiveState
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

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

public typealias StateAndQueue = MutPtr

public open class Once internal constructor(
    stateAndQueueCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
) {
    public var stateAndQueue: Atomic<MutPtr, Align8<MutPtr>> = stateAndQueueCtorArg
        internal set

    public open fun isCompleted(): Boolean {
        // An `Acquire` load is enough because that makes all the initialization
        // operations visible to us, and, this being a fast path, weaker
        // ordering helps with performance. This `Acquire` synchronizes with
        // `Release` operations on the slow path.
        return (this.stateAndQueue.load(Acquire).addr() == COMPLETE).toUByte()
    }

    public open fun state(): OnceExclusiveState =
        when (this.stateAndQueue.getMut().addr()) {
            INCOMPLETE -> OnceExclusiveState.Incomplete
            POISONED -> OnceExclusiveState.Poisoned
            COMPLETE -> OnceExclusiveState.Complete
            else -> error("invalid Once state")
        }

    public open fun setState(newState: OnceExclusiveState) {
        this.stateAndQueue.getMut() = when (newState) {
            OnceExclusiveState.Incomplete -> ptr.withoutProvenanceMut(INCOMPLETE)
            OnceExclusiveState.Poisoned -> ptr.withoutProvenanceMut(POISONED)
            OnceExclusiveState.Complete -> ptr.withoutProvenanceMut(COMPLETE)
        }
    }

    public open fun wait(ignorePoisoning: Boolean) {
        var current = this.stateAndQueue.load(Acquire)
        while (true) {
            val state = toState(current)
            when {
                state == COMPLETE -> { return }
                state == POISONED && !ignorePoisoning -> {
                    // Panic to propagate the poison.
                    error("Once instance has previously been poisoned")
                }
                else -> {
                    current = wait(this.stateAndQueue, current, (!ignorePoisoning).toUByte())
                }
            }
        }
    }

    // This is a non-generic function to reduce the monomorphization cost of
    // using `callOnce` (this isn't exactly a trivial or small implementation).
    //
    // Additionally, this is tagged with `@cold` as it should indeed be cold
    // and it helps let LLVM know that calls to this function should be off the
    // fast path. Essentially, this should help generate more straight line code
    // in LLVM.
    //
    // Finally, this takes an `FnMut` instead of a `FnOnce` because there's
    // currently no way to take an `FnOnce` and call it via virtual dispatch
    // without some allocation overhead.
    public open fun call(ignorePoisoning: Boolean, init: (io.github.kotlinmania.rustlang.std.sync.once.OnceState) -> Unit) {
        var current = this.stateAndQueue.load(Acquire)
        while (true) {
            val state = toState(current)
            when {
                state == COMPLETE -> { break }
                state == POISONED && !ignorePoisoning -> {
                    // Panic to propagate the poison.
                    error("Once instance has previously been poisoned")
                }
                state == POISONED || state == INCOMPLETE -> {
                    // Try to register this thread as the one RUNNING.
                    when (val matchVal = this.stateAndQueue.compareExchangeWeak(current, current.mask(QUEUE_MASK).wrappingByteAdd(RUNNING), Acquire, Acquire)) {
                        is Err -> {
                            val new = matchVal.error
                            current = new
                            continue
                        }
                        else -> {}
                    }
                    // `waiterQueue` will manage other waiting threads, and
                    // wake them up on drop.
                    val waiterQueue = WaiterQueue(stateAndQueue = this.stateAndQueue, setStateOnDropTo = ptr.withoutProvenanceMut(POISONED))
                    // Run the initialization function, letting it know if we're
                    // poisoned or not.
                    val initState = io.github.kotlinmania.rustlang.std.sync.once.OnceState(inner = OnceState(poisoned = (state == POISONED).toUByte(), setStateOnDropTo = Cell.new(ptr.withoutProvenanceMut(COMPLETE))))
                    init(initState)
                    waiterQueue.setStateOnDropTo = initState.inner.setStateOnDropTo.get()
                    return
                }
                else -> {
                    // All other values must be RUNNING with possibly a
                    // pointer to the waiter queue in the more significant bits.
                    check(state == RUNNING)
                    current = wait(this.stateAndQueue, current, true)
                }
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Once").finishNonExhaustive()

    public companion object {
        public operator fun invoke(
            stateAndQueue: Atomic<MutPtr, Align8<MutPtr>>,
        ): Once = Once(
            stateAndQueueCtorArg = stateAndQueue,
        )

        public fun new(): Once = Once(stateAndQueue = AtomicPtr.new(ptr.withoutProvenanceMut(INCOMPLETE)))

        public fun newComplete(): Once = Once(stateAndQueue = AtomicPtr.new(ptr.withoutProvenanceMut(COMPLETE)))
    }
}

public open class OnceState internal constructor(
    public var poisoned: Boolean,
    setStateOnDropToCtorArg: Cell<StateAndQueue>,
) {
    public var setStateOnDropTo: Cell<StateAndQueue> = setStateOnDropToCtorArg
        internal set

    public open fun isPoisoned(): Boolean = this.poisoned

    public open fun poison() {
        this.setStateOnDropTo.set(ptr.withoutProvenanceMut(POISONED))
    }

    public companion object {
        public operator fun invoke(
            poisoned: Boolean,
            setStateOnDropTo: Cell<StateAndQueue>,
        ): OnceState = OnceState(
            poisoned = poisoned,
            setStateOnDropToCtorArg = setStateOnDropTo,
        )
    }
}

// Four states that a Once can be in, encoded into the lower bits of
// `stateAndQueue` in the Once structure. By choosing COMPLETE as the all-zero
// state the `isCompleted` check can be a bit faster on some platforms.
public val INCOMPLETE: Int = 0x3

public val POISONED: Int = 0x2

public val RUNNING: Int = 0x1

public val COMPLETE: Int = 0x0

// Mask to learn about the state. All other bits are the queue of waiters if
// this is in the RUNNING state.
public val STATE_MASK: Int = 0x3

public val QUEUE_MASK: Int = STATE_MASK.inv()

// Representation of a node in the linked list of waiters, used while in the
// RUNNING state.
// Note: `Waiter` can't hold a mutable pointer to the next thread, because then
// `wait` would both hand out a mutable reference to its `Waiter` node, and keep
// a shared reference to check `signaled`. Instead we hold shared references and
// use interior mutability.
// Ensure the two lower bits are free to use as state bits.
public open class Waiter internal constructor(
    public var thread: Thread,
    signaledCtorArg: Atomic<Boolean, Align1<UByte>>,
    nextCtorArg: Cell<ConstPtr>,
) {
    public var signaled: Atomic<Boolean, Align1<UByte>> = signaledCtorArg
        internal set

    public var next: Cell<ConstPtr> = nextCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            thread: Thread,
            signaled: Atomic<Boolean, Align1<UByte>>,
            next: Cell<ConstPtr>,
        ): Waiter = Waiter(
            thread = thread,
            signaledCtorArg = signaled,
            nextCtorArg = next,
        )
    }
}

// Head of a linked list of waiters.
// Every node is a struct on the stack of a waiting thread.
// Will wake up the waiters when it gets dropped, i.e. also on panic.
public open class WaiterQueue internal constructor(
    stateAndQueueCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
    setStateOnDropToCtorArg: StateAndQueue,
) {
    public var stateAndQueue: Atomic<MutPtr, Align8<MutPtr>> = stateAndQueueCtorArg
        internal set

    public var setStateOnDropTo: StateAndQueue = setStateOnDropToCtorArg
        internal set

    public open fun drop() {
        // Swap out our state with however we finished.
        val current = this.stateAndQueue.swap(this.setStateOnDropTo, AcqRel)
        // We should only ever see an old state which was RUNNING.
        check(current.addr() and STATE_MASK == RUNNING)
        // Walk the entire linked list of waiters and wake them up (in lifo
        // order, last to register is first to wake up).
        run {
            // Right after setting `node.signaled = true` the other thread may
            // free `node` if there happens to be has a spurious wakeup.
            // So we have to take out the `thread` field and copy the pointer to
            // `next` first.
            var queue = toQueue(current)
            while (!((queue).rawAddress == 0L)) {
                val next = (UnresolvedTypeLayout<Waiter>()).readNative((queue).rawAddress).next.get()
                val thread = (UnresolvedTypeLayout<Waiter>()).readNative((queue).rawAddress).thread.clone()
                (UnresolvedTypeLayout<Waiter>()).readNative((queue).rawAddress).signaled.store(true, Release)
                thread.unpark()
                queue = next
            }
        }
    }

    public companion object {
        public operator fun invoke(
            stateAndQueue: Atomic<MutPtr, Align8<MutPtr>>,
            setStateOnDropTo: StateAndQueue,
        ): WaiterQueue = WaiterQueue(
            stateAndQueueCtorArg = stateAndQueue,
            setStateOnDropToCtorArg = setStateOnDropTo,
        )
    }
}

public fun toQueue(current: StateAndQueue): ConstPtr = current.mask(QUEUE_MASK).cast()

public fun toState(current: StateAndQueue): Int = current.addr() and STATE_MASK

/**
 * A type to guard against the unwinds of stacks that nodes are located on due to panics.
 */
public open class PanicGuard {
    public open fun drop() {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("tried to drop node in intrusive list.").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("tried to drop node in intrusive list.").toString() + ", aborting\n"
            }
            abort()
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<PanicGuard> =
            object : TypeLayout<PanicGuard> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): PanicGuard =
                    PanicGuard()
                override fun writeNative(addr: Long, value: PanicGuard) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): PanicGuard =
                    PanicGuard()
                override fun writeBytes(buf: ByteArray, offset: Int, value: PanicGuard) {
                }
            }
    }
}

public fun wait(stateAndQueue: Atomic<MutPtr, Align8<MutPtr>>, current: StateAndQueue, returnOnPoisoned: Boolean): StateAndQueue {
    var current1 = current
    val node = Waiter(thread = thread.currentOrUnnamed(), signaled = AtomicBool.new(false), next = Cell.new(ConstPtr(0L)))
    while (true) {
        val state = toState(current1)
        val queue = toQueue(current1)
        // If initialization has finished, return.
        if (state == COMPLETE || (returnOnPoisoned && state == POISONED)) {
            return current1
        }
        // Update the node for our current thread.
        node.next.set(queue)
        // Try to slide in the node at the head of the linked list, making sure
        // that another thread didn't just replace the head of the linked list.
        when (val matchVal = stateAndQueue.compareExchangeWeak(current1, mutPtrFromRustCast(node.wrappingByteAdd(state)), Release, Acquire)) {
            is Err -> {
                val new = matchVal.error
                current1 = new
                continue
            }
            else -> {}
        }
        // Guard against unwinds using a `PanicGuard` that aborts when dropped.
        val guard = PanicGuard()
        // We have enqueued ourselves, now lets wait.
        // It is important not to return before being signaled, otherwise we
        // would drop our `Waiter` node and leave a hole in the linked list
        // (and a dangling reference). Guard against spurious wakeups by
        // reparking ourselves until we are signaled.
        while (!node.signaled.load(Acquire)) {
            // If the managing thread happens to signal and unpark us before we
            // can park ourselves, the result could be this thread never gets
            // unparked. Luckily `park` comes with the guarantee that if it got
            // an `unpark` just before on an unparked thread it does not park. Crucially, we know
            // the `unpark` must have happened between the `compareExchangeWeak` above and here,
            // and there's no other `park` in that code that could steal our token.
            // SAFETY: we retrieved this handle on the current thread above.
            node.thread.park()
        }
        // The node was removed from the queue, disarm the guard.
        forget(guard)
        return stateAndQueue.load(Acquire)
    }
}
