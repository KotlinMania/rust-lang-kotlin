// port-lint: source io/io_slice.rs
package io.github.kotlinmania.rustlang.core.io.ioslice

import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * A buffer type used with `Read.readVectored`.
 *
 * It is semantically a wrapper around a `[UByte]`, but is guaranteed to be
 * ABI compatible with the `iovec` type on Unix platforms and `WSABUF` on
 * Windows.
 */
public open class IoSliceMut(
    public var value: io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSliceMut,
) {
    public var field0: io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSliceMut
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append((this.value.asSlice()).toString()); Result.Ok<Unit, Unit>(Unit) }

    /**
     * Advance the internal cursor of the slice.
     *
     * Also see [IoSliceMut.advanceSlices] to advance the cursors of
     * multiple buffers.
     *
     * # Panics
     *
     * Panics when trying to advance beyond the end of the slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.IoSliceMut
     *
     * var data = MutableList(8) { 1 }
     * var buf = IoSliceMut.new(data)
     * // Mark 3 bytes as read.
     * buf.advance(3)
     * check(buf.deref() == List(5) { 1 }.asRef())
     * ```
     */
    public open fun advance(n: Int) {
        this.value.advance(n)
    }

    /**
     * Get the underlying bytes as a mutable slice with the original scope.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.IoSliceMut
     *
     * @feature(ioSliceAsBytes)
     * public fun doctestEntry() {
     *     var data = "abcdef".encodeToByteArray()
     *     val ioSlice = IoSliceMut.new(data)
     *     ioSlice.intoSlice()[0] = 65u.toUByte()
     *     check(data == "Abcdef".encodeToByteArray())
     * }
     * ```
     */
    public open fun intoSlice(): MutableList<UByte> = this.value.intoSlice()

    public open fun deref(): List<UByte> = this.value.asSlice()

    public open fun derefMut(): MutableList<UByte> = this.value.asMutSlice()

    public companion object {
        /**
         * Creates a new `IoSliceMut` wrapping a byte slice.
         *
         * # Panics
         *
         * Panics on Windows if the slice is larger than 4GB.
         */
        public fun new(buf: MutableList<UByte>): IoSliceMut = IoSliceMut(io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSliceMut.new(buf))

        /**
         * Advance a slice of slices.
         *
         * Shrinks the slice to remove any `IoSliceMut`s that are fully advanced over.
         * If the cursor ends up in the middle of an `IoSliceMut`, it is modified
         * to start at that cursor.
         *
         * For example, if we have a slice of two 8-byte `IoSliceMut`s, and we advance by 10 bytes,
         * the result will only include the second `IoSliceMut`, advanced by 2 bytes.
         *
         * # Panics
         *
         * Panics when trying to advance beyond the end of the slices.
         *
         * # Examples
         *
         * ```kotlin
         * import std.io.IoSliceMut
         *
         * var buf1 = MutableList(8) { 1 }
         * var buf2 = MutableList(16) { 2 }
         * var buf3 = MutableList(8) { 3 }
         * var bufs = listOf(IoSliceMut.new(buf1), IoSliceMut.new(buf2), IoSliceMut.new(buf3))
         * // Mark 10 bytes as read.
         * IoSliceMut.advanceSlices(bufs, 10)
         * check(bufs[0].deref() == List(14) { 2 }.asRef())
         * check(bufs[1].deref() == List(8) { 3 }.asRef())
         * ```
         */
        public fun advanceSlices(bufs: MutableList<IoSliceMut>, n: Int) {
            var bufs1 = bufs
            // Number of buffers to remove.
            var remove = 0
            // Remaining length before reaching n.
            var left = n
            for (buf in bufs1.iter()) {
                when (val matchVal = left.checkedSub(buf.len())) {
                    null -> {
                        break
                    }
                    else -> {
                        val remainder = matchVal
                        left = remainder
                        remove += 1
                    }
                }
            }
            bufs1 = take(bufs1)[remove until take(bufs1).size]
            if (bufs1.isEmpty()) {
                check(left == 0) { "advancing io slices beyond their length" }
            } else {
                bufs1[0].advance(left)
            }
        }
    }
}

/**
 * A buffer type used with `Write.writeVectored`.
 *
 * It is semantically a wrapper around a `[UByte]`, but is guaranteed to be
 * ABI compatible with the `iovec` type on Unix platforms and `WSABUF` on
 * Windows.
 */
public open class IoSlice(
    public var value: io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSlice,
) {
    public var field0: io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSlice
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): IoSlice = IoSlice(value)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append((this.value.asSlice()).toString()); Result.Ok<Unit, Unit>(Unit) }

    /**
     * Advance the internal cursor of the slice.
     *
     * Also see [IoSlice.advanceSlices] to advance the cursors of multiple
     * buffers.
     *
     * # Panics
     *
     * Panics when trying to advance beyond the end of the slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.IoSlice
     *
     * val data = List(8) { 1 }
     * var buf = IoSlice.new(data)
     * // Mark 3 bytes as read.
     * buf.advance(3)
     * check(buf.deref() == List(5) { 1 }.asRef())
     * ```
     */
    public open fun advance(n: Int) {
        this.value.advance(n)
    }

    /**
     * Get the underlying bytes as a slice with the original scope.
     *
     * This doesn't borrow from `self`, so is less restrictive than calling
     * `.deref()`, which does.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.IoSlice
     *
     * @feature(ioSliceAsBytes)
     * public fun doctestEntry() {
     *     val data = "abcdef".encodeToByteArray()
     *     var ioSlice = IoSlice.new(data)
     *     val tail = ioSlice.asSlice()[3 until ioSlice.asSlice().size]
     *     // This works because `tail` doesn't borrow `ioSlice`
     *     ioSlice = IoSlice.new(tail)
     *     check(ioSlice.asSlice() == "def".encodeToByteArray())
     * }
     * ```
     */
    public open fun asSlice(): List<UByte> = this.value.asSlice()

    public open fun deref(): List<UByte> = this.value.asSlice()

    public companion object {
        /**
         * Creates a new `IoSlice` wrapping a byte slice.
         *
         * # Panics
         *
         * Panics on Windows if the slice is larger than 4GB.
         */
        public fun new(buf: List<UByte>): IoSlice = IoSlice(io.github.kotlinmania.rustlang.core.io.ioslice.repr.IoSlice.new(buf))

        /**
         * Advance a slice of slices.
         *
         * Shrinks the slice to remove any `IoSlice`s that are fully advanced over.
         * If the cursor ends up in the middle of an `IoSlice`, it is modified
         * to start at that cursor.
         *
         * For example, if we have a slice of two 8-byte `IoSlice`s, and we advance by 10 bytes,
         * the result will only include the second `IoSlice`, advanced by 2 bytes.
         *
         * # Panics
         *
         * Panics when trying to advance beyond the end of the slices.
         *
         * # Examples
         *
         * ```kotlin
         * import std.io.IoSlice
         *
         * val buf1 = List(8) { 1 }
         * val buf2 = List(16) { 2 }
         * val buf3 = List(8) { 3 }
         * var bufs = listOf(IoSlice.new(buf1), IoSlice.new(buf2), IoSlice.new(buf3))
         * // Mark 10 bytes as written.
         * IoSlice.advanceSlices(bufs, 10)
         * check(bufs[0].deref() == List(14) { 2 }.asRef())
         * check(bufs[1].deref() == List(8) { 3 }.asRef())
         * ```
         */
        public fun advanceSlices(bufs: MutableList<IoSlice>, n: Int) {
            var bufs1 = bufs
            // Number of buffers to remove.
            var remove = 0
            // Remaining length before reaching n. This prevents overflow
            // that could happen if the length of slices in `bufs` were instead
            // accumulated. Those slice may be aliased and, if they are large
            // enough, their added length may overflow a `Int`.
            var left = n
            for (buf in bufs1.iter()) {
                when (val matchVal = left.checkedSub(buf.len())) {
                    null -> {
                        break
                    }
                    else -> {
                        val remainder = matchVal
                        left = remainder
                        remove += 1
                    }
                }
            }
            bufs1 = take(bufs1)[remove until take(bufs1).size]
            if (bufs1.isEmpty()) {
                check(left == 0) { "advancing io slices beyond their length" }
            } else {
                bufs1[0].advance(left)
            }
        }
    }
}
