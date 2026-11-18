// port-lint: source iter/adapters/step_by.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.stepby

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.iter.sources.fromfn.fromFn
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.divCeil
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingMul
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.range.iter.RangeIter
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * An iterator for stepping iterators by a custom amount.
 *
 * This `class` is created by the [stepBy] method on [Iterator]. See
 * its documentation for more.
 *
 * [stepBy]: Iterator.stepBy
 * [Iterator]: trait.Iterator.html
 */
public open class StepBy<I>(
    /**
     * This field is guaranteed to be preprocessed by the specialized `SpecRangeSetup.setup`
     * in the constructor.
     * For most iterators that processing is a no-op, but for Range<(integer)> types it is lossy
     * which means the inner iterator cannot be returned to user code.
     * Additionally this type-dependent preprocessing means specialized implementations
     * cannot be used interchangeably.
     */
    public var iter: I,
    /**
     * This field is `step - 1`, aka the correct amount to pass to `nth` when iterating.
     * It MUST NOT be `Int.MAX`, as `unchecked` code depends on being able to add one
     * without the risk of overflow.  (This is important so that length calculations
     * don't need to check for division-by-zero, for example.)
     */
    public var stepMinusOne: Int,
    public var firstTake: Boolean,
) : StepByImpl<I, Item>, StepByBackImpl<I, Item>, Iterable<Item> {
    override fun iterator(): Iterator<Item> = object : AbstractIterator<Item>() {
        override fun computeNext() {
            when (val v = this@StepBy.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): StepBy<I> = StepBy(iter, stepMinusOne, firstTake)

    /**
     * The `step` that was originally passed to `Iterator.stepBy(step)`,
     * aka `self.stepMinusOne + 1`.
     */
    public open fun originalStep(): NonZero<Int, NonZeroUsizeInner> {
        // SAFETY: By type invariant, `stepMinusOne` cannot be `MAX`, which
        // means the addition cannot overflow and the result cannot be zero.
        return NonZero.newUnchecked<NonZeroUsizeInner, Int>(((this.stepMinusOne) + (1)))
    }

    public open fun next(): Item? = this.specNext()

    public open fun sizeHint(): Pair<Int, Int?> = this.specSizeHint()

    public open fun nth(n: Int): Item? = this.specNth(n)

    public open fun <Residual, Acc, R> tryFold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.specTryFold(acc, f)

    public open fun <Acc> fold(acc: Acc, f: (Acc, Item) -> Acc): Acc = this.specFold(acc, f)

    // The zero-based index starting from the end of the iterator of the
    // last element. Used in the `DoubleEndedIterator` implementation.
    public open fun nextBackIndex(): Int {
        val rem = this.iter.len() % this.originalStep()
        return if (this.firstTake) {
            if (rem == 0) {
                this.stepMinusOne
            } else {
                rem - 1
            }
        } else {
            rem
        }
    }

    public open fun nextBack(): Item? = this.specNextBack()

    public open fun nthBack(n: Int): Item? = this.specNthBack(n)

    public open fun <Residual, Acc, R> tryRfold(init: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> = this.specTryRfold(init, f)

    public open fun <Acc> rfold(init: Acc, f: (Acc, Item) -> Acc): Acc = this.specRfold(init, f)

    // StepBy can only make the iterator shorter, so the len will still fit.

    // StepBy stops yielding items once the underlying iterator does, so it is fused
    // whenever the underlying iterator is fused.

    public override fun specNext(): Item? {
        val stepSize =
            if (this.firstTake) {
                0
            } else {
                this.stepMinusOne
            }
        this.firstTake = false
        return this.iter.nth(stepSize)
    }

    public override fun specSizeHint(): Pair<Int, Int?> {
        fun firstSize(step: NonZero<Int, NonZeroUsizeInner>): (Int) -> Int =
            { n -> if (n == 0) {
                0
            } else {
                1 + (n - 1) / step
            } }
        fun otherSize(step: NonZero<Int, NonZeroUsizeInner>): (Int) -> Int = { n -> n / step }
        val (low, high) = this.iter.sizeHint()
        return if (this.firstTake) {
            val f = firstSize(this.originalStep())
            Pair(f(low), high.map(f))
        } else {
            val f1 = otherSize(this.originalStep())
            Pair(f1(low), high.map(f1))
        }
    }

    public override fun specNth(n: Int): Item? {
        var n1 = n
        if (this.firstTake) {
            this.firstTake = false
            val first = (this.iter.next() ?: return null)
            if (n1 == 0) {
                return first
            }
            n1 -= 1
        }
        // n and self.stepMinusOne are indices, we need to add 1 to get the amount of elements
        // When calling `.nth`, we need to subtract 1 again to convert back to an index
        var step = this.originalStep().get()
        // n + 1 could overflow
        // thus, if n is usize.Max, instead of adding one, we call .nth(step)
        if (n1 == Int.MAX_VALUE) {
            (this.iter.nth(step - 1) ?: return null)
        } else {
            n1 += 1
        }
        // overflow handling
        while (true) {
            val mul = n1.checkedMul(step.toInt())
            if (mul != null) {
                return this.iter.nth(mul!! - 1)
            }
            val divN = Int.MAX_VALUE / n1
            val divStep = Int.MAX_VALUE / step
            val nthN = divN * n1
            val nthStep = divStep * step
            val nth =
                if (nthN > nthStep) {
                    step -= divN
                    nthN
                } else {
                    n1 -= divStep
                    nthStep
                }
            (this.iter.nth(nth - 1) ?: return null)
        }
    }

    public override fun <Residual, Acc, R> specTryFold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        var acc1 = acc
        fun <Item, I : Iterator<Item>> nth(iter: I, stepMinusOne: Int): () -> Item? = { iter.nth(stepMinusOne) }
        if (this.firstTake) {
            this.firstTake = false
            when (val matchVal = this.iter.next()) {
                null -> { return acc1 }
                else -> {
                    val x = matchVal
                    acc1 = when (val tryResult = f(acc1, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                }
            }
        }
        return fromFn(nth(this.iter, this.stepMinusOne)).tryFold(acc1, f)
    }

    public override fun <Acc> specFold(acc: Acc, f: (Acc, Item) -> Acc): Acc {
        var acc1 = acc
        fun <Item, I : Iterator<Item>> nth(iter: I, stepMinusOne: Int): () -> Item? = { iter.nth(stepMinusOne) }
        if (this.firstTake) {
            this.firstTake = false
            when (val matchVal = this.iter.next()) {
                null -> { return acc1 }
                else -> {
                    val x = matchVal
                    acc1 = f(acc1, x)
                }
            }
        }
        return fromFn(nth(this.iter, this.stepMinusOne)).fold(acc1, f)
    }

    public override fun specNextBack(): Item? = this.iter.nthBack(this.nextBackIndex())

    public override fun specNthBack(n: Int): Item? {
        // `self.iter.nthBack(Int.MAX)` does the right thing here when `n`
        // is out of bounds because the length of `self.iter` does not exceed
        // `Int.MAX` (because `I: ExactSizeIterator`) and `nthBack` is
        // zero-indexed
        val n1 = n.saturatingMul(this.originalStep().get().toInt()).saturatingAdd(this.nextBackIndex().toInt())
        return this.iter.nthBack(n1)
    }

    public override fun <Residual, Acc, R> specTryRfold(init: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual> {
        fun <Item, I : Iterator<Item>> nthBack(iter: I, stepMinusOne: Int): () -> Item? = { iter.nthBack(stepMinusOne) }
        return when (val matchVal = this.nextBack()) {
            null -> init
            else -> {
                val x = matchVal
                val acc = when (val tryResult = f(init, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
                fromFn(nthBack(this.iter, this.stepMinusOne)).tryFold(acc, f)
            }
        }
    }

    public override fun <Acc> specRfold(init: Acc, f: (Acc, Item) -> Acc): Acc {
        fun <Item, I : Iterator<Item>> nthBack(iter: I, stepMinusOne: Int): () -> Item? = { iter.nthBack(stepMinusOne) }
        return when (val matchVal = this.nextBack()) {
            null -> init
            else -> {
                val x = matchVal
                val acc = f(init, x)
                fromFn(nthBack(this.iter, this.stepMinusOne)).fold(acc, f)
            }
        }
    }

    public companion object {
        public fun <I> typeLayout(typeLayoutI: TypeLayout<I>): TypeLayout<StepBy<I>> =
            object : TypeLayout<StepBy<I>> {
                override val alignBytes: Long = listOf(8L, typeLayoutI.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutI.sizeBytes, 8L)
                private val off2: Long = alignUpBytes((off1 + 8L), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 1L), alignBytes)
                override fun readNative(addr: Long): StepBy<I> =
                    StepBy(
                        (typeLayoutI).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off1),
                        (PrimitiveLayouts.BOOL).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: StepBy<I>) {
                    (typeLayoutI).writeNative(addr, value.iter)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off1, value.stepMinusOne)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off2, value.firstTake)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StepBy<I> =
                    StepBy(
                        (typeLayoutI).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StepBy<I>) {
                    (typeLayoutI).writeBytes(buf, offset, value.iter)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off1.toInt(), value.stepMinusOne)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off2.toInt(), value.firstTake)
                }
            }

        public fun <I> new(iter: I, step: Int, setupIFn: (I, Int) -> Any?): StepBy<I> {
            check(step != 0)
            val iter1 = setupIFn(iter, step)
            return StepBy(iter = iter1, stepMinusOne = step - 1, firstTake = true)
        }
    }
}

// SAFETY: This adapter is shortening. TrustedLen requires the upper bound to be calculated correctly.
// These requirements can only be satisfied when the upper bound of the inner iterator's upper
// bound is never `None`. I: TrustedRandomAccess happens to provide this guarantee while
// I: TrustedLen would not.
// This also covers the Range specializations since the ranges also implement TRA

public interface SpecRangeSetup<T> {
    public fun setup(inner: T, step: Int): T
}

public fun <T> specRangeSetupSetupT(inner: T, step: Int): T = inner

/**
 * Specialization trait to optimize `StepBy<Range<( integer )>>` iteration.
 *
 * # Safety
 *
 * Technically this is safe to implement (look ma, no unchecked!), but in reality
 * a lot of unchecked code relies on ranges over integers being correct.
 *
 * For correctness *all* public StepBy methods must be specialized
 * because `setup` drastically alters the meaning of the struct fields so that mixing
 * different implementations would lead to incorrect results.
 */
public interface StepByImpl<I, Item> {
    public fun specNext(): Item?

    public fun specSizeHint(): Pair<Int, Int?>

    public fun specNth(n: Int): Item?

    public fun <Residual, Acc, R> specTryFold(acc: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual>

    public fun <Acc> specFold(acc: Acc, f: (Acc, Item) -> Acc): Acc
}

/**
 * Specialization trait for double-ended iteration.
 *
 * See also: `StepByImpl`
 *
 * # Safety
 *
 * The specializations must be implemented together with `StepByImpl`
 * where applicable. I.e. if `StepBy` does support backwards iteration
 * for a given iterator and that is specialized for forward iteration then
 * it must also be specialized for backwards iteration.
 */
public interface StepByBackImpl<I, Item> {
    public fun <Item> specNextBack(): Item?

    public fun <Item> specNthBack(n: Int): Item?

    public fun <Item, Residual, Acc, R> specTryRfold(init: Acc, f: (Acc, Item) -> R): R where R : Try<R, Acc, Residual>

    public fun <Item, Acc> specRfold(init: Acc, f: (Acc, Item) -> Acc): Acc
}

/**
 * For these implementations, `SpecRangeSetup` calculates the number
 * of iterations that will be needed and stores that in `iter.end`.
 *
 * The various iterator implementations then rely on that to not need
 * overflow checking, letting loops just be counted instead.
 *
 * These only work for unsigned types, and will need to be reworked
 * if you want to use it to specialize on signed types.
 *
 * Currently these are only implemented for integers up to `Int` due to
 * correctness issues around `ExactSizeIterator` impls on 16bit platforms.
 * And since `ExactSizeIterator` is a prerequisite for backwards iteration
 * and we must consistently specialize backwards and forwards iteration
 * that makes the situation complicated enough that it's not covered
 * for now.
 *
 * After `SpecRangeSetup.setup`, both `Range<T>` and its new-range wrapper
 * `RangeIter<T>` carry the cursor and countdown in the same underlying legacy
 * `Range`. This accessor exposes that shared range so one specialization can
 * serve both: it is an identity for `Range<T>` and unwraps the newtype for
 * `RangeIter<T>`, so it compiles away.
 */
public interface AsLegacyRange<T> {
    public fun asLegacyRange(): Range<T>

    public fun asLegacyRangeMut(): Range<T>
}

public fun <T> Range<T>.asLegacyRange(): Range<T> = this

public fun <T> Range<T>.asLegacyRangeMut(): Range<T> = this

public fun <T> RangeIter<T>.asLegacyRange(): Range<T> = this.value

public fun <T> RangeIter<T>.asLegacyRangeMut(): Range<T> = this.value

public fun specRangeSetupSetupRangeUByte(r: Range<UByte>, step: Int): Range<UByte> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUByte()
    return r
}

public fun StepBy<Range<UByte>>.specNext(): UByte? {
    val step = UByte.tryFrom(this.originalStep().get()).unwrapOr(UByte.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<Range<UByte>>.specSizeHint(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<Range<UByte>>.specNth(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<Range<UByte>>.specTryFold(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UByte>>.specFold(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UByte.tryFrom(this.originalStep().get()).unwrapOr(UByte.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeUShort(r: Range<UShort>, step: Int): Range<UShort> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUShort()
    return r
}

public fun StepBy<Range<UShort>>.specNextRangeUShort(): UShort? {
    val step = UShort.tryFrom(this.originalStep().get()).unwrapOr(UShort.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<Range<UShort>>.specSizeHintRangeUShort(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<Range<UShort>>.specNthRangeUShort(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<Range<UShort>>.specTryFoldRangeUShort(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UShort>>.specFoldRangeUShort(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UShort.tryFrom(this.originalStep().get()).unwrapOr(UShort.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeUInt(r: Range<UInt>, step: Int): Range<UInt> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUInt()
    return r
}

public fun StepBy<Range<UInt>>.specNextRangeUInt(): UInt? {
    val step = UInt.tryFrom(this.originalStep().get()).unwrapOr(UInt.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<Range<UInt>>.specSizeHintRangeUInt(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<Range<UInt>>.specNthRangeUInt(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<Range<UInt>>.specTryFoldRangeUInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UInt>>.specFoldRangeUInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UInt.tryFrom(this.originalStep().get()).unwrapOr(UInt.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeULong(r: Range<ULong>, step: Int): Range<ULong> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toULong()
    return r
}

public fun StepBy<Range<ULong>>.specNextRangeULong(): ULong? {
    val step = ULong.tryFrom(this.originalStep().get()).unwrapOr(ULong.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<Range<ULong>>.specSizeHintRangeULong(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<Range<ULong>>.specNthRangeULong(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<Range<ULong>>.specTryFoldRangeULong(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<ULong>>.specFoldRangeULong(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = ULong.tryFrom(this.originalStep().get()).unwrapOr(ULong.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeInt(r: IntRange, step: Int): IntRange {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toInt()
    return r
}

public fun StepBy<IntRange>.specNextRangeInt(): Int? {
    val step = Int.tryFrom(this.originalStep().get()).unwrapOr(Int.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<IntRange>.specSizeHintRangeInt(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<IntRange>.specNthRangeInt(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<IntRange>.specTryFoldRangeInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<IntRange>.specFoldRangeInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = Int.tryFrom(this.originalStep().get()).unwrapOr(Int.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeIterUByte(r: RangeIter<UByte>, step: Int): RangeIter<UByte> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUByte()
    return r
}

public fun StepBy<RangeIter<UByte>>.specNextRangeIterUByte(): UByte? {
    val step = UByte.tryFrom(this.originalStep().get()).unwrapOr(UByte.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<RangeIter<UByte>>.specSizeHintRangeIterUByte(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<RangeIter<UByte>>.specNthRangeIterUByte(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<RangeIter<UByte>>.specTryFoldRangeIterUByte(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<UByte>>.specFoldRangeIterUByte(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UByte.tryFrom(this.originalStep().get()).unwrapOr(UByte.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeIterUShort(r: RangeIter<UShort>, step: Int): RangeIter<UShort> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUShort()
    return r
}

public fun StepBy<RangeIter<UShort>>.specNextRangeIterUShort(): UShort? {
    val step = UShort.tryFrom(this.originalStep().get()).unwrapOr(UShort.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<RangeIter<UShort>>.specSizeHintRangeIterUShort(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<RangeIter<UShort>>.specNthRangeIterUShort(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<RangeIter<UShort>>.specTryFoldRangeIterUShort(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<UShort>>.specFoldRangeIterUShort(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UShort.tryFrom(this.originalStep().get()).unwrapOr(UShort.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeIterUInt(r: RangeIter<UInt>, step: Int): RangeIter<UInt> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toUInt()
    return r
}

public fun StepBy<RangeIter<UInt>>.specNextRangeIterUInt(): UInt? {
    val step = UInt.tryFrom(this.originalStep().get()).unwrapOr(UInt.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<RangeIter<UInt>>.specSizeHintRangeIterUInt(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<RangeIter<UInt>>.specNthRangeIterUInt(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<RangeIter<UInt>>.specTryFoldRangeIterUInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<UInt>>.specFoldRangeIterUInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = UInt.tryFrom(this.originalStep().get()).unwrapOr(UInt.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeIterULong(r: RangeIter<ULong>, step: Int): RangeIter<ULong> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toULong()
    return r
}

public fun StepBy<RangeIter<ULong>>.specNextRangeIterULong(): ULong? {
    val step = ULong.tryFrom(this.originalStep().get()).unwrapOr(ULong.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<RangeIter<ULong>>.specSizeHintRangeIterULong(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<RangeIter<ULong>>.specNthRangeIterULong(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<RangeIter<ULong>>.specTryFoldRangeIterULong(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<ULong>>.specFoldRangeIterULong(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = ULong.tryFrom(this.originalStep().get()).unwrapOr(ULong.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun specRangeSetupSetupRangeIterInt(r: RangeIter<Int>, step: Int): RangeIter<Int> {
    val innerLen = r.sizeHint().first
    val yieldCount = innerLen.divCeil(step)
    r.asLegacyRangeMut().end = yieldCount.toInt()
    return r
}

public fun StepBy<RangeIter<Int>>.specNextRangeIterInt(): Int? {
    val step = Int.tryFrom(this.originalStep().get()).unwrapOr(Int.MAX_VALUE)
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val value = r.start
        r.start = value.wrappingAdd(step)
        r.end = remaining - 1
        value
    } else {
        null
    }
}

public fun StepBy<RangeIter<Int>>.specSizeHintRangeIterInt(): Pair<Int, Int?> {
    val remaining = (this.iter.asLegacyRange().end).toInt()
    return Pair(remaining, remaining)
}

public fun StepBy<RangeIter<Int>>.specNthRangeIterInt(n: Int): I.Item? {
    (this.advanceBy(n).ok() ?: return null)
    return this.next()
}

public fun <Residual, Acc, R> StepBy<RangeIter<Int>>.specTryFoldRangeIterInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<Int>>.specFoldRangeIterInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    val step = Int.tryFrom(this.originalStep().get()).unwrapOr(Int.MAX_VALUE)
    val r = this.iter.asLegacyRange()
    val remaining = r.end
    var acc = init
    var value = r.start
    for (unused in 0 until remaining) {
        acc = f(acc, value)
        value = value.wrappingAdd(step)
    }
    return acc
}

public fun StepBy<Range<UByte>>.specNextBack(): I.Item? {
    val step = this.originalStep().get().toUByte()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1u)
    } else {
        null
    }
}

public fun StepBy<Range<UByte>>.specNthBack(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<Range<UByte>>.specTryRfold(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UByte>>.specRfold(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<Range<UShort>>.specNextBackRangeUShort(): I.Item? {
    val step = this.originalStep().get().toUShort()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1u)
    } else {
        null
    }
}

public fun StepBy<Range<UShort>>.specNthBackRangeUShort(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<Range<UShort>>.specTryRfoldRangeUShort(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UShort>>.specRfoldRangeUShort(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<Range<UInt>>.specNextBackRangeUInt(): I.Item? {
    val step = this.originalStep().get().toUInt()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1u)
    } else {
        null
    }
}

public fun StepBy<Range<UInt>>.specNthBackRangeUInt(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<Range<UInt>>.specTryRfoldRangeUInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<Range<UInt>>.specRfoldRangeUInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<IntRange>.specNextBackRangeInt(): I.Item? {
    val step = this.originalStep().get().toInt()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1)
    } else {
        null
    }
}

public fun StepBy<IntRange>.specNthBackRangeInt(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<IntRange>.specTryRfoldRangeInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<IntRange>.specRfoldRangeInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<RangeIter<UByte>>.specNextBackRangeIterUByte(): I.Item? {
    val step = this.originalStep().get().toUByte()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1u)
    } else {
        null
    }
}

public fun StepBy<RangeIter<UByte>>.specNthBackRangeIterUByte(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<RangeIter<UByte>>.specTryRfoldRangeIterUByte(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<UByte>>.specRfoldRangeIterUByte(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<RangeIter<UShort>>.specNextBackRangeIterUShort(): I.Item? {
    val step = this.originalStep().get().toUShort()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1u)
    } else {
        null
    }
}

public fun StepBy<RangeIter<UShort>>.specNthBackRangeIterUShort(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<RangeIter<UShort>>.specTryRfoldRangeIterUShort(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<UShort>>.specRfoldRangeIterUShort(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun StepBy<RangeIter<Int>>.specNextBackRangeIterInt(): I.Item? {
    val step = this.originalStep().get().toInt()
    val r = this.iter.asLegacyRangeMut()
    val remaining = r.end
    return if (remaining > 0) {
        val start = r.start
        r.end = remaining - 1
        start + step * (remaining - 1)
    } else {
        null
    }
}

public fun StepBy<RangeIter<Int>>.specNthBackRangeIterInt(n: Int): I.Item? {
    if (this.advanceBackBy(n).isErr()) {
        return null
    }
    return this.nextBack()
}

public fun <Residual, Acc, R> StepBy<RangeIter<Int>>.specTryRfoldRangeIterInt(init: Acc, f: (Acc, I.Item) -> R): R where R : Try<R, Acc, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Acc> StepBy<RangeIter<Int>>.specRfoldRangeIterInt(init: Acc, f: (Acc, I.Item) -> Acc): Acc {
    var accum = init
    while (true) {
        val matchVal = this.nextBack()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}
