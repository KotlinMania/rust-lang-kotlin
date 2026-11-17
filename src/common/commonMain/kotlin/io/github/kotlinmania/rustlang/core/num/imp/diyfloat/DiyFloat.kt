// port-lint: source num/imp/diy_float.rs
package io.github.kotlinmania.rustlang.core.num.imp.diyfloat

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed

// Extended precision "soft float", for internal use only.

/**
 * A custom 64-bit floating point type, representing `f * 2 ^ e`.
 */
public open class Fp(
    /**
     * The integer mantissa.
     */
    public var f: ULong,
    /**
     * The exponent in base 2.
     */
    public var e: Short,
) {
    public open fun clone(): Fp = Fp(f, e)

    /**
     * Returns a correctly rounded product of itself and `other`.
     */
    public open fun mul(other: Fp): Fp {
        val (lo, hi) = this.f.carryingMul(other.f, 0)
        val f = hi + (lo shr 63)
        val e = this.e + other.e + 64
        return Fp(f = f, e = e)
    }

    /**
     * Normalizes itself so that the resulting mantissa is at least `2 ^ 63`.
     */
    public open fun normalize(): Fp {
        val lz = this.f.leadingZeros()
        val f = this.f shl lz.toInt()
        val e = this.e - lz.toShort()
        if (false) {
            check(f >= (1uL shl 63))
        }
        return Fp(f = f, e = e)
    }

    /**
     * Normalizes itself to have the shared exponent.
     * It can only decrease the exponent (and thus increase the mantissa).
     */
    public open fun normalizeTo(e: Short): Fp {
        val edelta = this.e - e
        check(edelta >= 0.toShort())
        val edelta1 = edelta.toInt()
        when (val matchVal = Pair(this.f shl edelta1 shr edelta1, this.f)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = AssertKind.Eq
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
        return Fp(f = this.f shl edelta1, e = e)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Fp> =
            object : TypeLayout<Fp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Fp =
                    Fp(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Fp) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.f)
                    (PrimitiveLayouts.I16).writeNative(addr + 8L, value.e)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Fp =
                    Fp(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Fp) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.f)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 8, value.e)
                }
            }
    }
}
