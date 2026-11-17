// port-lint: source iter/range.rs
package io.github.kotlinmania.rustlang.core.iter.range

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.TryFrom
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedStep
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.num.overflowingAdd
import io.github.kotlinmania.rustlang.core.num.overflowingSub
import io.github.kotlinmania.rustlang.core.num.wrappingAdd
import io.github.kotlinmania.rustlang.core.num.wrappingSub
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.ascii.Char as AsciiChar

// Safety: This macro must only be used on types that are `Copy` and result in ranges
// which have an exact `sizeHint()` where the upper bound must not be `None`.

// These macros generate `ExactSizeIterator` impls for various range types.
//
// * `ExactSizeIterator.len` is required to always return an exact `Int`,
// so no range can be longer than `Int.MAX`.
// * For integer types in `Range<_>` this is the case for types narrower than or as wide as `Int`.
// For integer types in `RangeInclusive<_>`
// this is the case for types *strictly narrower* than `Int`
// since e.g. `(0 ..= ULong.MAX).len()` would be `ULong.MAX + 1`.

/**
 * Objects that have a notion of *successor* and *predecessor* operations.
 *
 * The *successor* operation moves towards values that compare greater.
 * The *predecessor* operation moves towards values that compare lesser.
 */
public interface Step<T> {
    /**
     * Returns the bounds on the number of *successor* steps required to get from `start` to `end`
     * like [`Iterator.sizeHint`][Iterator.sizeHint].
     *
     * Returns `(Int.MAX, None)` if the number of steps would overflow `Int`, or is infinite.
     *
     * # Invariants
     *
     * For any `a`, `b`, and `n`:
     *
     * * `stepsBetween(a, b) == (n, Some(n))` if and only if `Step.forwardChecked(a, n) == Some(b)`
     * * `stepsBetween(a, b) == (n, Some(n))` if and only if `Step.backwardChecked(b, n) == Some(a)`
     * * `stepsBetween(a, b) == (n, Some(n))` only if `a <= b`
     *   * Corollary: `stepsBetween(a, b) == (0, Some(0))` if and only if `a == b`
     * * `stepsBetween(a, b) == (0, None)` if `a> b`
     */
    public fun stepsBetween(start: T, end: T): Pair<Int, Int?>

    /**
     * Returns the value that would be obtained by taking the *successor*
     * of `self` `count` times.
     *
     * If this would overflow the range of values supported by `Self`, returns `None`.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`:
     *
     * * `Step.forwardChecked(a, n).andThen(| x | Step.forwardChecked(x, m)) == Step.forwardChecked(a, m).andThen(| x | Step.forwardChecked(x, n))`
     * * `Step.forwardChecked(a, n).andThen(| x | Step.forwardChecked(x, m)) == try ( Step.forwardChecked(a, n.checkedAdd(m)) )`
     *
     * For any `a` and `n`:
     *
     * * `Step.forwardChecked(a, n) == (0 .. n).tryFold(a, | x, _ | Step.forwardChecked(x, 1))`
     *   * Corollary: `Step.forwardChecked(a, 0) == Some(a)`
     */
    public fun forwardChecked(start: T, count: Int): T?

    /**
     * Returns the value that would be obtained by taking the *successor*
     * of `self` `count` times along with a boolean tracking whether overflow
     * occurred.
     *
     * If this would overflow the range of values supported by `Self`, the
     * value returned is unspecified and should not be relied on, though
     * typically wrapping (modular arithmetic) is the most effective
     * implementation to enable optimizations.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`, where no overflow occurs:
     *
     * * `Step.forwardOverflowing(Step.forwardOverflowing(a, n).0, m) == Step.forwardOverflowing(a, n + m)`
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.forwardOverflowing(a, n) == (Step.forwardChecked(a, n).unwrap(), false)`
     *
     * For any `a` and `n`:
     *
     * * `Step.forwardOverflowing(a, n) == (0 .. n).fold((a, false), |(x, y), _ | ( val (s, o) = Step.forwardOverflowing(x, 1); (s, y || o) ))`
     *   * Corollary: `Step.forwardOverflowing(a, 0) == (a, false)`
     */
    public fun forwardOverflowing(start: T, count: Int): Pair<T, Boolean>

    /**
     * Returns the value that would be obtained by taking the *successor*
     * of `self` `count` times.
     *
     * If this would overflow the range of values supported by `Self`,
     * this function is allowed to panic, wrap, or saturate.
     * The suggested behavior is to panic when debug assertions are enabled,
     * and to wrap or saturate otherwise.
     *
     * Unsafe code should not rely on the correctness of behavior after overflow.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`, where no overflow occurs:
     *
     * * `Step.forward(Step.forward(a, n), m) == Step.forward(a, n + m)`
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.forwardChecked(a, n) == Some(Step.forward(a, n))`
     * * `Step.forward(a, n) == (0 .. n).fold(a, | x, _ | Step.forward(x, 1))`
     *   * Corollary: `Step.forward(a, 0) == a`
     * * `Step.forward(a, n) >= a`
     * * `Step.backward(Step.forward(a, n), n) == a`
     */
    public fun forward(start: T, count: Int): T

    /**
     * Returns the value that would be obtained by taking the *successor*
     * of `self` `count` times.
     *
     * # Safety
     *
     * It is undefined behavior for this operation to overflow the
     * range of values supported by `Self`. If you cannot guarantee that this
     * will not overflow, use `forward` or `forwardChecked` instead.
     *
     * # Invariants
     *
     * For any `a`:
     *
     * * if there exists `b` such that `b> a`, it is safe to call `Step.forwardUnchecked(a, 1)`
     * * if there exists `b`, `n` such that `stepsBetween(a, b) == Some(n)`,
     *   it is safe to call `Step.forwardUnchecked(a, m)` for any `m <= n`.
     *   * Corollary: `Step.forwardUnchecked(a, 0)` is always safe.
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.forwardUnchecked(a, n)` is equivalent to `Step.forward(a, n)`
     */
    public fun forwardUnchecked(start: T, count: Int): T

    /**
     * Returns the value that would be obtained by taking the *predecessor*
     * of `self` `count` times.
     *
     * If this would overflow the range of values supported by `Self`, returns `None`.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`:
     *
     * * `Step.backwardChecked(a, n).andThen(| x | Step.backwardChecked(x, m)) == n.checkedAdd(m).andThen(| x | Step.backwardChecked(a, x))`
     * * `Step.backwardChecked(a, n).andThen(| x | Step.backwardChecked(x, m)) == try ( Step.backwardChecked(a, n.checkedAdd(m)) )`
     *
     * For any `a` and `n`:
     *
     * * `Step.backwardChecked(a, n) == (0 .. n).tryFold(a, | x, _ | Step.backwardChecked(x, 1))`
     *   * Corollary: `Step.backwardChecked(a, 0) == Some(a)`
     */
    public fun backwardChecked(start: T, count: Int): T?

    /**
     * Returns the value that would be obtained by taking the *successor*
     * of `self` `count` times along with a boolean tracking whether overflow
     * occurred.
     *
     * If this would overflow the range of values supported by `Self`, the
     * value returned is unspecified and should not be relied on, though
     * typically wrapping (modular arithmetic) is the most effective
     * implementation to enable optimizations.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`, where no overflow occurs:
     *
     * * `Step.backwardOverflowing(Step.backwardOverflowing(a, n).0, m) == Step.backwardOverflowing(a, n + m)`
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.backwardOverflowing(a, n) == (Step.backwardChecked(a, n).unwrap(), false)`
     *
     * For any `a` and `n`:
     *
     * * `Step.backwardOverflowing(a, n) == (0 .. n).fold((a, false), |(x, y), _ | ( val (s, o) = Step.backwardOverflowing(x, 1); (s, y || o) ))`
     *   * Corollary: `Step.backwardOverflowing(a, 0) == (a, false)`
     */
    public fun backwardOverflowing(start: T, count: Int): Pair<T, Boolean>

    /**
     * Returns the value that would be obtained by taking the *predecessor*
     * of `self` `count` times.
     *
     * If this would overflow the range of values supported by `Self`,
     * this function is allowed to panic, wrap, or saturate.
     * The suggested behavior is to panic when debug assertions are enabled,
     * and to wrap or saturate otherwise.
     *
     * Unsafe code should not rely on the correctness of behavior after overflow.
     *
     * # Invariants
     *
     * For any `a`, `n`, and `m`, where no overflow occurs:
     *
     * * `Step.backward(Step.backward(a, n), m) == Step.backward(a, n + m)`
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.backwardChecked(a, n) == Some(Step.backward(a, n))`
     * * `Step.backward(a, n) == (0 .. n).fold(a, | x, _ | Step.backward(x, 1))`
     *   * Corollary: `Step.backward(a, 0) == a`
     * * `Step.backward(a, n) <= a`
     * * `Step.forward(Step.backward(a, n), n) == a`
     */
    public fun backward(start: T, count: Int): T

    /**
     * Returns the value that would be obtained by taking the *predecessor*
     * of `self` `count` times.
     *
     * # Safety
     *
     * It is undefined behavior for this operation to overflow the
     * range of values supported by `Self`. If you cannot guarantee that this
     * will not overflow, use `backward` or `backwardChecked` instead.
     *
     * # Invariants
     *
     * For any `a`:
     *
     * * if there exists `b` such that `b<a`, it is safe to call `Step.backwardUnchecked(a, 1)`
     * * if there exists `b`, `n` such that `stepsBetween(b, a) == (n, Some(n))`,
     *   it is safe to call `Step.backwardUnchecked(a, m)` for any `m <= n`.
     *   * Corollary: `Step.backwardUnchecked(a, 0)` is always safe.
     *
     * For any `a` and `n`, where no overflow occurs:
     *
     * * `Step.backwardUnchecked(a, n)` is equivalent to `Step.backward(a, n)`
     */
    public fun backwardUnchecked(start: T, count: Int): T
}

public fun stepForwardUByte(start: UByte, n: Int): UByte {
    if (UByte.forwardChecked(start, n) == null) {
        UByte.MAX_VALUE + 1
    }
    return (start + (n.toUByte())).toUByte()
}

public fun stepBackwardUByte(start: UByte, n: Int): UByte {
    if (UByte.backwardChecked(start, n) == null) {
        UByte.MIN_VALUE - 1
    }
    return (start - (n.toUByte())).toUByte()
}

public fun stepForwardUncheckedUByte(start: UByte, n: Int): UByte = start.uncheckedAdd(n.toUByte())

public fun stepBackwardUncheckedUByte(start: UByte, n: Int): UByte = start.uncheckedSub(n.toUByte())

public fun stepStepsBetweenUByte(start: UByte, end: UByte): Pair<Int, Int?> =
    if (start <= end) {
        val steps = (end - start).toInt()
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedUByte(start: UByte, n: Int): UByte? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1.toUByte())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedUByte(start: UByte, n: Int): UByte? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedSub(n1.toUByte())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingUByte(start: UByte, n: Int): Pair<UByte, Boolean> =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAdd(n1.toUByte())
        }; is Result.Err<*, *> -> {
            Pair((start + (n.toUByte())).toUByte(), true)
        } }
    }

public fun stepBackwardOverflowingUByte(start: UByte, n: Int): Pair<UByte, Boolean> =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSub(n1.toUByte())
        }; is Result.Err<*, *> -> {
            Pair((start - (n.toUByte())).toUByte(), true)
        } }
    }

public fun stepForwardByte(start: Byte, n: Int): Byte {
    if (Byte.forwardChecked(start, n) == null) {
        Byte.MAX_VALUE + 1
    }
    return (start + (n.toByte())).toByte()
}

public fun stepBackwardByte(start: Byte, n: Int): Byte {
    if (Byte.backwardChecked(start, n) == null) {
        Byte.MIN_VALUE - 1
    }
    return (start - (n.toByte())).toByte()
}

public fun stepForwardUncheckedByte(start: Byte, n: Int): Byte = start.checkedAddUnsigned(n.toUByte()).unwrapUnchecked()

public fun stepBackwardUncheckedByte(start: Byte, n: Int): Byte = start.checkedSubUnsigned(n.toUByte()).unwrapUnchecked()

public fun stepStepsBetweenByte(start: Byte, end: Byte): Pair<Int, Int?> =
    if (start <= end) {
        val steps = ((end.toInt()) - (start.toInt()))
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedByte(start: Byte, n: Int): Byte? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start + (n1.toByte())).toByte()
            if (wrapped >= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedByte(start: Byte, n: Int): Byte? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start - (n1.toByte())).toByte()
            if (wrapped <= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingByte(start: Byte, n: Int): Pair<Byte, Boolean> =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAddUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingAddUnsigned(n.toUByte()), true)
        } }
    }

public fun stepBackwardOverflowingByte(start: Byte, n: Int): Pair<Byte, Boolean> =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSubUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingSubUnsigned(n.toUByte()), true)
        } }
    }

public fun stepForwardUShort(start: UShort, n: Int): UShort {
    if (UShort.forwardChecked(start, n) == null) {
        UShort.MAX_VALUE + 1
    }
    return (start + (n.toUShort())).toUShort()
}

public fun stepBackwardUShort(start: UShort, n: Int): UShort {
    if (UShort.backwardChecked(start, n) == null) {
        UShort.MIN_VALUE - 1
    }
    return (start - (n.toUShort())).toUShort()
}

public fun stepForwardUncheckedUShort(start: UShort, n: Int): UShort = start.uncheckedAdd(n.toUShort())

public fun stepBackwardUncheckedUShort(start: UShort, n: Int): UShort = start.uncheckedSub(n.toUShort())

public fun stepStepsBetweenUShort(start: UShort, end: UShort): Pair<Int, Int?> =
    if (start <= end) {
        val steps = (end - start).toInt()
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedUShort(start: UShort, n: Int): UShort? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1.toUShort())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedUShort(start: UShort, n: Int): UShort? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedSub(n1.toUShort())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingUShort(start: UShort, n: Int): Pair<UShort, Boolean> =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAdd(n1.toUShort())
        }; is Result.Err<*, *> -> {
            Pair((start + (n.toUShort())).toUShort(), true)
        } }
    }

public fun stepBackwardOverflowingUShort(start: UShort, n: Int): Pair<UShort, Boolean> =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSub(n1.toUShort())
        }; is Result.Err<*, *> -> {
            Pair((start - (n.toUShort())).toUShort(), true)
        } }
    }

public fun stepForwardShort(start: Short, n: Int): Short {
    if (Short.forwardChecked(start, n) == null) {
        Short.MAX_VALUE + 1
    }
    return (start + (n.toShort())).toShort()
}

public fun stepBackwardShort(start: Short, n: Int): Short {
    if (Short.backwardChecked(start, n) == null) {
        Short.MIN_VALUE - 1
    }
    return (start - (n.toShort())).toShort()
}

public fun stepForwardUncheckedShort(start: Short, n: Int): Short = start.checkedAddUnsigned(n.toUShort()).unwrapUnchecked()

public fun stepBackwardUncheckedShort(start: Short, n: Int): Short = start.checkedSubUnsigned(n.toUShort()).unwrapUnchecked()

public fun stepStepsBetweenShort(start: Short, end: Short): Pair<Int, Int?> =
    if (start <= end) {
        val steps = ((end.toInt()) - (start.toInt()))
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedShort(start: Short, n: Int): Short? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start + (n1.toShort())).toShort()
            if (wrapped >= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedShort(start: Short, n: Int): Short? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start - (n1.toShort())).toShort()
            if (wrapped <= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingShort(start: Short, n: Int): Pair<Short, Boolean> =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAddUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingAddUnsigned(n.toUShort()), true)
        } }
    }

public fun stepBackwardOverflowingShort(start: Short, n: Int): Pair<Short, Boolean> =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSubUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingSubUnsigned(n.toUShort()), true)
        } }
    }

public fun stepForwardUInt(start: UInt, n: Int): UInt {
    if (UInt.forwardChecked(start, n) == null) {
        UInt.MAX_VALUE + 1
    }
    return (start + (n.toUInt()))
}

public fun stepBackwardUInt(start: UInt, n: Int): UInt {
    if (UInt.backwardChecked(start, n) == null) {
        UInt.MIN_VALUE - 1
    }
    return (start - (n.toUInt()))
}

public fun stepForwardUncheckedUInt(start: UInt, n: Int): UInt = start.uncheckedAdd(n.toUInt())

public fun stepBackwardUncheckedUInt(start: UInt, n: Int): UInt = start.uncheckedSub(n.toUInt())

public fun stepStepsBetweenUInt(start: UInt, end: UInt): Pair<Int, Int?> =
    if (start <= end) {
        val steps = (end - start).toInt()
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedUInt(start: UInt, n: Int): UInt? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1.toUInt())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedUInt(start: UInt, n: Int): UInt? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedSub(n1.toUInt())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingUInt(start: UInt, n: Int): Pair<UInt, Boolean> =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAdd(n1.toUInt())
        }; is Result.Err<*, *> -> {
            Pair((start + (n.toUInt())), true)
        } }
    }

public fun stepBackwardOverflowingUInt(start: UInt, n: Int): Pair<UInt, Boolean> =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSub(n1.toUInt())
        }; is Result.Err<*, *> -> {
            Pair((start - (n.toUInt())), true)
        } }
    }

public fun stepForwardInt(start: Int, n: Int): Int {
    if (Int.forwardChecked(start, n) == null) {
        Int.MAX_VALUE + 1
    }
    return (start + ((n).toInt()))
}

public fun stepBackwardInt(start: Int, n: Int): Int {
    if (Int.backwardChecked(start, n) == null) {
        Int.MIN_VALUE - 1
    }
    return (start - ((n).toInt()))
}

public fun stepForwardUncheckedInt(start: Int, n: Int): Int = start.checkedAddUnsigned(n.toUInt()).unwrapUnchecked()

public fun stepBackwardUncheckedInt(start: Int, n: Int): Int = start.checkedSubUnsigned(n.toUInt()).unwrapUnchecked()

public fun stepStepsBetweenInt(start: Int, end: Int): Pair<Int, Int?> =
    if (start <= end) {
        val steps = ((end) - ((start).toInt()))
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedInt(start: Int, n: Int): Int? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start + (n1.toInt()))
            if (wrapped >= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedInt(start: Int, n: Int): Int? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start - (n1.toInt()))
            if (wrapped <= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingInt(start: Int, n: Int): Pair<Int, Boolean> =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAddUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingAddUnsigned(n.toUInt()), true)
        } }
    }

public fun stepBackwardOverflowingInt(start: Int, n: Int): Pair<Int, Boolean> =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSubUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingSubUnsigned(n.toUInt()), true)
        } }
    }

public fun stepForwardULong(start: ULong, n: Int): ULong {
    if (ULong.forwardChecked(start, n) == null) {
        ULong.MAX_VALUE + 1
    }
    return (start + (n.toULong()))
}

public fun stepBackwardULong(start: ULong, n: Int): ULong {
    if (ULong.backwardChecked(start, n) == null) {
        ULong.MIN_VALUE - 1
    }
    return (start - (n.toULong()))
}

public fun stepForwardUncheckedULong(start: ULong, n: Int): ULong = start.uncheckedAdd(n.toULong())

public fun stepBackwardUncheckedULong(start: ULong, n: Int): ULong = start.uncheckedSub(n.toULong())

public fun stepStepsBetweenULong(start: ULong, end: ULong): Pair<Int, Int?> =
    if (start <= end) {
        val steps = (end - start).toInt()
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedULong(start: ULong, n: Int): ULong? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1.toULong())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedULong(start: ULong, n: Int): ULong? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedSub(n1.toULong())
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingULong(start: ULong, n: Int): Pair<ULong, Boolean> =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAdd(n1.toULong())
        }; is Result.Err<*, *> -> {
            Pair((start + (n.toULong())), true)
        } }
    }

public fun stepBackwardOverflowingULong(start: ULong, n: Int): Pair<ULong, Boolean> =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSub(n1.toULong())
        }; is Result.Err<*, *> -> {
            Pair((start - (n.toULong())), true)
        } }
    }

public fun stepForwardLong(start: Long, n: Int): Long {
    if (Long.forwardChecked(start, n) == null) {
        Long.MAX_VALUE + 1
    }
    return (start + (n.toLong()))
}

public fun stepBackwardLong(start: Long, n: Int): Long {
    if (Long.backwardChecked(start, n) == null) {
        Long.MIN_VALUE - 1
    }
    return (start - (n.toLong()))
}

public fun stepForwardUncheckedLong(start: Long, n: Int): Long = start.checkedAddUnsigned(n.toULong()).unwrapUnchecked()

public fun stepBackwardUncheckedLong(start: Long, n: Int): Long = start.checkedSubUnsigned(n.toULong()).unwrapUnchecked()

public fun stepStepsBetweenLong(start: Long, end: Long): Pair<Int, Int?> =
    if (start <= end) {
        val steps = ((end.toInt()) - (start.toInt()))
        Pair(steps, steps)
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedLong(start: Long, n: Int): Long? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start + (n1.toLong()))
            if (wrapped >= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedLong(start: Long, n: Int): Long? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            val wrapped = (start - (n1.toLong()))
            if (wrapped <= start) {
                wrapped
            } else {
                null
            }
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardOverflowingLong(start: Long, n: Int): Pair<Long, Boolean> =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingAddUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingAddUnsigned(n.toULong()), true)
        } }
    }

public fun stepBackwardOverflowingLong(start: Long, n: Int): Pair<Long, Boolean> =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.overflowingSubUnsigned(n1)
        }; is Result.Err<*, *> -> {
            Pair(start.wrappingSubUnsigned(n.toULong()), true)
        } }
    }

public fun stepForwardU128(start: U128, n: Int): U128 {
    if (U128.forwardChecked(start, n) == null) {
        U128.MAX_VALUE + 1
    }
    return start.wrappingAdd(U128.fromInt(n))
}

public fun stepBackwardU128(start: U128, n: Int): U128 {
    if (U128.backwardChecked(start, n) == null) {
        U128.MIN_VALUE - 1
    }
    return start.wrappingSub(U128.fromInt(n))
}

public fun stepForwardUncheckedU128(start: U128, n: Int): U128 = start.uncheckedAdd(U128.fromInt(n))

public fun stepBackwardUncheckedU128(start: U128, n: Int): U128 = start.uncheckedSub(U128.fromInt(n))

public fun stepStepsBetweenU128(start: U128, end: U128): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end - start)) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedU128(start: U128, n: Int): U128? = start.checkedAdd(U128.fromInt(n))

public fun stepBackwardCheckedU128(start: U128, n: Int): U128? = start.checkedSub(U128.fromInt(n))

public fun stepForwardOverflowingU128(start: U128, n: Int): Pair<U128, Boolean> = start.overflowingAdd(U128.fromInt(n))

public fun stepBackwardOverflowingU128(start: U128, n: Int): Pair<U128, Boolean> = start.overflowingSub(U128.fromInt(n))

public fun stepForwardI128(start: I128, n: Int): I128 {
    if (I128.forwardChecked(start, n) == null) {
        I128.MAX_VALUE + 1
    }
    return start.wrappingAdd(I128.fromInt(n))
}

public fun stepBackwardI128(start: I128, n: Int): I128 {
    if (I128.backwardChecked(start, n) == null) {
        I128.MIN_VALUE - 1
    }
    return start.wrappingSub(I128.fromInt(n))
}

public fun stepForwardUncheckedI128(start: I128, n: Int): I128 = start.checkedAddUnsigned(U128.fromInt(n)).unwrapUnchecked()

public fun stepBackwardUncheckedI128(start: I128, n: Int): I128 = start.checkedSubUnsigned(U128.fromInt(n)).unwrapUnchecked()

public fun stepStepsBetweenI128(start: I128, end: I128): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = end.checkedSub(start)) {
            null -> Pair(Int.MAX_VALUE, null)
            else -> {
                val result = matchVal
                when (val matchVal1 = Int.TryFrom(result)) {
                    is Result.Ok<*, *> -> {
                        val steps = matchVal1.okValue()
                        Pair(steps, steps)
                    }
                    else -> {
                        Pair(Int.MAX_VALUE, null)
                    }
                }
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedI128(start: I128, n: Int): I128? = start.checkedAdd(I128.fromInt(n))

public fun stepBackwardCheckedI128(start: I128, n: Int): I128? = start.checkedSub(I128.fromInt(n))

public fun stepForwardOverflowingI128(start: I128, n: Int): Pair<I128, Boolean> = start.overflowingAddUnsigned(U128.fromInt(n))

public fun stepBackwardOverflowingI128(start: I128, n: Int): Pair<I128, Boolean> = start.overflowingSubUnsigned(U128.fromInt(n))

public fun stepForwardUncheckedNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked<NonZeroU8Inner, UByte>(start.get().uncheckedAdd(n.toUByte()))

public fun stepBackwardUncheckedNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked<NonZeroU8Inner, UByte>(start.get().uncheckedSub(n.toUByte()))

public fun stepForwardNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner> {
    if (NonZero.forwardChecked(start, n) == null) {
        UByte.MAX_VALUE + 1u
    }
    return start.saturatingAdd(n.toUByte())
}

public fun stepBackwardNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner> {
    if (NonZero.backwardChecked(start, n) == null) {
        UByte.MIN_VALUE - 1u
    }
    return (NonZero.new<NonZeroU8Inner, UByte>(start.get().saturatingSub(n.toUByte())) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): Pair<NonZero<UByte, NonZeroU8Inner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): Pair<NonZero<UByte, NonZeroU8Inner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, end: NonZero<UByte, NonZeroU8Inner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner>? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedNonZeroUByte(start: NonZero<UByte, NonZeroU8Inner>, n: Int): NonZero<UByte, NonZeroU8Inner>? =
    run {
        val matchVal = UByte.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.get().checkedSub(n1)?.let(NonZero::new)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardUncheckedNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked<NonZeroU16Inner, UShort>(start.get().uncheckedAdd(n.toUShort()))

public fun stepBackwardUncheckedNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked<NonZeroU16Inner, UShort>(start.get().uncheckedSub(n.toUShort()))

public fun stepForwardNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner> {
    if (NonZero.forwardChecked(start, n) == null) {
        UShort.MAX_VALUE + 1u
    }
    return start.saturatingAdd(n.toUShort())
}

public fun stepBackwardNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner> {
    if (NonZero.backwardChecked(start, n) == null) {
        UShort.MIN_VALUE - 1u
    }
    return (NonZero.new<NonZeroU16Inner, UShort>(start.get().saturatingSub(n.toUShort())) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): Pair<NonZero<UShort, NonZeroU16Inner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): Pair<NonZero<UShort, NonZeroU16Inner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, end: NonZero<UShort, NonZeroU16Inner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner>? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedNonZeroUShort(start: NonZero<UShort, NonZeroU16Inner>, n: Int): NonZero<UShort, NonZeroU16Inner>? =
    run {
        val matchVal = UShort.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.get().checkedSub(n1)?.let(NonZero::new)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardUncheckedNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked<NonZeroU32Inner, UInt>(start.get().uncheckedAdd(n.toUInt()))

public fun stepBackwardUncheckedNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked<NonZeroU32Inner, UInt>(start.get().uncheckedSub(n.toUInt()))

public fun stepForwardNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner> {
    if (NonZero.forwardChecked(start, n) == null) {
        UInt.MAX_VALUE + 1u
    }
    return start.saturatingAdd(n.toUInt())
}

public fun stepBackwardNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner> {
    if (NonZero.backwardChecked(start, n) == null) {
        UInt.MIN_VALUE - 1u
    }
    return (NonZero.new<NonZeroU32Inner, UInt>(start.get().saturatingSub(n.toUInt())) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): Pair<NonZero<UInt, NonZeroU32Inner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): Pair<NonZero<UInt, NonZeroU32Inner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, end: NonZero<UInt, NonZeroU32Inner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner>? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedNonZeroUInt(start: NonZero<UInt, NonZeroU32Inner>, n: Int): NonZero<UInt, NonZeroU32Inner>? =
    run {
        val matchVal = UInt.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.get().checkedSub(n1)?.let(NonZero::new)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardUncheckedNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked<NonZeroU64Inner, ULong>(start.get().uncheckedAdd(n.toULong()))

public fun stepBackwardUncheckedNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked<NonZeroU64Inner, ULong>(start.get().uncheckedSub(n.toULong()))

public fun stepForwardNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner> {
    if (NonZero.forwardChecked(start, n) == null) {
        ULong.MAX_VALUE + 1uL
    }
    return start.saturatingAdd(n.toULong())
}

public fun stepBackwardNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner> {
    if (NonZero.backwardChecked(start, n) == null) {
        ULong.MIN_VALUE - 1uL
    }
    return (NonZero.new<NonZeroU64Inner, ULong>(start.get().saturatingSub(n.toULong())) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): Pair<NonZero<ULong, NonZeroU64Inner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): Pair<NonZero<ULong, NonZeroU64Inner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, end: NonZero<ULong, NonZeroU64Inner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner>? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedNonZeroULong(start: NonZero<ULong, NonZeroU64Inner>, n: Int): NonZero<ULong, NonZeroU64Inner>? =
    run {
        val matchVal = ULong.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.get().checkedSub(n1)?.let(NonZero::new)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardUncheckedNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked<NonZeroUsizeInner, Int>(start.get().uncheckedAdd(n))

public fun stepBackwardUncheckedNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked<NonZeroUsizeInner, Int>(start.get().uncheckedSub(n))

public fun stepForwardNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner> {
    if (NonZero.forwardChecked(start, n) == null) {
        Int.MAX_VALUE + 1
    }
    return start.saturatingAdd(n)
}

public fun stepBackwardNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner> {
    if (NonZero.backwardChecked(start, n) == null) {
        0 - 1
    }
    return (NonZero.new<NonZeroUsizeInner, Int>(start.get().saturatingSub(n)) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): Pair<NonZero<Int, NonZeroUsizeInner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): Pair<NonZero<Int, NonZeroUsizeInner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, end: NonZero<Int, NonZeroUsizeInner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner>? =
    run {
        val matchVal = Int.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.checkedAdd(n1)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepBackwardCheckedNonZeroInt(start: NonZero<Int, NonZeroUsizeInner>, n: Int): NonZero<Int, NonZeroUsizeInner>? =
    run {
        val matchVal = Int.TryFrom(n)
        when (matchVal) { is Result.Ok<*, *> -> {
            val n1 = matchVal.okValue()
            start.get().checkedSub(n1)?.let(NonZero::new)
        }; is Result.Err<*, *> -> {
            null
        } }
    }

public fun stepForwardUncheckedNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked<NonZeroU128Inner, U128>(start.get().uncheckedAdd(U128.fromInt(n)))

public fun stepBackwardUncheckedNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked<NonZeroU128Inner, U128>(start.get().uncheckedSub(U128.fromInt(n)))

public fun stepForwardNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner> {
    if (NonZero.forwardChecked(start, n) == null) {
        U128.MAX_VALUE + U128(0x0uL, 0x1uL)
    }
    return start.saturatingAdd(U128.fromInt(n))
}

public fun stepBackwardNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner> {
    if (NonZero.backwardChecked(start, n) == null) {
        U128.MIN_VALUE - U128(0x0uL, 0x1uL)
    }
    return (NonZero.new<NonZeroU128Inner, U128>(start.get().saturatingSub(U128.fromInt(n))) ?: NonZero.MIN)
}

public fun stepForwardOverflowingNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): Pair<NonZero<U128, NonZeroU128Inner>, Boolean> =
    when (val matchVal = Step.forwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MAX, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepBackwardOverflowingNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): Pair<NonZero<U128, NonZeroU128Inner>, Boolean> =
    when (val matchVal = Step.backwardChecked(start, n)) {
        null -> {
            Pair(NonZero.MIN, true)
        }
        else -> {
            val s = matchVal
            Pair(s, false)
        }
    }

public fun stepStepsBetweenNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, end: NonZero<U128, NonZeroU128Inner>): Pair<Int, Int?> =
    if (start <= end) {
        when (val matchVal = Int.TryFrom(end.get() - start.get())) {
            is Result.Ok<*, *> -> {
                val steps = matchVal.okValue()
                Pair(steps, steps)
            }
            else -> {
                Pair(Int.MAX_VALUE, null)
            }
        }
    } else {
        Pair(0, null)
    }

public fun stepForwardCheckedNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner>? = start.checkedAdd(U128.fromInt(n))

public fun stepBackwardCheckedNonZeroU128(start: NonZero<U128, NonZeroU128Inner>, n: Int): NonZero<U128, NonZeroU128Inner>? = start.get().checkedSub(U128.fromInt(n))?.let(NonZero::new)

public fun stepStepsBetweenChar(start: Char, end: Char): Pair<Int, Int?> {
    val start1 = start.toUInt()
    val end1 = end.toUInt()
    return if (start1 <= end1) {
        val count = end1 - start1
        if (start1 < 0xD800u && 0xE000u <= end1) {
            when (val matchVal = Int.TryFrom(count - 0x800u)) {
                is Result.Ok<*, *> -> {
                    val steps = matchVal.okValue()
                    Pair(steps, steps)
                }
                else -> {
                    Pair(Int.MAX_VALUE, null)
                }
            }
        } else {
            when (val matchVal1 = Int.TryFrom(count)) {
                is Result.Ok<*, *> -> {
                    val steps1 = matchVal1.okValue()
                    Pair(steps1, steps1)
                }
                else -> {
                    Pair(Int.MAX_VALUE, null)
                }
            }
        }
    } else {
        Pair(0, null)
    }
}

public fun stepForwardCheckedChar(start: Char, count: Int): Char? {
    val start1 = start.toUInt()
    var res = (Step.forwardChecked(start1, count) ?: return null)
    if (start1 < 0xD800u && 0xD800 <= res) {
        res = (Step.forwardChecked(res, 0x800) ?: return null)
    }
    return if (res <= Char.MAX_VALUE.toUInt()) {
        // SAFETY: res is a valid unicode scalar
        // (below 0x110000 and not in 0xD800..0xE000)
        Char.fromU32Unchecked(res)
    } else {
        null
    }
}

public fun stepBackwardCheckedChar(start: Char, count: Int): Char? {
    val start1 = start.toUInt()
    var res = (Step.backwardChecked(start1, count) ?: return null)
    if (start1 >= 0xE000u && 0xE000 > res) {
        res = (Step.backwardChecked(res, 0x800) ?: return null)
    }
    // SAFETY: res is a valid unicode scalar
    // (below 0x110000 and not in 0xD800..0xE000)
    return Char.fromU32Unchecked(res)
}

// Note: These char overflowing implementations were chosen for
// code simplicity. Alternative impls were examined, and some
// yielded marginally simpler assembly, but none resulted in the same
// loop -> arithmetic optimizations seen with the bare integers.
public fun stepForwardOverflowingChar(start: Char, count: Int): Pair<Char, Boolean> =
    when (val matchVal = Step.forwardChecked(start, count)) {
        null -> {
            Pair(Char.MAX, true)
        }
        else -> {
            val c = matchVal
            Pair(c, false)
        }
    }

public fun stepBackwardOverflowingChar(start: Char, count: Int): Pair<Char, Boolean> =
    when (val matchVal = Step.backwardChecked(start, count)) {
        null -> {
            Pair(Char.MIN, true)
        }
        else -> {
            val c = matchVal
            Pair(c, false)
        }
    }

public fun stepForwardUncheckedChar(start: Char, count: Int): Char {
    val start1 = start.toUInt()
    // SAFETY: the caller must guarantee that this doesn't overflow
    // the range of values for a char.
    var res = Step.forwardUnchecked(start1, count)
    if (start1 < 0xD800u && 0xD800 <= res) {
        // SAFETY: the caller must guarantee that this doesn't overflow
        // the range of values for a char.
        res = Step.forwardUnchecked(res, 0x800)
    }
    // SAFETY: because of the previous contract, this is guaranteed
    // by the caller to be a valid char.
    return Char.fromU32Unchecked(res)
}

public fun stepBackwardUncheckedChar(start: Char, count: Int): Char {
    val start1 = start.toUInt()
    // SAFETY: the caller must guarantee that this doesn't overflow
    // the range of values for a char.
    var res = Step.backwardUnchecked(start1, count)
    if (start1 >= 0xE000u && 0xE000 > res) {
        // SAFETY: the caller must guarantee that this doesn't overflow
        // the range of values for a char.
        res = Step.backwardUnchecked(res, 0x800)
    }
    // SAFETY: because of the previous contract, this is guaranteed
    // by the caller to be a valid char.
    return Char.fromU32Unchecked(res)
}

public fun stepStepsBetweenAsciiChar(start: AsciiChar, end: AsciiChar): Pair<Int, Int?> = Step.stepsBetween(start.toU8(), end.toU8())

public fun stepForwardCheckedAsciiChar(start: AsciiChar, count: Int): AsciiChar? {
    val end = (Step.forwardChecked(start.toU8(), count) ?: return null)
    return AsciiChar.fromU8(end)
}

public fun stepBackwardCheckedAsciiChar(start: AsciiChar, count: Int): AsciiChar? {
    val end = (Step.backwardChecked(start.toU8(), count) ?: return null)
    // SAFETY: Values below that of a valid ASCII character are also valid ASCII
    return AsciiChar.fromU8Unchecked(end)
}

public fun stepForwardOverflowingAsciiChar(start: AsciiChar, count: Int): Pair<AsciiChar, Boolean> {
    val (s, o) = (start.toInt()).overflowingAdd(count)
    val ret = s and (AsciiChar.MAX.toInt())
    // SAFETY: Clamped to [0, MAX], must be valid ASCII
    return Pair(AsciiChar.fromU8Unchecked(ret.toUByte()), o || ret < s)
}

public fun stepBackwardOverflowingAsciiChar(start: AsciiChar, count: Int): Pair<AsciiChar, Boolean> {
    val (s, o) = (start.toInt()).overflowingSub(count)
    val ret = s and (AsciiChar.MAX.toInt())
    // SAFETY: Clamped to [0, MAX], must be valid ASCII
    return Pair(AsciiChar.fromU8Unchecked(ret.toUByte()), o || ret < s)
}

public fun stepForwardUncheckedAsciiChar(start: AsciiChar, count: Int): AsciiChar {
    // SAFETY: Caller asserts that result is a valid ASCII character,
    // and therefore it is a valid u8.
    val end = Step.forwardUnchecked(start.toU8(), count)
    // SAFETY: Caller asserts that result is a valid ASCII character.
    return AsciiChar.fromU8Unchecked(end)
}

public fun stepBackwardUncheckedAsciiChar(start: AsciiChar, count: Int): AsciiChar {
    // SAFETY: Caller asserts that result is a valid ASCII character,
    // and therefore it is a valid u8.
    val end = Step.backwardUnchecked(start.toU8(), count)
    // SAFETY: Caller asserts that result is a valid ASCII character.
    return AsciiChar.fromU8Unchecked(end)
}

public fun stepStepsBetweenIpv4Addr(start: Ipv4Addr, end: Ipv4Addr): Pair<Int, Int?> = UInt.stepsBetween(start.toBits(), end.toBits())

public fun stepForwardCheckedIpv4Addr(start: Ipv4Addr, count: Int): Ipv4Addr? = UInt.forwardChecked(start.toBits(), count).map(Ipv4Addr::fromBits)

public fun stepBackwardCheckedIpv4Addr(start: Ipv4Addr, count: Int): Ipv4Addr? = UInt.backwardChecked(start.toBits(), count).map(Ipv4Addr::fromBits)

public fun stepForwardOverflowingIpv4Addr(start: Ipv4Addr, count: Int): Pair<Ipv4Addr, Boolean> {
    val (s, o) = UInt.forwardOverflowing(start.toBits(), count)
    return Pair(Ipv4Addr.fromBits(s), o)
}

public fun stepBackwardOverflowingIpv4Addr(start: Ipv4Addr, count: Int): Pair<Ipv4Addr, Boolean> {
    val (s, o) = UInt.backwardOverflowing(start.toBits(), count)
    return Pair(Ipv4Addr.fromBits(s), o)
}

public fun stepForwardUncheckedIpv4Addr(start: Ipv4Addr, count: Int): Ipv4Addr {
    // SAFETY: Since u32 and Ipv4Addr are losslessly convertible,
    // this is as safe as the u32 version.
    return Ipv4Addr.fromBits(UInt.forwardUnchecked(start.toBits(), count))
}

public fun stepBackwardUncheckedIpv4Addr(start: Ipv4Addr, count: Int): Ipv4Addr {
    // SAFETY: Since u32 and Ipv4Addr are losslessly convertible,
    // this is as safe as the u32 version.
    return Ipv4Addr.fromBits(UInt.backwardUnchecked(start.toBits(), count))
}

public fun stepStepsBetweenIpv6Addr(start: Ipv6Addr, end: Ipv6Addr): Pair<Int, Int?> = U128.stepsBetween(start.toBits(), end.toBits())

public fun stepForwardCheckedIpv6Addr(start: Ipv6Addr, count: Int): Ipv6Addr? = U128.forwardChecked(start.toBits(), count).map(Ipv6Addr::fromBits)

public fun stepBackwardCheckedIpv6Addr(start: Ipv6Addr, count: Int): Ipv6Addr? = U128.backwardChecked(start.toBits(), count).map(Ipv6Addr::fromBits)

public fun stepForwardOverflowingIpv6Addr(start: Ipv6Addr, count: Int): Pair<Ipv6Addr, Boolean> {
    val (s, o) = U128.forwardOverflowing(start.toBits(), count)
    return Pair(Ipv6Addr.fromBits(s), o)
}

public fun stepBackwardOverflowingIpv6Addr(start: Ipv6Addr, count: Int): Pair<Ipv6Addr, Boolean> {
    val (s, o) = U128.backwardOverflowing(start.toBits(), count)
    return Pair(Ipv6Addr.fromBits(s), o)
}

public fun stepForwardUncheckedIpv6Addr(start: Ipv6Addr, count: Int): Ipv6Addr {
    // SAFETY: Since u128 and Ipv6Addr are losslessly convertible,
    // this is as safe as the u128 version.
    return Ipv6Addr.fromBits(U128.forwardUnchecked(start.toBits(), count))
}

public fun stepBackwardUncheckedIpv6Addr(start: Ipv6Addr, count: Int): Ipv6Addr {
    // SAFETY: Since u128 and Ipv6Addr are losslessly convertible,
    // this is as safe as the u128 version.
    return Ipv6Addr.fromBits(U128.backwardUnchecked(start.toBits(), count))
}

/**
 * Specialization implementations for `Range`.
 */
public interface RangeIteratorImpl<out Item> {
    public fun specNext(): Item?

    public fun specNth(n: Int): Item?

    public fun specAdvanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>

    public fun specNextBack(): Item?

    public fun specNthBack(n: Int): Item?

    public fun specAdvanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>
}

public fun <A : Step<A>> Range<A>.specNext(): A? =
    if (this.start < this.end) {
        val n = (Step.forwardChecked(this.start.clone(), 1) ?: error("`Step` invariants not upheld"))
        replace(this.start, n)
    } else {
        null
    }

public fun <A : Step<A>> Range<A>.specNth(n: Int): A? {
    when (val matchVal = Step.forwardChecked(this.start.clone(), n)) {
        null -> {}
        else -> {
            val plusN = matchVal
            if (plusN < this.end) {
                this.start = (Step.forwardChecked(plusN.clone(), 1) ?: error("`Step` invariants not upheld"))
                return plusN
            }
        }
    }
    this.start = this.end.clone()
    return null
}

public fun <A : Step<A>> Range<A>.specAdvanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
    val steps = Step.stepsBetween(this.start, this.end)
    val available = steps.second.unwrapOr(steps.first)
    val taken = available.min(n)
    this.start = (Step.forwardChecked(this.start.clone(), taken) ?: error("`Step` invariants not upheld"))
    return ((NonZero.new<NonZeroUsizeInner, Int>(n - taken))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
}

public fun <A : Step<A>> Range<A>.specNextBack(): A? =
    if (this.start < this.end) {
        this.end = (Step.backwardChecked(this.end.clone(), 1) ?: error("`Step` invariants not upheld"))
        this.end.clone()
    } else {
        null
    }

public fun <A : Step<A>> Range<A>.specNthBack(n: Int): A? {
    when (val matchVal = Step.backwardChecked(this.end.clone(), n)) {
        null -> {}
        else -> {
            val minusN = matchVal
            if (minusN > this.start) {
                this.end = (Step.backwardChecked(minusN, 1) ?: error("`Step` invariants not upheld"))
                return this.end.clone()
            }
        }
    }
    this.end = this.start.clone()
    return null
}

public fun <A : Step<A>> Range<A>.specAdvanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
    val steps = Step.stepsBetween(this.start, this.end)
    val available = steps.second.unwrapOr(steps.first)
    val taken = available.min(n)
    this.end = (Step.backwardChecked(this.end.clone(), taken) ?: error("`Step` invariants not upheld"))
    return ((NonZero.new<NonZeroUsizeInner, Int>(n - taken))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
}

public fun <A : Step<A>> Range<A>.next(): A? = this.specNext()

public fun <A : Step<A>> Range<A>.sizeHint(): Pair<Int, Int?> =
    if (this.start < this.end) {
        Step.stepsBetween(this.start, this.end)
    } else {
        Pair(0, 0)
    }

public fun <A : Step<A>> Range<A>.count(): Int =
    if (this.start < this.end) {
        Step.stepsBetween(this.start, this.end).second.expect("count overflowed usize")
    } else {
        0
    }

public fun <A : Step<A>> Range<A>.nth(n: Int): A? = this.specNth(n)

public fun <A : Step<A>> Range<A>.last(): A? = this.nextBack()

public fun <A : Step<A>> Range<A>.min(): A? = this.next()

public fun <A : Step<A>> Range<A>.max(): A? = this.nextBack()

public fun <A : Step<A>> Range<A>.isSorted(): Boolean = true

public fun <A : Step<A>> Range<A>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.specAdvanceBy(n)

public fun <A : Step<A>> Range<A>.iteratorGetUnchecked(idx: Int): A {
    // SAFETY: The TrustedRandomAccess contract requires that callers only pass an index
    // that is in bounds.
    // Additionally Self: TrustedRandomAccess is only implemented for Copy types
    // which means even repeated reads of the same index would be safe.
    return Step.forwardUnchecked(this.start.clone(), idx)
}

// These are incorrect per the reasoning above,
// but removing them would be a breaking change as they were stabilized in Rust 1.0.0.
// So e.g. `(0 .. 66_000_u32).len()` for example will compile without error or warnings
// on 16-bit platforms, but continue to give a wrong result.

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

public val MAY_HAVE_SIDE_EFFECT10: Boolean = false

public val MAY_HAVE_SIDE_EFFECT11: Boolean = false

public val MAY_HAVE_SIDE_EFFECT12: Boolean = false

public val MAY_HAVE_SIDE_EFFECT13: Boolean = false

public val MAY_HAVE_SIDE_EFFECT14: Boolean = false

// Since RangeInclusive<NonZero<uN>> can only be 1..=uN.Max the length of this range is always
// <= uN.Max, so they are always valid ExactSizeIterator unlike the ranges that include zero.

// These are incorrect per the reasoning above,
// but removing them would be a breaking change as they were stabilized in Rust 1.26.0.
// So e.g. `(0 ..= UShort.MAX).len()` for example will compile without error or warnings
// on 16-bit platforms, but continue to give a wrong result.

public fun <A : Step<A>> Range<A>.nextBack(): A? = this.specNextBack()

public fun <A : Step<A>> Range<A>.nthBack(n: Int): A? = this.specNthBack(n)

public fun <A : Step<A>> Range<A>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.specAdvanceBackBy(n)

// Safety:
// The following invariants for `Step.stepsBetween` exist:
//
// > * `stepsBetween(a, b) == (n, Some(n))` only if `a <= b`
// >   * Note that `a <= b` does _not_ imply `stepsBetween(a, b) != (n, None)`;
// >     this is the case when it would require more than `Int.MAX` steps to
// >     get to `b`
// > * `stepsBetween(a, b) == (0, None)` if `a> b`
//
// The first invariant is what is generally required for `TrustedLen` to be
// sound. The note addendum satisfies an additional `TrustedLen` invariant.
//
// > The upper bound must only be `None` if the actual iterator length is larger
// > than `Int.MAX`
//
// The second invariant logically follows the first so long as the `PartialOrd`
// implementation is correct; regardless it is explicitly stated. If `a<b`
// then `(0, Some(0))` is returned by `ops.Range<A: Step>.sizeHint`. As such
// the second invariant is upheld.

public fun <A : Step<A>> RangeFrom<A>.next(): A? {
    val n = Step.forward(this.start.clone(), 1)
    return replace(this.start, n)
}

public fun <A : Step<A>> RangeFrom<A>.sizeHint(): Pair<Int, Int?> = Pair(Int.MAX_VALUE, null)

public fun <A : Step<A>> RangeFrom<A>.nth(n: Int): A? {
    val plusN = Step.forward(this.start.clone(), n)
    this.start = Step.forward(plusN.clone(), 1)
    return plusN
}

// Safety: See above implementation for `ops.Range<A>`

public interface RangeInclusiveIteratorImpl<Item> {
    public fun <Residual, B, R> specTryFold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual>

    public fun <Residual, B, R> specTryRfold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual>
}

public fun <Residual, A : Step<A>, B, R> RangeInclusive<A>.specTryFold(init: B, f: (B, A) -> R): R where R : Try<R, B, Residual> {
    if (this.isEmpty()) {
        return init
    }
    var accum = init
    while (this.start < this.end) {
        val n = (Step.forwardChecked(this.start.clone(), 1) ?: error("`Step` invariants not upheld"))
        val n1 = replace(this.start, n)
        accum = when (val tryResult = f(accum, n1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    this.exhausted = true
    if (this.start == this.end) {
        accum = when (val tryResult = f(accum, this.start.clone())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Residual, A : Step<A>, B, R> RangeInclusive<A>.specTryRfold(init: B, f: (B, A) -> R): R where R : Try<R, B, Residual> {
    if (this.isEmpty()) {
        return init
    }
    var accum = init
    while (this.start < this.end) {
        val n = (Step.backwardChecked(this.end.clone(), 1) ?: error("`Step` invariants not upheld"))
        val n1 = replace(this.end, n)
        accum = when (val tryResult = f(accum, n1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    this.exhausted = true
    if (this.start == this.end) {
        accum = when (val tryResult = f(accum, this.start.clone())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Residual, T : TrustedStep<T>, B, R> RangeInclusive<T>.specTryFold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> {
    if (this.isEmpty()) {
        return init
    }
    var accum = init
    while (this.start < this.end) {
        // SAFETY: just checked precondition
        val n = Step.forwardUnchecked(this.start, 1)
        val n1 = replace(this.start, n)
        accum = when (val tryResult = f(accum, n1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    this.exhausted = true
    if (this.start == this.end) {
        accum = when (val tryResult = f(accum, this.start)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Residual, T : TrustedStep<T>, B, R> RangeInclusive<T>.specTryRfold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> {
    if (this.isEmpty()) {
        return init
    }
    var accum = init
    while (this.start < this.end) {
        // SAFETY: just checked precondition
        val n = Step.backwardUnchecked(this.end, 1)
        val n1 = replace(this.end, n)
        accum = when (val tryResult = f(accum, n1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    this.exhausted = true
    if (this.start == this.end) {
        accum = when (val tryResult = f(accum, this.start)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <A : Step<A>> RangeInclusive<A>.next(): A? {
    if (this.isEmpty()) {
        return null
    }
    val (n, o) = Step.forwardOverflowing(this.start.clone(), 1)
    this.exhausted = o
    return replace(this.start, n)
}

public fun <A : Step<A>> RangeInclusive<A>.sizeHint(): Pair<Int, Int?> {
    if (this.isEmpty()) {
        return Pair(0, 0)
    }
    val hint = Step.stepsBetween(this.start, this.end)
    return Pair(hint.first.saturatingAdd(1), hint.second.andThen({ steps -> steps.checkedAdd(1) }))
}

public fun <A : Step<A>> RangeInclusive<A>.count(): Int {
    if (this.isEmpty()) {
        return 0
    }
    return Step.stepsBetween(this.start, this.end).second.andThen({ steps -> steps.checkedAdd(1) }).expect("count overflowed usize")
}

public fun <A : Step<A>> RangeInclusive<A>.nth(n: Int): A? {
    if (this.isEmpty()) {
        return null
    }
    val (plusN, on) = Step.forwardOverflowing(this.start.clone(), n)
    val (plus1, o1) = Step.forwardOverflowing(plusN.clone(), 1)
    this.start = plus1
    this.exhausted = on or o1
    return if (!on && plusN <= this.end) {
        plusN
    } else {
        null
    }
}

public fun <Residual, A : Step<A>, B, R> RangeInclusive<A>.tryFold(init: B, f: (B, A) -> R): R where R : Try<R, B, Residual> = this.specTryFold(init, f)

public fun <A : Step<A>, Aaa> RangeInclusive<A>.fold(init: Aaa, fold: (Aaa, A) -> Aaa): Aaa = this.tryFold(init, NeverShortCircuit.wrapMut2(fold)).first

public fun <A : Step<A>> RangeInclusive<A>.last(): A? = this.nextBack()

public fun <A : Step<A>> RangeInclusive<A>.min(): A? = this.next()

public fun <A : Step<A>> RangeInclusive<A>.max(): A? = this.nextBack()

public fun <A : Step<A>> RangeInclusive<A>.isSorted(): Boolean = true

public fun <A : Step<A>> RangeInclusive<A>.nextBack(): A? {
    if (this.isEmpty()) {
        return null
    }
    val (n, o) = Step.backwardOverflowing(this.end.clone(), 1)
    this.exhausted = o
    return replace(this.end, n)
}

public fun <A : Step<A>> RangeInclusive<A>.nthBack(n: Int): A? {
    if (this.isEmpty()) {
        return null
    }
    val (minusN, on) = Step.backwardOverflowing(this.end.clone(), n)
    val (minus1, o1) = Step.backwardOverflowing(minusN.clone(), 1)
    this.end = minus1
    this.exhausted = on or o1
    return if (!on && minusN >= this.start) {
        minusN
    } else {
        null
    }
}

public fun <Residual, A : Step<A>, B, R> RangeInclusive<A>.tryRfold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual> = this.specTryRfold(init, f)

public fun <A : Step<A>, Aaa> RangeInclusive<A>.rfold(init: Aaa, fold: (Aaa, Item) -> Aaa): Aaa = this.tryRfold(init, NeverShortCircuit.wrapMut2(fold)).first

// Safety: See above implementation for `ops.Range<A>`
