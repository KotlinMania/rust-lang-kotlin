// port-lint: source convert/num.rs
package io.github.kotlinmania.rustlang.core.convert.num

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmDoubleToU128Unchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmDoubleToU64Unchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloatToU128Unchecked
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloatToU64Unchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.floatToIntUncheckedWithLayout
import io.github.kotlinmania.rustlang.core.num.error.IntErrorKind
import io.github.kotlinmania.rustlang.core.num.error.TryFromIntError
import io.github.kotlinmania.rustlang.core.num.f16.nextDown
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroIsizeInner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result

// Implement `From<Boolean>` for integers
//
// Implement `From<$ small>` for `$ large`
//
// Implement `TryFrom<integer>` for `Boolean`

/**
 * Supporting trait for inherent methods of `Float` and `Double` such as `toIntUnchecked`.
 * Typically doesn’t need to be used directly.
 */
public interface FloatToInt<Int> {
    public fun toIntUnchecked(): Int

    public fun toIntSaturating(): Int

    public fun toIntChecked(): Int?
}

// boolean -> integer
// unsigned integer -> unsigned integer
// signed integer -> signed integer
// unsigned integer -> signed integer
// The C99 standard defines bounds on INTPTR_MIN, INTPTR_MAX, and UINTPTR_MAX
// which imply that pointer-sized integers must be at least 16 bits:
// https://port70.net/~nsz/c/c99/n1256.html#7.18.2.4
// RISC-V defines the possibility of a 128-bit address space (RV128).
// CHERI proposes 128-bit “capabilities”. Unclear if this would be relevant to Int/isize.
// https://www.cl.cam.ac.uk/research/security/ctsrd/pdfs/20171017a-cheri-poster.pdf
// https://www.cl.cam.ac.uk/techreports/UCAM-CL-TR-951.pdf
// Note: integers can only be represented with full precision in a float if
// they fit in the significand, which is:
// * 11 bits in f16
// * 24 bits in f32
// * 53 bits in f64
// * 113 bits in f128
// Lossy float conversions are not implemented at this time.
// Note(f16,f128): The `f16`/`f128` impls `@stable` attributes should be changed to reference
// `f16`/`f128` when they are stabilised (trait impls have to have a `@stable` attribute, but none
// of the `f16`/`f128` impls can be used on stable as the `f16` and `f128` types are unstable).
// signed integer -> float
// unsigned integer -> float
// float -> float
// Note(f16): adding the additional `From<{ float }>` impl to `Float` would break inference in cases
// like `Float.from(1.0)`. The type checker has a custom workaround to keep that and similar code
// compiling even with the second `From<16> for Float` instance. We keep this instance unstable for
// now so that we can later remove the workaround.
//
// See also <https://github.com/rust-lang/rust/issues/123831>.
// Also @unstable(feature = "f16", issue = "116909"):
// boolean -> float
// integer -> bool
// unsigned integer -> unsigned integer
// signed integer -> signed integer
// unsigned integer -> signed integer
// signed integer -> unsigned integer
// Int/isize
// non-zero unsigned integer -> non-zero unsigned integer
// non-zero signed integer -> non-zero signed integer
// non-zero unsigned -> non-zero signed integer
// integer -> non-zero integer
// unsigned non-zero integer -> unsigned non-zero integer
// signed non-zero integer -> signed non-zero integer
// unsigned non-zero integer -> signed non-zero integer
// signed non-zero integer -> unsigned non-zero integer
public fun F16.toIntUnchecked(): UByte = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.U8)).toUByte()

public fun F16.toIntSaturating(): UByte = this.toUByte()

public fun F16.toIntChecked(): UByte? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromUByte(UByte.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (UByte.MAX_VALUE / 2u) + 1u
            F16.fromUByte(half) + F16.fromUByte(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun F16.toIntUncheckedUShort(): UShort = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.U16)).toUShort()

public fun F16.toIntSaturatingUShort(): UShort = this.toUShort()

public fun F16.toIntCheckedUShort(): UShort? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromUShort(UShort.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (UShort.MAX_VALUE / 2u) + 1u
            F16.fromUShort(half) + F16.fromUShort(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUShort()
    } else {
        null
    }
}

public fun F16.toIntUncheckedUInt(): UInt = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.U32)).toUInt()

public fun F16.toIntSaturatingUInt(): UInt = this.toUInt()

public fun F16.toIntCheckedUInt(): UInt? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromUInt(UInt.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (UInt.MAX_VALUE / 2u) + 1u
            F16.fromUInt(half) + F16.fromUInt(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUInt()
    } else {
        null
    }
}

public fun F16.toIntUncheckedULong(): ULong = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.U64)).toULong()

public fun F16.toIntSaturatingULong(): ULong = this.toULong()

public fun F16.toIntCheckedULong(): ULong? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromULong(ULong.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (ULong.MAX_VALUE / 2uL) + 1uL
            F16.fromULong(half) + F16.fromULong(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toULong()
    } else {
        null
    }
}

public fun F16.toIntUncheckedU128(): U128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

public fun F16.toIntSaturatingU128(): U128 = U128.fromULong(this.toULong())

public fun F16.toIntCheckedU128(): U128? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromDouble(U128.MIN_VALUE.toDouble())
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (U128.MAX_VALUE / U128(0x0uL, 0x2uL)) + U128(0x0uL, 0x1uL)
            F16.fromDouble(half.toDouble()) + F16.fromDouble(half.toDouble())
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun F16.toIntUncheckedInt(): Int = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.U32)

public fun F16.toIntSaturatingInt(): Int = this.toInt()

public fun F16.toIntCheckedInt(): Int? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromInt(0)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (Int.MAX_VALUE / 2) + 1
            F16.fromInt(half) + F16.fromInt(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toInt()
    } else {
        null
    }
}

public fun F16.toIntUncheckedByte(): Byte = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.I8)).toByte()

public fun F16.toIntSaturatingByte(): Byte = this.toByte()

public fun F16.toIntCheckedByte(): Byte? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromByte(Byte.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (Byte.MAX_VALUE / 2) + 1
            F16.fromByte(half) + F16.fromByte(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toByte()
    } else {
        null
    }
}

public fun F16.toIntUncheckedShort(): Short = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.I16)).toShort()

public fun F16.toIntSaturatingShort(): Short = this.toShort()

public fun F16.toIntCheckedShort(): Short? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromShort(Short.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (Short.MAX_VALUE / 2) + 1
            F16.fromShort(half) + F16.fromShort(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toShort()
    } else {
        null
    }
}

public fun F16.toIntUncheckedLong(): Long = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.I64)).toLong()

public fun F16.toIntSaturatingLong(): Long = this.toLong()

public fun F16.toIntCheckedLong(): Long? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromLong(Long.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (Long.MAX_VALUE / 2L) + 1L
            F16.fromLong(half) + F16.fromLong(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toLong()
    } else {
        null
    }
}

public fun F16.toIntUncheckedI128(): I128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F16_LAYOUT, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

public fun F16.toIntSaturatingI128(): I128 = I128.fromLong(this.toLong())

public fun F16.toIntCheckedI128(): I128? {
    val LOW_THRESHOLD: F16 =
        run {
            val intMin = F16.fromDouble(I128.MIN_VALUE.toDouble())
            val oneBelowIfRepresentable = intMin - F16.fromFloat(1.0f)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F16 =
        run {
            val half = (I128.MAX_VALUE / I128(0x0L, 0x2uL)) + I128(0x0L, 0x1uL)
            F16.fromDouble(half.toDouble()) + F16.fromDouble(half.toDouble())
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUnchecked(): UByte = kmFloatToU64Unchecked(this).toUByte()

public fun kotlin.Float.toIntSaturating(): UByte = this.toUByte()

public fun kotlin.Float.toIntChecked(): UByte? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = UByte.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (UByte.MAX_VALUE / 2u) + 1u
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedUShort(): UShort = kmFloatToU64Unchecked(this).toUShort()

public fun kotlin.Float.toIntSaturatingUShort(): UShort = this.toUShort()

public fun kotlin.Float.toIntCheckedUShort(): UShort? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = UShort.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (UShort.MAX_VALUE / 2u) + 1u
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUShort()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedUInt(): UInt = kmFloatToU64Unchecked(this).toUInt()

public fun kotlin.Float.toIntSaturatingUInt(): UInt = this.toUInt()

public fun kotlin.Float.toIntCheckedUInt(): UInt? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = UInt.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (UInt.MAX_VALUE / 2u) + 1u
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUInt()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedULong(): ULong = (kmFloatToU64Unchecked(this)).toULong()

public fun kotlin.Float.toIntSaturatingULong(): ULong = this.toULong()

public fun kotlin.Float.toIntCheckedULong(): ULong? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = ULong.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (ULong.MAX_VALUE / 2uL) + 1uL
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toULong()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedU128(): U128 = kmFloatToU128Unchecked(this).let { U128(it.hi, it.lo) }

public fun kotlin.Float.toIntSaturatingU128(): U128 = U128.fromULong(this.toULong())

public fun kotlin.Float.toIntCheckedU128(): U128? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = U128.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (U128.MAX_VALUE / U128(0x0uL, 0x2uL)) + U128(0x0uL, 0x1uL)
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedInt(): Int = kmFloatToU64Unchecked(this).toUInt()

public fun kotlin.Float.toIntSaturatingInt(): Int = this.toInt()

public fun kotlin.Float.toIntCheckedInt(): Int? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = 0.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (Int.MAX_VALUE / 2) + 1
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toInt()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedByte(): Byte = (this).toInt().toByte()

public fun kotlin.Float.toIntSaturatingByte(): Byte = this.toByte()

public fun kotlin.Float.toIntCheckedByte(): Byte? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = Byte.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (Byte.MAX_VALUE / 2) + 1
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toByte()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedShort(): Short = (this).toInt().toShort()

public fun kotlin.Float.toIntSaturatingShort(): Short = this.toShort()

public fun kotlin.Float.toIntCheckedShort(): Short? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = Short.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (Short.MAX_VALUE / 2) + 1
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toShort()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedLong(): Long = (this).toLong()

public fun kotlin.Float.toIntSaturatingLong(): Long = this.toLong()

public fun kotlin.Float.toIntCheckedLong(): Long? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = Long.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (Long.MAX_VALUE / 2L) + 1L
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toLong()
    } else {
        null
    }
}

public fun kotlin.Float.toIntUncheckedI128(): I128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F32, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

public fun kotlin.Float.toIntSaturatingI128(): I128 = I128.fromLong(this.toLong())

public fun kotlin.Float.toIntCheckedI128(): I128? {
    val LOW_THRESHOLD: kotlin.Float =
        run {
            val intMin = I128.MIN_VALUE.toFloat()
            val oneBelowIfRepresentable = intMin - 1.0f
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: kotlin.Float =
        run {
            val half = (I128.MAX_VALUE / I128(0x0L, 0x2uL)) + I128(0x0L, 0x1uL)
            half.toFloat() + half.toFloat()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun Double.toIntUnchecked(): UByte = kmDoubleToU64Unchecked(this).toUByte()

public fun Double.toIntSaturating(): UByte = this.toUByte()

public fun Double.toIntChecked(): UByte? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = UByte.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (UByte.MAX_VALUE / 2u) + 1u
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun Double.toIntUncheckedUShort(): UShort = kmDoubleToU64Unchecked(this).toUShort()

public fun Double.toIntSaturatingUShort(): UShort = this.toUShort()

public fun Double.toIntCheckedUShort(): UShort? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = UShort.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (UShort.MAX_VALUE / 2u) + 1u
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUShort()
    } else {
        null
    }
}

public fun Double.toIntUncheckedUInt(): UInt = kmDoubleToU64Unchecked(this).toUInt()

public fun Double.toIntSaturatingUInt(): UInt = this.toUInt()

public fun Double.toIntCheckedUInt(): UInt? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = UInt.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (UInt.MAX_VALUE / 2u) + 1u
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUInt()
    } else {
        null
    }
}

public fun Double.toIntUncheckedULong(): ULong = (kmDoubleToU64Unchecked(this)).toULong()

public fun Double.toIntSaturatingULong(): ULong = this.toULong()

public fun Double.toIntCheckedULong(): ULong? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = ULong.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (ULong.MAX_VALUE / 2uL) + 1uL
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toULong()
    } else {
        null
    }
}

public fun Double.toIntUncheckedU128(): U128 = kmDoubleToU128Unchecked(this).let { U128(it.hi, it.lo) }

public fun Double.toIntSaturatingU128(): U128 = U128.fromULong(this.toULong())

public fun Double.toIntCheckedU128(): U128? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = U128.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (U128.MAX_VALUE / U128(0x0uL, 0x2uL)) + U128(0x0uL, 0x1uL)
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun Double.toIntUncheckedInt(): Int = kmDoubleToU64Unchecked(this).toUInt()

public fun Double.toIntSaturatingInt(): Int = this.toInt()

public fun Double.toIntCheckedInt(): Int? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = 0.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (Int.MAX_VALUE / 2) + 1
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toInt()
    } else {
        null
    }
}

public fun Double.toIntUncheckedByte(): Byte = (this).toInt().toByte()

public fun Double.toIntSaturatingByte(): Byte = this.toByte()

public fun Double.toIntCheckedByte(): Byte? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = Byte.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (Byte.MAX_VALUE / 2) + 1
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toByte()
    } else {
        null
    }
}

public fun Double.toIntUncheckedShort(): Short = (this).toInt().toShort()

public fun Double.toIntSaturatingShort(): Short = this.toShort()

public fun Double.toIntCheckedShort(): Short? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = Short.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (Short.MAX_VALUE / 2) + 1
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toShort()
    } else {
        null
    }
}

public fun Double.toIntUncheckedLong(): Long = (this).toLong()

public fun Double.toIntSaturatingLong(): Long = this.toLong()

public fun Double.toIntCheckedLong(): Long? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = Long.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (Long.MAX_VALUE / 2L) + 1L
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toLong()
    } else {
        null
    }
}

public fun Double.toIntUncheckedI128(): I128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F64, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

public fun Double.toIntSaturatingI128(): I128 = I128.fromLong(this.toLong())

public fun Double.toIntCheckedI128(): I128? {
    val LOW_THRESHOLD: Double =
        run {
            val intMin = I128.MIN_VALUE.toDouble()
            val oneBelowIfRepresentable = intMin - 1.0
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: Double =
        run {
            val half = (I128.MAX_VALUE / I128(0x0L, 0x2uL)) + I128(0x0L, 0x1uL)
            half.toDouble() + half.toDouble()
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun F128.toIntUnchecked(): UByte = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.U8)).toUByte()

public fun F128.toIntSaturating(): UByte = this.toUByte()

public fun F128.toIntChecked(): UByte? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromUByte(UByte.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (UByte.MAX_VALUE / 2u) + 1u
            F128.fromUByte(half) + F128.fromUByte(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun F128.toIntUncheckedUShort(): UShort = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.U16)).toUShort()

public fun F128.toIntSaturatingUShort(): UShort = this.toUShort()

public fun F128.toIntCheckedUShort(): UShort? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromUShort(UShort.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (UShort.MAX_VALUE / 2u) + 1u
            F128.fromUShort(half) + F128.fromUShort(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUShort()
    } else {
        null
    }
}

public fun F128.toIntUncheckedUInt(): UInt = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.U32)).toUInt()

public fun F128.toIntSaturatingUInt(): UInt = this.toUInt()

public fun F128.toIntCheckedUInt(): UInt? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromUInt(UInt.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (UInt.MAX_VALUE / 2u) + 1u
            F128.fromUInt(half) + F128.fromUInt(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toUInt()
    } else {
        null
    }
}

public fun F128.toIntUncheckedULong(): ULong = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.U64)).toULong()

public fun F128.toIntSaturatingULong(): ULong = this.toULong()

public fun F128.toIntCheckedULong(): ULong? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromULong(ULong.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (ULong.MAX_VALUE / 2uL) + 1uL
            F128.fromULong(half) + F128.fromULong(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toULong()
    } else {
        null
    }
}

public fun F128.toIntUncheckedU128(): U128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

public fun F128.toIntSaturatingU128(): U128 = U128.fromULong(this.toULong())

public fun F128.toIntCheckedU128(): U128? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromDouble(U128.MIN_VALUE.toDouble())
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (U128.MAX_VALUE / U128(0x0uL, 0x2uL)) + U128(0x0uL, 0x1uL)
            F128.fromDouble(half.toDouble()) + F128.fromDouble(half.toDouble())
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

public fun F128.toIntUncheckedInt(): Int = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.U32)

public fun F128.toIntSaturatingInt(): Int = this.toInt()

public fun F128.toIntCheckedInt(): Int? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromInt(0)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (Int.MAX_VALUE / 2) + 1
            F128.fromInt(half) + F128.fromInt(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toInt()
    } else {
        null
    }
}

public fun F128.toIntUncheckedByte(): Byte = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.I8)).toByte()

public fun F128.toIntSaturatingByte(): Byte = this.toByte()

public fun F128.toIntCheckedByte(): Byte? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromByte(Byte.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (Byte.MAX_VALUE / 2) + 1
            F128.fromByte(half) + F128.fromByte(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toByte()
    } else {
        null
    }
}

public fun F128.toIntUncheckedShort(): Short = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.I16)).toShort()

public fun F128.toIntSaturatingShort(): Short = this.toShort()

public fun F128.toIntCheckedShort(): Short? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromShort(Short.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (Short.MAX_VALUE / 2) + 1
            F128.fromShort(half) + F128.fromShort(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toShort()
    } else {
        null
    }
}

public fun F128.toIntUncheckedLong(): Long = (floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.I64)).toLong()

public fun F128.toIntSaturatingLong(): Long = this.toLong()

public fun F128.toIntCheckedLong(): Long? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromLong(Long.MIN_VALUE)
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (Long.MAX_VALUE / 2L) + 1L
            F128.fromLong(half) + F128.fromLong(half)
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        (this.toIntUnchecked()).toLong()
    } else {
        null
    }
}

public fun F128.toIntUncheckedI128(): I128 = floatToIntUncheckedWithLayout(this, PrimitiveLayouts.F128_LAYOUT, PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

public fun F128.toIntSaturatingI128(): I128 = I128.fromLong(this.toLong())

public fun F128.toIntCheckedI128(): I128? {
    val LOW_THRESHOLD: F128 =
        run {
            val intMin = F128.fromDouble(I128.MIN_VALUE.toDouble())
            val oneBelowIfRepresentable = intMin - F128.fromDouble(1.0)
            if (oneBelowIfRepresentable == intMin) {
                intMin.nextDown()
            } else {
                oneBelowIfRepresentable
            }
        }
    val HIGH_THRESHOLD: F128 =
        run {
            val half = (I128.MAX_VALUE / I128(0x0L, 0x2uL)) + I128(0x0L, 0x1uL)
            F128.fromDouble(half.toDouble()) + F128.fromDouble(half.toDouble())
        }
    return if (LOW_THRESHOLD < this && this < HIGH_THRESHOLD) {
        this.toIntUnchecked()
    } else {
        null
    }
}

/**
 * Supporting trait for the inherent `cast` method converting between float types.
 * Typically doesn’t need to be used directly.
 */
public interface FloatToFloat<Flt> {
    public fun cast(): Flt
}

public fun F16.cast(): F16 = this

public fun F16.castFloat(): kotlin.Float = this.toFloat()

public fun F16.castDouble(): Double = this.toDouble()

public fun F16.castF128(): F128 = F128.fromF16(this)

public fun kotlin.Float.cast(): F16 = F16.fromFloat(this)

public fun kotlin.Float.castFloat(): kotlin.Float = this

public fun kotlin.Float.castDouble(): Double = this.toDouble()

public fun kotlin.Float.castF128(): F128 = F128.fromFloat(this)

public fun Double.cast(): F16 = F16.fromDouble(this)

public fun Double.castFloat(): kotlin.Float = this.toFloat()

public fun Double.castDouble(): Double = this

public fun Double.castF128(): F128 = F128.fromDouble(this)

public fun F128.cast(): F16 = F16.fromDouble(this.toDouble())

public fun F128.castFloat(): kotlin.Float = this.toFloat()

public fun F128.castDouble(): Double = this.toDouble()

public fun F128.castF128(): F128 = this

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromUByte(b: Boolean): UByte = b.toUByte()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromUShort(b: Boolean): UShort = b.toUShort()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromUInt(b: Boolean): UInt = b.toUInt()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromULong(b: Boolean): ULong = b.toULong()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromU128(b: Boolean): U128 = U128.fromLong(b.toLong())

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromInt(b: Boolean): Int = b.toInt()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromByte(b: Boolean): Byte = b.toByte()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromShort(b: Boolean): Short = b.toShort()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromLong(b: Boolean): Long = b.toLong()

/**
 * Converts from [bool] to
 * , by turning `false` into `0` and `true` into `1`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun fromI128(b: Boolean): I128 = I128.fromLong(b.toLong())

public fun fromUShort(small: UByte): UShort {
    if (false) {
        check(I128.fromUShort(UShort.MIN_VALUE) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromUShort(UShort.MAX_VALUE))
    }
    return small.toUShort()
}

public fun fromUInt(small: UByte): UInt {
    if (false) {
        check(I128.fromUInt(UInt.MIN_VALUE) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromUInt(UInt.MAX_VALUE))
    }
    return small.toUInt()
}

public fun fromULong(small: UByte): ULong {
    if (false) {
        check(I128.fromULong(ULong.MIN_VALUE) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromULong(ULong.MAX_VALUE))
    }
    return small.toULong()
}

public fun fromU128(small: UByte): U128 {
    if (false) {
        check(U128.MIN_VALUE.toI128() <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.MAX_VALUE)
    }
    return U128.fromUByte(small)
}

public fun fromInt(small: UByte): Int {
    if (false) {
        check(I128.fromInt(0) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromInt(Int.MAX_VALUE))
    }
    return small.toInt()
}

public fun fromUInt(small: UShort): UInt {
    if (false) {
        check(I128.fromUInt(UInt.MIN_VALUE) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromUInt(UInt.MAX_VALUE))
    }
    return small.toUInt()
}

public fun fromULong(small: UShort): ULong {
    if (false) {
        check(I128.fromULong(ULong.MIN_VALUE) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromULong(ULong.MAX_VALUE))
    }
    return small.toULong()
}

public fun fromU128(small: UShort): U128 {
    if (false) {
        check(U128.MIN_VALUE.toI128() <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.MAX_VALUE)
    }
    return U128.fromUShort(small)
}

public fun fromULong(small: UInt): ULong {
    if (false) {
        check(I128.fromULong(ULong.MIN_VALUE) <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= U128.fromULong(ULong.MAX_VALUE))
    }
    return small.toULong()
}

public fun fromU128(small: UInt): U128 {
    if (false) {
        check(U128.MIN_VALUE.toI128() <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= U128.MAX_VALUE)
    }
    return U128.fromUInt(small)
}

public fun fromU128(small: ULong): U128 {
    if (false) {
        check(U128.MIN_VALUE.toI128() <= I128.fromULong(ULong.MIN_VALUE))
    }
    if (false) {
        check(U128.fromULong(ULong.MAX_VALUE) <= U128.MAX_VALUE)
    }
    return U128.fromULong(small)
}

public fun fromShort(small: Byte): Short {
    if (false) {
        check(I128.fromShort(Short.MIN_VALUE) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromShort(Short.MAX_VALUE))
    }
    return small.toShort()
}

public fun fromInt(small: Byte): Int {
    if (false) {
        check(I128.fromInt(Int.MIN_VALUE) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromInt(Int.MAX_VALUE))
    }
    return small.toInt()
}

public fun fromLong(small: Byte): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: Byte): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromByte(small)
}

public fun fromInt(small: Short): Int {
    if (false) {
        check(I128.fromInt(Int.MIN_VALUE) <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= U128.fromInt(Int.MAX_VALUE))
    }
    return small.toInt()
}

public fun fromLong(small: Short): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: Short): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromShort(small)
}

public fun fromLong(small: Int): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromInt(Int.MIN_VALUE))
    }
    if (false) {
        check(U128.fromInt(Int.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: Int): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromInt(Int.MIN_VALUE))
    }
    if (false) {
        check(U128.fromInt(Int.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromInt(small)
}

public fun fromI128(small: Long): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromLong(Long.MIN_VALUE))
    }
    if (false) {
        check(U128.fromLong(Long.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromLong(small)
}

public fun fromShort(small: UByte): Short {
    if (false) {
        check(I128.fromShort(Short.MIN_VALUE) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromShort(Short.MAX_VALUE))
    }
    return small.toShort()
}

public fun fromLong(small: UByte): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: UByte): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromUByte(small)
}

public fun fromInt(small: UShort): Int {
    if (false) {
        check(I128.fromInt(Int.MIN_VALUE) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromInt(Int.MAX_VALUE))
    }
    return small.toInt()
}

public fun fromLong(small: UShort): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: UShort): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromUShort(small)
}

public fun fromLong(small: UInt): Long {
    if (false) {
        check(I128.fromLong(Long.MIN_VALUE) <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= U128.fromLong(Long.MAX_VALUE))
    }
    return small.toLong()
}

public fun fromI128(small: UInt): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromUInt(small)
}

public fun fromI128(small: ULong): I128 {
    if (false) {
        check(I128.MIN_VALUE <= I128.fromULong(ULong.MIN_VALUE))
    }
    if (false) {
        check(U128.fromULong(ULong.MAX_VALUE) <= I128.MAX_VALUE.toU128())
    }
    return I128.fromULong(small)
}

public fun fromF16(small: Byte): F16 {
    if (false) {
        check(I128.fromLong(F16.MIN.toLong()) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromULong(F16.MAX.toULong()))
    }
    return F16.fromByte(small)
}

public fun fromFloat(small: Byte): kotlin.Float {
    if (false) {
        check(I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromULong(kotlin.Float.MAX_VALUE.toULong()))
    }
    return small.toFloat()
}

public fun fromDouble(small: Byte): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: Byte): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromByte(Byte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromByte(Byte.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromByte(small)
}

public fun fromFloat(small: Short): kotlin.Float {
    if (false) {
        check(I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()) <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= U128.fromULong(kotlin.Float.MAX_VALUE.toULong()))
    }
    return small.toFloat()
}

public fun fromDouble(small: Short): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: Short): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromShort(Short.MIN_VALUE))
    }
    if (false) {
        check(U128.fromShort(Short.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromShort(small)
}

public fun fromDouble(small: Int): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromInt(Int.MIN_VALUE))
    }
    if (false) {
        check(U128.fromInt(Int.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: Int): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromInt(Int.MIN_VALUE))
    }
    if (false) {
        check(U128.fromInt(Int.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromInt(small)
}

public fun fromF128(small: Long): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromLong(Long.MIN_VALUE))
    }
    if (false) {
        check(U128.fromLong(Long.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromLong(small)
}

public fun fromF16(small: UByte): F16 {
    if (false) {
        check(I128.fromLong(F16.MIN.toLong()) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromULong(F16.MAX.toULong()))
    }
    return F16.fromUByte(small)
}

public fun fromFloat(small: UByte): kotlin.Float {
    if (false) {
        check(I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromULong(kotlin.Float.MAX_VALUE.toULong()))
    }
    return small.toFloat()
}

public fun fromDouble(small: UByte): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: UByte): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromUByte(UByte.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUByte(UByte.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromUByte(small)
}

public fun fromFloat(small: UShort): kotlin.Float {
    if (false) {
        check(I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromULong(kotlin.Float.MAX_VALUE.toULong()))
    }
    return small.toFloat()
}

public fun fromDouble(small: UShort): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: UShort): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromUShort(UShort.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUShort(UShort.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromUShort(small)
}

public fun fromDouble(small: UInt): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: UInt): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromUInt(UInt.MIN_VALUE))
    }
    if (false) {
        check(U128.fromUInt(UInt.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromUInt(small)
}

public fun fromF128(small: ULong): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromULong(ULong.MIN_VALUE))
    }
    if (false) {
        check(U128.fromULong(ULong.MAX_VALUE) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromULong(small)
}

public fun fromFloat(small: F16): kotlin.Float {
    if (false) {
        check(I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()) <= I128.fromLong(F16.MIN.toLong()))
    }
    if (false) {
        check(U128.fromULong(F16.MAX.toULong()) <= U128.fromULong(kotlin.Float.MAX_VALUE.toULong()))
    }
    return small.toFloat()
}

public fun fromDouble(small: F16): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromLong(F16.MIN.toLong()))
    }
    if (false) {
        check(U128.fromULong(F16.MAX.toULong()) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: F16): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromLong(F16.MIN.toLong()))
    }
    if (false) {
        check(U128.fromULong(F16.MAX.toULong()) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromF16(small)
}

public fun fromDouble(small: kotlin.Float): Double {
    if (false) {
        check(I128.fromLong(((-Double.MAX_VALUE)).toLong()) <= I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()))
    }
    if (false) {
        check(U128.fromULong(kotlin.Float.MAX_VALUE.toULong()) <= U128.fromULong(Double.MAX_VALUE.toULong()))
    }
    return small.toDouble()
}

public fun fromF128(small: kotlin.Float): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromLong(((-kotlin.Float.MAX_VALUE)).toLong()))
    }
    if (false) {
        check(U128.fromULong(kotlin.Float.MAX_VALUE.toULong()) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromFloat(small)
}

public fun fromF128(small: Double): F128 {
    if (false) {
        check(I128.fromLong(F128.MIN.toLong()) <= I128.fromLong(((-Double.MAX_VALUE)).toLong()))
    }
    if (false) {
        check(U128.fromULong(Double.MAX_VALUE.toULong()) <= U128.fromULong(F128.MAX.toULong()))
    }
    return F128.fromDouble(small)
}

/**
 * The resulting value is positive `0.0` for `false` and `1.0` for `true` values.
 *
 * # Examples
 * ```kotlin
 * @feature(f16)
 * public fun doctestEntry() {
 *     check(x == 0.0)
 *     check(x.isSignPositive())
 *     check(y == 1.0)
 * }
 * ```
 */
public fun fromF16(small: Boolean): F16 = F16.fromUByte((small.toUByte()))

/**
 * The resulting value is positive `0.0` for `false` and `1.0` for `true` values.
 *
 * # Examples
 * ```kotlin
 * check(x == 0.0)
 * check(x.isSignPositive())
 * check(y == 1.0)
 * ```
 */
public fun fromFloat(small: Boolean): kotlin.Float = (small.toUByte()).toFloat()

/**
 * The resulting value is positive `0.0` for `false` and `1.0` for `true` values.
 *
 * # Examples
 * ```kotlin
 * check(x == 0.0)
 * check(x.isSignPositive())
 * check(y == 1.0)
 * ```
 */
public fun fromDouble(small: Boolean): Double = (small.toUByte()).toDouble()

/**
 * The resulting value is positive `0.0` for `false` and `1.0` for `true` values.
 *
 * # Examples
 * ```kotlin
 * @feature(f128)
 * public fun doctestEntry() {
 *     check(x == 0.0)
 *     check(x.isSignPositive())
 *     check(y == 1.0)
 * }
 * ```
 */
public fun fromF128(small: Boolean): F128 = F128.fromUByte((small.toUByte()))

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: U128): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: ULong): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: UInt): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: UShort): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: UByte): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: I128): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        i < 0 -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: Long): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        i < 0 -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: Int): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        i < 0 -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: Short): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        i < 0 -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create a bool from an integer type.
 * Returns an error if the integer is not 0 or 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun tryFromBoolean(i: Byte): Result<Boolean, TryFromIntError> =
    when {
        i == 0 -> Result.Ok<Boolean, TryFromIntError>(false)
        i == 1 -> Result.Ok<Boolean, TryFromIntError>(true)
        i < 0 -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
        else -> Result.Err<Boolean, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUByte(u: UShort): Result<UByte, TryFromIntError> =
    if (u > (UByte.MAX_VALUE.toUShort())) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUByte(u: UInt): Result<UByte, TryFromIntError> =
    if (u > (UByte.MAX_VALUE.toUInt())) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUShort(u: UInt): Result<UShort, TryFromIntError> =
    if (u > (UShort.MAX_VALUE.toUInt())) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUByte(u: ULong): Result<UByte, TryFromIntError> =
    if (u > (UByte.MAX_VALUE.toULong())) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUShort(u: ULong): Result<UShort, TryFromIntError> =
    if (u > (UShort.MAX_VALUE.toULong())) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUInt(u: ULong): Result<UInt, TryFromIntError> =
    if (u > (UInt.MAX_VALUE.toULong())) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUByte(u: U128): Result<UByte, TryFromIntError> =
    if (u > (U128.fromLong(UByte.MAX_VALUE.toLong()))) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUShort(u: U128): Result<UShort, TryFromIntError> =
    if (u > (U128.fromLong(UShort.MAX_VALUE.toLong()))) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUInt(u: U128): Result<UInt, TryFromIntError> =
    if (u > (U128.fromLong(UInt.MAX_VALUE.toLong()))) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromULong(u: U128): Result<ULong, TryFromIntError> =
    if (u > (U128.fromLong(ULong.MAX_VALUE.toLong()))) {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromByte(u: Short): Result<Byte, TryFromIntError> {
    val min = Byte.MIN_VALUE.toShort()
    val max = Byte.MAX_VALUE.toShort()
    return if (u < min) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromByte(u: Int): Result<Byte, TryFromIntError> {
    val min = Byte.MIN_VALUE.toInt()
    val max = Byte.MAX_VALUE.toInt()
    return if (u < min) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromShort(u: Int): Result<Short, TryFromIntError> {
    val min = Short.MIN_VALUE.toInt()
    val max = Short.MAX_VALUE.toInt()
    return if (u < min) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromByte(u: Long): Result<Byte, TryFromIntError> {
    val min = Byte.MIN_VALUE.toLong()
    val max = Byte.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromShort(u: Long): Result<Short, TryFromIntError> {
    val min = Short.MIN_VALUE.toLong()
    val max = Short.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromInt(u: Long): Result<Int, TryFromIntError> {
    val min = Int.MIN_VALUE.toLong()
    val max = Int.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromByte(u: I128): Result<Byte, TryFromIntError> {
    val min = I128.fromLong(Byte.MIN_VALUE.toLong())
    val max = I128.fromLong(Byte.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromShort(u: I128): Result<Short, TryFromIntError> {
    val min = I128.fromLong(Short.MIN_VALUE.toLong())
    val max = I128.fromLong(Short.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromInt(u: I128): Result<Int, TryFromIntError> {
    val min = I128.fromLong(Int.MIN_VALUE.toLong())
    val max = I128.fromLong(Int.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromLong(u: I128): Result<Long, TryFromIntError> {
    val min = I128.fromLong(Long.MIN_VALUE.toLong())
    val max = I128.fromLong(Long.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<Long, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<Long, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Long, TryFromIntError>(u.toLong())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromByte(u: UByte): Result<Byte, TryFromIntError> =
    if (u > (Byte.MAX_VALUE.toUByte())) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromByte(u: UShort): Result<Byte, TryFromIntError> =
    if (u > (Byte.MAX_VALUE.toUShort())) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromShort(u: UShort): Result<Short, TryFromIntError> =
    if (u > (Short.MAX_VALUE.toUShort())) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromByte(u: UInt): Result<Byte, TryFromIntError> =
    if (u > (Byte.MAX_VALUE.toUInt())) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromShort(u: UInt): Result<Short, TryFromIntError> =
    if (u > (Short.MAX_VALUE.toUInt())) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: UInt): Result<Int, TryFromIntError> =
    if (u > (Int.MAX_VALUE.toUInt())) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromByte(u: ULong): Result<Byte, TryFromIntError> =
    if (u > (Byte.MAX_VALUE.toULong())) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromShort(u: ULong): Result<Short, TryFromIntError> =
    if (u > (Short.MAX_VALUE.toULong())) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: ULong): Result<Int, TryFromIntError> =
    if (u > (Int.MAX_VALUE.toULong())) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromLong(u: ULong): Result<Long, TryFromIntError> =
    if (u > (Long.MAX_VALUE.toULong())) {
        Result.Err<Long, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Long, TryFromIntError>(u.toLong())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromByte(u: U128): Result<Byte, TryFromIntError> =
    if (u > (U128.fromLong(Byte.MAX_VALUE.toLong()))) {
        Result.Err<Byte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Byte, TryFromIntError>(u.toByte())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromShort(u: U128): Result<Short, TryFromIntError> =
    if (u > (U128.fromLong(Short.MAX_VALUE.toLong()))) {
        Result.Err<Short, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Short, TryFromIntError>(u.toShort())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: U128): Result<Int, TryFromIntError> =
    if (u > (U128.fromLong(Int.MAX_VALUE.toLong()))) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromLong(u: U128): Result<Long, TryFromIntError> =
    if (u > (U128.fromLong(Long.MAX_VALUE.toLong()))) {
        Result.Err<Long, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Long, TryFromIntError>(u.toLong())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromI128(u: U128): Result<I128, TryFromIntError> =
    if (u > (U128.fromLong(I128.MAX_VALUE.toLong()))) {
        Result.Err<I128, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<I128, TryFromIntError>(u.toI128())
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUByte(u: Byte): Result<UByte, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    } else {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUShort(u: Byte): Result<UShort, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    } else {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUInt(u: Byte): Result<UInt, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    } else {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromULong(u: Byte): Result<ULong, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    } else {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromU128(u: Byte): Result<U128, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<U128, TryFromIntError>(U128.fromByte(u))
    } else {
        Result.Err<U128, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUByte(u: Short): Result<UByte, TryFromIntError> {
    val min = UByte.MIN_VALUE.toShort()
    val max = UByte.MAX_VALUE.toShort()
    return if (u < min) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUShort(u: Short): Result<UShort, TryFromIntError> =
    if (u >= 0.toShort()) {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    } else {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUInt(u: Short): Result<UInt, TryFromIntError> =
    if (u >= 0.toShort()) {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    } else {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromULong(u: Short): Result<ULong, TryFromIntError> =
    if (u >= 0.toShort()) {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    } else {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromU128(u: Short): Result<U128, TryFromIntError> =
    if (u >= 0.toShort()) {
        Result.Ok<U128, TryFromIntError>(U128.fromShort(u))
    } else {
        Result.Err<U128, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUByte(u: Int): Result<UByte, TryFromIntError> {
    val min = UByte.MIN_VALUE.toInt()
    val max = UByte.MAX_VALUE.toInt()
    return if (u < min) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUShort(u: Int): Result<UShort, TryFromIntError> {
    val min = UShort.MIN_VALUE.toInt()
    val max = UShort.MAX_VALUE.toInt()
    return if (u < min) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromUInt(u: Int): Result<UInt, TryFromIntError> =
    if (u >= 0) {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    } else {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromULong(u: Int): Result<ULong, TryFromIntError> =
    if (u >= 0) {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    } else {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromU128(u: Int): Result<U128, TryFromIntError> =
    if (u >= 0) {
        Result.Ok<U128, TryFromIntError>(U128.fromInt(u))
    } else {
        Result.Err<U128, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUByte(u: Long): Result<UByte, TryFromIntError> {
    val min = UByte.MIN_VALUE.toLong()
    val max = UByte.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUShort(u: Long): Result<UShort, TryFromIntError> {
    val min = UShort.MIN_VALUE.toLong()
    val max = UShort.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUInt(u: Long): Result<UInt, TryFromIntError> {
    val min = UInt.MIN_VALUE.toLong()
    val max = UInt.MAX_VALUE.toLong()
    return if (u < min) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromULong(u: Long): Result<ULong, TryFromIntError> =
    if (u >= 0L) {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    } else {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromU128(u: Long): Result<U128, TryFromIntError> =
    if (u >= 0L) {
        Result.Ok<U128, TryFromIntError>(U128.fromLong(u))
    } else {
        Result.Err<U128, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUByte(u: I128): Result<UByte, TryFromIntError> {
    val min = I128.fromLong(UByte.MIN_VALUE.toLong())
    val max = I128.fromLong(UByte.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UByte, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UByte, TryFromIntError>(u.toUByte())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUShort(u: I128): Result<UShort, TryFromIntError> {
    val min = I128.fromLong(UShort.MIN_VALUE.toLong())
    val max = I128.fromLong(UShort.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UShort, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UShort, TryFromIntError>(u.toUShort())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromUInt(u: I128): Result<UInt, TryFromIntError> {
    val min = I128.fromLong(UInt.MIN_VALUE.toLong())
    val max = I128.fromLong(UInt.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<UInt, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<UInt, TryFromIntError>(u.toUInt())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 * is outside of the range of the target type.
 */
public fun tryFromULong(u: I128): Result<ULong, TryFromIntError> {
    val min = I128.fromLong(ULong.MIN_VALUE.toLong())
    val max = I128.fromLong(ULong.MAX_VALUE.toLong())
    return if (u < min) {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    } else if (u > max) {
        Result.Err<ULong, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<ULong, TryFromIntError>(u.toULong())
    }
}

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromU128(u: I128): Result<U128, TryFromIntError> =
    if (u >= I128(0x0L, 0x0uL)) {
        Result.Ok<U128, TryFromIntError>(u.toU128())
    } else {
        Result.Err<U128, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: Int): Result<Int, TryFromIntError> =
    if (u > (Int.MAX_VALUE.toInt())) {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Int, TryFromIntError>(u)
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromLong(u: Int): Result<Long, TryFromIntError> =
    if (u > (Long.MAX_VALUE.toInt())) {
        Result.Err<Long, TryFromIntError>(TryFromIntError(IntErrorKind.PosOverflow))
    } else {
        Result.Ok<Long, TryFromIntError>(u.toLong())
    }

/**
 * Tries to create the target number type from a source
 * number type. This never returns an error.
 */
public fun tryFromI128(value: Int): Result<I128, TryFromIntError> = Result.Ok<I128, TryFromIntError>(I128.fromInt(value))

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: Byte): Result<Int, TryFromIntError> =
    if (u >= 0.toByte()) {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    } else {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This returns an error if the source value
 */
public fun tryFromInt(u: Short): Result<Int, TryFromIntError> =
    if (u >= 0.toShort()) {
        Result.Ok<Int, TryFromIntError>(u.toInt())
    } else {
        Result.Err<Int, TryFromIntError>(TryFromIntError(IntErrorKind.NegOverflow))
    }

/**
 * Tries to create the target number type from a source
 * number type. This never returns an error.
 */
public fun tryFromInt(value: UShort): Result<Int, TryFromIntError> = Result.Ok<Int, TryFromIntError>(value.toInt())

public fun fromNonZeroUShort(small: NonZero<UByte, NonZeroU8Inner>): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked<NonZeroU16Inner, UShort>(From.from(small.get()))

public fun fromNonZeroUInt(small: NonZero<UByte, NonZeroU8Inner>): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked<NonZeroU32Inner, UInt>(From.from(small.get()))

public fun fromNonZeroULong(small: NonZero<UByte, NonZeroU8Inner>): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked<NonZeroU64Inner, ULong>(From.from(small.get()))

public fun fromNonZeroU128(small: NonZero<UByte, NonZeroU8Inner>): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked<NonZeroU128Inner, U128>(From.from(small.get()))

public fun fromNonZeroInt(small: NonZero<UByte, NonZeroU8Inner>): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked<NonZeroUsizeInner, Int>(From.from(small.get()))

public fun fromNonZeroShort(small: NonZero<Byte, NonZeroI8Inner>): NonZero<Short, NonZeroI16Inner> = NonZero.newUnchecked<NonZeroI16Inner, Short>(From.from(small.get()))

public fun fromNonZeroLong(small: NonZero<Byte, NonZeroI8Inner>): NonZero<Long, NonZeroI64Inner> = NonZero.newUnchecked<NonZeroI64Inner, Long>(From.from(small.get()))

public fun fromNonZeroI128(small: NonZero<Byte, NonZeroI8Inner>): NonZero<I128, NonZeroI128Inner> = NonZero.newUnchecked<NonZeroI128Inner, I128>(From.from(small.get()))

public fun tryFromNonZeroUByte(value: UByte): Result<NonZero<UByte, NonZeroU8Inner>, TryFromIntError> = ((NonZero.new<NonZeroU8Inner, UByte>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroUShort(value: UShort): Result<NonZero<UShort, NonZeroU16Inner>, TryFromIntError> = ((NonZero.new<NonZeroU16Inner, UShort>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroUInt(value: UInt): Result<NonZero<UInt, NonZeroU32Inner>, TryFromIntError> = ((NonZero.new<NonZeroU32Inner, UInt>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroULong(value: ULong): Result<NonZero<ULong, NonZeroU64Inner>, TryFromIntError> = ((NonZero.new<NonZeroU64Inner, ULong>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroU128(value: U128): Result<NonZero<U128, NonZeroU128Inner>, TryFromIntError> = ((NonZero.new<NonZeroU128Inner, U128>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroInt(value: Int): Result<NonZero<Int, NonZeroUsizeInner>, TryFromIntError> = ((NonZero.new<NonZeroUsizeInner, Int>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroByte(value: Byte): Result<NonZero<Byte, NonZeroI8Inner>, TryFromIntError> = ((NonZero.new<NonZeroI8Inner, Byte>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroShort(value: Short): Result<NonZero<Short, NonZeroI16Inner>, TryFromIntError> = ((NonZero.new<NonZeroI16Inner, Short>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroLong(value: Long): Result<NonZero<Long, NonZeroI64Inner>, TryFromIntError> = ((NonZero.new<NonZeroI64Inner, Long>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroI128(value: I128): Result<NonZero<I128, NonZeroI128Inner>, TryFromIntError> = ((NonZero.new<NonZeroI128Inner, I128>(value))?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroUByte(value: NonZero<UShort, NonZeroU16Inner>): Result<NonZero<UByte, NonZeroU8Inner>, TryFromIntError> {
    return Result.Ok<NonZero<UByte, NonZeroU8Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroU8Inner, UByte>(when (val tryResult = UByte.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<UByte, NonZeroU8Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroUShort(value: NonZero<UInt, NonZeroU32Inner>): Result<NonZero<UShort, NonZeroU16Inner>, TryFromIntError> {
    return Result.Ok<NonZero<UShort, NonZeroU16Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroU16Inner, UShort>(when (val tryResult = UShort.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<UShort, NonZeroU16Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroInt(value: NonZero<UInt, NonZeroU32Inner>): Result<NonZero<Int, NonZeroUsizeInner>, TryFromIntError> {
    return Result.Ok<NonZero<Int, NonZeroUsizeInner>, TryFromIntError>(NonZero.newUnchecked<NonZeroUsizeInner, Int>(when (val tryResult = Int.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<Int, NonZeroUsizeInner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroUInt(value: NonZero<ULong, NonZeroU64Inner>): Result<NonZero<UInt, NonZeroU32Inner>, TryFromIntError> {
    return Result.Ok<NonZero<UInt, NonZeroU32Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroU32Inner, UInt>(when (val tryResult = UInt.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<UInt, NonZeroU32Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroULong(value: NonZero<U128, NonZeroU128Inner>): Result<NonZero<ULong, NonZeroU64Inner>, TryFromIntError> {
    return Result.Ok<NonZero<ULong, NonZeroU64Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroU64Inner, ULong>(when (val tryResult = ULong.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<ULong, NonZeroU64Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroU128(value: NonZero<Int, NonZeroUsizeInner>): Result<NonZero<U128, NonZeroU128Inner>, TryFromIntError> {
    return Result.Ok<NonZero<U128, NonZeroU128Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroU128Inner, U128>(when (val tryResult = U128.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<U128, NonZeroU128Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroByte(value: NonZero<Short, NonZeroI16Inner>): Result<NonZero<Byte, NonZeroI8Inner>, TryFromIntError> {
    return Result.Ok<NonZero<Byte, NonZeroI8Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroI8Inner, Byte>(when (val tryResult = Byte.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<Byte, NonZeroI8Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroShort(value: NonZero<Int, NonZeroI32Inner>): Result<NonZero<Short, NonZeroI16Inner>, TryFromIntError> {
    return Result.Ok<NonZero<Short, NonZeroI16Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroI16Inner, Short>(when (val tryResult = Short.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<Short, NonZeroI16Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroLong(value: NonZero<I128, NonZeroI128Inner>): Result<NonZero<Long, NonZeroI64Inner>, TryFromIntError> {
    return Result.Ok<NonZero<Long, NonZeroI64Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroI64Inner, Long>(when (val tryResult = Long.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<Long, NonZeroI64Inner>, TryFromIntError>(tryResult.error) }))
}

public fun tryFromNonZeroI128(value: NonZero<Int, NonZeroIsizeInner>): Result<NonZero<I128, NonZeroI128Inner>, TryFromIntError> {
    return Result.Ok<NonZero<I128, NonZeroI128Inner>, TryFromIntError>(NonZero.newUnchecked<NonZeroI128Inner, I128>(when (val tryResult = I128.tryFrom(value.get())) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<NonZero<I128, NonZeroI128Inner>, TryFromIntError>(tryResult.error) }))
}

/**
 * Conversion between integers, wrapping around or saturating at the target type's boundaries.
 */
public interface BoundedCastFromInt<T, SelfType> {
    /**
     * Converts `value` to this type, wrapping around at the boundary of the type.
     */
    public fun wrappingCastFrom(value: T): SelfType

    /**
     * Converts `value` to this type, saturating at the numeric bounds instead of overflowing.
     */
    public fun saturatingCastFrom(value: T): SelfType
}

/**
 * Fallible conversion between integers.
 */
public interface CheckedCastFromInt<T, SelfType> {
    /**
     * Converts `value` to this type, returning `None` if overflow would have occurred.
     */
    public fun checkedCastFrom(value: T): SelfType?

    /**
     * Converts `value` to this type, assuming overflow cannot occur.
     *
     * # Safety
     *
     * This results in undefined behavior when `value` will overflow when
     * converted to this type.
     */
    public fun uncheckedCastFrom(value: T): SelfType

    /**
     * Converts `value` to this type, panicking on overflow.
     *
     * # Panics
     *
     * This function will always panic on overflow, regardless of whether overflow checks are enabled.
     */
    public fun strictCastFrom(value: T): SelfType
}

public fun checkedCastFromIntCheckedCastFromUByte(value: UByte): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: UByte): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: UByte): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: UByte): UByte = value

public fun boundedCastFromIntSaturatingCastFromUByte(value: UByte): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: UByte): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: UByte): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: UByte): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: UByte): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: UByte): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: UByte): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: UByte): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: UByte): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: UByte): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: UByte): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: UByte): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: UByte): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: UByte): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: UByte): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: UByte): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: UByte): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: UByte): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: UByte): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: UByte): U128 = U128.fromUByte(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: UByte): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: UByte): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: UByte): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: UByte): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: UByte): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: UByte): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: UByte): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: UByte): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: UByte): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: UByte): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: UByte): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: UByte): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: UByte): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: UByte): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: UByte): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: UByte): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: UByte): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: UByte): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: UByte): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: UByte): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: UByte): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: UByte): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: UByte): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: UByte): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: UByte): I128 = I128.fromUByte(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: UByte): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUByte()) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: UShort): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: UShort): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: UShort): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: UShort): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: UShort): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: UShort): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: UShort): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: UShort): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: UShort): UShort = value

public fun boundedCastFromIntSaturatingCastFromUShort(value: UShort): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: UShort): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: UShort): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: UShort): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: UShort): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: UShort): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: UShort): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: UShort): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: UShort): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: UShort): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: UShort): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: UShort): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: UShort): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: UShort): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: UShort): U128 = U128.fromUShort(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: UShort): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: UShort): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: UShort): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: UShort): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: UShort): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: UShort): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: UShort): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: UShort): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: UShort): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: UShort): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: UShort): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: UShort): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: UShort): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: UShort): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: UShort): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: UShort): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: UShort): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: UShort): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: UShort): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: UShort): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: UShort): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: UShort): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: UShort): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: UShort): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: UShort): I128 = I128.fromUShort(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: UShort): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u.toUShort()) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: UInt): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: UInt): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: UInt): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: UInt): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: UInt): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: UInt): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: UInt): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: UInt): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: UInt): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: UInt): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: UInt): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: UInt): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: UInt): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: UInt): UInt = value

public fun boundedCastFromIntSaturatingCastFromUInt(value: UInt): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: UInt): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: UInt): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: UInt): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: UInt): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: UInt): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: UInt): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: UInt): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: UInt): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: UInt): U128 = U128.fromUInt(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: UInt): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: UInt): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: UInt): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: UInt): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: UInt): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: UInt): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: UInt): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: UInt): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: UInt): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: UInt): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: UInt): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: UInt): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: UInt): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: UInt): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: UInt): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: UInt): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: UInt): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: UInt): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: UInt): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: UInt): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: UInt): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: UInt): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: UInt): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: UInt): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: UInt): I128 = I128.fromUInt(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: UInt): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0u) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: ULong): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: ULong): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: ULong): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: ULong): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: ULong): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: ULong): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: ULong): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: ULong): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: ULong): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: ULong): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: ULong): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: ULong): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: ULong): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: ULong): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: ULong): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: ULong): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: ULong): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: ULong): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: ULong): ULong = value

public fun boundedCastFromIntSaturatingCastFromULong(value: ULong): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: ULong): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: ULong): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: ULong): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: ULong): U128 = U128.fromULong(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: ULong): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: ULong): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: ULong): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: ULong): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: ULong): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: ULong): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: ULong): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: ULong): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: ULong): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: ULong): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: ULong): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: ULong): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: ULong): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: ULong): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: ULong): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: ULong): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: ULong): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: ULong): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: ULong): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: ULong): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: ULong): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: ULong): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: ULong): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: ULong): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: ULong): I128 = I128.fromULong(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: ULong): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0uL) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: U128): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: U128): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: U128): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: U128): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: U128): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: U128): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: U128): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: U128): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: U128): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: U128): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: U128): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: U128): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: U128): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: U128): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: U128): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: U128): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: U128): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: U128): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: U128): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: U128): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: U128): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: U128): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: U128): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: U128): U128 = value

public fun boundedCastFromIntSaturatingCastFromU128(value: U128): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: U128): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: U128): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: U128): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: U128): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: U128): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: U128): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: U128): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: U128): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: U128): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: U128): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: U128): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: U128): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: U128): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: U128): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: U128): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: U128): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: U128): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: U128): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: U128): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: U128): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: U128): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: U128): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: U128): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: U128): I128 = value.toI128()

public fun boundedCastFromIntSaturatingCastFromI128(value: U128): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < U128(0x0uL, 0x0uL)) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: Int): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: Int): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: Int): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: Int): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: Int): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: Int): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: Int): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: Int): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: Int): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: Int): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: Int): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: Int): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: Int): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: Int): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: Int): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: Int): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: Int): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: Int): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: Int): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: Int): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: Int): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: Int): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: Int): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: Int): U128 = U128.fromInt(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: Int): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: Int): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: Int): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: Int): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: Int): Int = value

public fun boundedCastFromIntSaturatingCastFromInt(value: Int): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: Int): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: Int): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: Int): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: Int): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: Int): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: Int): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: Int): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: Int): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: Int): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: Int): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: Int): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: Int): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: Int): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: Int): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: Int): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: Int): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: Int): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: Int): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: Int): I128 = I128.fromInt(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: Int): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: Byte): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: Byte): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: Byte): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: Byte): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: Byte): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: Byte): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: Byte): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: Byte): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: Byte): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: Byte): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: Byte): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: Byte): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: Byte): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: Byte): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: Byte): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: Byte): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: Byte): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: Byte): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: Byte): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: Byte): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: Byte): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: Byte): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: Byte): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: Byte): U128 = U128.fromByte(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: Byte): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: Byte): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: Byte): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: Byte): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: Byte): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: Byte): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: Byte): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: Byte): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: Byte): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: Byte): Byte = value

public fun boundedCastFromIntSaturatingCastFromByte(value: Byte): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: Byte): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: Byte): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: Byte): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: Byte): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: Byte): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: Byte): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: Byte): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: Byte): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: Byte): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: Byte): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: Byte): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: Byte): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: Byte): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: Byte): I128 = I128.fromByte(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: Byte): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toByte()) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: Short): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: Short): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: Short): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: Short): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: Short): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: Short): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: Short): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: Short): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: Short): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: Short): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: Short): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: Short): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: Short): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: Short): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: Short): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: Short): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: Short): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: Short): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: Short): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: Short): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: Short): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: Short): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: Short): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: Short): U128 = U128.fromShort(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: Short): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: Short): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: Short): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: Short): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: Short): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: Short): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: Short): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: Short): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: Short): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: Short): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: Short): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: Short): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: Short): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: Short): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: Short): Short = value

public fun boundedCastFromIntSaturatingCastFromShort(value: Short): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: Short): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: Short): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: Short): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: Short): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: Short): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: Short): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: Short): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: Short): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: Short): I128 = I128.fromShort(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: Short): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0.toShort()) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: Long): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: Long): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: Long): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: Long): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: Long): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: Long): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: Long): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: Long): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: Long): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: Long): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: Long): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: Long): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: Long): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: Long): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: Long): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: Long): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: Long): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: Long): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: Long): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: Long): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: Long): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: Long): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: Long): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: Long): U128 = U128.fromLong(value)

public fun boundedCastFromIntSaturatingCastFromU128(value: Long): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: Long): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: Long): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: Long): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: Long): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: Long): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: Long): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: Long): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: Long): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: Long): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: Long): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: Long): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: Long): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: Long): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: Long): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: Long): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: Long): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: Long): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: Long): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: Long): Long = value

public fun boundedCastFromIntSaturatingCastFromLong(value: Long): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: Long): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: Long): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: Long): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: Long): I128 = I128.fromLong(value)

public fun boundedCastFromIntSaturatingCastFromI128(value: Long): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < 0L) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUByte(value: I128): UByte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUByte(value: I128): UByte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUByte(value: I128): UByte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUByte(value: I128): UByte = value.toUByte()

public fun boundedCastFromIntSaturatingCastFromUByte(value: I128): UByte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        UByte.MIN_VALUE
    } else {
        UByte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUShort(value: I128): UShort? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUShort(value: I128): UShort = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUShort(value: I128): UShort =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUShort(value: I128): UShort = value.toUShort()

public fun boundedCastFromIntSaturatingCastFromUShort(value: I128): UShort {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        UShort.MIN_VALUE
    } else {
        UShort.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromUInt(value: I128): UInt? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromUInt(value: I128): UInt = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromUInt(value: I128): UInt =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromUInt(value: I128): UInt = value.toUInt()

public fun boundedCastFromIntSaturatingCastFromUInt(value: I128): UInt {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        UInt.MIN_VALUE
    } else {
        UInt.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromULong(value: I128): ULong? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromULong(value: I128): ULong = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromULong(value: I128): ULong =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromULong(value: I128): ULong = value.toULong()

public fun boundedCastFromIntSaturatingCastFromULong(value: I128): ULong {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        ULong.MIN_VALUE
    } else {
        ULong.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromU128(value: I128): U128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromU128(value: I128): U128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromU128(value: I128): U128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromU128(value: I128): U128 = value.toU128()

public fun boundedCastFromIntSaturatingCastFromU128(value: I128): U128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        U128.MIN_VALUE
    } else {
        U128.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromInt(value: I128): Int? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromInt(value: I128): Int = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromInt(value: I128): Int =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromInt(value: I128): Int = value.toInt()

public fun boundedCastFromIntSaturatingCastFromInt(value: I128): Int {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        0
    } else {
        Int.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromByte(value: I128): Byte? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromByte(value: I128): Byte = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromByte(value: I128): Byte =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromByte(value: I128): Byte = value.toByte()

public fun boundedCastFromIntSaturatingCastFromByte(value: I128): Byte {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        Byte.MIN_VALUE
    } else {
        Byte.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromShort(value: I128): Short? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromShort(value: I128): Short = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromShort(value: I128): Short =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromShort(value: I128): Short = value.toShort()

public fun boundedCastFromIntSaturatingCastFromShort(value: I128): Short {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        Short.MIN_VALUE
    } else {
        Short.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromLong(value: I128): Long? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromLong(value: I128): Long = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromLong(value: I128): Long =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromLong(value: I128): Long = value.toLong()

public fun boundedCastFromIntSaturatingCastFromLong(value: I128): Long {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        Long.MIN_VALUE
    } else {
        Long.MAX_VALUE
    }
}

public fun checkedCastFromIntCheckedCastFromI128(value: I128): I128? = value.tryInto().ok()

public fun checkedCastFromIntUncheckedCastFromI128(value: I128): I128 = value.tryInto().unwrapUnchecked()

public fun checkedCastFromIntStrictCastFromI128(value: I128): I128 =
    run {
        val matchVal = value.tryInto()
        when (matchVal) { is Result.Ok -> {
            val x = matchVal.value
            x
        }; is Result.Err -> {
            castInteger()
        } }
    }

public fun boundedCastFromIntWrappingCastFromI128(value: I128): I128 = value

public fun boundedCastFromIntSaturatingCastFromI128(value: I128): I128 {
    when (val matchVal = value.tryInto()) {
        is Ok -> {
            val x = matchVal.value
            return x
        }
        else -> {}
    }
    return if (value < I128(0x0L, 0x0uL)) {
        I128.MIN_VALUE
    } else {
        I128.MAX_VALUE
    }
}
