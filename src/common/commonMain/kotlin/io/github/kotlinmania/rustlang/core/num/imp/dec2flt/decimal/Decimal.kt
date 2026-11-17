// port-lint: source num/imp/dec2flt/decimal.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.decimal

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.Lemire

// Representation of a float as the significant digits and exponent.

public val INT_POW10: ULongArray = ulongArrayOf(1uL, 10uL, 100uL, 1000uL, 10000uL, 100000uL, 1000000uL, 10000000uL, 100000000uL, 1000000000uL, 10000000000uL, 100000000000uL, 1000000000000uL, 10000000000000uL, 100000000000000uL, 1000000000000000uL)

/**
 * A floating point number with up to 64 bits of mantissa and an `Long` exponent.
 */
public open class Decimal(
    public var exponent: Long,
    public var mantissa: ULong,
    public var negative: Boolean,
    public var manyDigits: Boolean,
) {
    public open fun clone(): Decimal = Decimal(exponent, mantissa, negative, manyDigits)

    /**
     * Detect if the float can be accurately reconstructed from native floats.
     */
    public open fun <Int, F : Lemire<F, Int>> canUseFastPath(): Boolean = F.MIN_EXPONENT_FAST_PATH <= this.exponent && this.exponent <= F.MAX_EXPONENT_DISGUISED_FAST_PATH && this.mantissa <= F.MAX_MANTISSA_FAST_PATH && !this.manyDigits

    /**
     * Try turning the decimal into an exact float representation, using machine-sized integers
     * and floats.
     *
     * This is extracted into a separate function so that it can be attempted before constructing
     * a Decimal. This only works if both the mantissa and the exponent
     * can be exactly represented as a machine float, since IEE-754 guarantees
     * no rounding will occur.
     *
     * There is an exception: disguised fast-path cases, where we can shift
     * powers-of-10 from the exponent to the significant digits.
     */
    public open fun <Int, F : Lemire<F, Int>> tryFastPath(fromU64FFn: (ULong) -> F, pow10FastPathFFn: (Int) -> F): F? {
        // Here we need to work around <https://github.com/rust-lang/rust/issues/114479>.
        // The fast path crucially depends on arithmetic being rounded to the correct number of bits
        // without any intermediate rounding. On x86 (without SSE or SSE2) this requires the precision
        // of the x87 FPU stack to be changed so that it directly rounds to 64/32 bit.
        // The `setPrecision` function takes care of setting the precision on architectures which
        // require setting it by changing the global state (like the control word of the x87 FPU).
        if (!this.canUseFastPath<F>()) {
            return null
        }
        val value1 =
            if (this.exponent <= F.MAX_EXPONENT_FAST_PATH) {
                // normal fast path
                val value = fromU64FFn(this.mantissa)
                if (this.exponent < 0L) {
                    value / pow10FastPathFFn(null)
                } else {
                    value * pow10FastPathFFn(null)
                }
            } else {
                // disguised fast path
                val shift = this.exponent - F.MAX_EXPONENT_FAST_PATH
                val mantissa = (this.mantissa.checkedMul(INT_POW10[shift.toInt()]) ?: return null)
                if (mantissa > F.MAX_MANTISSA_FAST_PATH) {
                    return null
                }
                fromU64FFn(mantissa) * pow10FastPathFFn(null)
            }
        return if (this.negative) {
            -value1
        } else {
            value1
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Decimal> =
            object : TypeLayout<Decimal> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Decimal =
                    Decimal(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 16L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 17L),
                    )
                override fun writeNative(addr: Long, value: Decimal) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.exponent)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.mantissa)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 16L, value.negative)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 17L, value.manyDigits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Decimal =
                    Decimal(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 17),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Decimal) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.exponent)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.mantissa)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 16, value.negative)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 17, value.manyDigits)
                }
            }
    }
}
