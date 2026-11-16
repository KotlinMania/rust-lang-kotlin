// port-lint: source intrinsics/bounds.rs
package io.github.kotlinmania.rustlang.core.intrinsics.bounds

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128

// Various traits used to restrict intrinsics to not-completely-wrong types.

/**
 * Types with a built-in dereference operator in runtime MIR,
 * aka references and raw pointers.
 *
 * # Safety
 * Must actually *be* such a type.
 */
public interface BuiltinDeref<out Pointee>

public interface ChangePointee<U, out Output> : BuiltinDeref<Any?>

/**
 * Built-in float types (f16, f32, f64 and f128).
 *
 * # Safety
 * Must actually *be* such a type.
 */
public interface FloatPrimitive<T, UInt> : Mul<T, T>, Add<T, T> {
    public val SIGN_MASK: UInt

    public fun toBits(): UInt

    public fun fromBits(bits: UInt): T
}

public val SIGN_MASK: UShort = F16.SIGN_MASK.toUShort()

public fun F16.toBits(): UShort = this.toBits()

public fun floatPrimitiveFromBitsF16(bits: UShort): F16 = F16.fromBits(bits)

public val SIGN_MASK1: UInt = kotlin.Float.SIGN_MASK.toUInt()

public fun kotlin.Float.toBits(): UInt = this.toRawBits().toUInt()

public fun floatPrimitiveFromBitsFloat(bits: UInt): kotlin.Float = kotlin.Float.fromBits(bits.toInt())

public val SIGN_MASK2: ULong = Double.SIGN_MASK.toULong()

public fun Double.toBits(): ULong = this.toRawBits().toULong()

public fun floatPrimitiveFromBitsDouble(bits: ULong): Double = Double.fromBits(bits.toLong())

public val SIGN_MASK3: U128 = F128.SIGN_MASK

public fun F128.toBits(): U128 = this.toBits()

public fun floatPrimitiveFromBitsF128(bits: U128): F128 = F128.fromBits(bits)

/**
 * Built-in integer types (i8, i16, .., i128, isize, u8, u16, .., u128, Int).
 *
 * Intentionally does not include other integer-repr types like `Boolean` or `char`.
 *
 * # Safety
 * Must actually *be* such a type.
 */
public interface IntegerPrimitive<T>
