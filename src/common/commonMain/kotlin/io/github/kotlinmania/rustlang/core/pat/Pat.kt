// port-lint: source pat.rs
package io.github.kotlinmania.rustlang.core.pat

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.checkedSub

// Helper module for exporting the `patternType` macro

/**
 * A trait implemented for integer types and `char`.
 * Useful in the future for generic pattern types, but
 * used right now to simplify ast lowering of pattern type ranges.
 */
public interface RangePattern<T> {
    /**
     * Trait version of the inherent `MIN` assoc const.
     */
    public val MIN: T

    /**
     * Trait version of the inherent `MIN` assoc const.
     */
    public val MAX: T

    /**
     * A compile-time helper to subtract 1 for exclusive ranges.
     */
    public fun subOne(): T
}

public val MIN: Byte = Byte.MIN_VALUE

public val MAX: Byte = Byte.MAX_VALUE

public fun Byte.subOne(): Byte =
    when (val matchVal = this.checkedSub(1.toByte())) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN1: Short = Short.MIN_VALUE

public val MAX1: Short = Short.MAX_VALUE

public fun Short.subOne(): Short =
    when (val matchVal = this.checkedSub(1.toShort())) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN2: Int = Int.MIN_VALUE

public val MAX2: Int = Int.MAX_VALUE

public fun Int.subOne(): Int =
    when (val matchVal = this.checkedSub(1)) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN3: Long = Long.MIN_VALUE

public val MAX3: Long = Long.MAX_VALUE

public fun Long.subOne(): Long =
    when (val matchVal = this.checkedSub(1L)) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN4: I128 = I128.MIN_VALUE

public val MAX4: I128 = I128.MAX_VALUE

public fun I128.subOne(): I128 =
    when (val matchVal = this.checkedSub(I128(0x0L, 0x1uL))) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN5: Int = Int.MIN_VALUE

public val MAX5: Int = Int.MAX_VALUE

public val MIN6: UByte = UByte.MIN_VALUE

public val MAX6: UByte = UByte.MAX_VALUE

public fun UByte.subOne(): UByte =
    when (val matchVal = this.checkedSub(1u.toUByte())) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN7: UShort = UShort.MIN_VALUE

public val MAX7: UShort = UShort.MAX_VALUE

public fun UShort.subOne(): UShort =
    when (val matchVal = this.checkedSub(1u.toUShort())) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN8: UInt = UInt.MIN_VALUE

public val MAX8: UInt = UInt.MAX_VALUE

public fun UInt.subOne(): UInt =
    when (val matchVal = this.checkedSub(1u)) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN9: ULong = ULong.MIN_VALUE

public val MAX9: ULong = ULong.MAX_VALUE

public fun ULong.subOne(): ULong =
    when (val matchVal = this.checkedSub(1uL)) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN10: U128 = U128.MIN_VALUE

public val MAX10: U128 = U128.MAX_VALUE

public fun U128.subOne(): U128 =
    when (val matchVal = this.checkedSub(U128(0x0uL, 0x1uL))) {
        null -> error("exclusive range end at minimum value of type")
        else -> {
            val value = matchVal
            value
        }
    }

public val MIN11: Int = 0

public val MAX11: Int = Int.MAX_VALUE

public val MIN12: Char = Char.MIN_VALUE

public val MAX12: Char = Char.MAX_VALUE

public fun Char.subOneChar(): Char =
    when (val matchVal = Char.fromU32(this.toUInt() - 1u)) {
        null -> error("exclusive range to start of valid chars")
        else -> {
            val value = matchVal
            value
        }
    }
