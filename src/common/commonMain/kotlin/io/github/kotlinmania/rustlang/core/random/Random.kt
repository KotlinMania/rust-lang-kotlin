// port-lint: source random.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.random

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.rustWrappingNeg
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed

// Random value generation.

/**
 * A source of randomness.
 */
public interface Rng {
    /**
     * Fills `bytes` with random bytes.
     *
     * Note that calling `fillBytes` multiple times is not equivalent to calling `fillBytes` once
     * with a larger buffer. An `Rng` is allowed to return different bytes for those two cases. For
     * instance, this allows an `Rng` to generate a word at a time and throw part of it away if not
     * needed.
     */
    public fun fillBytes(bytes: MutableList<UByte>)
}

/**
 * A trait representing a distribution of random values for a type.
 */
public interface Distribution<T> {
    /**
     * Samples a random value from the distribution, using the specified random source.
     */
    public fun sample(source: Rng): T
}

public fun RangeFull.sample(source: Rng): Boolean {
    val byte: UByte = RangeFull.sample(source)
    return byte and 1u.toUByte() == 1u.toUByte()
}

// Use 32-bit integers for small integers since it reduces the likelihood of
// sample rejections.
public fun RangeFull.sampleUByte(source: Rng): UByte {
    val bytes = ubyteArrayOf((0.toUByte()))
    source.fillBytes(bytes)
    return bytes[0]
}

public fun RangeFull.sampleByte(source: Rng): Byte {
    val bytes = ubyteArrayOf((0.toByte()).toUByte())
    source.fillBytes(bytes)
    return bytes[0].toByte()
}

public fun RangeFull.sampleUShort(source: Rng): UShort {
    val bytes = run { val rawBits = (0.toUShort()).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp0 = bytes; ((bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()).toUShort() }
}

public fun RangeFull.sampleShort(source: Rng): Short {
    val bytes = run { val rawBits = (0.toShort()).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp1 = bytes; ((bytesTmp1[1].toInt() shl 8) or bytesTmp1[0].toInt()).toShort() }
}

public fun RangeFull.sampleUInt(source: Rng): UInt {
    val bytes = run { val rawBits = (0.toUInt()); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp2 = bytes; ((bytesTmp2[3].toUInt() shl 24) or (bytesTmp2[2].toUInt() shl 16) or (bytesTmp2[1].toUInt() shl 8) or bytesTmp2[0].toUInt()) }
}

public fun RangeFull.sampleInt(source: Rng): Int {
    val bytes = run { val rawBits = (0).toUInt(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp3 = bytes; ((bytesTmp3[3].toInt() shl 24) or (bytesTmp3[2].toInt() shl 16) or (bytesTmp3[1].toInt() shl 8) or bytesTmp3[0].toInt()) }
}

public fun RangeFull.sampleULong(source: Rng): ULong {
    val bytes = run { val rawBits = (0.toULong()); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp4 = bytes; ((bytesTmp4[7].toULong() shl 56) or (bytesTmp4[6].toULong() shl 48) or (bytesTmp4[5].toULong() shl 40) or (bytesTmp4[4].toULong() shl 32) or (bytesTmp4[3].toULong() shl 24) or (bytesTmp4[2].toULong() shl 16) or (bytesTmp4[1].toULong() shl 8) or bytesTmp4[0].toULong()) }
}

public fun RangeFull.sampleLong(source: Rng): Long {
    val bytes = run { val rawBits = (0.toLong()).toULong(); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 56).toUByte()) }
    source.fillBytes(bytes)
    return run { val bytesTmp5 = bytes; ((bytesTmp5[7].toLong() shl 56) or (bytesTmp5[6].toLong() shl 48) or (bytesTmp5[5].toLong() shl 40) or (bytesTmp5[4].toLong() shl 32) or (bytesTmp5[3].toLong() shl 24) or (bytesTmp5[2].toLong() shl 16) or (bytesTmp5[1].toLong() shl 8) or bytesTmp5[0].toLong()) }
}

public fun RangeFull.sampleU128(source: Rng): U128 {
    val bytes = (U128.fromLong(0.toLong())).toLeBytes()
    source.fillBytes(bytes)
    return U128.fromLeBytes(bytes)
}

public fun RangeFull.sampleI128(source: Rng): I128 {
    val bytes = (I128.fromLong(0.toLong())).toLeBytes()
    source.fillBytes(bytes)
    return I128.fromLeBytes(bytes)
}

public fun emptyRange(): Nothing {
    error("cannot sample from an empty distribution")
}

public fun bounded32(bound: UInt, source: Rng): UInt {
    if (false) {
        when (val matchVal = Pair(bound, 0)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (leftVal == rightVal) {
                    val kind = AssertKind.Ne
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
    }
    val sample: UInt = (0 until 0).sample(source)
    var (l, res) = sample.carryingMul(bound, 0)
    if (l < bound) {
        val t = rustWrappingNeg(bound) % bound
        while (l < t) {
            val sample1: UInt = (0 until 0).sample(source)
            Pair(l, res) = sample1.carryingMul(bound, 0)
        }
    }
    if (false) {
        check(res < bound)
    }
    return res
}

public fun bounded64(bound: ULong, source: Rng): ULong {
    if (false) {
        when (val matchVal = Pair(bound, 0)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (leftVal == rightVal) {
                    val kind = AssertKind.Ne
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
    }
    val sample: ULong = (0 until 0).sample(source)
    var (l, res) = sample.carryingMul(bound, 0)
    if (l < bound) {
        val t = rustWrappingNeg(bound) % bound
        while (l < t) {
            val sample1: ULong = (0 until 0).sample(source)
            Pair(l, res) = sample1.carryingMul(bound, 0)
        }
    }
    if (false) {
        check(res < bound)
    }
    return res
}

public fun bounded128(bound: U128, source: Rng): U128 {
    if (false) {
        when (val matchVal = Pair(bound, 0)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (leftVal == rightVal) {
                    val kind = AssertKind.Ne
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
    }
    val sample: U128 = (0 until 0).sample(source)
    var (l, res) = sample.carryingMul(bound, 0)
    if (l < bound) {
        val t = rustWrappingNeg(bound) % bound
        while (l < t) {
            val sample1: U128 = (0 until 0).sample(source)
            Pair(l, res) = sample1.carryingMul(bound, 0)
        }
    }
    if (false) {
        check(res < bound)
    }
    return res
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<UByte>.sample(source: Rng): UByte {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UByte = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source).toUByte()
        }
    return (this.start + offset).toUByte()
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<Byte>.sampleByte(source: Rng): Byte {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = this.last.wrappingSub(this.start).castUnsigned().checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UByte = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source).toUByte()
        }
    return this.start.wrappingAddUnsigned(offset)
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<UShort>.sampleUShort(source: Rng): UShort {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UShort = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source).toUShort()
        }
    return (this.start + offset).toUShort()
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<Short>.sampleShort(source: Rng): Short {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = this.last.wrappingSub(this.start).castUnsigned().checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UShort = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source).toUShort()
        }
    return this.start.wrappingAddUnsigned(offset)
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<UInt>.sampleUInt(source: Rng): UInt {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UInt = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source)
        }
    return this.start + offset
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun IntRange.sample(source: Rng): Int {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = this.last.wrappingSub(this.start).castUnsigned().checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: UInt = RangeFull.sample(source)
            sample and (bound - 1u)
        } else {
            bounded32(bound.toUInt(), source)
        }
    return this.start.wrappingAddUnsigned(offset)
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<ULong>.sampleULong(source: Rng): ULong {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: ULong = RangeFull.sample(source)
            sample and (bound - 1uL)
        } else {
            bounded64(bound.toULong(), source)
        }
    return this.start + offset
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<Long>.sampleLong(source: Rng): Long {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = this.last.wrappingSub(this.start).castUnsigned().checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: ULong = RangeFull.sample(source)
            sample and (bound - 1uL)
        } else {
            bounded64(bound.toULong(), source)
        }
    return this.start.wrappingAddUnsigned(offset)
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<U128>.sampleU128(source: Rng): U128 {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: U128 = RangeFull.sample(source)
            sample and (bound - U128(0x0uL, 0x1uL))
        } else {
            bounded128(U128.fromLong(bound.toLong()), source)
        }
    return this.start + offset
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun RangeInclusive<I128>.sampleI128(source: Rng): I128 {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = this.last.wrappingSub(this.start).castUnsigned().checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: U128 = RangeFull.sample(source)
            sample and (bound - U128(0x0uL, 0x1uL))
        } else {
            bounded128(U128.fromLong(bound.toLong()), source)
        }
    return this.start.wrappingAddUnsigned(offset)
}

/**
 * Chooses a random number within the range.
 *
 * Every possible result value is equally likely. In other words,
 * this operation uses unbiased uniform sampling.
 *
 * # Panics
 *
 * Panics if the range is empty.
 *
 * # Side-channels
 *
 * This implementation does not claim to be resistant against side-
 * channel attacks. In particular, the execution time of this operation
 * may leak information about the returned value, and not just the
 * values of the range bounds. While this implementation tries to
 * avoid operations with particularly data-dependent timing (such
 * as divisions), Rust as a language has no facilities for ensuring
 * data-independent timing, voiding all promises about side-channel-
 * freedom.
 *
 * # Examples
 *
 * A D20 dice roll:
 * ```kotlin
 * import std.random.Distribution
 * import std.random.SystemRng
 * import std.range.RangeInclusive
 *
 * @feature(random)
 * public fun doctestEntry() {
 *     val roll = RangeInclusive.from(1..20).sample(SystemRng)
 *     check(1 <= roll && roll <= 20)
 *     if (roll == 20) {
 *         println("Wow! You achieve writing a sound linked list.")
 *     } else {
 *         println("Miri attacks!")
 *     }
 * }
 * ```
 */
public fun IntRange.sampleInt(source: Rng): Int {
    if (this.start > this.last) {
        emptyRange()
    }
    if (this.start == this.last) {
        return this.start
    }
    val bound = (this.last - this.start).checkedAdd(1) ?: return RangeFull.sample(source)
    val offset =
        if (bound.isPowerOfTwo()) {
            val sample: Int = RangeFull.sample(source)
            sample and (bound - 1)
        } else {
            bounded64(bound.toULong(), source).toInt()
        }
    return this.start + offset
}
