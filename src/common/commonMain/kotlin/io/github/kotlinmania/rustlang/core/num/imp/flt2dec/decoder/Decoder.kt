// port-lint: source num/imp/flt2dec/decoder.rs
package io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.FpCategory
import io.github.kotlinmania.rustlang.core.num.f32.MIN_POSITIVE
import io.github.kotlinmania.rustlang.core.num.imp.traits.FloatExt

// Decodes a floating-point value into individual parts and error ranges.

/**
 * Decoded unsigned finite value, such that:
 *
 * - The original value equals to `mant * 2 ^ exp`.
 *
 * - Any number from `(mant - minus) * 2 ^ exp` to `(mant + plus) * 2 ^ exp` will
 *   round to the original value. The range is inclusive only when
 *   `inclusive` is `true`.
 */
public open class Decoded(
    /**
     * The scaled mantissa.
     */
    public var mant: ULong,
    /**
     * The lower error range.
     */
    public var minus: ULong,
    /**
     * The upper error range.
     */
    public var plus: ULong,
    /**
     * The shared exponent in base 2.
     */
    public var exp: Short,
    /**
     * True when the error range is inclusive.
     *
     * In IEEE 754, this is true when the original mantissa was even.
     */
    public var inclusive: Boolean,
) {
    public open fun clone(): Decoded = Decoded(mant, minus, plus, exp, inclusive)

    public companion object {
        public fun typeLayout(): TypeLayout<Decoded> =
            object : TypeLayout<Decoded> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Decoded =
                    Decoded(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.I16).readNative(addr + 24L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 26L),
                    )
                override fun writeNative(addr: Long, value: Decoded) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.mant)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.minus)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.plus)
                    (PrimitiveLayouts.I16).writeNative(addr + 24L, value.exp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 26L, value.inclusive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Decoded =
                    Decoded(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 26),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Decoded) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.mant)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.minus)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.plus)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 24, value.exp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 26, value.inclusive)
                }
            }
    }
}

/**
 * Decoded unsigned value.
 */
public sealed class FullDecoded {
    /**
     * Not-a-number.
     */
    public data object Nan : FullDecoded()
    /**
     * Infinities, either positive or negative.
     */
    public data object Infinite : FullDecoded()
    /**
     * Zero, either positive or negative.
     */
    public data object Zero : FullDecoded()
    /**
     * Finite numbers with further decoded fields.
     */
    public data class Finite(public var value: Decoded) : FullDecoded()

    public fun rustEquals(other: FullDecoded): Boolean = this == other
}

/**
 * A floating point type which can be `decode`d.
 */
public interface DecodableFloat<T> : FloatExt<T> {
    /**
     * The minimum positive normalized value.
     */
    public fun minPosNormValue(): T
}

public fun decodableFloatMinPosNormValueF16(): F16 = MIN_POSITIVE

public fun decodableFloatMinPosNormValueFloat(): kotlin.Float = 1.17549435E-38f

public fun decodableFloatMinPosNormValueDouble(): Double = 2.2250738585072014E-308

/**
 * Returns a sign (true when negative) and `FullDecoded` value
 * from given floating point number.
 */
public fun <T : DecodableFloat<T>> decode(v: T, minPosNormValueTFn: () -> T): Pair<Boolean, FullDecoded> {
    val (mant, exp, sign) = v.integerDecode()
    val even = (mant and 1) == 0
    val decoded =
        when (v.classify()) {
            FpCategory.Nan -> FullDecoded.Nan
            FpCategory.Infinite -> FullDecoded.Infinite
            FpCategory.Zero -> FullDecoded.Zero
            FpCategory.Subnormal -> {
                // neighbors: (mant - 2, exp) -- (mant, exp) -- (mant + 2, exp)
                // Float.integerDecode always preserves the exponent,
                // so the mantissa is scaled for subnormals.
                FullDecoded.Finite(Decoded(mant = mant, minus = 1uL, plus = 1uL, exp = exp, inclusive = even))
            }
            FpCategory.Normal -> {
                val minnorm = minPosNormValueTFn().integerDecode()
                if (mant == minnorm.first) {
                    // neighbors: (maxmant, exp - 1) -- (minnormmant, exp) -- (minnormmant + 1, exp)
                    // where maxmant = minnormmant * 2 - 1
                    FullDecoded.Finite(Decoded(mant = mant shl 2, minus = 1uL, plus = 2uL, exp = (exp - 2).toShort(), inclusive = even))
                } else {
                    // neighbors: (mant - 1, exp) -- (mant, exp) -- (mant + 1, exp)
                    FullDecoded.Finite(Decoded(mant = mant shl 1, minus = 1uL, plus = 1uL, exp = (exp - 1).toShort(), inclusive = even))
                }
            }
        }
    return Pair(sign < 0, decoded)
}
