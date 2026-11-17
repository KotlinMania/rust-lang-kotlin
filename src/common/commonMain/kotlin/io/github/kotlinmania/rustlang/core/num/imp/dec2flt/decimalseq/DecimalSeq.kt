// port-lint: source num/imp/dec2flt/decimal_seq.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.decimalseq

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common.is8digits
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.splitFirst

// Arbitrary-precision decimal type used by fallback algorithms.
//
// This is only used if the fast-path (native floats) and
// the Eisel-Lemire algorithm are unable to unambiguously
// determine the float.
//
// The technique used is "Simple Decimal Conversion", developed
// by Nigel Tao and Ken Thompson. A detailed description of the
// algorithm can be found in "ParseNumberF64 by Simple Decimal Conversion",
// available online: <https://nigeltao.github.io/blog/2020/parse-number-f64-simple.html>.

/**
 * A decimal floating-point number, represented as a sequence of decimal digits.
 */
public open class DecimalSeq(
    /**
     * The number of significant digits in the decimal.
     */
    public var numDigits: Int,
    /**
     * The offset of the decimal point in the significant digits.
     */
    public var decimalPoint: Int,
    /**
     * If the number of significant digits stored in the decimal is truncated.
     */
    public var truncated: Boolean,
    /**
     * Buffer of the raw digits, in the range [0, 9].
     */
    public var digits: UByteArray,
) {
    public open fun clone(): DecimalSeq = DecimalSeq(numDigits, decimalPoint, truncated, digits)

    // Note(tgross35): it may be better for this to return an option
    // Note(tgross35): incrementing the digit counter even if we don't push anything
    // seems incorrect.

    /**
     * Append a digit to the buffer if it fits.
     */
    public open fun tryAddDigit(digit: UByte) {
        if (this.numDigits < DecimalSeq.MAX_DIGITS) {
            this.digits[this.numDigits] = digit
        }
        this.numDigits += 1
    }

    // Note(tgross35): this could be `.rev().position()` if perf is okay

    /**
     * Trim trailing zeros from the buffer.
     */
    public open fun trim() {
        if (false) {
            check(this.numDigits <= DecimalSeq.MAX_DIGITS)
        }
        while (this.numDigits != 0 && this.digits[this.numDigits - 1] == 0u.toUByte()) {
            this.numDigits -= 1
        }
    }

    public open fun round(): ULong {
        if (this.numDigits == 0 || this.decimalPoint < 0) {
            return 0uL
        } else if (this.decimalPoint >= DecimalSeq.MAX_DIGITS_WITHOUT_OVERFLOW.toInt()) {
            return 0xFFFFFFFFFFFFFFFFuL
        }
        val dp = this.decimalPoint
        var n = 0uL
        for (i in 0 until dp) {
            n *= 10uL
            if (i < this.numDigits) {
                n += this.digits[i].toULong()
            }
        }
        var roundUp = false
        if (dp < this.numDigits) {
            roundUp = this.digits[dp] >= 5u.toUByte()
            if (this.digits[dp] == 5u.toUByte() && dp + 1 == this.numDigits) {
                roundUp = this.truncated || ((dp != 0) && (1u.toUByte() and this.digits[dp - 1] != 0u.toUByte()))
            }
        }
        if (roundUp) {
            n += 1uL
        }
        return n
    }

    /**
     * Computes decimal * 2^shift.
     */
    public open fun leftShift(shift: Int) {
        if (this.numDigits == 0) {
            return
        }
        val numNewDigits = numberOfDigitsDecimalLeftShift(this, shift)
        var readIndex = this.numDigits
        var writeIndex = this.numDigits + numNewDigits
        var n = 0uL
        while (readIndex != 0) {
            readIndex -= 1
            writeIndex -= 1
            n += (this.digits[readIndex].toULong()) shl shift
            val quotient = n / 10uL
            val remainder = n - (10uL * quotient)
            if (writeIndex < DecimalSeq.MAX_DIGITS) {
                this.digits[writeIndex] = remainder.toUByte()
            } else if (remainder > 0uL) {
                this.truncated = true
            }
            n = quotient
        }
        while (n > 0uL) {
            writeIndex -= 1
            val quotient1 = n / 10uL
            val remainder1 = n - (10uL * quotient1)
            if (writeIndex < DecimalSeq.MAX_DIGITS) {
                this.digits[writeIndex] = remainder1.toUByte()
            } else if (remainder1 > 0uL) {
                this.truncated = true
            }
            n = quotient1
        }
        this.numDigits += numNewDigits
        if (this.numDigits > DecimalSeq.MAX_DIGITS) {
            this.numDigits = DecimalSeq.MAX_DIGITS
        }
        this.decimalPoint += numNewDigits
        this.trim()
    }

    /**
     * Computes decimal * 2^-shift.
     */
    public open fun rightShift(shift: Int) {
        var readIndex = 0
        var writeIndex = 0
        var n = 0uL
        while ((n shr shift) == 0uL) {
            if (readIndex < this.numDigits) {
                n = (10uL * n) + this.digits[readIndex].toULong()
                readIndex += 1
            } else if (n == 0uL) {
                return
            } else {
                while ((n shr shift) == 0uL) {
                    n *= 10uL
                    readIndex += 1
                }
                break
            }
        }
        this.decimalPoint -= readIndex.toInt() - 1
        if (this.decimalPoint < -DecimalSeq.DECIMAL_POINT_RANGE) {
            // `self = Self.Default()`, but without the overhead of clearing `digits`.
            this.numDigits = 0
            this.decimalPoint = 0
            this.truncated = false
            return
        }
        val mask = (1uL shl shift) - 1uL
        while (readIndex < this.numDigits) {
            val newDigit = (n shr shift).toUByte()
            n = (10uL * (n and mask)) + this.digits[readIndex].toULong()
            readIndex += 1
            this.digits[writeIndex] = newDigit
            writeIndex += 1
        }
        while (n > 0uL) {
            val newDigit1 = (n shr shift).toUByte()
            n = 10uL * (n and mask)
            if (writeIndex < DecimalSeq.MAX_DIGITS) {
                this.digits[writeIndex] = newDigit1
                writeIndex += 1
            } else if (newDigit1 > 0u.toUByte()) {
                this.truncated = true
            }
        }
        this.numDigits = writeIndex
        this.trim()
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DecimalSeq> =
            object : TypeLayout<DecimalSeq> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((13L + ((DecimalSeq.MAX_DIGITS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): DecimalSeq =
                    DecimalSeq(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 12L),
                        (PrimitiveArrayLayouts.uByteArray((DecimalSeq.MAX_DIGITS).toInt())).readNative(addr + 13L),
                    )
                override fun writeNative(addr: Long, value: DecimalSeq) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.numDigits)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.decimalPoint)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 12L, value.truncated)
                    (PrimitiveArrayLayouts.uByteArray((DecimalSeq.MAX_DIGITS).toInt())).writeNative(addr + 13L, value.digits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DecimalSeq =
                    DecimalSeq(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 12),
                        (PrimitiveArrayLayouts.uByteArray((DecimalSeq.MAX_DIGITS).toInt())).readBytes(buf, offset + 13),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DecimalSeq) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.numDigits)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.decimalPoint)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 12, value.truncated)
                    (PrimitiveArrayLayouts.uByteArray((DecimalSeq.MAX_DIGITS).toInt())).writeBytes(buf, offset + 13, value.digits)
                }
            }

        public fun default(): DecimalSeq = DecimalSeq(numDigits = 0, decimalPoint = 0, truncated = false, digits = UByteArray((DecimalSeq.MAX_DIGITS).toInt()) { 0u.toUByte() })

        /**
         * The maximum number of digits required to unambiguously round up to a 64-bit float.
         *
         * For an IEEE 754 binary64 float, this required 767 digits. So we store the max digits + 1.
         *
         * We can exactly represent a float in radix `b` from radix 2 if
         * `b` is divisible by 2. This function calculates the exact number of
         * digits required to exactly represent that float.
         *
         * According to the "Handbook of Floating Point Arithmetic",
         * for IEEE754, with `emin` being the min exponent, `p2` being the
         * precision, and `b` being the radix, the number of digits follows as:
         *
         * `−emin + p2 + ⌊(emin + 1) log(2, b) − log(1 − 2^(−p2), b)⌋`
         *
         * For f32, this follows as:
         *     emin = -126
         *     p2 = 24
         *
         * For f64, this follows as:
         *     emin = -1022
         *     p2 = 53
         *
         * In Python:
         *     `- emin + p2 + math.floor((emin + 1) * math.log(2, b) - math.log(1 - 2 * * (- p2), b))`
         */
        public val MAX_DIGITS: Int = 768

        /**
         * The max decimal digits that can be exactly represented in a 64-bit integer.
         */
        public val MAX_DIGITS_WITHOUT_OVERFLOW: Int = 19

        public val DECIMAL_POINT_RANGE: Int = 2047
    }
}

/**
 * Parse a big integer representation of the float as a decimal.
 */
public fun parseDecimalSeq(s: List<UByte>): DecimalSeq {
    var s1 = s
    val d = DecimalSeq.default()
    val start = s1
    while (true) {
        val matchVal = s1.splitFirst()
        if (matchVal == null) break
        val sNext = matchVal.second
        s1 = sNext
    }
    s1 = s1.parseDigits({ digit -> d.tryAddDigit(digit) })
    run {
        val matchVal1 = s1.splitFirst()
        if (matchVal1 != null && matchVal1.first == 46u) {
            val sNext1 = matchVal1.second
            s1 = sNext1
            val first = s1
            // Skip leading zeros.
            if (d.numDigits == 0) {
                while (true) {
                    val matchVal2 = s1.splitFirst()
                    if (matchVal2 == null) break
                    val sNext2 = matchVal2.second
                    s1 = sNext2
                }
            }
            while (s1.size >= 8 && d.numDigits + 8 < DecimalSeq.MAX_DIGITS) {
                val v = s1.readU64()
                if (!is8digits(v)) {
                    break
                }
                ((d.digits).sliceArray(d.numDigits until d.digits.size)).writeU64(v - 0x3030303030303030)
                d.numDigits += 8
                s1 = ((s1).subList(8, (s1).size))
            }
            s1 = s1.parseDigits({ digit -> d.tryAddDigit(digit) })
            d.decimalPoint = s1.size - first.size
        }
    }
    if (d.numDigits != 0) {
        // Ignore the trailing zeros if there are any
        var nTrailingZeros = 0
        for (c in ((start).subList(0, (start.size - s1.size))).iter().rev()) {
            if (c == 48u) {
                nTrailingZeros += 1
            } else if (c != 46u) {
                break
            }
        }
        d.decimalPoint += nTrailingZeros.toInt()
        d.numDigits -= nTrailingZeros
        d.decimalPoint += d.numDigits
        if (d.numDigits > DecimalSeq.MAX_DIGITS) {
            d.truncated = true
            d.numDigits = DecimalSeq.MAX_DIGITS
        }
    }
    when (val matchVal3 = s1.splitFirst()) {
        null -> {}
        else -> {
            val ch = matchVal3.first
            val sNext3 = matchVal3.second
            if (ch == 101u || ch == 69u) {
                s1 = sNext3
                var negExp = false
                when (val matchVal4 = s1.splitFirst()) {
                    null -> {}
                    else -> {
                        val ch1 = matchVal4.first
                        val sNext4 = matchVal4.second
                        negExp = ch1 == 45u
                        if (ch1 == 45u || ch1 == 43u) {
                            s1 = sNext4
                        }
                    }
                }
                var expNum = 0
                s1.parseDigits({ digit ->
                    if (expNum < 0x10000) {
                        expNum = 10 * expNum + digit.toInt()
                    }
                })
                d.decimalPoint +=
                    if (negExp) {
                        -expNum
                    } else {
                        expNum
                    }
            }
        }
    }
    for (i in d.numDigits until DecimalSeq.MAX_DIGITS_WITHOUT_OVERFLOW) {
        d.digits[i] = 0u
    }
    return d
}

public fun numberOfDigitsDecimalLeftShift(d: DecimalSeq, shift: Int): Int {
    var shift1 = shift
    val TABLE: UShortArray = ushortArrayOf(0x0000u, 0x0800u, 0x0801u, 0x0803u, 0x1006u, 0x1009u, 0x100Du, 0x1812u, 0x1817u, 0x181Du, 0x2024u, 0x202Bu, 0x2033u, 0x203Cu, 0x2846u, 0x2850u, 0x285Bu, 0x3067u, 0x3073u, 0x3080u, 0x388Eu, 0x389Cu, 0x38ABu, 0x38BBu, 0x40CCu, 0x40DDu, 0x40EFu, 0x4902u, 0x4915u, 0x4929u, 0x513Eu, 0x5153u, 0x5169u, 0x5180u, 0x5998u, 0x59B0u, 0x59C9u, 0x61E3u, 0x61FDu, 0x6218u, 0x6A34u, 0x6A50u, 0x6A6Du, 0x6A8Bu, 0x72AAu, 0x72C9u, 0x72E9u, 0x7B0Au, 0x7B2Bu, 0x7B4Du, 0x8370u, 0x8393u, 0x83B7u, 0x83DCu, 0x8C02u, 0x8C28u, 0x8C4Fu, 0x9477u, 0x949Fu, 0x94C8u, 0x9CF2u, 0x051Cu, 0x051Cu, 0x051Cu, 0x051Cu)
    val TABLE_POW5: UByteArray = ubyteArrayOf(5u, 2u, 5u, 1u, 2u, 5u, 6u, 2u, 5u, 3u, 1u, 2u, 5u, 1u, 5u, 6u, 2u, 5u, 7u, 8u, 1u, 2u, 5u, 3u, 9u, 0u, 6u, 2u, 5u, 1u, 9u, 5u, 3u, 1u, 2u, 5u, 9u, 7u, 6u, 5u, 6u, 2u, 5u, 4u, 8u, 8u, 2u, 8u, 1u, 2u, 5u, 2u, 4u, 4u, 1u, 4u, 0u, 6u, 2u, 5u, 1u, 2u, 2u, 0u, 7u, 0u, 3u, 1u, 2u, 5u, 6u, 1u, 0u, 3u, 5u, 1u, 5u, 6u, 2u, 5u, 3u, 0u, 5u, 1u, 7u, 5u, 7u, 8u, 1u, 2u, 5u, 1u, 5u, 2u, 5u, 8u, 7u, 8u, 9u, 0u, 6u, 2u, 5u, 7u, 6u, 2u, 9u, 3u, 9u, 4u, 5u, 3u, 1u, 2u, 5u, 3u, 8u, 1u, 4u, 6u, 9u, 7u, 2u, 6u, 5u, 6u, 2u, 5u, 1u, 9u, 0u, 7u, 3u, 4u, 8u, 6u, 3u, 2u, 8u, 1u, 2u, 5u, 9u, 5u, 3u, 6u, 7u, 4u, 3u, 1u, 6u, 4u, 0u, 6u, 2u, 5u, 4u, 7u, 6u, 8u, 3u, 7u, 1u, 5u, 8u, 2u, 0u, 3u, 1u, 2u, 5u, 2u, 3u, 8u, 4u, 1u, 8u, 5u, 7u, 9u, 1u, 0u, 1u, 5u, 6u, 2u, 5u, 1u, 1u, 9u, 2u, 0u, 9u, 2u, 8u, 9u, 5u, 5u, 0u, 7u, 8u, 1u, 2u, 5u, 5u, 9u, 6u, 0u, 4u, 6u, 4u, 4u, 7u, 7u, 5u, 3u, 9u, 0u, 6u, 2u, 5u, 2u, 9u, 8u, 0u, 2u, 3u, 2u, 2u, 3u, 8u, 7u, 6u, 9u, 5u, 3u, 1u, 2u, 5u, 1u, 4u, 9u, 0u, 1u, 1u, 6u, 1u, 1u, 9u, 3u, 8u, 4u, 7u, 6u, 5u, 6u, 2u, 5u, 7u, 4u, 5u, 0u, 5u, 8u, 0u, 5u, 9u, 6u, 9u, 2u, 3u, 8u, 2u, 8u, 1u, 2u, 5u, 3u, 7u, 2u, 5u, 2u, 9u, 0u, 2u, 9u, 8u, 4u, 6u, 1u, 9u, 1u, 4u, 0u, 6u, 2u, 5u, 1u, 8u, 6u, 2u, 6u, 4u, 5u, 1u, 4u, 9u, 2u, 3u, 0u, 9u, 5u, 7u, 0u, 3u, 1u, 2u, 5u, 9u, 3u, 1u, 3u, 2u, 2u, 5u, 7u, 4u, 6u, 1u, 5u, 4u, 7u, 8u, 5u, 1u, 5u, 6u, 2u, 5u, 4u, 6u, 5u, 6u, 6u, 1u, 2u, 8u, 7u, 3u, 0u, 7u, 7u, 3u, 9u, 2u, 5u, 7u, 8u, 1u, 2u, 5u, 2u, 3u, 2u, 8u, 3u, 0u, 6u, 4u, 3u, 6u, 5u, 3u, 8u, 6u, 9u, 6u, 2u, 8u, 9u, 0u, 6u, 2u, 5u, 1u, 1u, 6u, 4u, 1u, 5u, 3u, 2u, 1u, 8u, 2u, 6u, 9u, 3u, 4u, 8u, 1u, 4u, 4u, 5u, 3u, 1u, 2u, 5u, 5u, 8u, 2u, 0u, 7u, 6u, 6u, 0u, 9u, 1u, 3u, 4u, 6u, 7u, 4u, 0u, 7u, 2u, 2u, 6u, 5u, 6u, 2u, 5u, 2u, 9u, 1u, 0u, 3u, 8u, 3u, 0u, 4u, 5u, 6u, 7u, 3u, 3u, 7u, 0u, 3u, 6u, 1u, 3u, 2u, 8u, 1u, 2u, 5u, 1u, 4u, 5u, 5u, 1u, 9u, 1u, 5u, 2u, 2u, 8u, 3u, 6u, 6u, 8u, 5u, 1u, 8u, 0u, 6u, 6u, 4u, 0u, 6u, 2u, 5u, 7u, 2u, 7u, 5u, 9u, 5u, 7u, 6u, 1u, 4u, 1u, 8u, 3u, 4u, 2u, 5u, 9u, 0u, 3u, 3u, 2u, 0u, 3u, 1u, 2u, 5u, 3u, 6u, 3u, 7u, 9u, 7u, 8u, 8u, 0u, 7u, 0u, 9u, 1u, 7u, 1u, 2u, 9u, 5u, 1u, 6u, 6u, 0u, 1u, 5u, 6u, 2u, 5u, 1u, 8u, 1u, 8u, 9u, 8u, 9u, 4u, 0u, 3u, 5u, 4u, 5u, 8u, 5u, 6u, 4u, 7u, 5u, 8u, 3u, 0u, 0u, 7u, 8u, 1u, 2u, 5u, 9u, 0u, 9u, 4u, 9u, 4u, 7u, 0u, 1u, 7u, 7u, 2u, 9u, 2u, 8u, 2u, 3u, 7u, 9u, 1u, 5u, 0u, 3u, 9u, 0u, 6u, 2u, 5u, 4u, 5u, 4u, 7u, 4u, 7u, 3u, 5u, 0u, 8u, 8u, 6u, 4u, 6u, 4u, 1u, 1u, 8u, 9u, 5u, 7u, 5u, 1u, 9u, 5u, 3u, 1u, 2u, 5u, 2u, 2u, 7u, 3u, 7u, 3u, 6u, 7u, 5u, 4u, 4u, 3u, 2u, 3u, 2u, 0u, 5u, 9u, 4u, 7u, 8u, 7u, 5u, 9u, 7u, 6u, 5u, 6u, 2u, 5u, 1u, 1u, 3u, 6u, 8u, 6u, 8u, 3u, 7u, 7u, 2u, 1u, 6u, 1u, 6u, 0u, 2u, 9u, 7u, 3u, 9u, 3u, 7u, 9u, 8u, 8u, 2u, 8u, 1u, 2u, 5u, 5u, 6u, 8u, 4u, 3u, 4u, 1u, 8u, 8u, 6u, 0u, 8u, 0u, 8u, 0u, 1u, 4u, 8u, 6u, 9u, 6u, 8u, 9u, 9u, 4u, 1u, 4u, 0u, 6u, 2u, 5u, 2u, 8u, 4u, 2u, 1u, 7u, 0u, 9u, 4u, 3u, 0u, 4u, 0u, 4u, 0u, 0u, 7u, 4u, 3u, 4u, 8u, 4u, 4u, 9u, 7u, 0u, 7u, 0u, 3u, 1u, 2u, 5u, 1u, 4u, 2u, 1u, 0u, 8u, 5u, 4u, 7u, 1u, 5u, 2u, 0u, 2u, 0u, 0u, 3u, 7u, 1u, 7u, 4u, 2u, 2u, 4u, 8u, 5u, 3u, 5u, 1u, 5u, 6u, 2u, 5u, 7u, 1u, 0u, 5u, 4u, 2u, 7u, 3u, 5u, 7u, 6u, 0u, 1u, 0u, 0u, 1u, 8u, 5u, 8u, 7u, 1u, 1u, 2u, 4u, 2u, 6u, 7u, 5u, 7u, 8u, 1u, 2u, 5u, 3u, 5u, 5u, 2u, 7u, 1u, 3u, 6u, 7u, 8u, 8u, 0u, 0u, 5u, 0u, 0u, 9u, 2u, 9u, 3u, 5u, 5u, 6u, 2u, 1u, 3u, 3u, 7u, 8u, 9u, 0u, 6u, 2u, 5u, 1u, 7u, 7u, 6u, 3u, 5u, 6u, 8u, 3u, 9u, 4u, 0u, 0u, 2u, 5u, 0u, 4u, 6u, 4u, 6u, 7u, 7u, 8u, 1u, 0u, 6u, 6u, 8u, 9u, 4u, 5u, 3u, 1u, 2u, 5u, 8u, 8u, 8u, 1u, 7u, 8u, 4u, 1u, 9u, 7u, 0u, 0u, 1u, 2u, 5u, 2u, 3u, 2u, 3u, 3u, 8u, 9u, 0u, 5u, 3u, 3u, 4u, 4u, 7u, 2u, 6u, 5u, 6u, 2u, 5u, 4u, 4u, 4u, 0u, 8u, 9u, 2u, 0u, 9u, 8u, 5u, 0u, 0u, 6u, 2u, 6u, 1u, 6u, 1u, 6u, 9u, 4u, 5u, 2u, 6u, 6u, 7u, 2u, 3u, 6u, 3u, 2u, 8u, 1u, 2u, 5u, 2u, 2u, 2u, 0u, 4u, 4u, 6u, 0u, 4u, 9u, 2u, 5u, 0u, 3u, 1u, 3u, 0u, 8u, 0u, 8u, 4u, 7u, 2u, 6u, 3u, 3u, 3u, 6u, 1u, 8u, 1u, 6u, 4u, 0u, 6u, 2u, 5u, 1u, 1u, 1u, 0u, 2u, 2u, 3u, 0u, 2u, 4u, 6u, 2u, 5u, 1u, 5u, 6u, 5u, 4u, 0u, 4u, 2u, 3u, 6u, 3u, 1u, 6u, 6u, 8u, 0u, 9u, 0u, 8u, 2u, 0u, 3u, 1u, 2u, 5u, 5u, 5u, 5u, 1u, 1u, 1u, 5u, 1u, 2u, 3u, 1u, 2u, 5u, 7u, 8u, 2u, 7u, 0u, 2u, 1u, 1u, 8u, 1u, 5u, 8u, 3u, 4u, 0u, 4u, 5u, 4u, 1u, 0u, 1u, 5u, 6u, 2u, 5u, 2u, 7u, 7u, 5u, 5u, 5u, 7u, 5u, 6u, 1u, 5u, 6u, 2u, 8u, 9u, 1u, 3u, 5u, 1u, 0u, 5u, 9u, 0u, 7u, 9u, 1u, 7u, 0u, 2u, 2u, 7u, 0u, 5u, 0u, 7u, 8u, 1u, 2u, 5u, 1u, 3u, 8u, 7u, 7u, 7u, 8u, 7u, 8u, 0u, 7u, 8u, 1u, 4u, 4u, 5u, 6u, 7u, 5u, 5u, 2u, 9u, 5u, 3u, 9u, 5u, 8u, 5u, 1u, 1u, 3u, 5u, 2u, 5u, 3u, 9u, 0u, 6u, 2u, 5u, 6u, 9u, 3u, 8u, 8u, 9u, 3u, 9u, 0u, 3u, 9u, 0u, 7u, 2u, 2u, 8u, 3u, 7u, 7u, 6u, 4u, 7u, 6u, 9u, 7u, 9u, 2u, 5u, 5u, 6u, 7u, 6u, 2u, 6u, 9u, 5u, 3u, 1u, 2u, 5u, 3u, 4u, 6u, 9u, 4u, 4u, 6u, 9u, 5u, 1u, 9u, 5u, 3u, 6u, 1u, 4u, 1u, 8u, 8u, 8u, 2u, 3u, 8u, 4u, 8u, 9u, 6u, 2u, 7u, 8u, 3u, 8u, 1u, 3u, 4u, 7u, 6u, 5u, 6u, 2u, 5u, 1u, 7u, 3u, 4u, 7u, 2u, 3u, 4u, 7u, 5u, 9u, 7u, 6u, 8u, 0u, 7u, 0u, 9u, 4u, 4u, 1u, 1u, 9u, 2u, 4u, 4u, 8u, 1u, 3u, 9u, 1u, 9u, 0u, 6u, 7u, 3u, 8u, 2u, 8u, 1u, 2u, 5u, 8u, 6u, 7u, 3u, 6u, 1u, 7u, 3u, 7u, 9u, 8u, 8u, 4u, 0u, 3u, 5u, 4u, 7u, 2u, 0u, 5u, 9u, 6u, 2u, 2u, 4u, 0u, 6u, 9u, 5u, 9u, 5u, 3u, 3u, 6u, 9u, 1u, 4u, 0u, 6u, 2u, 5u)
    shift1 = shift1 and 63
    val xA = TABLE[shift1]
    val xB = TABLE[shift1 + 1]
    val numNewDigits = (xA shr 11)
    val pow5A = (0x7FF and xA).toInt()
    val pow5B = (0x7FF and xB).toInt()
    val pow5 = TABLE_POW5[pow5A until TABLE_POW5.size]
    for ((i, p5) in pow5.iter().mapIndexed { index, item -> Pair(index, item) }.take(pow5B - pow5A)) {
        if (i >= d.numDigits) {
            return numNewDigits - 1
        } else if (d.digits[i] == p5) {
            continue
        } else if (d.digits[i] < p5) {
            return numNewDigits - 1
        } else {
            return numNewDigits
        }
    }
    return numNewDigits
}
