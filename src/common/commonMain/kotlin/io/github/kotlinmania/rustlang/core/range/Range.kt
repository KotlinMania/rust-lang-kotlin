// port-lint: source range.rs
package io.github.kotlinmania.rustlang.core.range

import io.github.kotlinmania.rustlang.core.cmp.compareNaturalOrder
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.range.iter.RangeFromIter
import io.github.kotlinmania.rustlang.core.range.iter.RangeInclusiveIter
import io.github.kotlinmania.rustlang.core.range.iter.RangeIter
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.ops.Bound.Excluded
import io.github.kotlinmania.rustlang.ops.Bound.Included
import io.github.kotlinmania.rustlang.ops.Bound.Unbounded
import io.github.kotlinmania.rustlang.ops.range.Bound
import io.github.kotlinmania.rustlang.ops.range.OneSidedRangeBound

// # Replacement range types
//
// The types within this module are meant to replace the legacy `Range`,
// `RangeInclusive`, `RangeToInclusive` and `RangeFrom` types in a future edition.
//
// ```kotlin
// import core.range.Range
// import core.range.RangeFrom
// import core.range.RangeInclusive
// import core.range.RangeToInclusive
//
// val arr = listOf(0, 1, 2, 3, 4)
// check(arr == listOf(0, 1, 2, 3, 4))
// check(arr[0 until 3] == listOf(0, 1, 2))
// check(arr[RangeToInclusive.from(0..3)] == listOf(0, 1, 2, 3))
// check(arr[RangeFrom.from(1 until Int.MAX_VALUE)] == listOf(1, 2, 3, 4))
// check(arr[Range.from(1 until 3)] == listOf(1, 2))
// check(arr[RangeInclusive.from(1..3)] == listOf(1, 2, 3))
// ```

/**
 * A (half-open) range bounded inclusively below and exclusively above.
 *
 * The `Range` contains all values with `start <= x<end`.
 * It is empty if `start >= end`.
 *
 * Note that this type is not suited to represent all possible ranges. For example, `Range<UByte>`
 * cannot represent the range that covers all of `UByte`. Use [`(Bound<T>, Bound<T>)`][Bound] if you
 * need a type that can store an arbitrary range.
 *
 * # Examples
 *
 * ```kotlin
 * import core.range.Range
 *
 * check(Range.from(3 until 5) == Range(start = 3, end = 5))
 * check(3 + 4 + 5 == Range.from(3 until 6).intoIter().sum())
 * ```
 *
 * # Edition notes
 *
 * It is planned that the syntax `start .. end` will construct this
 * type in a future edition, but it does not do so today.
 */
public open class Range<Idx>(
    /**
     * The lower bound of the range (inclusive).
     */
    public var start: Idx,
    /**
     * The upper bound of the range (exclusive).
     */
    public var end: Idx,
) {
    public open fun clone(): Range<Idx> = Range(start, end)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = fmt.writeFmt("..")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.end.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    /**
     * Creates an iterator over the elements within this range.
     *
     * Shorthand for `.clone().intoIter()`
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.Range
     *
     * var i = Range.from(3 until 9).iter().map({ n -> n * n })
     * check(i.next() == 9)
     * check(i.next() == 16)
     * check(i.next() == 25)
     * ```
     */
    public open fun iter(): RangeIter<Idx> = this.clone().intoIter()

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.Range
     *
     * check(!Range.from(3 until 5).contains(2))
     * check(Range.from(3 until 5).contains(3))
     * check(Range.from(3 until 5).contains(4))
     * check(!Range.from(3 until 5).contains(5))
     * check(!Range.from(3 until 3).contains(3))
     * check(!Range.from(3 until 2).contains(3))
     * check(Range.from(0.0 until 1.0).contains(0.5))
     * check(!Range.from(0.0 until 1.0).contains(Float.NaN))
     * check(!Range.from(0.0 until Float.NaN).contains(0.5))
     * check(!Range.from(Float.NaN until 1.0).contains(0.5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = Range.contains(this, item)

    /**
     * Returns `true` if the range contains no items.
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.Range
     *
     * check(!Range.from(3 until 5).isEmpty())
     * check(Range.from(3 until 3).isEmpty())
     * check(Range.from(3 until 2).isEmpty())
     * ```
     *
     * The range is empty if either side is incomparable:
     *
     * ```kotlin
     * import core.range.Range
     *
     * check(!Range.from(3.0 until 5.0).isEmpty())
     * check(Range.from(3.0 until Float.NaN).isEmpty())
     * check(Range.from(Float.NaN until 5.0).isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = !((compareNaturalOrder(this.start, this.end) < 0))

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<Range<Idx>> =
            object : TypeLayout<Range<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutIdx.sizeBytes, typeLayoutIdx.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutIdx.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Range<Idx> =
                    Range(
                        (typeLayoutIdx).readNative(addr),
                        (typeLayoutIdx).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Range<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.start)
                    (typeLayoutIdx).writeNative(addr + off1, value.end)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Range<Idx> =
                    Range(
                        (typeLayoutIdx).readBytes(buf, offset),
                        (typeLayoutIdx).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Range<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.start)
                    (typeLayoutIdx).writeBytes(buf, offset + off1.toInt(), value.end)
                }
            }
    }
}

public fun <T> Range<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> Range<T>.endBound(): Bound<T> = Excluded(this.end)

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `start .. end` with `(Bound.Included(start), Bound.Excluded(end))`.
 */
public fun <T> Range<T>.startBoundT(): Bound<T> = Included(this.start)

public fun <T> Range<T>.endBoundT(): Bound<T> = Excluded(this.end)

public fun <T> Range<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Included(this.start), Excluded(this.end))

public fun <T> fromRangeT(value: Range<T>): io.github.kotlinmania.rustlang.range.Range<T> = io.github.kotlinmania.rustlang.range.Range(start = value.start, end = value.end)

public fun <T> fromRangeT(value: io.github.kotlinmania.rustlang.range.Range<T>): Range<T> = Range(start = value.start, end = value.end)

/**
 * A range bounded inclusively below and above.
 *
 * The `RangeInclusive` contains all values with `x >= start`
 * and `x <= last`. It is empty unless `start <= last`.
 *
 * # Examples
 *
 * ```kotlin
 * import core.range.RangeInclusive
 *
 * check(RangeInclusive.from(3..5) == RangeInclusive(start = 3, last = 5))
 * check(3 + 4 + 5 == RangeInclusive.from(3..5).intoIter().sum())
 * ```
 *
 * # Edition notes
 *
 * It is planned that the syntax  `start ..= last` will construct this
 * type in a future edition, but it does not do so today.
 */
public open class RangeInclusive<Idx>(
    /**
     * The lower bound of the range (inclusive).
     */
    public var start: Idx,
    /**
     * The upper bound of the range (inclusive).
     */
    public var last: Idx,
) {
    public open fun clone(): RangeInclusive<Idx> = RangeInclusive(start, last)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = fmt.writeFmt("..=")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.last.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.RangeInclusive
     *
     * check(!RangeInclusive.from(3..5).contains(2))
     * check(RangeInclusive.from(3..5).contains(3))
     * check(RangeInclusive.from(3..5).contains(4))
     * check(RangeInclusive.from(3..5).contains(5))
     * check(!RangeInclusive.from(3..5).contains(6))
     * check(RangeInclusive.from(3..3).contains(3))
     * check(!RangeInclusive.from(3..2).contains(3))
     * check(RangeInclusive.from(0.0..1.0).contains(1.0))
     * check(!RangeInclusive.from(0.0..1.0).contains(Float.NaN))
     * check(!RangeInclusive.from(0.0..Float.NaN).contains(0.0))
     * check(!RangeInclusive.from(Float.NaN..1.0).contains(1.0))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = RangeInclusive.contains(this, item)

    /**
     * Returns `true` if the range contains no items.
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.RangeInclusive
     *
     * check(!RangeInclusive.from(3..5).isEmpty())
     * check(!RangeInclusive.from(3..3).isEmpty())
     * check(RangeInclusive.from(3..2).isEmpty())
     * ```
     *
     * The range is empty if either side is incomparable:
     *
     * ```kotlin
     * import core.range.RangeInclusive
     *
     * check(!RangeInclusive.from(3.0..5.0).isEmpty())
     * check(RangeInclusive.from(3.0..Float.NaN).isEmpty())
     * check(RangeInclusive.from(Float.NaN..5.0).isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = !((compareNaturalOrder(this.start, this.last) <= 0))

    /**
     * Creates an iterator over the elements within this range.
     *
     * Shorthand for `.clone().intoIter()`
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.RangeInclusive
     *
     * var i = RangeInclusive.from(3..8).iter().map({ n -> n * n })
     * check(i.next() == 9)
     * check(i.next() == 16)
     * check(i.next() == 25)
     * ```
     */
    public open fun iter(): RangeInclusiveIter<Idx> = this.clone().intoIter()

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<RangeInclusive<Idx>> =
            object : TypeLayout<RangeInclusive<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutIdx.sizeBytes, typeLayoutIdx.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off1 + typeLayoutIdx.sizeBytes), alignBytes)
                override fun readNative(addr: Long): RangeInclusive<Idx> =
                    RangeInclusive(
                        (typeLayoutIdx).readNative(addr),
                        (typeLayoutIdx).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: RangeInclusive<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.start)
                    (typeLayoutIdx).writeNative(addr + off1, value.last)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeInclusive<Idx> =
                    RangeInclusive(
                        (typeLayoutIdx).readBytes(buf, offset),
                        (typeLayoutIdx).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeInclusive<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.start)
                    (typeLayoutIdx).writeBytes(buf, offset + off1.toInt(), value.last)
                }
            }
    }
}

public fun <T> RangeInclusive<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> RangeInclusive<T>.endBound(): Bound<T> = Included(this.last)

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `start ..= end` with `(Bound.Included(start), Bound.Included(end))`.
 */
public fun <T> RangeInclusive<T>.startBoundT(): Bound<T> = Included(this.start)

public fun <T> RangeInclusive<T>.endBoundT(): Bound<T> = Included(this.last)

// @stable(feature = "newRangeInclusiveApi", since = "1.95.0")
public fun <T> RangeInclusive<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Included(this.start), Included(this.last))

public fun <T> fromRangeInclusiveT(value: RangeInclusive<T>): io.github.kotlinmania.rustlang.range.RangeInclusive<T> = io.github.kotlinmania.rustlang.range.RangeInclusive.new(value.start, value.last)

/**
 * Converts from a legacy range to a non-legacy range, potentially panicking.
 *
 * # Panics
 *
 * If the legacy range iterator has been exhausted,
 * this function will either panic or return an empty range.
 *
 * # Examples
 *
 * ```kotlin
 * import core.range.RangeInclusive
 * import core.range.legacy
 *
 * val single: legacy.RangeInclusive<Int> = 0..1
 * val single1 = RangeInclusive.from(single)
 * check(Pair(single1.start, single1.last) == Pair(0, 1))
 * val empty: legacy.RangeInclusive<Int> = 0..0
 * val empty1 = RangeInclusive.from(empty)
 * check(Pair(empty1.start, empty1.last) == Pair(0, 0))
 * ```
 *
 * ```kotlin
 * import core.range.RangeInclusive
 * import core.range.legacy
 * import std.panic.catchUnwind
 *
 * var exhausted: legacy.RangeInclusive<Int> = 0..0
 * exhausted.next()
 * val result = catchUnwind({ RangeInclusive.from(exhausted) })
 * // The `from` call either panicked or returned an empty range.
 * check(result.isErr() || result.isOkAnd({ range -> range.isEmpty() }))
 * ```
 */
public fun <T> fromRangeInclusiveT(value: io.github.kotlinmania.rustlang.range.RangeInclusive<T>): RangeInclusive<T> {
    check(!value.exhausted) { "attempted to convert from an exhausted `legacy::RangeInclusive`" }
    val (start, last) = value.intoInner()
    return RangeInclusive(start = start, last = last)
}

/**
 * A range only bounded inclusively below.
 *
 * The `RangeFrom` contains all values with `x >= start`.
 *
 * *Note*: Overflow in the [IntoIterator] implementation (when the contained
 * data type reaches its numerical limit) is allowed to panic, wrap, or
 * saturate. This behavior is defined by the implementation of the [Step]
 * trait. For primitive integers, this follows the normal rules, and respects
 * the overflow checks profile (panic in debug, wrap in release). Unlike
 * its legacy counterpart, the iterator will only panic after yielding the
 * maximum value when overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * import core.range.RangeFrom
 *
 * check(RangeFrom.from(2 until Int.MAX_VALUE) == RangeFrom(start = 2))
 * check(2 + 3 + 4 == RangeFrom.from(2 until Int.MAX_VALUE).intoIter().take(3).sum())
 * ```
 *
 * # Edition notes
 *
 * It is planned that the syntax  `start ..` will construct this
 * type in a future edition, but it does not do so today.
 */
public open class RangeFrom<Idx>(
    /**
     * The lower bound of the range (inclusive).
     */
    public var start: Idx,
) {
    public open fun clone(): RangeFrom<Idx> = RangeFrom(start)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = fmt.writeFmt("..")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    /**
     * Creates an iterator over the elements within this range.
     *
     * Shorthand for `.clone().intoIter()`
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.RangeFrom
     *
     * var i = RangeFrom.from(3 until Int.MAX_VALUE).iter().map({ n -> n * n })
     * check(i.next() == 9)
     * check(i.next() == 16)
     * check(i.next() == 25)
     * ```
     */
    public open fun iter(): RangeFromIter<Idx> = this.clone().intoIter()

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * import core.range.RangeFrom
     *
     * check(!RangeFrom.from(3 until Int.MAX_VALUE).contains(2))
     * check(RangeFrom.from(3 until Int.MAX_VALUE).contains(3))
     * check(RangeFrom.from(3 until Int.MAX_VALUE).contains(1000000000))
     * check(RangeFrom.from(0.0 until Int.MAX_VALUE).contains(0.5))
     * check(!RangeFrom.from(0.0 until Int.MAX_VALUE).contains(Float.NaN))
     * check(!RangeFrom.from(Float.NaN until Int.MAX_VALUE).contains(0.5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = RangeFrom.contains(this, item)

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<RangeFrom<Idx>> =
            object : TypeLayout<RangeFrom<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutIdx.sizeBytes, alignBytes)
                override fun readNative(addr: Long): RangeFrom<Idx> =
                    RangeFrom(
                        (typeLayoutIdx).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RangeFrom<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.start)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeFrom<Idx> =
                    RangeFrom(
                        (typeLayoutIdx).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeFrom<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.start)
                }
            }
    }
}

public fun <T> RangeFrom<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> RangeFrom<T>.endBound(): Bound<T> = Unbounded

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `start ..` with `(Bound.Included(start), Bound.Unbounded)`.
 */
public fun <T> RangeFrom<T>.startBoundT(): Bound<T> = Included(this.start)

public fun <T> RangeFrom<T>.endBoundT(): Bound<T> = Unbounded

public fun <T> RangeFrom<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Included(this.start), Unbounded)

public fun <T> RangeFrom<T>.bound(): Pair<OneSidedRangeBound, T> = Pair(OneSidedRangeBound.StartInclusive, this.start)

public fun <T> fromRangeFromT(value: RangeFrom<T>): io.github.kotlinmania.rustlang.range.RangeFrom<T> = io.github.kotlinmania.rustlang.range.RangeFrom(start = value.start)

public fun <T> fromRangeFromT(value: io.github.kotlinmania.rustlang.range.RangeFrom<T>): RangeFrom<T> = RangeFrom(start = value.start)

/**
 * A range only bounded inclusively above.
 *
 * The `RangeToInclusive` contains all values with `x <= last`.
 * It cannot serve as an [Iterator] because it doesn't have a starting point.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(newRange)
 * public fun doctestEntry() {
 *     check((0..5) == RangeToInclusive(last = 5))
 * }
 * ```
 *
 * It does not have an [IntoIterator] implementation, so you can't use it in a
 * `for` loop directly. This won't compile:
 *
 * ```compile_fail,E0277
 * // error[E0277]: the trait bound `std.range.RangeToInclusive<{integer}>:
 * // std.iter.Iterator` is not satisfied
 * for i in ..=5 {
 *     // ...
 * }
 * ```
 *
 * When used as a [slicing index], `RangeToInclusive` produces a slice of all
 * array elements up to and including the index indicated by `last`.
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * // This is a `RangeToInclusive`
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * check(arr[1 until 3] == listOf(1, 2))
 * check(arr[1..3] == listOf(1, 2, 3))
 * ```
 *
 * # Edition notes
 *
 * It is planned that the syntax  `..= last` will construct this
 * type in a future edition, but it does not do so today.
 */
public open class RangeToInclusive<Idx>(
    /**
     * The upper bound of the range (inclusive)
     */
    public var last: Idx,
) {
    public open fun clone(): RangeToInclusive<Idx> = RangeToInclusive(last)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = fmt.writeFmt("..=")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = this.last.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check((0..5).contains(-1000000000))
     * check((0..5).contains(5))
     * check(!(0..5).contains(6))
     * check((0..1.0).contains(1.0))
     * check(!(0..1.0).contains(Float.NaN))
     * check(!(0..Float.NaN).contains(0.5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = RangeToInclusive.contains(this, item)

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<RangeToInclusive<Idx>> =
            object : TypeLayout<RangeToInclusive<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutIdx.sizeBytes, alignBytes)
                override fun readNative(addr: Long): RangeToInclusive<Idx> =
                    RangeToInclusive(
                        (typeLayoutIdx).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RangeToInclusive<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.last)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeToInclusive<Idx> =
                    RangeToInclusive(
                        (typeLayoutIdx).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeToInclusive<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.last)
                }
            }
    }
}

public fun <T> fromRangeToInclusiveT(value: io.github.kotlinmania.rustlang.range.RangeToInclusive<T>): RangeToInclusive<T> = RangeToInclusive(last = value.end)

public fun <T> fromRangeToInclusiveT(value: RangeToInclusive<T>): io.github.kotlinmania.rustlang.range.RangeToInclusive<T> = io.github.kotlinmania.rustlang.range.RangeToInclusive(end = value.last)

// RangeToInclusive<Idx> cannot impl From<RangeTo<Idx>>
// because underflow would be possible with (..0).into()
public fun <T> RangeToInclusive<T>.startBound(): Bound<T> = Unbounded

public fun <T> RangeToInclusive<T>.endBound(): Bound<T> = Included(this.last)

public fun <T> RangeToInclusive<T>.startBoundT(): Bound<T> = Unbounded

public fun <T> RangeToInclusive<T>.endBoundT(): Bound<T> = Included(this.last)

public fun <T> RangeToInclusive<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Unbounded, Included(this.last))

public fun <T> RangeToInclusive<T>.bound(): Pair<OneSidedRangeBound, T> = Pair(OneSidedRangeBound.EndInclusive, this.last)
