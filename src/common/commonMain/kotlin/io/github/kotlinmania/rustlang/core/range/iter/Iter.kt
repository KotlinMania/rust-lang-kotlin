// port-lint: source range/iter.rs
package io.github.kotlinmania.rustlang.core.range.iter

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.iter.range.Step
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.range.Range
import io.github.kotlinmania.rustlang.core.range.RangeFrom
import io.github.kotlinmania.rustlang.core.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.result.Result

// Safety: This macro must only be used on types that are `Copy` and result in ranges
// which have an exact `sizeHint()` where the upper bound must not be `None`.

/**
 * By-value [Range] iterator.
 */
public open class RangeIter<A>(
    public var value: Range<A>,
) {
    public var field0: Range<A>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): RangeIter<A> = RangeIter(value)

    /**
     * Returns the remainder of the range being iterated over.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(newRangeRemainder)
     * public fun doctestEntry() {
     *     val range = Range.from(3 until 11)
     *     var iter = range.intoIter()
     *     check(iter.clone().remainder() == range)
     *     iter.next()
     *     check(iter.clone().remainder() == Range.from(4 until 11))
     *     iter.byRef().forEach(drop)
     *     check(iter.remainder().isEmpty())
     * }
     * ```
     */
    public open fun remainder(): io.github.kotlinmania.rustlang.core.range.Range<A> = Range(start = this.value.start, end = this.value.end)
}

public val MAY_HAVE_SIDE_EFFECT: Boolean = false

public val MAY_HAVE_SIDE_EFFECT1: Boolean = false

public val MAY_HAVE_SIDE_EFFECT2: Boolean = false

public val MAY_HAVE_SIDE_EFFECT3: Boolean = false

public val MAY_HAVE_SIDE_EFFECT4: Boolean = false

public val MAY_HAVE_SIDE_EFFECT5: Boolean = false

public val MAY_HAVE_SIDE_EFFECT6: Boolean = false

public val MAY_HAVE_SIDE_EFFECT7: Boolean = false

public val MAY_HAVE_SIDE_EFFECT8: Boolean = false

public val MAY_HAVE_SIDE_EFFECT9: Boolean = false

public fun <A : Step<A>> RangeIter<A>.next(): A? = this.value.next()

public fun <A : Step<A>> RangeIter<A>.sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

public fun <A : Step<A>> RangeIter<A>.count(): Int = this.value.count()

public fun <A : Step<A>> RangeIter<A>.nth(n: Int): A? = this.value.nth(n)

public fun <A : Step<A>> RangeIter<A>.last(): A? = this.value.last()

public fun <A : Step<A>> RangeIter<A>.min(): A? = this.value.min()

public fun <A : Step<A>> RangeIter<A>.max(): A? = this.value.max()

public fun <A : Step<A>> RangeIter<A>.isSorted(): Boolean = true

public fun <A : Step<A>> RangeIter<A>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

public fun <A : Step<A>> RangeIter<A>.iteratorGetUnchecked(idx: Int): A {
    // SAFETY: The TrustedRandomAccess contract requires that callers only pass an index
    // that is in bounds.
    // Additionally Self: TrustedRandomAccess is only implemented for Copy types
    // which means even repeated reads of the same index would be safe.
    return Step.forwardUnchecked(this.value.start.clone(), idx)
}

public fun <A : Step<A>> RangeIter<A>.nextBack(): A? = this.value.nextBack()

public fun <A : Step<A>> RangeIter<A>.nthBack(n: Int): A? = this.value.nthBack(n)

public fun <A : Step<A>> RangeIter<A>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

public fun <A : Step<A>> io.github.kotlinmania.rustlang.core.range.Range<A>.intoIter(): RangeIter<A> = RangeIter(this.into())

/**
 * By-value [RangeInclusive] iterator.
 */
public open class RangeInclusiveIter<A>(
    public var value: io.github.kotlinmania.rustlang.core.range.legacy.RangeInclusive<A>,
) {
    public var field0: io.github.kotlinmania.rustlang.core.range.legacy.RangeInclusive<A>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): RangeInclusiveIter<A> = RangeInclusiveIter(value)

    /**
     * Returns the remainder of the range being iterated over.
     *
     * If the iterator is exhausted or empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(newRangeRemainder)
     * public fun doctestEntry() {
     *     val range = RangeInclusive.from(3..11)
     *     var iter = range.intoIter()
     *     check(iter.clone().remainder().unwrap() == range)
     *     iter.next()
     *     check(iter.clone().remainder().unwrap() == RangeInclusive.from(4..11))
     *     iter.byRef().forEach(drop)
     *     check(iter.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): RangeInclusive<A>? {
        if (this.value.isEmpty()) {
            return null
        }
        return RangeInclusive(start = this.value.start, last = this.value.end)
    }
}

public fun <A : Step<A>> RangeInclusiveIter<A>.next(): A? = this.value.next()

public fun <A : Step<A>> RangeInclusiveIter<A>.sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

public fun <A : Step<A>> RangeInclusiveIter<A>.count(): Int = this.value.count()

public fun <A : Step<A>> RangeInclusiveIter<A>.nth(n: Int): A? = this.value.nth(n)

public fun <A : Step<A>> RangeInclusiveIter<A>.last(): A? = this.value.last()

public fun <A : Step<A>> RangeInclusiveIter<A>.min(): A? = this.value.min()

public fun <A : Step<A>> RangeInclusiveIter<A>.max(): A? = this.value.max()

public fun <A : Step<A>> RangeInclusiveIter<A>.isSorted(): Boolean = true

public fun <A : Step<A>> RangeInclusiveIter<A>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBy(n)

public fun <A : Step<A>> RangeInclusiveIter<A>.nextBack(): A? = this.value.nextBack()

public fun <A : Step<A>> RangeInclusiveIter<A>.nthBack(n: Int): A? = this.value.nthBack(n)

public fun <A : Step<A>> RangeInclusiveIter<A>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.value.advanceBackBy(n)

public fun <A : Step<A>> RangeInclusive<A>.intoIter(): RangeInclusiveIter<A> = RangeInclusiveIter(this.into())

/**
 * By-value [RangeFrom] iterator.
 */
public open class RangeFromIter<A>(
    public var start: A,
    /**
     * Whether the maximum value of the iterator has yielded.
     * Only used when overflow checks are enabled.
     */
    public var exhausted: Boolean,
) {
    public open fun clone(): RangeFromIter<A> = RangeFromIter(start, exhausted)

    /**
     * Returns the remainder of the range being iterated over.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(newRangeRemainder)
     * public fun doctestEntry() {
     *     val range = RangeFrom.from(3 until Int.MAX_VALUE)
     *     var iter = range.intoIter()
     *     check(iter.clone().remainder() == range)
     *     iter.next()
     *     check(iter.remainder() == RangeFrom.from(4 until Int.MAX_VALUE))
     * }
     * ```
     */
    public open fun remainder(): RangeFrom<A> {
        // Need to handle this case even if overflow-checks are disabled,
        // because a `RangeFromIter` could be exhausted in a crate with
        // overflow-checks enabled, but then passed to a crate with them
        // disabled before this is called.
        if (this.exhausted) {
            return RangeFrom(start = Step.forward(this.start, 1))
        }
        return RangeFrom(start = this.start)
    }

    public companion object {
        public fun <A> typeLayout(typeLayoutA: TypeLayout<A>): TypeLayout<RangeFromIter<A>> =
            object : TypeLayout<RangeFromIter<A>> {
                override val alignBytes: Long = listOf(1L, typeLayoutA.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutA.sizeBytes, 1L)
                override val sizeBytes: Long = alignUpBytes((off1 + 1L), alignBytes)
                override fun readNative(addr: Long): RangeFromIter<A> =
                    RangeFromIter(
                        (typeLayoutA).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: RangeFromIter<A>) {
                    (typeLayoutA).writeNative(addr, value.start)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off1, value.exhausted)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeFromIter<A> =
                    RangeFromIter(
                        (typeLayoutA).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeFromIter<A>) {
                    (typeLayoutA).writeBytes(buf, offset, value.start)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off1.toInt(), value.exhausted)
                }
            }
    }
}

public fun <A : Step<A>> RangeFromIter<A>.next(): A? {
    if (this.exhausted) {
        // This should panic if overflow checks are enabled, since
        // `forwardChecked` returned `None` in prior iteration.
        this.start = Step.forward(this.start.clone(), 1)
        // If we get here, if means this iterator was exhausted by a crate
        // with overflow-checks enabled, but now we're iterating in a crate with
        // overflow-checks disabled. Since we successfully incremented `self.start`
        // above (in many cases this will wrap around to MIN), we now unset
        // the flag so we don't repeat this process in the next iteration.
        //
        // This could also happen if `forwardChecked` returned None but
        // (for whatever reason, not applicable to any std implementors)
        // `forward` doesn't panic when overflow-checks are enabled. In that
        // case, this is also the correct behavior.
        this.exhausted = false
    }
    if (false) {
        val n = Step.forwardChecked(this.start.clone(), 1) ?: run {
            this.exhausted = true
            return this.start.clone()
        }
        return replace(this.start, n)
    }
    val n1 = Step.forward(this.start.clone(), 1)
    return replace(this.start, n1)
}

public fun <A : Step<A>> RangeFromIter<A>.sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)

public fun <A : Step<A>> RangeFromIter<A>.nth(n: Int): A? {
    // Typically `forward` will cause an overflow-check panic here,
    // but unset the exhausted flag to handle the uncommon cases.
    // See the comments in `next` for more details.
    if (this.exhausted) {
        this.start = Step.forward(this.start.clone(), 1)
        this.exhausted = false
    }
    if (false) {
        val plusN = Step.forward(this.start.clone(), n)
        when (val matchVal = Step.forwardChecked(plusN.clone(), 1)) {
            null -> {
                this.start = plusN.clone()
                this.exhausted = true
            }
            else -> {
                val plusN1 = matchVal
                this.start = plusN1
            }
        }
        return plusN
    }
    val plusN1 = Step.forward(this.start.clone(), n)
    this.start = Step.forward(plusN1.clone(), 1)
    return plusN1
}

public fun <A : Step<A>> RangeFrom<A>.intoIter(): RangeFromIter<A> = RangeFromIter(start = this.start, exhausted = false)
