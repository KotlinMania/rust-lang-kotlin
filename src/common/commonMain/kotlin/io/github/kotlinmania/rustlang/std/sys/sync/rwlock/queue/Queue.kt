// port-lint: source sys/sync/rwlock/queue.rs
package io.github.kotlinmania.rustlang.std.sys.sync.rwlock.queue

import io.github.kotlinmania.rustlang.core.cell.once.OnceCell
import io.github.kotlinmania.rustlang.core.hint.spinLoop
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.mapAddr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.mask
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.AcqRel
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.current.currentOrUnnamed
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

// Efficient read-write locking without `pthreadRwlockT`.
//
// The readers-writer lock provided by the `pthread` library has a number of problems which make it
// a suboptimal choice for `std`:
//
// * It is non-movable, so it needs to be allocated (lazily, to make the constructor `const`).
// * `pthread` is an external library, meaning the fast path of acquiring an uncontended lock
// cannot be inlined.
// * Some platforms (at least glibc before version 2.25) have buggy implementations that can easily
// lead to undefined behaviour in safe Rust code when not properly guarded against.
// * On some platforms (e.g. macOS), the lock is very slow.
//
// Therefore, we implement our own [RwLock]! Naively, one might reach for a spinlock, but those
// can be quite [problematic] when the lock is contended.
//
// Instead, this [RwLock] copies its implementation strategy from the Windows [SRWLOCK] and the
// [usync] library implementations.
//
// Spinning is still used for the fast path, but it is bounded: after spinning fails, threads will
// locklessly add an information structure ([Node]) containing a [Thread] handle into a queue
// of waiters associated with the lock. The lock owner, upon releasing the lock, will scan through
// the queue and wake up threads as appropriate, and the newly-awoken threads will then try to
// acquire the lock themselves.
//
// The resulting [RwLock] is:
//
// * adaptive, since it spins before doing any heavyweight parking operations
// * allocation-free, modulo the per-thread [Thread] handle, which is allocated anyways when
// using threads created by `std`
// * writer-preferring, even if some readers may still slip through
// * unfair, which reduces context-switching and thus drastically improves performance
//
// and also quite fast in most cases.
//
// [problematic]: https://matklad.github.io/2020/01/02/spinlocks-considered-harmful.html
// [SRWLOCK]: https://learn.microsoft.com/en-us/windows/win32/sync/slim-reader-writer--srw--locks
// [usync]: https://crates.io/crates/usync
//
// # Implementation
//
// ## State
//
// A single [AtomicPtr] is used as state variable. The lowest four bits are used to indicate the
// meaning of the remaining bits:
//
// | [Locked] | [Queued] | [QueueLocked] | [Downgraded] | Remaining    |                                                                                                                             |
// |------------|:-----------|:-----------------|:---------------|:-------------|:----------------------------------------------------------------------------------------------------------------------------|
// | 0          | 0          | 0                | 0              | 0            | The lock is unlocked, no threads are waiting                                                                                |
// | 1          | 0          | 0                | 0              | 0            | The lock is write-locked, no threads waiting                                                                                |
// | 1          | 0          | 0                | 0              | n > 0        | The lock is read-locked with n readers                                                                                      |
// | 0          | 1          | *                | 0              | `* Node`  | The lock is unlocked, but some threads are waiting. Only writers may lock the lock                                          |
// | 1          | 1          | *                | *              | `* Node`  | The lock is locked, but some threads are waiting. If the lock is read-locked, the last queue node contains the reader count |
//
// ## Waiter Queue
//
// When threads are waiting on the lock (the `QUEUE` bit is set), the lock state points to a queue
// of waiters, which is implemented as a linked list of nodes stored on the stack to avoid memory
// allocation.
//
// To enable lock-free enqueuing of new nodes to the queue, the linked list is singly-linked upon
// creation.
//
// When the lock is read-locked, the lock count (number of readers) is stored in the last link of
// the queue. Threads have to traverse the queue to find the last element upon releasing the lock.
// To avoid having to traverse the entire list every time we want to access the reader count, a
// pointer to the found tail is cached in the (current) first element of the queue.
//
// Also, while the lock is unfair for performance reasons, it is still best to wake the tail node
// first (FIFO ordering). Since we always pop nodes off the tail of the queue, we must store
// backlinks to previous nodes so that we can update the `tail` field of the (current) first
// element of the queue. Adding backlinks is done at the same time as finding the tail (via the
// function [findTailAndAddBacklinks]), and thus encountering a set tail field on a node
// indicates that all following nodes in the queue are initialized.
//
// TLDR: Here's a diagram of what the queue looks like:
//
// ```text
// state
//   │
//   ▼
// ╭───────╮ next ╭───────╮ next ╭───────╮ next ╭───────╮
// │       ├─────►│       ├─────►│       ├─────►│ count │
// │       │      │       │      │       │      │       │
// │       │      │       │◄─────┤       │◄─────┤       │
// ╰───────╯      ╰───────╯ prev ╰───────╯ prev ╰───────╯
//                      │                           ▲
//                      └───────────────────────────┘
//                                  tail
// ```
//
// Invariants:
// 1. At least one node must contain a non-null, current `tail` field.
// 2. The first non-null `tail` field must be valid and current.
// 3. All nodes preceding this node must have a correct, non-null `next` field.
// 4. All nodes following this node must have a correct, non-null `prev` field.
//
// Access to the queue is controlled by the `QUEUE_LOCKED` bit. Threads will try to set this bit
// in two cases: one is when a thread enqueues itself and eagerly adds backlinks to the queue
// (which drastically improves performance), and the other is after a thread unlocks the lock to
// wake up the next waiter(s).
//
// `QUEUE_LOCKED` is set atomically at the same time as the enqueuing/unlocking operations. The
// thread releasing the `QUEUE_LOCKED` bit will check the state of the lock (in particular, whether
// a downgrade was requested using the [Downgraded] bit) and wake up waiters as appropriate. This
// guarantees forward progress even if the unlocking thread could not acquire the queue lock.
//
// ## Memory Orderings
//
// To properly synchronize changes to the data protected by the lock, the lock is acquired and
// released with [Acquire] and [Release] ordering, respectively. To propagate the
// initialization of nodes, changes to the queue lock are also performed using these orderings.

/**
 * The atomic lock state.
 */
public typealias AtomicState = Atomic<State>

/**
 * The inner lock state.
 */
public typealias State = MutPtr

public val LOCKED: Int = 1 shl 0

public val QUEUED: Int = 1 shl 1

public val QUEUE_LOCKED: Int = 1 shl 2

public val DOWNGRADED: Int = 1 shl 3

public val SINGLE: Int = 1 shl 4

public val STATE: Int = DOWNGRADED or QUEUE_LOCKED or QUEUED or LOCKED

public val NODE_MASK: Int = STATE.inv()

/**
 * Locking uses exponential backoff. `SPIN_COUNT` indicates how many times the locking operation
 * will be retried.
 *
 * In other words, `spinLoop` will be called `2.pow(SPIN_COUNT) - 1` times.
 */
public val SPIN_COUNT: Int = 7

public val UNLOCKED: State = withoutProvenanceMut(0)

/**
 * Marks the state as write-locked, if possible.
 */
public fun writeLock(state: State): State? =
    if (state.addr() and LOCKED == 0) {
        state.mapAddr({ addr -> addr or LOCKED })
    } else {
        null
    }

/**
 * Marks the state as read-locked, if possible.
 */
public fun readLock(state: State): State? {
    return if (state.addr() and QUEUED == 0 && state.addr() != LOCKED) {
        withoutProvenanceMut((state.addr().checkedAdd(SINGLE) ?: return null) or LOCKED)
    } else {
        null
    }
}

/**
 * Converts a `State` into a `Node` by masking out the bottom bits of the state, assuming that the
 * state points to a queue node.
 *
 * # Safety
 *
 * The state must contain a valid pointer to a queue node.
 */
public fun toNode(state: State): NonNull = NonNull.newUnchecked(state.mask(NODE_MASK)).cast()

/**
 * The representation of a thread waiting on the lock queue.
 *
 * We initialize these `Node`s on thread execution stacks to avoid allocation.
 *
 * Note that we need an alignment of 16 to ensure that the last 4 bits of any
 * pointers to `Node`s are always zeroed (for the bit flags described in the
 * module-level documentation).
 */
public open class Node internal constructor(
    public var next: AtomicLink,
    public var prev: AtomicLink,
    public var tail: AtomicLink,
    public var write: Boolean,
    threadCtorArg: OnceCell<Thread>,
    completedCtorArg: Atomic<Boolean, Align1<UByte>>,
) {
    public var thread: OnceCell<Thread> = threadCtorArg
        internal set

    public var completed: Atomic<Boolean, Align1<UByte>> = completedCtorArg
        internal set

    /**
     * Prepare this node for waiting.
     */
    public open fun prepare() {
        // Fall back to creating an unnamed `Thread` handle to allow locking in TLS destructors.
        this.thread.getOrInit(currentOrUnnamed)
        this.completed = AtomicBool.new(false)
    }

    /**
     * Wait until this node is marked as [`complete`](Node.complete)d by another thread.
     *
     * # Safety
     *
     * May only be called from the thread that created the node.
     */
    public open fun wait() {
        while (!this.completed.load(Acquire)) {
            run {
                this.thread.get()!!.park()
            }
        }
    }

    public companion object {
        public operator fun invoke(
            next: AtomicLink,
            prev: AtomicLink,
            tail: AtomicLink,
            write: Boolean,
            thread: OnceCell<Thread>,
            completed: Atomic<Boolean, Align1<UByte>>,
        ): Node = Node(
            next = next,
            prev = prev,
            tail = tail,
            write = write,
            threadCtorArg = thread,
            completedCtorArg = completed,
        )

        /**
         * Creates a new queue node.
         */
        public fun new(write: Boolean): Node = Node(next = AtomicLink.new(null), prev = AtomicLink.new(null), tail = AtomicLink.new(null), write = write, thread = OnceCell.new(), completed = AtomicBool.new(false))

        /**
         * Atomically mark this node as completed.
         *
         * # Safety
         *
         * `node` must point to a valid `Node`, and the node may not outlive this call.
         */
        public fun complete(node: NonNull) {
            // Since the node may be destroyed immediately after the completed flag is set, clone the
            // thread handle before that.
            val thread = node.asRef().thread.get()!!.clone()
            run {
                node.asRef().completed.store(true, Release)
            }
            thread.unpark()
        }
    }
}

/**
 * An atomic node pointer with relaxed operations.
 */
public open class AtomicLink internal constructor(
    valueCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
) {
    public var value: Atomic<MutPtr, Align8<MutPtr>> = valueCtorArg
        internal set

    public var field0: Atomic<MutPtr, Align8<MutPtr>>
        get() = value
        set(v) {
            value = v
        }

    public open fun get(): NonNull? = NonNull.new(this.value.load(Relaxed))

    public open fun set(v: NonNull?) {
        this.value.store((v?.let(NonNull::asPtr) ?: MutPtr(0L)), Relaxed)
    }

    public companion object {
        public operator fun invoke(
            value: Atomic<MutPtr, Align8<MutPtr>>,
        ): AtomicLink = AtomicLink(
            valueCtorArg = value,
        )

        public fun new(v: NonNull?): AtomicLink = AtomicLink(AtomicPtr.new((v?.let(NonNull::asPtr) ?: MutPtr(0L))))
    }
}

/**
 * Traverse the queue and find the tail, adding backlinks to the queue while traversing.
 *
 * This may be called from multiple threads at the same time as long as the queue is not being
 * modified (this happens when unlocking multiple readers).
 *
 * # Safety
 *
 * * `head` must point to a node in a valid queue.
 * * `head` must be in front of the previous head node that was used to perform the last removal.
 * * The part of the queue starting with `head` must not be modified during this call.
 */
public fun findTailAndAddBacklinks(head: NonNull): NonNull {
    var current = head
    // Traverse the queue until we find a node that has a set `tail`.
    val tail1 =
        while (true) {
            val c = current.asRef()
            when (val matchVal = c.tail.get()) {
                null -> {}
                else -> {
                    break
                }
            }
            // SAFETY: All `next` fields before the first node with a set `tail` are non-null and valid
            // (by Invariant 3).
            run {
                val next = c.next.get().unwrapUnchecked()
                next.asRef().prev.set(current)
                current = next
            }
        }
    return run {
        head.asRef().tail.set(tail1)
        tail1
    }
}

/**
 * [`complete`](Node.complete)s all threads in the queue ending with `tail`.
 *
 * # Safety
 *
 * * `tail` must be a valid tail of a fully linked queue.
 * * The current thread must have exclusive access to that queue.
 */
public fun completeAll(tail: NonNull) {
    var current = tail
    // Traverse backwards through the queue (FIFO) and `complete` all of the nodes.
    while (true) {
        val prev = current.asRef().prev.get()
        run {
            Node.complete(current)
        }
        when (prev) {
            null -> { return }
            else -> {
                val prev1 = prev
                current = prev1
            }
        }
    }
}

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

/**
 * The public inner `RwLock` type.
 */
public open class RwLock internal constructor(
    stateCtorArg: AtomicState,
) {
    public var state: AtomicState = stateCtorArg
        internal set

    public open fun tryRead(): Boolean = this.state.tryUpdate(Acquire, Relaxed, ::readLock).isOk()

    public open fun read() {
        if (!this.tryRead()) {
            this.lockContended(false)
        }
    }

    public open fun tryWrite(): Boolean {
        // Atomically set the `LOCKED` bit. This is lowered to a single atomic instruction on most
        // modern processors (e.g. "lock bts" on x86 and "ldseta" on modern AArch64), and therefore
        // is more efficient than `tryUpdate(lock(true))`, which can spuriously fail if a new
        // node is appended to the queue.
        return (this.state.fetchOr(LOCKED, Acquire).addr() and LOCKED == 0).toUByte()
    }

    public open fun write() {
        if (!this.tryWrite()) {
            this.lockContended(true)
        }
    }

    public open fun lockContended(write: Boolean) {
        val node = Node.new(write)
        var state = this.state.load(Relaxed)
        var count = 0
        val updateFn =
            if (write) {
                ::writeLock
            } else {
                ::readLock
            }
        while (true) {
            // Optimistically update the state.
            when (val matchVal = updateFn(state)) {
                null -> {
                    if (state.addr() and QUEUED == 0 && count < SPIN_COUNT) {
                        // If the lock is not available and no threads are queued, optimistically spin for a
                        // while, using exponential backoff to decrease cache contention.
                        for (unused in 0 until (1 shl count.toInt())) {
                            spinLoop()
                        }
                        state = this.state.load(Relaxed)
                        count += 1
                        continue
                    }
                }
                else -> {
                    val next = matchVal
                    // The lock is available, try locking it.
                    run {
                        val matchVal1 = this.state.compareExchangeWeak(state, next, Acquire, Relaxed)
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            return
                        }; is Result.Err<*, *> -> {
                            val new = matchVal1.errValue()
                            state = new
                        } }
                    }
                    continue
                }
            }
            // The optimistic paths did not succeed, so fall back to parking the thread.
            // First, prepare the node.
            node.prepare()
            // If there are threads queued, this will set the `next` field to be a pointer to the
            // first node in the queue.
            // If the state is read-locked, this will set `next` to the lock count.
            // If it is write-locked, it will set `next` to zero.
            node.next.value = AtomicPtr.new(state.mask(NODE_MASK).cast())
            node.prev = AtomicLink.new(null)
            // Set the `QUEUED` bit and preserve the `LOCKED` and `DOWNGRADED` bit.
            var next1 = mutPtrFromRustCast(node.mapAddr({ addr -> addr or QUEUED or (state.addr() and (DOWNGRADED or LOCKED)) }))
            var isQueueLocked = false
            if (state.addr() and QUEUED == 0) {
                // If this is the first node in the queue, set the `tail` field to the node itself
                // to ensure there is a valid `tail` field in the queue (Invariants 1 & 2).
                // This needs to use `set` to avoid invalidating the new pointer.
                node.tail.set(NonNull.from(node))
            } else {
                // Otherwise, the tail of the queue is not known.
                node.tail.set(null)
                // Try locking the queue to eagerly add backlinks.
                next1 = next1.mapAddr({ addr -> addr or QUEUE_LOCKED })
                // Track if we changed the `QUEUE_LOCKED` bit from off to on.
                isQueueLocked = state.addr() and QUEUE_LOCKED == 0
            }
            // Register the node, using release ordering to propagate our changes to the waking
            // thread.
            when (val matchVal2 = this.state.compareExchangeWeak(state, next1, AcqRel, Relaxed)) {
                is Result.Err<*, *> -> {
                    val new1 = matchVal2.errValue()
                    // The state has changed, just try again.
                    state = new1
                    continue
                }
                else -> {}
            }
            // The node has been registered, so the structure must not be mutably accessed or
            // destroyed while other threads may be accessing it.
            // Guard against unwinds using a `PanicGuard` that aborts when dropped.
            val guard = PanicGuard()
            // If the current thread locked the queue, unlock it to eagerly adding backlinks.
            if (isQueueLocked) {
                // SAFETY: This thread set the `QUEUE_LOCKED` bit above.
                run {
                    this.unlockQueue(next1)
                }
            }
            // Wait until the node is removed from the queue.
            // SAFETY: the node was created by the current thread.
            run {
                node.wait()
            }
            // The node was removed from the queue, disarm the guard.
            forget(guard)
            // Reload the state and try again.
            state = this.state.load(Relaxed)
            count = 0
        }
    }

    public open fun readUnlock() {
        run {
            val matchVal = this.state.tryUpdate(Release, Acquire, { state ->
            if (state.addr() and QUEUED == 0) {
                // If there are no threads queued, simply decrement the reader count.
                val count = state.addr() - (SINGLE or LOCKED)
                if (count > 0) {
                    withoutProvenanceMut(count or LOCKED)
                } else {
                    UNLOCKED
                }
            } else if (state.addr() and DOWNGRADED != 0) {
                // This thread used to have exclusive access, but requested a downgrade. This has
                // not been completed yet, so we still have exclusive access.
                // Retract the downgrade request and unlock, but leave waking up new threads to the
                // thread that already holds the queue lock.
                state.mask((DOWNGRADED or LOCKED).inv())
            } else {
                null
            }
        })
            when (matchVal) { is Result.Ok<*, *> -> {}; is Result.Err<*, *> -> {
                val state = matchVal.errValue()
                this.readUnlockContended(state)
            } }
        }
    }

    /**
     * # Safety
     *
     * * There must be threads queued on the lock.
     * * `state` must be a pointer to a node in a valid queue.
     * * There cannot be a `downgrade` in progress.
     */
    public open fun readUnlockContended(state: State) {
        // SAFETY:
        // The state was observed with acquire ordering above, so the current thread will have
        // observed all node initializations.
        // We also know that no threads can be modifying the queue starting at `state`: because new
        // read-locks cannot be acquired while there are any threads queued on the lock, all
        // queue-lock owners will observe a set `LOCKED` bit in `self.state` and will not modify
        // the queue. The other case that a thread could modify the queue is if a downgrade is in
        // progress (removal of the entire queue), but since that is part of this function's safety
        // contract, we can guarantee that no other threads can modify the queue.
        val tail = findTailAndAddBacklinks(toNode(state)).asRef()
        // The lock count is stored in the `next` field of `tail`.
        // Decrement it, making sure to observe all changes made to the queue by the other lock
        // owners by using acquire-release ordering.
        val wasLast = tail.next.first.fetchByteSub(SINGLE, AcqRel).addr() - SINGLE == 0
        if (wasLast) {
            // SAFETY: Other threads cannot read-lock while threads are queued. Also, the `LOCKED`
            // bit is still set, so there are no writers. Thus the current thread exclusively owns
            // this lock, even though it is a reader.
            this.unlockContended(state)
        }
    }

    public open fun writeUnlock() {
        when (val matchVal = this.state.compareExchange(withoutProvenanceMut(LOCKED), UNLOCKED, Release, Relaxed)) {
            is Result.Err<*, *> -> {
                val state = matchVal.errValue()
                // SAFETY: Since other threads cannot acquire the lock, the state can only have changed
                // because there are threads queued on the lock.
                this.unlockContended(state)
            }
            else -> {}
        }
    }

    /**
     * # Safety
     *
     * * The lock must be exclusively owned by this thread.
     * * There must be threads queued on the lock.
     * * `state` must be a pointer to a node in a valid queue.
     * * There cannot be a `downgrade` in progress.
     */
    public open fun unlockContended(state: State) {
        check(state.addr() and (DOWNGRADED or QUEUED or LOCKED) == QUEUED or LOCKED)
        var current = state
        // We want to atomically release the lock and try to acquire the queue lock.
        while (true) {
            // First check if the queue lock is already held.
            if (current.addr() and QUEUE_LOCKED != 0) {
                // Another thread holds the queue lock, so let them wake up waiters for us.
                val next = current.mask(LOCKED.inv())
                run {
                    val matchVal = this.state.compareExchangeWeak(current, next, Release, Relaxed)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        return
                    }; is Result.Err<*, *> -> {
                        val new = matchVal.errValue()
                        current = new
                        continue
                    } }
                }
            }
            // Atomically release the lock and try to acquire the queue lock.
            val next1 = current.mapAddr({ addr -> (addr and LOCKED.inv()) or QUEUE_LOCKED })
            run {
                val matchVal1 = this.state.compareExchangeWeak(current, next1, AcqRel, Relaxed)
                when (matchVal1) { is Result.Ok<*, *> -> {
                    // SAFETY: This thread just acquired the queue lock, and this function's safety
                    // contract requires that there are threads already queued on the lock.
                    this.unlockQueue(next1)
                    return
                }; is Result.Err<*, *> -> {
                    val new1 = matchVal1.errValue()
                    current = new1
                } }
            }
        }
    }

    /**
     * # Safety
     *
     * * The lock must be write-locked by this thread.
     */
    public open fun downgrade() {
        // Optimistically change the state from write-locked with a single writer and no waiters to
        // read-locked with a single reader and no waiters.
        when (val matchVal = this.state.compareExchange(withoutProvenanceMut(LOCKED), withoutProvenanceMut(SINGLE or LOCKED), Release, Relaxed)) {
            is Result.Err<*, *> -> {
                val state = matchVal.errValue()
                // SAFETY: The only way the state can have changed is if there are threads queued.
                // Wake all of them up.
                this.downgradeSlow(state)
            }
            else -> {}
        }
    }

    /**
     * Downgrades the lock from write-locked to read-locked in the case that there are threads
     * waiting on the wait queue.
     *
     * This function will either wake up all of the waiters on the wait queue or designate the
     * current holder of the queue lock to wake up all of the waiters instead. Once the waiters
     * wake up, they will continue in the execution loop of `lockContended`.
     *
     * # Safety
     *
     * * The lock must be write-locked by this thread.
     * * `state` must be a pointer to a node in a valid queue.
     * * There must be threads queued on the lock.
     */
    public open fun downgradeSlow(state: State) {
        var state1 = state
        check(state1.addr() and (DOWNGRADED or QUEUED or LOCKED) == QUEUED or LOCKED)
        // Attempt to wake up all waiters by taking ownership of the entire waiter queue.
        while (true) {
            if (state1.addr() and QUEUE_LOCKED != 0) {
                // Another thread already holds the queue lock. Tell it to wake up all waiters.
                // If the other thread succeeds in waking up waiters before we release our lock, the
                // effect will be just the same as if we had changed the state below.
                // Otherwise, the `DOWNGRADED` bit will still be set, meaning that when this thread
                // calls `readUnlock` later (because it holds a read lock and must unlock
                // eventually), it will realize that the lock is still exclusively locked and act
                // accordingly.
                val next = state1.mapAddr({ addr -> addr or DOWNGRADED })
                run {
                    val matchVal = this.state.compareExchangeWeak(state1, next, Release, Relaxed)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        return
                    }; is Result.Err<*, *> -> {
                        val new = matchVal.errValue()
                        state1 = new
                    } }
                }
            } else {
                // Grab the entire queue by swapping the `state` with a single reader.
                val next1 = ptr.withoutProvenanceMut(SINGLE or LOCKED)
                when (val matchVal1 = this.state.compareExchangeWeak(state1, next1, AcqRel, Relaxed)) {
                    is Result.Err<*, *> -> {
                        val new1 = matchVal1.errValue()
                        state1 = new1
                        continue
                    }
                    else -> {}
                }
                // SAFETY: We have full ownership of this queue now, so nobody else can modify it.
                val tail = findTailAndAddBacklinks(toNode(state1))
                // Wake up all waiters.
                // SAFETY: `tail` was just computed, meaning the whole queue is linked, and we have
                // full ownership of the queue, so we have exclusive access.
                completeAll(tail)
                return
            }
        }
    }

    /**
     * Unlocks the queue. Wakes up all threads if a downgrade was requested, otherwise wakes up the
     * next eligible thread(s) if the lock is unlocked.
     *
     * # Safety
     *
     * * The queue lock must be held by the current thread.
     * * `state` must be a pointer to a node in a valid queue.
     * * There must be threads queued on the lock.
     */
    public open fun unlockQueue(state: State) {
        var state1 = state
        check(state1.addr() and (QUEUED or QUEUE_LOCKED) == QUEUED or QUEUE_LOCKED)
        while (true) {
            // SAFETY: Since we have the queue lock, nobody else can be modifying the queue.
            val tail = findTailAndAddBacklinks(toNode(state1))
            if (state1.addr() and (DOWNGRADED or LOCKED) == LOCKED) {
                // Another thread has locked the lock and no downgrade was requested.
                // Leave waking up waiters to them by releasing the queue lock.
                run {
                    val matchVal = this.state.compareExchangeWeak(state1, state1.mask(QUEUE_LOCKED.inv()), Release, Acquire)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        return
                    }; is Result.Err<*, *> -> {
                        val new = matchVal.errValue()
                        state1 = new
                        continue
                    } }
                }
            }
            // Since we hold the queue lock and downgrades cannot be requested if the lock is
            // already read-locked, we have exclusive control over the queue here and can make
            // modifications.
            val downgrade = state1.addr() and DOWNGRADED != 0
            val isWriter = tail.asRef().write
            if (downgrade.inv() && isWriter && tail.asRef().prev.get() != null) {
                // If we are not downgrading and the next thread is a writer, only wake up that
                // writing thread.
                // Split off `tail`.
                // There are no set `tail` links before the node pointed to by `state`, so the first
                // non-null tail field will be current (Invariant 2).
                // We also fulfill Invariant 4 since `findTail` was called on this node, which
                // ensures all backlinks are set.
                run {
                    toNode(state1).asRef().tail.set(prev)
                }
                // Try to release the queue lock. We need to check the state again since another
                // thread might have acquired the lock and requested a downgrade.
                val next = state1.mask(QUEUE_LOCKED.inv())
                when (val matchVal1 = this.state.compareExchangeWeak(state1, next, Release, Acquire)) {
                    is Result.Err<*, *> -> {
                        val new1 = matchVal1.errValue()
                        // Undo the tail modification above, so that we can find the tail again above.
                        // As mentioned above, we have exclusive control over the queue, so no other
                        // thread could have noticed the change.
                        run {
                            toNode(state1).asRef().tail.set(tail)
                        }
                        state1 = new1
                        continue
                    }
                    else -> {}
                }
                // The tail was split off and the lock was released. Mark the node as completed.
                run {
                    return Node.complete(tail)
                }
            } else {
                // We are either downgrading, the next waiter is a reader, or the queue only
                // consists of one waiter. In any case, just wake all threads.
                // Clear the queue.
                val next1 =
                    if (downgrade) {
                        ptr.withoutProvenanceMut(SINGLE or LOCKED)
                    } else {
                        UNLOCKED
                    }
                when (val matchVal2 = this.state.compareExchangeWeak(state1, next1, Release, Acquire)) {
                    is Result.Err<*, *> -> {
                        val new2 = matchVal2.errValue()
                        state1 = new2
                        continue
                    }
                    else -> {}
                }
                // SAFETY: we computed `tail` above, and no new nodes can have been added since
                // (otherwise the CAS above would have failed).
                // Thus we have complete control over the whole queue.
                run {
                    return completeAll(tail)
                }
            }
        }
    }

    public companion object {
        public operator fun invoke(
            state: AtomicState,
        ): RwLock = RwLock(
            stateCtorArg = state,
        )

        public fun new(): RwLock = RwLock(state = AtomicPtr.new(UNLOCKED))
    }
}
