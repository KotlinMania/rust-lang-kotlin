// port-lint: source sys/sync/rwlock/futex.rs
package io.github.kotlinmania.rustlang.std.sys.sync.rwlock.futex

import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Acquire
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Relaxed
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Release
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.Futex
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.Primitive
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWake
import io.github.kotlinmania.rustlang.std.sys.sync.futex.unix.futexWakeAll

public open class RwLock(
    // The state consists of a 30-bit reader counter, a readers waiting' flag, and a writers waiting' flag.
    // Bits 0..30:
    // 0: Unlocked
    // 1..=0x3FffFffe: Locked by N readers
    // 0x3FffFfff: Write locked
    // Bit 30: Readers are waiting on this futex.
    // Bit 31: Writers are waiting on the writerNotify futex.
    public var state: Futex,
    // The condition variable' to notify writers through.
    // Incremented on every signal.
    public var writerNotify: Futex,
) {
    public open fun tryRead(): Boolean = this.state.tryUpdate(Acquire, Relaxed, { s -> isReadLockable(s).then({ s + READ_LOCKED }) }).isOk()

    public open fun read() {
        val state = this.state.load(Relaxed)
        if (!isReadLockable(state.toUInt()) || this.state.compareExchangeWeak(state, state + READ_LOCKED.toInt(), Acquire, Relaxed).isErr()) {
            this.readContended()
        }
    }

    /**
     * # Safety
     *
     * The `RwLock` must be read-locked (N readers) in order to call this.
     */
    public open fun readUnlock() {
        val state = this.state.fetchSub(READ_LOCKED, Release) - READ_LOCKED
        // It's impossible for a reader to be waiting on a read-locked RwLock,
        // except if there is also a writer waiting.
        check(!hasReadersWaiting(state) || hasWritersWaiting(state))
        // Wake up a writer if we were the last reader and there's a writer waiting.
        if (isUnlocked(state) && hasWritersWaiting(state)) {
            this.wakeWriterOrReaders(state)
        }
    }

    public open fun readContended() {
        var hasSlept = false
        var state = this.spinRead()
        while (true) {
            // If we have just been woken up, first check for a `downgrade` call.
            // Otherwise, if we can read-lock it, lock it.
            if ((hasSlept && isReadLockableAfterWakeup(state)) || isReadLockable(state)) {
                run {
                    val matchVal = this.state.compareExchangeWeak(state, state + READ_LOCKED, Acquire, Relaxed)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        return
                    }; is Result.Err<*, *> -> {
                        val s = matchVal.errValue()
                        state = s
                        continue
                    } }
                }
            }
            // Check for overflow.
            check(!hasReachedMaxReaders(state)) { "too many active read locks on RwLock" }
            // Make sure the readers waiting bit is set before we go to sleep.
            if (!hasReadersWaiting(state)) {
                when (val matchVal1 = this.state.compareExchange(state, state or READERS_WAITING, Relaxed, Relaxed)) {
                    is Result.Err<*, *> -> {
                        val s1 = matchVal1.errValue()
                        state = s1
                        continue
                    }
                    else -> {}
                }
            }
            // Wait for the state to change.
            futexWait(this.state, state or READERS_WAITING, null)
            hasSlept = true
            // Spin again after waking up.
            state = this.spinRead()
        }
    }

    public open fun tryWrite(): Boolean = this.state.tryUpdate(Acquire, Relaxed, { s -> isUnlocked(s).then({ s + WRITE_LOCKED }) }).isOk()

    public open fun write() {
        if (this.state.compareExchangeWeak(0, WRITE_LOCKED, Acquire, Relaxed).isErr()) {
            this.writeContended()
        }
    }

    /**
     * # Safety
     *
     * The `RwLock` must be write-locked (single writer) in order to call this.
     */
    public open fun writeUnlock() {
        val state = this.state.fetchSub(WRITE_LOCKED, Release) - WRITE_LOCKED
        check(isUnlocked(state))
        if (hasWritersWaiting(state) || hasReadersWaiting(state)) {
            this.wakeWriterOrReaders(state)
        }
    }

    /**
     * # Safety
     *
     * The `RwLock` must be write-locked (single writer) in order to call this.
     */
    public open fun downgrade() {
        // Removes all write bits and adds a single read bit.
        val state = this.state.fetchAdd(DOWNGRADE, Release)
        check(isWriteLocked(state.toUInt())) { "RwLock must be write locked to call `downgrade`" }
        if (hasReadersWaiting(state.toUInt())) {
            // Since we had the exclusive lock, nobody else can unset this bit.
            this.state.fetchSub(READERS_WAITING, Relaxed)
            futexWakeAll(this.state)
        }
    }

    public open fun writeContended() {
        var state = this.spinWrite()
        var otherWritersWaiting = 0
        while (true) {
            // If it's unlocked, we try to lock it.
            if (isUnlocked(state)) {
                run {
                    val matchVal = this.state.compareExchangeWeak(state, state or WRITE_LOCKED or otherWritersWaiting, Acquire, Relaxed)
                    when (matchVal) { is Result.Ok<*, *> -> {
                        return
                    }; is Result.Err<*, *> -> {
                        val s = matchVal.errValue()
                        state = s
                        continue
                    } }
                }
            }
            // Set the waiting bit indicating that we're waiting on it.
            if (!hasWritersWaiting(state)) {
                when (val matchVal1 = this.state.compareExchange(state, state or WRITERS_WAITING, Relaxed, Relaxed)) {
                    is Result.Err<*, *> -> {
                        val s1 = matchVal1.errValue()
                        state = s1
                        continue
                    }
                    else -> {}
                }
            }
            // Other writers might be waiting now too, so we should make sure
            // we keep that bit on once we manage lock it.
            otherWritersWaiting = WRITERS_WAITING
            // Examine the notification counter before we check if `state` has changed,
            // to make sure we don't miss any notifications.
            val seq = this.writerNotify.load(Acquire)
            // Don't go to sleep if the lock has become available,
            // or if the writers waiting bit is no longer set.
            state = this.state.load(Relaxed)
            if (isUnlocked(state) || !hasWritersWaiting(state)) {
                continue
            }
            // Wait for the state to change.
            futexWait(this.writerNotify, seq, null)
            // Spin again after waking up.
            state = this.spinWrite()
        }
    }

    /**
     * Wakes up waiting threads after unlocking.
     *
     * If both are waiting, this will wake up only one writer, but will fall
     * back to waking up readers if there was no writer to wake up.
     */
    public open fun wakeWriterOrReaders(state: Primitive) {
        var state1 = state
        check(isUnlocked(state1))
        // The readers waiting bit might be turned on at any point now,
        // since readers will block when there's anything waiting.
        // Writers will just lock the lock though, regardless of the waiting bits,
        // so we don't have to worry about the writer waiting bit.
        //
        // If the lock gets locked in the meantime, we don't have to do
        // anything, because then the thread that locked the lock will take
        // care of waking up waiters when it unlocks.
        // If only writers are waiting, wake one of them up.
        if (state1 == WRITERS_WAITING) {
            run {
                val matchVal = this.state.compareExchange(state1, 0, Relaxed, Relaxed)
                when (matchVal) { is Result.Ok<*, *> -> {
                    this.wakeWriter()
                    return
                }; is Result.Err<*, *> -> {
                    val s = matchVal.errValue()
                    // Maybe some readers are now waiting too. So, continue to the next `if`.
                    state1 = s
                } }
            }
        }
        // If both writers and readers are waiting, leave the readers waiting
        // and only wake up one writer.
        if (state1 == READERS_WAITING + WRITERS_WAITING) {
            if (this.state.compareExchange(state1, READERS_WAITING, Relaxed, Relaxed).isErr()) {
                // The lock got locked. Not our problem anymore.
                return
            }
            if (this.wakeWriter()) {
                return
            }
            // No writers were actually blocked on futexWait, so we continue
            // to wake up readers instead, since we can't be sure if we notified a writer.
            state1 = READERS_WAITING
        }
        // If readers are waiting, wake them all up.
        if (state1 == READERS_WAITING) {
            if (this.state.compareExchange(state1, 0, Relaxed, Relaxed).isOk()) {
                futexWakeAll(this.state)
            }
        }
    }

    /**
     * This wakes one writer and returns true if we woke up a writer that was
     * blocked on futexWait.
     *
     * If this returns false, it might still be the case that we notified a
     * writer that was about to go to sleep.
     */
    public open fun wakeWriter(): Boolean {
        this.writerNotify.fetchAdd(1, Release)
        return futexWake(this.writerNotify)
        // Note that FreeBSD and DragonFlyBSD don't tell us whether they woke
        // up any threads or not, and always return `false` here. That still
        // results in correct behavior: it just means readers get woken up as
        // well in case both readers and writers were waiting.
    }

    /**
     * Spin for a while, but stop directly at the given condition.
     */
    public open fun spinUntil(f: (Primitive) -> Boolean): Primitive {
        var spin = 100
        // Chosen by fair dice roll.
        while (true) {
            val state = this.state.load(Relaxed)
            if (f(state) || spin == 0) {
                return state.toUInt()
            }
            kmSpinLoopHint()
            spin -= 1
        }
    }

    public open fun spinWrite(): Primitive {
        // Stop spinning when it's unlocked or when there's waiting writers, to keep things somewhat fair.
        return this.spinUntil({ state -> isUnlocked(state.toUInt()) || hasWritersWaiting(state.toUInt()) })
    }

    public open fun spinRead(): Primitive {
        // Stop spinning when it's unlocked or read locked, or when there's waiting threads.
        return this.spinUntil({ state ->
            !isWriteLocked(state.toUInt()) || hasReadersWaiting(state.toUInt()) || hasWritersWaiting(state.toUInt())
        })
    }

    public companion object {
        public fun new(): RwLock = RwLock(state = Futex.new(0), writerNotify = Futex.new(0))
    }
}

public val READ_LOCKED: Primitive = 1u

public val MASK: Primitive = (1u shl 30) - 1u

public val WRITE_LOCKED: Primitive = MASK

// READ_LOCKED - WRITE_LOCKED
public val MAX_READERS: Primitive = MASK - 1u

public val READERS_WAITING: Primitive = 1u shl 30

public val WRITERS_WAITING: Primitive = 1u shl 31

public val DOWNGRADE: Primitive = (READ_LOCKED - (WRITE_LOCKED.toUInt()))

public fun isUnlocked(state: Primitive): Boolean = (state and MASK == 0u).toUByte()

public fun isWriteLocked(state: Primitive): Boolean = (state and MASK == WRITE_LOCKED).toUByte()

public fun hasReadersWaiting(state: Primitive): Boolean = (state and READERS_WAITING != 0u).toUByte()

public fun hasWritersWaiting(state: Primitive): Boolean = (state and WRITERS_WAITING != 0u).toUByte()

public fun isReadLockable(state: Primitive): Boolean {
    // This also returns false if the counter could overflow if we tried to read lock it.
    //
    // We don't allow read-locking if there's readers waiting, even if the lock is unlocked
    // and there's no writers waiting. The only situation when this happens is after unlocking,
    // at which point the unlocking thread might be waking up writers, which have priority over readers.
    // The unlocking thread will clear the readers waiting bit and wake up readers, if necessary.
    return (state and MASK < MAX_READERS && !hasReadersWaiting(state) && !hasWritersWaiting(state)).toUByte()
}

public fun isReadLockableAfterWakeup(state: Primitive): Boolean {
    // We make a special case for checking if we can read-lock _after_ a reader thread that went to
    // sleep has been woken up by a call to `downgrade`.
    //
    // `downgrade` will wake up all readers and place the lock in read mode. Thus, there should be
    // no readers waiting and the lock should be read-locked (not write-locked or unlocked).
    //
    // Note that we do not check if any writers are waiting. This is because a call to `downgrade`
    // implies that the caller wants other readers to read the value protected by the lock. If we
    // did not allow readers to acquire the lock before writers after a `downgrade`, then only the
    // original writer would be able to read the value, thus defeating the purpose of `downgrade`.
    return (state and MASK < MAX_READERS && !hasReadersWaiting(state) && !isWriteLocked(state) && !isUnlocked(state)).toUByte()
}

public fun hasReachedMaxReaders(state: Primitive): Boolean = (state and MASK == MAX_READERS).toUByte()
