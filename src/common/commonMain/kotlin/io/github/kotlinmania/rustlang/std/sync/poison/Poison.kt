// port-lint: source sync/poison.rs
package io.github.kotlinmania.rustlang.std.sync.poison

import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sys.thread.unix.Thread

// Synchronization objects that employ poisoning.
//
// # Poisoning
//
// All synchronization objects in this module implement a strategy called
// "poisoning" where a primitive becomes poisoned if it recognizes that some
// thread has panicked while holding the exclusive access granted by the
// primitive. This information is then propagated to all other threads
// to signify that the data protected by this primitive is likely tainted
// (some invariant is not being upheld).
//
// The specifics of how this "poisoned" state affects other threads and whether
// the panics are recognized reliably or on a best-effort basis depend on the
// primitive. See [Overview](#overview) below.
//
// The synchronization objects in this module have alternative implementations that do not employ
// poisoning in the [std.sync.nonpoison] module.
//
// # Overview
//
// Below is a list of synchronization objects provided by this module
// with a high-level overview for each object and a description
// of how it employs "poisoning".
//
// - [Condvar]: Condition Variable, providing the ability to block
//   a thread while waiting for an event to occur.
//
//   Condition variables are typically associated with
//   a boolean predicate (a condition) and a mutex.
//   This implementation is associated with [`poison.Mutex`](Mutex),
//   which employs poisoning.
//   For this reason, [Condvar.wait] will return a [LockResult],
//   just like [`poison.Mutex.lock`](Mutex.lock) does.
//
// - [Mutex]: Mutual Exclusion mechanism, which ensures that at
//   most one thread at a time is able to access some data.
//
//   Panicking while holding the lock typically poisons the mutex, but it is
//   not guaranteed to detect this condition in all circumstances.
//   [Mutex.lock] returns a [LockResult], providing a way to deal with
//   the poisoned state. See [`Mutex`s documentation](Mutex#poisoning) for more.
//
// - [RwLock]: Provides a mutual exclusion mechanism which allows
//   multiple readers at the same time, while allowing only one
//   writer at a time. In some cases, this can be more efficient than
//   a mutex.
//
//   This implementation, like [Mutex], usually becomes poisoned on a panic.
//   Note, however, that an `RwLock` may only be poisoned if a panic occurs
//   while it is locked exclusively (write mode). If a panic occurs in any reader,
//   then the lock will not be poisoned.
//
// Note that the [Once] type also employs poisoning, but since it has non-poisoning `force`
// methods available on it, there is no separate `nonpoison` and `poison` version.

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

public open class Flag internal constructor(
    failedCtorArg: Atomic<Boolean, Align1<UByte>>,
) {
    public var failed: Atomic<Boolean, Align1<UByte>> = failedCtorArg
        internal set

    // Note that the Ordering uses to access the `failed` field of `Flag` below is
    // always `Relaxed`, and that's because this isn't actually protecting any data,
    // it's just a flag whether we've panicked or not.
    //
    // The actual location that this matters is when a mutex is **locked** which is
    // where we have external synchronization ensuring that we see memory
    // reads/writes to this flag.
    //
    // As a result, if it matters, we should see the correct value for `failed` in
    // all cases.

    /**
     * Checks the flag for an unguarded borrow, where we only care about existing poison.
     */
    public open fun borrow(): LockResult<Unit> =
        if (this.get()) {
            Result.Err(PoisonError.new())
        } else {
            Result.Ok(Unit)
        }

    /**
     * Checks the flag for a guarded borrow, where we may also set poison when `done`.
     */
    public open fun guard(): LockResult<Guard> {
        val ret = Guard(panicking = Thread.panicking())
        return if (this.get()) {
            Result.Err(PoisonError.new(ret))
        } else {
            Result.Ok(ret)
        }
    }

    public open fun done(guard: Guard) {
        if (!guard.panicking && Thread.panicking()) {
            this.failed.store(true, Ordering.Relaxed)
        }
    }

    public open fun get(): Boolean = this.failed.load(Ordering.Relaxed)

    public open fun clear() {
        this.failed.store(false, Ordering.Relaxed)
    }

    public companion object {
        public operator fun invoke(
            failed: Atomic<Boolean, Align1<UByte>>,
        ): Flag = Flag(
            failedCtorArg = failed,
        )

        public fun new(): Flag = Flag(failed = AtomicBool.new(false))
    }
}

public open class Guard(
    public var panicking: Boolean,
) {
    public open fun clone(): Guard = Guard(panicking)

    public companion object {
        public fun typeLayout(): TypeLayout<Guard> =
            object : TypeLayout<Guard> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Guard =
                    Guard(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Guard) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.panicking)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Guard =
                    Guard(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Guard) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.panicking)
                }
            }
    }
}

/**
 * A type of error which can be returned whenever a lock is acquired.
 *
 * Both [Mutex]es and [RwLock]s are poisoned whenever a thread fails while the lock
 * is held. The precise semantics for when a lock is poisoned is documented on
 * each lock. For a lock in the poisoned state, unless the state is cleared manually,
 * all future acquisitions will return this error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Mutex
 * import std.thread
 *
 * val mutex = Mutex.new(1)
 * // poison the mutex
 * val cMutex = mutex
 * thread.spawn({
 *     var data = cMutex.lock().unwrap()
 *     data = 2
 *     error("panic")
 * }).join()
 * run {
 *     val matchVal = mutex.lock()
 *     if (matchVal.isSuccess) {
 *         error("unreachable")
 *     } else {
 *         val pErr = matchVal.exceptionOrNull()!!
 *         val data = pErr.getRef()
 *         println("recovered: $data")
 *     }
 * }
 * ```
 */
public open class PoisonError<T>(
    public var data: T,
    public var never: Nothing,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "PoisonError").finishNonExhaustive()

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = "poisoned lock: another task failed inside".fmt(f)

    /**
     * Consumes this error indicating that a lock is poisoned, returning the
     * associated data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Mutex
     * import std.thread
     *
     * val mutex = Mutex.new(mutableSetOf())
     * // poison the mutex
     * val cMutex = mutex
     * thread.spawn({
     *     var data = cMutex.lock().unwrap()
     *     data.insert(10)
     *     error("panic")
     * }).join()
     * val pErr = mutex.lock().unwrapErr()
     * val data = pErr.intoInner()
     * println("recovered ${data.len()} items")
     * ```
     */
    public open fun intoInner(): T = this.data

    /**
     * Reaches into this error indicating that a lock is poisoned, returning a
     * reference to the associated data.
     */
    public open fun getRef(): T = this.data

    /**
     * Reaches into this error indicating that a lock is poisoned, returning a
     * mutable reference to the associated data.
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<T> {
        return this::data
    }

    public companion object {
        /**
         * Creates a `PoisonError`.
         *
         * This is generally created by methods like [`Mutex.lock`](sync.Mutex.lock)
         * or [`RwLock.read`](sync.RwLock.read).
         *
         * This method may panic if std was built with `panic = "abort"`.
         */
        public fun <T> new(data: T): PoisonError<T> = PoisonError(data = data)
    }
}

/**
 * An enumeration of possible errors associated with a [TryLockResult] which
 * can occur while trying to acquire a lock, from the [tryLock] method on a
 * [Mutex] or the [tryRead] and [tryWrite] methods on an [RwLock].
 */
public sealed class TryLockError<T> {
    /**
     * The lock could not be acquired because another thread failed while holding
     * the lock.
     */
    public data class Poisoned<T>(public var value: PoisonError<T>) : TryLockError<T>()
    /**
     * The lock could not be acquired at this time because the operation would
     * otherwise block.
     */
    public class WouldBlock<T>() : TryLockError<T>()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TryLockError.Poisoned -> "Poisoned(..)".fmt(f)
            TryLockError.WouldBlock -> "WouldBlock".fmt(f)
        }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> =
        when (this) {
            is TryLockError.Poisoned -> "poisoned lock: another task failed inside"
            TryLockError.WouldBlock -> "try_lock failed because the operation would block"
        }.fmt(f)

    public open fun cause(): Any? =
        when (this) {
            is TryLockError.Poisoned -> {
                val (p) = this
                p
            }
            else -> null
        }

    public companion object {
        public fun <T> from(err: PoisonError<T>): TryLockError<T> = TryLockError.Poisoned(err)
    }
}

/**
 * A type alias for the result of a lock method which can be poisoned.
 *
 * The [Ok] variant of this result indicates that the primitive was not
 * poisoned, and the operation result is contained within. The [Err] variant indicates
 * that the primitive was poisoned. Note that the [Err] variant *also* carries
 * an associated value assigned by the lock method, and it can be acquired through the
 * [intoInner] method. The semantics of the associated value depends on the corresponding
 * lock method.
 *
 * [intoInner]: PoisonError.intoInner
 */
public typealias LockResult<T> = Result<T, PoisonError<T>>

/**
 * A type alias for the result of a nonblocking locking method.
 *
 * For more information, see [LockResult]. A `TryLockResult` doesn't
 * necessarily hold the associated guard in the [Err] type as the lock might not
 * have been acquired for other reasons.
 */
public typealias TryLockResult<Guard> = Result<Guard, TryLockError<Guard>>

public fun <T, U> mapResult(result: LockResult<T>, f: (T) -> U): LockResult<U> =
    when {
        result is Result.Ok<*, *> -> {
            val t = (result as Result.Ok<*, *>).value
            Result.Ok(f(t))
        }
        else -> {
            val errorVal = (result as Result.Err<*, *>).error
            val data = errorVal.data
            Result.Err(PoisonError.new(f(data)))
        }
    }
