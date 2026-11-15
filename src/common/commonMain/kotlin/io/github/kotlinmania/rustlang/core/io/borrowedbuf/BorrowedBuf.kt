// port-lint: source io/borrowed_buf.rs
package io.github.kotlinmania.rustlang.core.io.borrowedbuf

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.fmt.Formatter
import io.github.kotlinmania.rustlang.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.ptr.nonnull.NonNull

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

/**
 * A borrowed buffer of initially uninitialized elements, which is incrementally filled.
 *
 * This type makes it safer to work with `MaybeUninit` buffers, such as to read into a buffer
 * without having to initialize it first. It tracks the region of elements that have been filled
 * and whether the unfilled region was initialized.
 *
 * In summary, the contents of the buffer can be visualized as:
 * ```kotlin
 * [capacity][filled | unfilled(may be initialized)]
 * ```
 *
 * A `BorrowedBuf` is created around some existing elements (or capacity for elements) via a unique
 * reference (). The `BorrowedBuf` can be configured (e.g., using `clear` or `setInit`), but
 * cannot be directly written. To write into the buffer, use `unfilled` to create a
 * `BorrowedCursor`. The cursor has write-only access to the unfilled portion of the buffer (you
 * can think of it as a write-only iterator).
 *
 * The scope `data` is a bound on the scope of the underlying elements.
 *
 * The type is most commonly used to manage bytes, but can manage any type of elements.
 */
public open class BorrowedBuf<T>(
    /**
     * The buffer's underlying elements.
     */
    public var buf: MutableList<MaybeUninit<T>>,
    /**
     * The number of elements of `self.buf` that are known to be filled.
     */
    public var filled: Int,
    /**
     * Whether the entire unfilled part of `self.buf` has explicitly been initialized.
     */
    public var init: Boolean,
) {
    public open fun fmt(f: Formatter): Result<Unit, Unit> = run { BorrowedBufDebug(init = this.init, filled = this.filled, capacity = this.capacity()).fmt(f); Result.Ok(Unit) }

    /**
     * Returns the total capacity of the buffer.
     */
    public open fun capacity(): Int = this.buf.size

    /**
     * Returns the length of the filled part of the buffer.
     */
    public open fun len(): Int = this.filled

    /**
     * Returns `true` if the buffer is initialized.
     */
    public open fun isInit(): Boolean = this.init

    /**
     * Returns a shared reference to the filled portion of the buffer.
     */
    public open fun filled(): List<T> {
        // SAFETY: We only slice the filled part of the buffer, which is always valid
        return run {
            val buf = this.buf.getUnchecked(0 until this.filled)
            buf.assumeInitRef()
        }
    }

    /**
     * Returns a mutable reference to the filled portion of the buffer.
     */
    public open fun filledMut(): MutableList<T> {
        // SAFETY: We only slice the filled part of the buffer, which is always valid
        return run {
            val buf = this.buf.getUncheckedMut(0 until this.filled)
            buf.assumeInitMut()
        }
    }

    /**
     * Returns a shared reference to the filled portion of the buffer with its original scope.
     */
    public open fun intoFilled(): List<T> {
        // SAFETY: We only slice the filled part of the buffer, which is always valid
        return run {
            val buf = this.buf.getUnchecked(0 until this.filled)
            buf.assumeInitRef()
        }
    }

    /**
     * Returns a mutable reference to the filled portion of the buffer with its original scope.
     */
    public open fun intoFilledMut(): MutableList<T> {
        // SAFETY: We only slice the filled part of the buffer, which is always valid
        return run {
            val buf = this.buf.getUncheckedMut(0 until this.filled)
            buf.assumeInitMut()
        }
    }

    /**
     * Returns a cursor over the unfilled part of the buffer.
     */
    public open fun unfilled(): BorrowedCursor<T> {
        val borrowedBuf = NonNull.fromMut(this)
        return BorrowedCursor(buf = NonNull.fromMut(this.buf).cast(), borrowedBuf = borrowedBuf)
    }

    /**
     * Clears the buffer, resetting the filled region to empty.
     *
     * The contents of the buffer are not modified.
     */
    public open fun clear(): BorrowedBuf<T> {
        this.filled = 0
        return this
    }

    /**
     * Asserts that the unfilled part of the buffer is initialized.
     *
     * # Safety
     *
     * All the elements of the buffer must be initialized.
     */
    public open fun setInit(): BorrowedBuf<T> {
        this.init = true
        return this
    }

    public companion object {
        /**
         * Creates a new `BorrowedBuf` from a fully initialized slice.
         */
        public fun <T> from(slice: MutableList<T>, typeLayoutT: TypeLayout<T>): BorrowedBuf<T> = BorrowedBuf(buf = RustMutableReference(MutPtr((((mutPtrFromRustCast(slice)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), ObjectSlotLayout<List<MaybeUninit<T>>>()), filled = 0, init = true)

        /**
         * Creates a new `BorrowedBuf` from an uninitialized buffer.
         */
        public fun <T> from(buf: MutableList<MaybeUninit<T>>): BorrowedBuf<T> = BorrowedBuf(buf = buf, filled = 0, init = false)

        /**
         * Creates a new `BorrowedBuf` from a cursor.
         *
         * Use `BorrowedCursor.withUnfilledBuf` instead for a safer alternative.
         */
        public fun <T> from(buf: BorrowedCursor<T>): BorrowedBuf<T> {
            val filled = buf.filled()
            val init = buf.isBufInit()
            val len = buf.bufLen()
            return BorrowedBuf(buf = rawMutSliceList(buf.buf.asPtr().add(filled), (len - filled).toInt(), PrimitiveLayouts.U8), filled = 0, init = init)
        }
    }
}

public open class BorrowedBufDebug(
    public var init: Boolean,
    public var filled: Int,
    public var capacity: Int,
) {
    public open fun fmt(f: Formatter): Result<Unit, Unit> = DebugStruct(f, "BorrowedBuf").field("init", this.init).field("filled", this.filled).field("capacity", this.capacity).finish()

    public companion object {
        public fun typeLayout(): TypeLayout<BorrowedBufDebug> =
            object : TypeLayout<BorrowedBufDebug> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): BorrowedBufDebug =
                    BorrowedBufDebug(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: BorrowedBufDebug) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.init)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.filled)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.capacity)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BorrowedBufDebug =
                    BorrowedBufDebug(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BorrowedBufDebug) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.init)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.filled)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.capacity)
                }
            }
    }
}

/**
 * A writeable view of the unfilled portion of a [BorrowedBuf].
 *
 * The unfilled portion may be uninitialized; see [BorrowedBuf] for details.
 *
 * Data can be written directly to the cursor by using [`append`](BorrowedCursor.append) or
 * indirectly by getting a slice of part or all of the cursor and writing into the slice. In the
 * indirect case, the caller must call [`advance`](BorrowedCursor.advance) after writing to inform
 * the cursor how many elements have been written.
 *
 * Once elements are written to the cursor, they become part of the filled portion of the
 * underlying `BorrowedBuf` and can no longer be accessed or re-written by the cursor. In other
 * words, the cursor tracks the unfilled part of the underlying `BorrowedBuf`.
 *
 * The scope `a` is a bound on the scope of the underlying buffer (which means it is a bound
 * on the elements in that buffer by transitivity).
 */
public open class BorrowedCursor<T> internal constructor(
    bufCtorArg: NonNull,
    borrowedBufCtorArg: NonNull,
) {
    /**
     * The start of the elements of the buffer this cursor was created from.
     * Safety invariant: this points to the start of the *whole* buffer of `* borrowedBuf` and is
     * valid for reads and writes of `(* borrowedBuf).buf.len()` elements, so that
     * `(* borrowedBuf).filled` indexes into it.
     */
    public var buf: NonNull = bufCtorArg
        internal set

    /**
     * The buffer this cursor was created from.
     * Safety invariants:
     * 1. `(* borrowedBuf).buf` is *never* accessed by the owner of the pointee while the `buf`
     *    field above is alive, because there is a  of the pointee while the cursor is alive.
     * 2. We promise to only access the `filled` and `init` fields and the metadata of the `buf`
     *    field through the `borrowedBuf` pointer, never triggering any retag of `buf`s pointer,
     *    as the `buf` field above holds a reborrow of it and reaching the parent again would be a
     *    foreign access for that reborrow. This includes not making a reference to the whole
     *    pointee out of `borrowedBuf`, but only accessing those fields directly through pointer
     *    manipulation.
     */
    public var borrowedBuf: NonNull = borrowedBufCtorArg
        internal set

    // SAFETY: A `BorrowedCursor<T>` is a unique borrow of a `BorrowedBuf<T>`, which is a
    // `[MaybeUninit<T>]` and two `Copy` fields. The `buf` raw pointer is used like
    // `[MaybeUninit<T>]` so `T: Send` -> `Send` and  is: `T: Sync` -> `Sync`, and the
    // `borrowedBuf` only touches two `Copy` fields, without depending of `T`.

    // SAFETY: See the `Send` impl above.

    public open fun fmt(f: Formatter): Result<Unit, Unit> {
        val buf = BorrowedBufDebug(init = this.isBufInit(), filled = this.filled(), capacity = this.bufLen())
        return DebugStruct(f, "BorrowedCursor").field("buf", buf).finish()
    }

    // Helpers to access underlying buffer state.
    public open fun bufMut(): MutableList<MaybeUninit<T>> {
        val len = this.bufLen()
        // SAFETY: `buf` points to `len` elements that this cursor borrows exclusively.
        return rawMutSliceList(this.buf.asPtr(), len.toInt(), PrimitiveLayouts.U8)
    }

    public open fun bufLen(): Int {
        // SAFETY: We read just the metadata of `buf` and avoid retagging the reference.
        return run {
            val borrowedBuf = this.borrowedBuf.asPtr()
            val bufPtr: ConstPtr = addrOf(borrowedBuf.buf)
            // Same layout:
            // https://doc.rust-lang.org/reference/type-layout.html#r-layout.pointer.intro
            val bufPtr1: ConstPtr = ConstPtr((bufPtr).rawAddress)
            val buf: ConstPtr = (PrimitiveLayouts.constPtrLayout(64L)).readNative((bufPtr1).rawAddress)
            buf.len()
        }
    }

    public open fun unfilledSlice(): MutableList<MaybeUninit<T>> {
        val filled = this.filled()
        // SAFETY: always in bounds
        return this.bufMut().getUncheckedMut(filled until Int.MAX_VALUE)
    }

    public open fun filled(): Int {
        // SAFETY: We access just `filled` and avoid foreign read on `buf`.
        return this.borrowedBuf.asPtr().filled
    }

    public open fun isBufInit(): Boolean {
        // SAFETY: We access just `init` and avoid foreign read on `buf`.
        return this.borrowedBuf.asPtr().init
    }

    /**
     * # Safety
     *
     * In case of `true` all the elements of the cursor must be initialized.
     */
    public open fun setBufInit(init: Boolean) {
        // SAFETY: We access just `init` and avoid foreign read on `buf`.
        run {
            this.borrowedBuf.asPtr().init = init
        }
    }

    /**
     * # Safety
     *
     * The next `n` elements of the cursor must be initialized.
     */
    public open fun addFilled(n: Int) {
        // SAFETY: We access just `filled` and avoid foreign read on `buf`.
        run {
            this.borrowedBuf.asPtr().filled += n
        }
    }

    /**
     * Reborrows this cursor by cloning it with a smaller scope.
     *
     * Since a cursor maintains unique access to its underlying buffer, the borrowed cursor is
     * not accessible while the new cursor exists.
     */
    public open fun reborrow(): BorrowedCursor<T> = BorrowedCursor(buf = this.buf, borrowedBuf = this.borrowedBuf)

    /**
     * Returns the available space in the cursor.
     */
    public open fun capacity(): Int = this.bufLen() - this.filled()

    /**
     * Returns the number of elements written to the `BorrowedBuf` this cursor was created from.
     *
     * In particular, the count returned is shared by all reborrows of the cursor.
     */
    public open fun written(): Int = this.filled()

    /**
     * Returns `true` if the buffer is initialized.
     */
    public open fun isInit(): Boolean = this.isBufInit()

    /**
     * Set the buffer as fully initialized.
     *
     * # Safety
     *
     * All the elements of the cursor must be initialized.
     */
    public open fun setInit() {
        // SAFETY: the caller guarantees that all the elements of the cursor are initialized.
        this.setBufInit(true)
    }

    /**
     * Returns a mutable reference to the whole cursor.
     *
     * # Safety
     *
     * The caller must not uninitialize any elements of the cursor if it is initialized.
     */
    public open fun asMut(): MutableList<MaybeUninit<T>> = this.unfilledSlice()

    /**
     * Advances the cursor by asserting that `n` elements have been filled.
     *
     * After advancing, the `n` elements are no longer accessible via the cursor and can only be
     * accessed via the underlying buffer. I.e., the buffer's filled portion grows by `n` elements
     * and its unfilled portion (and the capacity of this cursor) shrinks by `n` elements.
     *
     * If less than `n` elements initialized (by the cursor's point of view), `setInit` should be
     * called first.
     *
     * # Panics
     *
     * Panics if there are less than `n` elements initialized.
     */
    public open fun advanceChecked(n: Int): BorrowedCursor<T> {
        // The subtraction cannot underflow by invariant of this type.
        val initUnfilled =
            if (this.isBufInit()) {
                this.bufLen() - this.filled()
            } else {
                0
            }
        check(n <= initUnfilled)
        // SAFETY: the next `n` elements are initialized, as asserted above.
        this.advance(n)
        return this
    }

    /**
     * Advances the cursor by asserting that `n` elements have been filled.
     *
     * After advancing, the `n` elements are no longer accessible via the cursor and can only be
     * accessed via the underlying buffer. I.e., the buffer's filled portion grows by `n` elements
     * and its unfilled portion (and the capacity of this cursor) shrinks by `n` elements.
     *
     * # Safety
     *
     * The caller must ensure that the first `n` elements of the cursor have been initialized.
     */
    public open fun advance(n: Int): BorrowedCursor<T> {
        // SAFETY: the caller guarantees that the first `n` elements of the cursor are initialized.
        this.addFilled(n)
        return this
    }

    /**
     * Append elements to the cursor, advancing position within its buffer.
     *
     * # Panics
     *
     * Panics if `self.capacity()` is less than `buf.len()`.
     */
    public open fun append(buf: List<T>) {
        check(this.capacity() >= buf.size)
        // SAFETY: we do not de-initialize any of the elements of the slice
        run {
            this.asMut()[0 until buf.size].writeCopyOfSlice(buf)
        }
        // SAFETY: these elements have just been initialized.
        this.advance(buf.size)
    }

    /**
     * Runs the given closure with a `BorrowedBuf` containing the unfilled part
     * of the cursor.
     *
     * This enables inspecting what was written to the cursor.
     *
     * # Panics
     *
     * Panics if the `BorrowedBuf` given to the closure is replaced by another
     * one.
     */
    public open fun <R> withUnfilledBuf(f: (BorrowedBuf<T>) -> R): R {
        val buf = BorrowedBuf.from(this.reborrow())
        val prevPtr = constPtrFromRustCast(buf.buf)
        val res = f(buf)
        // Check that the caller didn't replace the `BorrowedBuf`.
        // This is necessary for the safety of the code below: if the check wasn't
        // there, one could mark some elements as initialized even though they aren't.
        check(eq(prevPtr, buf.buf))
        val filled = buf.filled
        val init = buf.init
        // Update `init` and `filled` fields with what was written to the buffer.
        // `self.buf.filled` was the starting length of the `BorrowedBuf`.
        //
        // SAFETY: These elements were initialized/filled in the `BorrowedBuf`, and therefore they
        // are initialized/filled in the cursor too, because the buffer wasn't replaced.
        run {
            this.setBufInit(init)
            this.advance(filled)
        }
        return res
    }

    /**
     * Initializes all elements in the cursor with their default value and
     * returns them.
     */
    public open fun ensureInit(): MutableList<T> {
        if (!this.isBufInit()) {
            this.unfilledSlice().writeDefault()
            // SAFETY: buf is now initialized.
            this.setBufInit(true)
        }
        // SAFETY: these elements have just been initialized if they weren't before
        return this.unfilledSlice().assumeInitMut()
    }

    public companion object {
        public operator fun <T> invoke(
            buf: NonNull,
            borrowedBuf: NonNull,
        ): BorrowedCursor<T> = BorrowedCursor<T>(
            bufCtorArg = buf,
            borrowedBufCtorArg = borrowedBuf,
        )
    }
}
