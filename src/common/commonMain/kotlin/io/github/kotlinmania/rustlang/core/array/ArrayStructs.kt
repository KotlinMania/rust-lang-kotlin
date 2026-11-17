package io.github.kotlinmania.rustlang.core.array

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * The error type returned when a conversion from a slice to an array fails.
 */
public open class TryFromSliceError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TryFromSliceError = TryFromSliceError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = "could not convert slice to array".fmt(f)

    public companion object {
        public fun typeLayout(): TypeLayout<TryFromSliceError> =
            object : TypeLayout<TryFromSliceError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): TryFromSliceError =
                    TryFromSliceError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: TryFromSliceError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TryFromSliceError =
                    TryFromSliceError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TryFromSliceError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }

        public fun from(x: Nothing): TryFromSliceError = when (x) {}
    }
}

/**
 * Panic guard for incremental initialization of arrays.
 *
 * Disarm the guard with `mem.forget` once the array has been initialized.
 *
 * # Safety
 *
 * All write accesses to this structure are unchecked and must maintain a correct
 * count of `initialized` elements.
 *
 * To minimize indirection, fields are still pub but callers should at least use
 * `pushUnchecked` to signal that something unchecked is going on.
 */
public open class Guard<T>(
    /**
     * The array to be initialized.
     */
    public var arrayMut: MutableList<MaybeUninit<T>>,
    /**
     * The number of items that have been initialized so far.
     */
    public var initialized: Int,
) {
    /**
     * Adds an item to the array and updates the initialized item counter.
     *
     * # Safety
     *
     * No more than N elements must be initialized.
     */
    public open fun pushUnchecked(item: T) {
        // SAFETY: If `initialized` was correct before and the caller does not
        // invoke this method more than N times, then writes will be in-bounds
        // and slots will not be initialized more than once.
        run {
            this.arrayMut.getUncheckedMut(this.initialized).write(item)
            this.initialized = this.initialized.uncheckedAdd(1)
        }
    }

    public open fun drop() {
        if (false) {
            check(this.initialized <= this.arrayMut.size)
        }
        // SAFETY: this slice will contain only initialized objects.
        run {
            this.arrayMut.getUncheckedMut(0 until this.initialized).assumeInitDrop()
        }
    }
}

/**
 * Panic guard for incremental initialization of arrays from the back.
 *
 * Elements of the array are populated starting from the end towards the beginning.
 * Disarm the guard with `mem.forget` once the array has been fully initialized.
 *
 * # Safety
 *
 * All write accesses to this structure are unchecked and must maintain a correct
 * count of `initialized` elements.
 */
public open class GuardBack<T>(
    /**
     * The array to be initialized (will be filled from the end).
     */
    public var arrayMut: MutableList<MaybeUninit<T>>,
    /**
     * The number of items that have been initialized so far.
     */
    public var initialized: Int,
) {
    /**
     * Adds an item to the array and updates the initialized item counter.
     *
     * # Safety
     *
     * No more than N elements must be initialized.
     */
    public open fun pushUnchecked(item: T) {
        // SAFETY: If `initialized` was correct before and the caller does not
        // invoke this method more than N times, then writes will be in-bounds
        // and slots will not be initialized more than once.
        run {
            val offset = this.initialized.uncheckedAdd(1)
            val index = this.arrayMut.size.uncheckedSub(offset)
            this.arrayMut.getUncheckedMut(index).write(item)
            this.initialized = offset
        }
    }

    public open fun drop() {
        if (false) {
            check(this.initialized <= this.arrayMut.size)
        }
        val len = this.arrayMut.size
        // SAFETY: this slice will contain only initialized objects.
        run {
            this.arrayMut.getUncheckedMut(len - this.initialized until len).assumeInitDrop()
        }
    }
}
