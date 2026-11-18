// port-lint: source ops/index_range.rs
package io.github.kotlinmania.rustlang.core.ops.indexrange

import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.uncheckedAdd
import io.github.kotlinmania.rustlang.core.num.uncheckedSub
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * Like a `Range<Int>`, but with a safety invariant that `start <= end`.
 *
 * This means that `end - start` cannot overflow, allowing some μoptimizations.
 *
 * (Normal `Range` code needs to handle degenerate ranges like `10 .. 0`,
 *  which takes extra checks compared to only handling the canonical form.)
 */
public open class IndexRange(
    public var start: Int,
    public var end: Int,
) : TrustedLen, Iterable<Int> {
    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@IndexRange.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): IndexRange = IndexRange(start, end)

    public open fun start(): Int = this.start

    public open fun end(): Int = this.end

    public open fun len(): Int {
        // SAFETY: By invariant, this cannot wrap
        // Using the intrinsic because a UB check here impedes LLVM optimization. (#131563)
        return ((this.end) - (this.start))
    }

    /**
     * # Safety
     * - Can only be called when `start<end`, aka when `len> 0`.
     */
    public open fun nextUnchecked(): Int {
        if (false) {
            check(this.start < this.end)
        }
        val value = this.start
        // SAFETY: The range isn't empty, so this cannot overflow
        this.start = value.uncheckedAdd(1)
        return value
    }

    /**
     * # Safety
     * - Can only be called when `start<end`, aka when `len> 0`.
     */
    public open fun nextBackUnchecked(): Int {
        if (false) {
            check(this.start < this.end)
        }
        // SAFETY: The range isn't empty, so this cannot overflow
        val value = this.end.uncheckedSub(1)
        this.end = value
        return value
    }

    /**
     * Removes the first `n` items from this range, returning them as an `IndexRange`.
     * If there are fewer than `n`, then the whole range is returned and
     * `self` is left empty.
     *
     * This is designed to help implement `Iterator.advanceBy`.
     */
    public open fun takePrefix(n: Int): IndexRange {
        val mid =
            if (n <= this.len()) {
                // SAFETY: We just checked that this will be between start and end,
                // and thus the addition cannot overflow.
                // Using the intrinsic avoids a superfluous UB check.
                ((this.start) + (n))
            } else {
                this.end
            }
        val prefix = IndexRange(start = this.start, end = mid)
        this.start = mid
        return prefix
    }

    /**
     * Removes the last `n` items from this range, returning them as an `IndexRange`.
     * If there are fewer than `n`, then the whole range is returned and
     * `self` is left empty.
     *
     * This is designed to help implement `Iterator.advanceBackBy`.
     */
    public open fun takeSuffix(n: Int): IndexRange {
        val mid =
            if (n <= this.len()) {
                // SAFETY: We just checked that this will be between start and end,
                // and thus the subtraction cannot overflow.
                // Using the intrinsic avoids a superfluous UB check.
                ((this.end) - (n))
            } else {
                this.start
            }
        val suffix = IndexRange(start = mid, end = this.end)
        this.end = mid
        return suffix
    }

    public open fun assumeRange() {
        // SAFETY: This is the type invariant
        assertUnchecked(this.start <= this.end)
    }

    public open fun next(): Int? =
        if (this.len() > 0) {
            // SAFETY: We just checked that the range is non-empty
            this.nextUnchecked()
        } else {
            null
        }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.len()
        return Pair(len, len)
    }

    public open fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val taken = this.takePrefix(n)
        return ((NonZero.new<NonZeroUsizeInner, Int>(n - taken.len()))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun <B> fold(init: B, f: (B, Int) -> B): B = this.tryFold(init, NeverShortCircuit.wrapMut2(f)).first

    public open fun <Residual, B, R> tryFold(accum: B, f: (B, Int) -> R): R where R : Try<R, B, Residual> {
        var accum1 = accum
        // `Range` needs to check `start<end`, but thanks to our type invariant
        // we can loop on the stricter `start != end`.
        this.assumeRange()
        while (this.start != this.end) {
            // SAFETY: We just checked that the range is non-empty
            val i = this.nextUnchecked()
            accum1 = when (val tryResult = f(accum1, i)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        }
        return accum1
    }

    public open fun nextBack(): Int? =
        if (this.len() > 0) {
            // SAFETY: We just checked that the range is non-empty
            this.nextBackUnchecked()
        } else {
            null
        }

    public open fun advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        val taken = this.takeSuffix(n)
        return ((NonZero.new<NonZeroUsizeInner, Int>(n - taken.len()))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun <B> rfold(init: B, f: (B, Int) -> B): B = this.tryRfold(init, NeverShortCircuit.wrapMut2(f)).first

    public open fun <Residual, B, R> tryRfold(accum: B, f: (B, Int) -> R): R where R : Try<R, B, Residual> {
        var accum1 = accum
        // `Range` needs to check `start<end`, but thanks to our type invariant
        // we can loop on the stricter `start != end`.
        this.assumeRange()
        while (this.start != this.end) {
            // SAFETY: We just checked that the range is non-empty
            val i = this.nextBackUnchecked()
            accum1 = when (val tryResult = f(accum1, i)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        }
        return accum1
    }

    public open fun lenExactSizeIterator(): Int = this.len()

    // SAFETY: Because we only deal in `Int`, our `len` is always perfect.

    public companion object {
        public fun typeLayout(): TypeLayout<IndexRange> =
            object : TypeLayout<IndexRange> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): IndexRange =
                    IndexRange(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IndexRange) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.start)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.end)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IndexRange =
                    IndexRange(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IndexRange) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.start)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.end)
                }
            }

        /**
         * # Safety
         * - `start <= end`
         */
        public fun newUnchecked(start: Int, end: Int): IndexRange {
            Unit
            return IndexRange(start = start, end = end)
        }

        public fun zeroTo(end: Int): IndexRange = IndexRange(start = 0, end = end)
    }
}
