// port-lint: source ops/range.rs
package io.github.kotlinmania.rustlang.core.ops.range

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.ops.range.Bound.Excluded
import io.github.kotlinmania.rustlang.core.ops.range.Bound.Included
import io.github.kotlinmania.rustlang.core.ops.range.Bound.Unbounded
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * An unbounded range (`..`).
 *
 * `RangeFull` is primarily used as a [slicing index], its shorthand is `..`.
 * It cannot serve as an [Iterator] because it doesn't have a starting point.
 *
 * # Examples
 *
 * The `..` syntax is a `RangeFull`:
 *
 * ```kotlin
 * check(0 until 0 == RangeFull)
 * ```
 *
 * It does not have an [IntoIterator] implementation, so you can't use it in
 * a `for` loop directly. This won't compile:
 *
 * ```compile_fail,E0277
 * for i in .. {
 *     // ...
 * }
 * ```
 *
 * Used as a [slicing index], `RangeFull` produces the full array as a slice.
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * // This is the `RangeFull`
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * check(arr[1 until 3] == listOf(1, 2))
 * check(arr[1..3] == listOf(1, 2, 3))
 * ```
 */
public open class RangeFull {
    public open fun clone(): RangeFull = RangeFull()

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> = run { fmt.append(".."); Result.Ok<Unit, Unit>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<RangeFull> =
            object : TypeLayout<RangeFull> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): RangeFull =
                    RangeFull()
                override fun writeNative(addr: Long, value: RangeFull) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeFull =
                    RangeFull()
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeFull) {
                }
            }
    }
}

// not Copy -- see #27186

/**
 * A (half-open) range bounded inclusively below and exclusively above
 * (`start .. end`).
 *
 * The range `start .. end` contains all values with `start <= x<end`.
 * It is empty if `start >= end`.
 *
 * Note that this type is not suited to represent all possible ranges. For example, `Range<UByte>`
 * cannot represent the range that covers all of `UByte`. Use [`(Bound<T>, Bound<T>)`][Bound] if you
 * need a type that can store an arbitrary range.
 *
 * # Examples
 *
 * The `start .. end` syntax is a `Range`:
 *
 * ```kotlin
 * check((3 until 5) == Range(start = 3, end = 5))
 * check(3 + 4 + 5 == (3 until 6).sum())
 * ```
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * check(arr[1 until 3] == listOf(1, 2))
 * // This is a `Range`
 * check(arr[1..3] == listOf(1, 2, 3))
 * ```
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
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        fmt.append("..")
        when (val tryResult = this.end.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check(!(3 until 5).contains(2))
     * check((3 until 5).contains(3))
     * check((3 until 5).contains(4))
     * check(!(3 until 5).contains(5))
     * check(!(3 until 3).contains(3))
     * check(!(3 until 2).contains(3))
     * check((0.0 until 1.0).contains(0.5))
     * check(!(0.0 until 1.0).contains(Float.NaN))
     * check(!(0.0 until Float.NaN).contains(0.5))
     * check(!(Float.NaN until 1.0).contains(0.5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = Range.contains(this, item)

    /**
     * Returns `true` if the range contains no items.
     *
     * # Examples
     *
     * ```kotlin
     * check(!(3 until 5).isEmpty())
     * check((3 until 3).isEmpty())
     * check((3 until 2).isEmpty())
     * ```
     *
     * The range is empty if either side is incomparable:
     *
     * ```kotlin
     * check(!(3.0 until 5.0).isEmpty())
     * check((3.0 until Float.NaN).isEmpty())
     * check((Float.NaN until 5.0).isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = !((kmCompareValues(this.start, this.end) < 0))

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

// not Copy -- see #27186

/**
 * A range only bounded inclusively below (`start ..`).
 *
 * The `RangeFrom` `start ..` contains all values with `x >= start`.
 *
 * *Note*: Overflow in the [Iterator] implementation (when the contained
 * data type reaches its numerical limit) is allowed to panic, wrap, or
 * saturate. This behavior is defined by the implementation of the [Step]
 * trait. For primitive integers, this follows the normal rules, and respects
 * the overflow checks profile (panic in debug, wrap in release). Note also
 * that overflow happens earlier than you might assume: the overflow happens
 * in the call to `next` that yields the maximum value, as the range must be
 * set to a state to yield the next value.
 *
 * # Examples
 *
 * The `start ..` syntax is a `RangeFrom`:
 *
 * ```kotlin
 * check((2 until Int.MAX_VALUE) == RangeFrom(start = 2))
 * check(2 + 3 + 4 == (2 until Int.MAX_VALUE).take(3).sum())
 * ```
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * // This is a `RangeFrom`
 * check(arr[1 until 3] == listOf(1, 2))
 * check(arr[1..3] == listOf(1, 2, 3))
 * ```
 */
public open class RangeFrom<Idx>(
    /**
     * The lower bound of the range (inclusive).
     */
    public var start: Idx,
) {
    public open fun clone(): RangeFrom<Idx> = RangeFrom(start)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        fmt.append("..")
        return Result.Ok<Unit, Unit>(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check(!(3 until Int.MAX_VALUE).contains(2))
     * check((3 until Int.MAX_VALUE).contains(3))
     * check((3 until Int.MAX_VALUE).contains(1000000000))
     * check((0.0 until Int.MAX_VALUE).contains(0.5))
     * check(!(0.0 until Int.MAX_VALUE).contains(Float.NaN))
     * check(!(Float.NaN until Int.MAX_VALUE).contains(0.5))
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

/**
 * A range only bounded exclusively above (`.. end`).
 *
 * The `RangeTo` `.. end` contains all values with `x<end`.
 * It cannot serve as an [Iterator] because it doesn't have a starting point.
 *
 * # Examples
 *
 * The `.. end` syntax is a `RangeTo`:
 *
 * ```kotlin
 * check((0 until 5) == RangeTo(end = 5))
 * ```
 *
 * It does not have an [IntoIterator] implementation, so you can't use it in
 * a `for` loop directly. This won't compile:
 *
 * ```compile_fail,E0277
 * // error[E0277]: the trait bound `std.ops.RangeTo<{integer}>:
 * // std.iter.Iterator` is not satisfied
 * for i in ..5 {
 *     // ...
 * }
 * ```
 *
 * When used as a [slicing index], `RangeTo` produces a slice of all array
 * elements before the index indicated by `end`.
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * // This is a `RangeTo`
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * check(arr[1 until 3] == listOf(1, 2))
 * check(arr[1..3] == listOf(1, 2, 3))
 * ```
 */
public open class RangeTo<Idx>(
    /**
     * The upper bound of the range (exclusive).
     */
    public var end: Idx,
) {
    public open fun clone(): RangeTo<Idx> = RangeTo(end)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        fmt.append("..")
        when (val tryResult = this.end.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check((0 until 5).contains(-1000000000))
     * check((0 until 5).contains(4))
     * check(!(0 until 5).contains(5))
     * check((0 until 1.0).contains(0.5))
     * check(!(0 until 1.0).contains(Float.NaN))
     * check(!(0 until Float.NaN).contains(0.5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = RangeTo.contains(this, item)

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<RangeTo<Idx>> =
            object : TypeLayout<RangeTo<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutIdx.sizeBytes, alignBytes)
                override fun readNative(addr: Long): RangeTo<Idx> =
                    RangeTo(
                        (typeLayoutIdx).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RangeTo<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.end)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeTo<Idx> =
                    RangeTo(
                        (typeLayoutIdx).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeTo<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.end)
                }
            }
    }
}

// not Copy -- see #27186

/**
 * A range bounded inclusively below and above (`start ..= end`).
 *
 * The `RangeInclusive` `start ..= end` contains all values with `x >= start`
 * and `x <= end`. It is empty unless `start <= end`.
 *
 * This iterator is [fused], but the specific values of `start` and `end` after
 * iteration has finished are **unspecified** other than that [isEmpty]
 * will return `true` once no more values will be produced.
 *
 * [isEmpty]: RangeInclusive.isEmpty
 *
 * # Examples
 *
 * The `start ..= end` syntax is a `RangeInclusive`:
 *
 * ```kotlin
 * check((3..5) == RangeInclusive.new(3, 5))
 * check(3 + 4 + 5 == (3..5).sum())
 * ```
 *
 * ```kotlin
 * val arr = listOf(0, 1, 2, 3, 4)
 * check(arr == listOf(0, 1, 2, 3, 4))
 * check(arr[0 until 3] == listOf(0, 1, 2))
 * check(arr[0..3] == listOf(0, 1, 2, 3))
 * check(arr[1 until arr.size] == listOf(1, 2, 3, 4))
 * check(arr[1 until 3] == listOf(1, 2))
 * check(arr[1..3] == listOf(1, 2, 3))
 * // This is a `RangeInclusive`
 * ```
 */
public open class RangeInclusive<Idx>(
    // Note that the fields here are not public to allow changing the
    // representation in the future; in particular, while we could plausibly
    // expose start/end, modifying them without changing (future/current)
    // private fields may lead to incorrect behavior, so we don't want to
    // support that mode.
    public var start: Idx,
    public var end: Idx,
    // This field represents an overflow flag for either bound (start or end):
    // - `false` upon construction
    // - `false` when iteration has yielded an element and
    // neither bound has overflowed the valid range of `Idx`
    // - `true` when iteration has caused either bound to
    // overflow the valid range of `Idx`
    //
    // When this is true, `start` or `end` may be left in an unspecified state,
    // often wrapping (modular arithmetic) around at the boundary of `Idx`.
    //
    // This is required to support PartialEq and Hash without a PartialOrd bound or specialization.
    public var exhausted: Boolean,
) {
    public open fun clone(): RangeInclusive<Idx> = RangeInclusive(start, end, exhausted)

    /**
     * Returns the lower bound of the range (inclusive).
     *
     * When using an inclusive range for iteration, the values of `start()` and
     * [end] are unspecified after the iteration ended. To determine
     * whether the inclusive range is empty, use the [isEmpty] method
     * instead of comparing `start()> end()`.
     *
     * Note: the value returned by this method is unspecified after the range
     * has been iterated to exhaustion.
     *
     * [end]: RangeInclusive.end
     * [isEmpty]: RangeInclusive.isEmpty
     *
     * # Examples
     *
     * ```kotlin
     * check((3..5).start() == 3)
     * ```
     */
    public open fun start(): Idx = this.start

    /**
     * Returns the upper bound of the range (inclusive).
     *
     * When using an inclusive range for iteration, the values of [start]
     * and `end()` are unspecified after the iteration ended. To determine
     * whether the inclusive range is empty, use the [isEmpty] method
     * instead of comparing `start()> end()`.
     *
     * Note: the value returned by this method is unspecified after the range
     * has been iterated to exhaustion.
     *
     * [start]: RangeInclusive.start
     * [isEmpty]: RangeInclusive.isEmpty
     *
     * # Examples
     *
     * ```kotlin
     * check((3..5).end() == 5)
     * ```
     */
    public open fun end(): Idx = this.end

    /**
     * Destructures the `RangeInclusive` into (lower bound, upper (inclusive) bound).
     *
     * Note: the value returned by this method is unspecified after the range
     * has been iterated to exhaustion.
     *
     * # Examples
     *
     * ```kotlin
     * check((3..5).intoInner() == Pair(3, 5))
     * ```
     */
    public open fun intoInner(): Pair<Idx, Idx> = Pair(this.start, this.end)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = this.start.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        fmt.append("..=")
        when (val tryResult = this.end.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        if (this.exhausted) {
            fmt.append(" (exhausted)")
        }
        return Result.Ok<Unit, Unit>(Unit)
    }

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check(!(3..5).contains(2))
     * check((3..5).contains(3))
     * check((3..5).contains(4))
     * check((3..5).contains(5))
     * check(!(3..5).contains(6))
     * check((3..3).contains(3))
     * check(!(3..2).contains(3))
     * check((0.0..1.0).contains(1.0))
     * check(!(0.0..1.0).contains(Float.NaN))
     * check(!(0.0..Float.NaN).contains(0.0))
     * check(!(Float.NaN..1.0).contains(1.0))
     * ```
     *
     * This method always returns `false` after iteration has finished:
     *
     * ```kotlin
     * var r = 3..5
     * check(r.contains(3) && r.contains(5))
     * for (unused in r.byRef()) {}
     * // Precise field values are unspecified here
     * check(!r.contains(3) && !r.contains(5))
     * ```
     */
    public open fun <U> contains(item: U): Boolean = RangeInclusive.contains(this, item)

    /**
     * Returns `true` if the range contains no items.
     *
     * # Examples
     *
     * ```kotlin
     * check(!(3..5).isEmpty())
     * check(!(3..3).isEmpty())
     * check((3..2).isEmpty())
     * ```
     *
     * The range is empty if either side is incomparable:
     *
     * ```kotlin
     * check(!(3.0..5.0).isEmpty())
     * check((3.0..Float.NaN).isEmpty())
     * check((Float.NaN..5.0).isEmpty())
     * ```
     *
     * This method returns `true` after iteration has finished:
     *
     * ```kotlin
     * var r = 3..5
     * for (unused in r.byRef()) {}
     * // Precise field values are unspecified here
     * check(r.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.exhausted || !((kmCompareValues(this.start, this.end) <= 0))

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<RangeInclusive<Idx>> =
            object : TypeLayout<RangeInclusive<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutIdx.sizeBytes, typeLayoutIdx.alignBytes)
                private val off2: Long = alignUpBytes((off1 + typeLayoutIdx.sizeBytes), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 1L), alignBytes)
                override fun readNative(addr: Long): RangeInclusive<Idx> =
                    RangeInclusive(
                        (typeLayoutIdx).readNative(addr),
                        (typeLayoutIdx).readNative(addr + off1),
                        (PrimitiveLayouts.BOOL).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: RangeInclusive<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.start)
                    (typeLayoutIdx).writeNative(addr + off1, value.end)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off2, value.exhausted)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeInclusive<Idx> =
                    RangeInclusive(
                        (typeLayoutIdx).readBytes(buf, offset),
                        (typeLayoutIdx).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeInclusive<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.start)
                    (typeLayoutIdx).writeBytes(buf, offset + off1.toInt(), value.end)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off2.toInt(), value.exhausted)
                }
            }

        /**
         * Creates a new inclusive range. Equivalent to writing `start ..= end`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.ops.RangeInclusive
         *
         * check(3..5 == RangeInclusive.new(3, 5))
         * ```
         */
        public fun <Idx> new(start: Idx, end: Idx): RangeInclusive<Idx> = RangeInclusive(start = start, end = end, exhausted = false)
    }
}

/**
 * Converts to an exclusive `Range` for `SliceIndex` implementations.
 * The caller is responsible for dealing with `end == Int.MAX`.
 */
public fun RangeInclusive<Int>.intoSliceRange(): Range<Int> {
    // Typically users should not be indexing with exhausted instances,
    // but this heuristic should apply to most cases. This doesn't
    // handle reverse iteration well (`nextBack` and `nthBack` can
    // cause `end` to wrap around to values at or near `Int.MAX`),
    // but using an exhausted `RangeInclusive` after reverse iteration
    // is an exceedingly rare case.
    // If we're not exhausted, we want to simply slice `start .. end + 1`.
    // If we are exhausted, then slicing with `end + 1 .. end + 1` gives us an
    // empty range that is still subject to bounds-checks for that endpoint.
    val exclusiveEnd = this.end + 1
    val start =
        if (this.exhausted) {
            exclusiveEnd
        } else {
            this.start
        }
    return start until exclusiveEnd
}

/**
 * A range only bounded inclusively above (`..= end`).
 *
 * The `RangeToInclusive` `..= end` contains all values with `x <= end`.
 * It cannot serve as an [Iterator] because it doesn't have a starting point.
 *
 * # Examples
 *
 * The `..= end` syntax is a `RangeToInclusive`:
 *
 * ```kotlin
 * check((0..5) == RangeToInclusive(end = 5))
 * ```
 *
 * It does not have an [IntoIterator] implementation, so you can't use it in a
 * `for` loop directly. This won't compile:
 *
 * ```compile_fail,E0277
 * // error[E0277]: the trait bound `std.ops.RangeToInclusive<{integer}>:
 * // std.iter.Iterator` is not satisfied
 * for i in ..=5 {
 *     // ...
 * }
 * ```
 *
 * When used as a [slicing index], `RangeToInclusive` produces a slice of all
 * array elements up to and including the index indicated by `end`.
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
 */
public open class RangeToInclusive<Idx>(
    /**
     * The upper bound of the range (inclusive)
     */
    public var end: Idx,
) {
    public open fun clone(): RangeToInclusive<Idx> = RangeToInclusive(end)

    public open fun fmt(fmt: StringBuilder): Result<Unit, Unit> {
        fmt.append("..=")
        when (val tryResult = this.end.fmt(fmt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
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
                    (typeLayoutIdx).writeNative(addr, value.end)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RangeToInclusive<Idx> =
                    RangeToInclusive(
                        (typeLayoutIdx).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RangeToInclusive<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.end)
                }
            }
    }
}

// RangeToInclusive<Idx> cannot impl From<RangeTo<Idx>>
// because underflow would be possible with (..0).into()

/**
 * An endpoint of a range of keys.
 *
 * # Examples
 *
 * `Bound`s are range endpoints:
 *
 * ```kotlin
 * import std.ops.Bound.*
 * import std.ops.RangeBounds
 *
 * check((0 until 100).startBound() == Unbounded)
 * check((1 until 12).startBound() == Included(1))
 * check((1 until 12).endBound() == Excluded(12))
 * ```
 *
 * Using a tuple of `Bound`s as an argument to [BTreeMap.range].
 * Note that in most cases, it's better to use range syntax (`1 .. 5`) instead.
 *
 * ```kotlin
 * import std.ops.bound.Excluded
 * import std.ops.bound.Included
 * import std.ops.bound.Unbounded
 *
 * var map = mutableMapOf()
 * map.put(3, "a")
 * map.put(5, "b")
 * map.put(8, "c")
 * for ((key, value) in map.range(Pair(Excluded(3), Included(8)))) {
 *     println("$key: $value")
 * }
 * check(Pair(3, "a") == map.range(Pair(Unbounded, Included(5))).next())
 * ```
 *
 * [BTreeMap.range]: ../../std/collections/btreeMap/struct.BTreeMap.html#method.range
 */
public sealed class Bound<T> {
    /**
     * An inclusive bound.
     */
    public data class Included<T>(public var value: T) : Bound<T>()
    /**
     * An exclusive bound.
     */
    public data class Excluded<T>(public var value: T) : Bound<T>()
    /**
     * An infinite endpoint. Indicates that there is no bound in this direction.
     */
    public class Unbounded<T>() : Bound<T>()

    public fun rustEquals(other: Bound<T>): Boolean = this == other

    /**
     * Converts from `Bound<T>` to `Bound<T>`.
     */
    public open fun asRef(): Bound<T> =
        when (this) {
            is Included<*> -> {
                val (x) = this as Included<T>
                Included(x)
            }
            is Excluded<*> -> {
                val (x1) = this as Excluded<T>
                Excluded(x1)
            }
            is Unbounded<*> -> Unbounded()
        }

    /**
     * Converts from `Bound<T>` to `Bound<T>`.
     */
    public open fun asMut(): Bound<T> =
        when (this) {
            is Included<*> -> {
                val (x) = this as Included<T>
                Included(x)
            }
            is Excluded<*> -> {
                val (x1) = this as Excluded<T>
                Excluded(x1)
            }
            is Unbounded<*> -> Unbounded()
        }

    /**
     * Maps a `Bound<T>` to a `Bound<U>` by applying a function to the contained value (including
     * both `Included` and `Excluded`), returning a `Bound` of the same kind.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound.*
     *
     * val boundString = Included("Hello, World!")
     * check(boundString.map({ s -> s.len() }) == Included(13))
     * ```
     *
     * ```kotlin
     * import std.ops.Bound
     * import std.ops.Bound.*
     *
     * val unboundedString: Bound<String> = Unbounded
     * check(unboundedString.map({ s -> s.len() }) == Unbounded)
     * ```
     */
    public open fun <U> map(f: (T) -> U): Bound<U> =
        when (this) {
            is Unbounded<*> -> Unbounded()
            is Included<*> -> {
                val (x) = this as Included<T>
                Included(f(x))
            }
            is Excluded<*> -> {
                val (x1) = this as Excluded<T>
                Excluded(f(x1))
            }
        }
}

/**
 * Map a `Bound<T>` to a `Bound<T>` by copying the contents of the bound.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.Bound.*
 * import std.ops.RangeBounds
 *
 * @feature(boundCopied)
 * public fun doctestEntry() {
 *     check((1 until 12).startBound() == Included(1))
 *     check((1 until 12).startBound().copied() == Included(1))
 * }
 * ```
 */
public fun <T> Bound<T>.copied(): Bound<T> =
    when (this) {
        is Bound.Unbounded<*> -> Bound.Unbounded()
        is Bound.Included<*> -> {
            val (x) = this as Bound.Included<T>
            Bound.Included(x)
        }
        is Bound.Excluded<*> -> {
            val (x1) = this as Bound.Excluded<T>
            Bound.Excluded(x1)
        }
    }

/**
 * Map a `Bound<T>` to a `Bound<T>` by cloning the contents of the bound.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ops.Bound.*
 * import std.ops.RangeBounds
 *
 * val a1 = "a"
 * val (a2, a3, a4) = Triple(a1.clone(), a1.clone(), a1.clone())
 * check(Included(a1) == (a2 until Int.MAX_VALUE).startBound())
 * check(Included(a3) == (a4 until Int.MAX_VALUE).startBound().cloned())
 * ```
 */
public fun <T> Bound<T>.cloned(cloneTFn: (T) -> T = { it }): Bound<T> =
    when (this) {
        is Bound.Unbounded<*> -> Bound.Unbounded()
        is Bound.Included<*> -> {
            val (x) = this as Bound.Included<T>
            Bound.Included(cloneTFn(x))
        }
        is Bound.Excluded<*> -> {
            val (x1) = this as Bound.Excluded<T>
            Bound.Excluded(cloneTFn(x1))
        }
    }

/**
 * `RangeBounds` is implemented by Rust's built-in range types, produced
 * by range syntax like `..`, `a ..`, `.. b`, `..= c`, `d .. e`, or `f ..= g`.
 */
public interface RangeBounds<T> {
    /**
     * Start index bound.
     *
     * Returns the start value as a `Bound`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound.*
     * import std.ops.RangeBounds
     *
     * check((0 until 10).startBound() == Unbounded)
     * check((3 until 10).startBound() == Included(3))
     * ```
     */
    public fun startBound(): Bound<T>

    /**
     * End index bound.
     *
     * Returns the end value as a `Bound`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound.*
     * import std.ops.RangeBounds
     *
     * check((3 until Int.MAX_VALUE).endBound() == Unbounded)
     * check((3 until 10).endBound() == Excluded(10))
     * ```
     */
    public fun endBound(): Bound<T>

    /**
     * Returns `true` if `item` is contained in the range.
     *
     * # Examples
     *
     * ```kotlin
     * check((3 until 5).contains(4))
     * check(!(3 until 5).contains(2))
     * check((0.0 until 1.0).contains(0.5))
     * check(!(0.0 until 1.0).contains(Float.NaN))
     * check(!(0.0 until Float.NaN).contains(0.5))
     * check(!(Float.NaN until 1.0).contains(0.5))
     * ```
     */
    public fun <U> contains(item: U): Boolean =
        (when (val matchVal = this.startBound()) {
            is RangeBounds.Included<*> -> {
                val (start) = matchVal
                (kmCompareValues(start, item) <= 0)
            }
            is RangeBounds.Excluded<*> -> {
                val (start1) = matchVal
                (kmCompareValues(start1, item) < 0)
            }
            is RangeBounds.Unbounded<*> -> true
        }) &&
            (when (val matchVal1 = this.endBound()) {
                is RangeBounds.Included<*> -> {
                    val (end) = matchVal1
                    (kmCompareValues(item, end) <= 0)
                }
                is RangeBounds.Excluded<*> -> {
                    val (end1) = matchVal1
                    (kmCompareValues(item, end1) < 0)
                }
                is RangeBounds.Unbounded<*> -> true
            })

    /**
     * Returns `true` if the range contains no items.
     * One-sided ranges (`RangeFrom`, etc) always return `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.RangeBounds
     *
     * @feature(rangeBoundsIsEmpty)
     * public fun doctestEntry() {
     *     check(!(3 until Int.MAX_VALUE).isEmpty())
     *     check(!(0 until 2).isEmpty())
     *     check(!RangeBounds.isEmpty((3 until 5)))
     *     check(RangeBounds.isEmpty((3 until 3)))
     *     check(RangeBounds.isEmpty((3 until 2)))
     * }
     * ```
     *
     * The range is empty if either side is incomparable:
     *
     * ```kotlin
     * import std.ops.RangeBounds
     *
     * @feature(rangeBoundsIsEmpty)
     * public fun doctestEntry() {
     *     check(!RangeBounds.isEmpty((3.0 until 5.0)))
     *     check(RangeBounds.isEmpty((3.0 until Float.NaN)))
     *     check(RangeBounds.isEmpty((Float.NaN until 5.0)))
     * }
     * ```
     *
     * But never empty if either side is unbounded:
     *
     * ```kotlin
     * import std.ops.RangeBounds
     *
     * @feature(rangeBoundsIsEmpty)
     * public fun doctestEntry() {
     *     check(!(0 until 0).isEmpty())
     *     check(!(Int.MAX_VALUE until Int.MAX_VALUE).isEmpty())
     *     check(!RangeBounds.isEmpty((0 until 0)))
     * }
     * ```
     *
     * `(Excluded(a), Excluded(b))` is only empty if `a >= b`:
     *
     * ```kotlin
     * import std.ops.Bound.*
     * import std.ops.RangeBounds
     *
     * @feature(rangeBoundsIsEmpty)
     * public fun doctestEntry() {
     *     check(!Pair(Excluded(1), Excluded(3)).isEmpty())
     *     check(!Pair(Excluded(1), Excluded(2)).isEmpty())
     *     check(Pair(Excluded(1), Excluded(1)).isEmpty())
     *     check(Pair(Excluded(2), Excluded(1)).isEmpty())
     *     check(Pair(Excluded(3), Excluded(1)).isEmpty())
     * }
     * ```
     */
    public fun isEmpty(): Boolean =
        !run {
            val matchVal = Pair(this.startBound(), this.endBound())
            when {
                matchVal.first is RangeBounds.Unbounded<*> -> true
                matchVal.second is RangeBounds.Unbounded<*> -> true
                matchVal.first is RangeBounds.Included<*> && matchVal.second is RangeBounds.Excluded<*> -> {
                    val (start) = matchVal.first as RangeBounds.Included<T>
                    val (end) = matchVal.second as RangeBounds.Excluded<T>
                    (kmCompareValues(start, end) < 0)
                }
                matchVal.first is RangeBounds.Excluded<*> && matchVal.second is RangeBounds.Included<*> -> {
                    val (start1) = matchVal.first as RangeBounds.Excluded<T>
                    val (end1) = matchVal.second as RangeBounds.Included<T>
                    (kmCompareValues(start1, end1) < 0)
                }
                matchVal.first is RangeBounds.Excluded<*> && matchVal.second is RangeBounds.Excluded<*> -> {
                    val (start2) = matchVal.first as RangeBounds.Excluded<T>
                    val (end2) = matchVal.second as RangeBounds.Excluded<T>
                    (kmCompareValues(start2, end2) < 0)
                }
                else -> {
                    val (start3) = matchVal.first as RangeBounds.Included<T>
                    val (end3) = matchVal.second as RangeBounds.Included<T>
                    (kmCompareValues(start3, end3) <= 0)
                }
            }
        }
}

/**
 * Used to convert a range into start and end bounds, consuming the
 * range by value.
 *
 * `IntoBounds` is implemented by Rust’s built-in range types, produced
 * by range syntax like `..`, `a ..`, `.. b`, `..= c`, `d .. e`, or `f ..= g`.
 */
public interface IntoBounds<T> : RangeBounds<T> {
    /**
     * Convert this range into the start and end bounds.
     * Returns `(startBound, endBound)`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound.*
     * import std.ops.IntoBounds
     *
     * @feature(rangeIntoBounds)
     * public fun doctestEntry() {
     *     check((0 until 5).intoBounds() == Pair(Included(0), Excluded(5)))
     *     check((0..7).intoBounds() == Pair(Unbounded, Included(7)))
     * }
     * ```
     */
    public fun intoBounds(): Pair<Bound<T>, Bound<T>>

    /**
     * Compute the intersection of  `self` and `other`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Bound.*
     * import std.ops.IntoBounds
     *
     * @feature(rangeIntoBounds)
     * public fun doctestEntry() {
     *     check((3 until Int.MAX_VALUE).intersect(0 until 5) == Pair(Included(3), Excluded(5)))
     *     check((-12 until 387).intersect(0 until 256) == Pair(Included(0), Excluded(256)))
     *     check((1 until 5).intersect(0 until 0) == Pair(Included(1), Excluded(5)))
     *     check((1..9).intersect(0 until 10) == Pair(Included(1), Included(9)))
     *     check((7..13).intersect(8 until 13) == Pair(Included(8), Excluded(13)))
     * }
     * ```
     *
     * Combine with `isEmpty` to determine if two ranges overlap.
     *
     * ```kotlin
     * import std.ops.IntoBounds
     * import std.ops.RangeBounds
     *
     * @feature(rangeIntoBounds)
     * @feature(rangeBoundsIsEmpty)
     * public fun doctestEntry() {
     *     check(!(3 until Int.MAX_VALUE).intersect(0 until 5).isEmpty())
     *     check(!(-12 until 387).intersect(0 until 256).isEmpty())
     *     check((1 until 5).intersect(6 until Int.MAX_VALUE).isEmpty())
     * }
     * ```
     */
    public fun <R> intersect(other: R): Pair<Bound<T>, Bound<T>> where R : IntoBounds<T> {
        val (selfStart, selfEnd) = this.intoBounds()
        val (otherStart, otherEnd) = other.intoBounds()
        val start =
            run {
                val matchVal = Pair(selfStart, otherStart)
                when {
                    matchVal.first is Included<*> && matchVal.second is Included<*> -> {
                        val (a) = matchVal.first as Included<T>
                        val (b) = matchVal.second as Included<T>
                        Included(a.max(b))
                    }
                    matchVal.first is Excluded<*> && matchVal.second is Excluded<*> -> {
                        val (a1) = matchVal.first as Excluded<T>
                        val (b1) = matchVal.second as Excluded<T>
                        Excluded(a1.max(b1))
                    }
                    matchVal.first is Unbounded<*> && matchVal.second is Unbounded<*> -> Unbounded()
                    matchVal.second is Unbounded<*> -> {
                        val x = matchVal.first
                        x
                    }
                    matchVal.first is Unbounded<*> -> {
                        val x1 = matchVal.second
                        x1
                    }
                    matchVal.first is Included<*> && matchVal.second is Excluded<*> -> {
                        val (i) = matchVal.first as Included<T>
                        val (e) = matchVal.second as Excluded<T>
                        if (i > e) {
                            Included(i)
                        } else {
                            Excluded(e)
                        }
                    }
                    else -> {
                        val (e1) = matchVal.first as Excluded<T>
                        val (i1) = matchVal.second as Included<T>
                        if (i1 > e1) {
                            Included(i1)
                        } else {
                            Excluded(e1)
                        }
                    }
                }
            }
        val end =
            run {
                val matchVal1 = Pair(selfEnd, otherEnd)
                when {
                    matchVal1.first is Included<*> && matchVal1.second is Included<*> -> {
                        val (a2) = matchVal1.first as Included<T>
                        val (b2) = matchVal1.second as Included<T>
                        Included(a2.min(b2))
                    }
                    matchVal1.first is Excluded<*> && matchVal1.second is Excluded<*> -> {
                        val (a3) = matchVal1.first as Excluded<T>
                        val (b3) = matchVal1.second as Excluded<T>
                        Excluded(a3.min(b3))
                    }
                    matchVal1.first is Unbounded<*> && matchVal1.second is Unbounded<*> -> Unbounded()
                    matchVal1.second is Unbounded<*> -> {
                        val x2 = matchVal1.first
                        x2
                    }
                    matchVal1.first is Unbounded<*> -> {
                        val x3 = matchVal1.second
                        x3
                    }
                    matchVal1.first is Included<*> && matchVal1.second is Excluded<*> -> {
                        val (i2) = matchVal1.first as Included<T>
                        val (e2) = matchVal1.second as Excluded<T>
                        if (i2 < e2) {
                            Included(i2)
                        } else {
                            Excluded(e2)
                        }
                    }
                    else -> {
                        val (e3) = matchVal1.first as Excluded<T>
                        val (i3) = matchVal1.second as Included<T>
                        if (i3 < e3) {
                            Included(i3)
                        } else {
                            Excluded(e3)
                        }
                    }
                }
            }
        return Pair(start, end)
    }
}

public fun <T> RangeFull.startBound(): Bound<T> = Unbounded()

public fun <T> RangeFull.endBound(): Bound<T> = Unbounded()

public fun <T> RangeFull.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Unbounded(), Unbounded())

public fun <T> RangeFrom<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> RangeFrom<T>.endBound(): Bound<T> = Unbounded()

public fun <T> RangeFrom<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Included(this.start), Unbounded())

public fun <T> RangeTo<T>.startBound(): Bound<T> = Unbounded()

public fun <T> RangeTo<T>.endBound(): Bound<T> = Excluded(this.end)

public fun <T> RangeTo<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Unbounded(), Excluded(this.end))

public fun <T> Range<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> Range<T>.endBound(): Bound<T> = Excluded(this.end)

public fun <T> Range<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Included(this.start), Excluded(this.end))

public fun <T> RangeInclusive<T>.startBound(): Bound<T> = Included(this.start)

public fun <T> RangeInclusive<T>.endBound(): Bound<T> {
    return if (this.exhausted) {
        // When the iterator is exhausted, it might have overflowed,
        // but we want the range to appear empty, containing nothing.
        // So in that case, we return bounds which are always empty:
        // Included(start)..Excluded(start)
        Excluded(this.start)
    } else {
        Included(this.end)
    }
}

public fun <T> RangeInclusive<T>.intoBounds(): Pair<Bound<T>, Bound<T>> {
    check(!this.exhausted) { "attempted to convert from an exhausted `RangeInclusive` (unspecified behavior)" }
    return Pair(Included(this.start), Included(this.end))
}

public fun <T> RangeToInclusive<T>.startBound(): Bound<T> = Unbounded()

public fun <T> RangeToInclusive<T>.endBound(): Bound<T> = Included(this.end)

public fun <T> RangeToInclusive<T>.intoBounds(): Pair<Bound<T>, Bound<T>> = Pair(Unbounded(), Included(this.end))

public fun <T> Pair<Bound<T>, Bound<T>>.startBound(): Bound<T> =
    when {
        this.first is Included<*> -> {
            val (start) = this.first as Included<T>
            Included(start)
        }
        this.first is Excluded<*> -> {
            val (start1) = this.first as Excluded<T>
            Excluded(start1)
        }
        else -> Unbounded()
    }

public fun <T> Pair<Bound<T>, Bound<T>>.endBound(): Bound<T> =
    when {
        this.second is Included<*> -> {
            val (end) = this.second as Included<T>
            Included(end)
        }
        this.second is Excluded<*> -> {
            val (end1) = this.second as Excluded<T>
            Excluded(end1)
        }
        else -> Unbounded()
    }

public fun <T> Pair<Bound<T>, Bound<T>>.intoBounds(): Pair<Bound<T>, Bound<T>> = this

public fun <T> Pair<Bound<T>, Bound<T>>.startBoundT(): Bound<T> = this.first

public fun <T> Pair<Bound<T>, Bound<T>>.endBoundT(): Bound<T> = this.second

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `start ..` with `(Bound.Included(start), Bound.Unbounded)`.
 */
public fun <T> RangeFrom<T>.startBoundT(): Bound<T> = Included(this.start)

public fun <T> RangeFrom<T>.endBoundT(): Bound<T> = Unbounded()

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `.. end` with `(Bound.Unbounded, Bound.Excluded(end))`.
 */
public fun <T> RangeTo<T>.startBoundT(): Bound<T> = Unbounded()

public fun <T> RangeTo<T>.endBoundT(): Bound<T> = Excluded(this.end)

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

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `start ..= end` with `(Bound.Included(start), Bound.Included(end))`.
 */
public fun <T> RangeInclusive<T>.startBoundT(): Bound<T> = Included(this.start)

public fun <T> RangeInclusive<T>.endBoundT(): Bound<T> = Included(this.end)

// This impl intentionally does not have `T: ? Sized`;
// see https://github.com/rust-lang/rust/pull/61584 for discussion of why.
//

/**
 * If you need to use this implementation where `T` is unsized,
 * consider using the `RangeBounds` impl for a 2-tuple of [`Bound.T`][Bound],
 * i.e. replace `..= end` with `(Bound.Unbounded, Bound.Included(end))`.
 */
public fun <T> RangeToInclusive<T>.startBoundT(): Bound<T> = Unbounded()

public fun <T> RangeToInclusive<T>.endBoundT(): Bound<T> = Included(this.end)

/**
 * An internal helper for `splitOff` functions indicating
 * which end a `OneSidedRange` is bounded on.
 */
public sealed class OneSidedRangeBound {
    /**
     * The range is bounded inclusively from below and is unbounded above.
     */
    public data object StartInclusive : OneSidedRangeBound()
    /**
     * The range is bounded exclusively from above and is unbounded below.
     */
    public data object End : OneSidedRangeBound()
    /**
     * The range is bounded inclusively from above and is unbounded below.
     */
    public data object EndInclusive : OneSidedRangeBound()

    private fun kmDiscriminant(): Int =
        when (this) {
        StartInclusive -> 0
        End -> 1
        EndInclusive -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

/**
 * `OneSidedRange` is implemented for built-in range types that are unbounded
 * on one side. For example, `a ..`, `.. b` and `..= c` implement `OneSidedRange`,
 * but `..`, `d .. e`, and `f ..= g` do not.
 *
 * Types that implement `OneSidedRange<T>` must return `Bound.Unbounded`
 * from one of `RangeBounds.startBound` or `RangeBounds.endBound`.
 */
public interface OneSidedRange<T> : RangeBounds<T> {
    /**
     * An internal-only helper function for `splitOff` and
     * `splitOffMut` that returns the bound of the one-sided range.
     */
    public fun bound(): Pair<OneSidedRangeBound, T>
}

public fun <T> RangeTo<T>.bound(): Pair<OneSidedRangeBound, T> = Pair(OneSidedRangeBound.End, this.end)

public fun <T> RangeFrom<T>.bound(): Pair<OneSidedRangeBound, T> = Pair(OneSidedRangeBound.StartInclusive, this.start)

public fun <T> RangeToInclusive<T>.bound(): Pair<OneSidedRangeBound, T> = Pair(OneSidedRangeBound.EndInclusive, this.end)
