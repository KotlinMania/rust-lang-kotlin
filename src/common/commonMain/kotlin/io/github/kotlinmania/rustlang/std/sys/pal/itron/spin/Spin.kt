// port-lint: source sys/pal/itron/spin.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.spin

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.process.id
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ID
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.disDsp
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.enaDsp
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.snsDsp
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.waitqueue.spinmutex.SpinMutexGuard

/**
 * A mutex implemented by `disDsp` (for intra-core synchronization) and a
 * spinlock (for inter-core synchronization).
 */
public open class SpinMutex<T> internal constructor(
    lockedCtorArg: Atomic<Boolean>,
    public var data: UnsafeCell<T>,
) {
    public var locked: Atomic<Boolean> = lockedCtorArg
        internal set

    /**
     * Acquire a lock.
     */
    public open fun <R> withLocked(f: (T) -> R): R {
        open class SpinMutexGuard(
            public var value: Atomic<Boolean>,
        ) {
            public var field0: Atomic<Boolean>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                this.value.store(false, Ordering.Release)
                enaDsp()
            }
        }
        val guard: Any?
        if (snsDsp() == 0) {
            val er = disDsp()
            check(er >= 0)
            // Wait until the current processor acquires a lock.
            while (this.locked.swap(true, Ordering.Acquire)) {}
            guard = SpinMutexGuard(this.locked)
        }
        return f(this.data.get())
    }

    public companion object {
        public operator fun <T> invoke(
            locked: Atomic<Boolean>,
            data: UnsafeCell<T>,
        ): SpinMutex<T> = SpinMutex<T>(
            lockedCtorArg = locked,
            data = data,
        )

        public fun <T> new(x: T): SpinMutex<T> = SpinMutex(locked = AtomicBool.new(false), data = UnsafeCell.new(x))
    }
}

/**
 * `OnceCell<(abi.ID, T)>` implemented by `disDsp` (for intra-core
 * synchronization) and a spinlock (for inter-core synchronization).
 *
 * It's assumed that `0` is not a valid ID, and all kernel
 * object IDs fall into range `1 ..= Int.MAX`.
 */
public open class SpinIdOnceCell<T> internal constructor(
    idCtorArg: Atomic<Int>,
    spinCtorArg: SpinMutex<Unit>,
    extraCtorArg: UnsafeCell<MaybeUninit<T>>,
) {
    public var id: Atomic<Int> = idCtorArg
        internal set

    public var spin: SpinMutex<Unit> = spinCtorArg
        internal set

    public var extra: UnsafeCell<MaybeUninit<T>> = extraCtorArg
        internal set

    public open fun get(): Pair<ID, T>? =
        when (val matchVal = this.id.load(Ordering.Acquire)) {
            ID_UNINIT -> null
            else -> {
                val id = matchVal
                Pair(id, (this.extra.get()).assumeInitRef())
            }
        }

    public open fun getMut(): Pair<ID, T>? =
        when (val matchVal = this.id.getMut()) {
            ID_UNINIT -> null
            else -> {
                val id = matchVal
                Pair(id, (this.extra.get()).assumeInitMut())
            }
        }

    public open fun getUnchecked(): Pair<ID, T> = Pair(this.id.load(Ordering.Acquire).toInt(), (this.extra.get()).assumeInitRef())

    /**
     * Assign the content without checking if it's already initialized or
     * being initialized.
     */
    public open fun setUnchecked(tupleArg0: Pair<ID, T>) {
        check(this.get() == null)
        // Assumption: A positive `abi.ID` fits in `Int`.
        check(id >= 0)
        check(Int.tryFrom(id).isOk())
        val id = id
        this.extra.get() = MaybeUninit.new(extra, PrimitiveLayouts.U8)
        this.id.store(id, Ordering.Release)
    }

    /**
     * Gets the contents of the cell, initializing it with `f` if
     * the cell was empty. If the cell was empty and `f` failed, an
     * error is returned.
     *
     * Warning: `f` must not perform a blocking operation, which
     * includes panicking.
     */
    public open fun <E> getOrTryInit(f: () -> Result<Pair<ID, T>, E>): Result<Pair<ID, T>, E> {
        // Fast path
        when (val matchVal = this.get()) {
            null -> {}
            else -> {
                val x = matchVal
                return Result.Ok(x)
            }
        }
        when (val tryResult = this.initialize(f)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        check(this.get() != null)
        // Safety: The inner value has been initialized
        return Result.Ok(this.getUnchecked())
    }

    public open fun <E> initialize(f: () -> Result<Pair<ID, T>, E>): Result<Unit, E> {
        return this.spin.withLocked(closure@{
            if (this.id.load(Ordering.Relaxed) == ID_UNINIT) {
                val (initializedId, initializedExtra) = when (val tryResult = f()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
                // Assumption: A positive `abi.ID` fits in `Int`.
                check(initializedId >= 0)
                check(Int.tryFrom(initializedId).isOk())
                val initializedId1 = initializedId.toInt()
                // Store the initialized contents. Use the release ordering to
                // make sure the write is visible to the callers of `get`.
                this.extra.get() = MaybeUninit.new(initializedExtra, PrimitiveLayouts.U8)
                this.id.store(initializedId1, Ordering.Release)
            }
            Result.Ok(Unit)
        })
    }

    public open fun drop() {
        if (this.getMut() != null) {
            (this.extra.get()).assumeInitDrop()
        }
    }

    public companion object {
        public operator fun <T> invoke(
            id: Atomic<Int>,
            spin: SpinMutex<Unit>,
            extra: UnsafeCell<MaybeUninit<T>>,
        ): SpinIdOnceCell<T> = SpinIdOnceCell<T>(
            idCtorArg = id,
            spinCtorArg = spin,
            extraCtorArg = extra,
        )

        public fun <T> new(): SpinIdOnceCell<T> = SpinIdOnceCell(id = AtomicUsize.new(ID_UNINIT), extra = UnsafeCell.new(MaybeUninit.uninit(PrimitiveLayouts.U8)), spin = SpinMutex.new())
    }
}

public val ID_UNINIT: Int = 0
