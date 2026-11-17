// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions

import io.github.kotlinmania.rustlang.core.char.methods.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

public open class L1Lut(
    public var l2Luts: List<L2Lut>,
)

public open class L2Lut(
    public var singles: List<Pair<Range, Short>>,
    public var multis: List<Pair<UShort, UShortArray>>,
    public var exceptions: List<Pair<UShort, List<Char>>>,
)

public open class Range(
    public var start: UShort,
    public var len: UByte,
    public var parity: Boolean,
) {
    public open fun clone(): Range = Range(start, len, parity)

    public open fun start(): UShort = this.start

    public open fun end(): UShort = this.start + this.len.toUShort()

    public companion object {
        public fun typeLayout(): TypeLayout<Range> =
            object : TypeLayout<Range> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Range =
                    Range(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: Range) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.start)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.len)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.parity)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Range =
                    Range(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Range) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.start)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.len)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.parity)
                }
            }

        public fun new(range: RangeInclusive<UShort>, parity: Boolean): Range {
            val start = range.start()
            val end = range.end()
            check(start <= end)
            val len = end - start
            check(len <= 255)
            return Range(start = start, len = len.toUByte(), parity = parity)
        }

        public fun singleton(start: UShort): Range = Range.new(RangeInclusive(start, start, false), false)

        public fun stepBy1(range: RangeInclusive<UShort>): Range = Range.new(range, false)

        public fun stepBy2(range: RangeInclusive<UShort>): Range = Range.new(range, true)
    }
}

public fun deconstruct(c: Char): Pair<UShort, UShort> {
    val c1 = c.toUInt()
    val plane = (c1 shr 16).toUShort()
    val low = c1.toUShort()
    return Pair(plane, low)
}

public fun reconstruct(plane: UShort, low: UShort): Char {
    // SAFETY: The caller must ensure that the result is a valid `char`.
    return Char.fromU32Unchecked(((plane.toUInt()) shl 16) or (low.toUInt()))
}

public fun lookup(input: Char, l1Lut: L1Lut): List<Char>? {
    val (inputHigh, inputLow) = deconstruct(input)
    val l2Lut = l1Lut.l2Luts.get(inputHigh.toInt()) ?: return null
    val idx =
        l2Lut.singles.binarySearchBy({ (range, _) ->
    
            if (inputLow < range.start()) {
                Ordering.Greater
            } else if (inputLow > range.end()) {
                Ordering.Less
            } else {
                Ordering.Equal
            }
        })
    if (idx is Result.Ok<*, *>) {
        val idx1 = idx.okValue()
        // SAFETY: binary search guarantees that the index is in bounds.
        val (range, outputDelta) = l2Lut.singles.getUnchecked(idx1)
        val mask = range.parity.toUShort()
        if (inputLow and mask == range.start() and mask) {
            val outputLow = inputLow.wrappingAddSigned(outputDelta)
            // SAFETY: Table data are guaranteed to be valid Unicode.
            val output = reconstruct(inputHigh, outputLow)
            return listOf(output, '\u0000', '\u0000')
        }
    }
    when (val matchVal = l2Lut.multis.binarySearchByKey(inputLow, { (p, _) -> p })) {
        is Result.Ok<*, *> -> {
            val idx2 = matchVal.okValue()
            // SAFETY: binary search guarantees that the index is in bounds.
            val (_, outputLows) = l2Lut.multis.getUnchecked(idx2)
            // SAFETY: Table data are guaranteed to be valid Unicode.
            val output1 = outputLows.map({ outputLow1 -> reconstruct(inputHigh, outputLow1) })
            return output1
        }
        else -> {}
    }
    for ((key, output2) in l2Lut.exceptions) {
        if (key == inputLow) {
            return output2
        }
    }
    return null
}

public fun toLower(c: Char): List<Char> {
    // https://util.unicode.org/UnicodeJsps/list-unicodeset.jsp?a=[:ChangesWhenLowercased:]-[:ASCII:]&abb=on
    if (c < '\u00c0') {
        return listOf(c.toAsciiLowercase(), '\u0000', '\u0000')
    }
    // The rest of Latin-1: À..=Þ (except ×) add 0x20, everything else maps to itself.
    if (c <= '\u00ff') {
        return when {
            c in '\u00c0'..'\u00de' && c != '\u00d7' -> {
                // SAFETY: U+00C0..=U+00DE plus 0x20 lands in U+00E0..=U+00FE.
                listOf(Char.fromU32Unchecked(c.toUInt() + 0x20u), '\u0000', '\u0000')
            }
            else -> listOf(c, '\u0000', '\u0000')
        }
    }
    return (lookup(c, LOWERCASE_LUT) ?: listOf(c, '\u0000', '\u0000'))
}

public fun toUpper(c: Char): List<Char> {
    // https://util.unicode.org/UnicodeJsps/list-unicodeset.jsp?a=[:ChangesWhenUppercased:]-[:ASCII:]&abb=on
    if (c < '\u00b5') {
        return listOf(c.toAsciiUppercase(), '\u0000', '\u0000')
    }
    // The rest of Latin-1: µ maps to U+039C, ß expands to SS,
    // à..=þ (except ÷) subtract 0x20, ÿ maps to U+0178.
    if (c <= '\u00ff') {
        return when {
            c == '\u00b5' -> listOf('\u039c', '\u0000', '\u0000')
            c == '\u00df' -> listOf('S', 'S', '\u0000')
            c in '\u00e0'..'\u00fe' && c != '\u00f7' -> {
                // SAFETY: U+00E0..=U+00FE minus 0x20 lands in U+00C0..=U+00DE.
                listOf(Char.fromU32Unchecked(c.toUInt() - 0x20u), '\u0000', '\u0000')
            }
            c == '\u00ff' -> listOf('\u0178', '\u0000', '\u0000')
            else -> listOf(c, '\u0000', '\u0000')
        }
    }
    return (lookup(c, UPPERCASE_LUT) ?: listOf(c, '\u0000', '\u0000'))
}

public fun toTitle(c: Char): List<Char> {
    // https://util.unicode.org/UnicodeJsps/list-unicodeset.jsp?a=[:ChangesWhenTitlecased:]-[:ASCII:]&abb=on
    if (c < '\u00b5') {
        return listOf(c.toAsciiUppercase(), '\u0000', '\u0000')
    }
    return ((lookup(c, TITLECASE_LUT) ?: lookup(c, UPPERCASE_LUT)) ?: listOf(c, '\u0000', '\u0000'))
}

public fun toCasefold(c: Char): List<Char> {
    // https://util.unicode.org/UnicodeJsps/list-unicodeset.jsp?a=[:ChangesWhenCasefolded:]-[:ASCII:]&abb=on
    if (c < '\u00b5') {
        return listOf(c.toAsciiLowercase(), '\u0000', '\u0000')
    }
    return (lookup(c, CASEFOLD_LUT) ?: {
        // Fall back to lowercase of uppercase
        val uppercase = (lookup(c, UPPERCASE_LUT) ?: listOf(c, '\u0000', '\u0000'))
        // We need to take the lowercase of each character in `uppercase`,
        // and then concatenate them together.
        // Lowercase the first uppercased char
        var finalResult = toLower(uppercase[0])
        if (uppercase[1] != '\u0000') {
            // There's a 2nd uppercase char, lowercase it as well
            val lowercase1 = toLower(uppercase[1])
            if (false) {
                when (val matchVal = Pair(lowercase1[2], '\u0000')) {
                    else -> {
                        val leftVal = matchVal.first
                        val rightVal = matchVal.second
                        if (!(leftVal == rightVal)) {
                            val kind = AssertKind.Eq
                            assertFailed(kind, leftVal, rightVal, Option.None)
                        }
                    }
                }
            }
            if (false) {
                when (val matchVal1 = Pair(finalResult[1], '\u0000')) {
                    else -> {
                        val leftVal1 = matchVal1.first
                        val rightVal1 = matchVal1.second
                        if (!(leftVal1 == rightVal1)) {
                            val kind1 = AssertKind.Eq
                            assertFailed(kind1, leftVal1, rightVal1, Option.None)
                        }
                    }
                }
            }
            // if finalResult[1] == '\0' (
            finalResult[1] = lowercase1[0]
            if (uppercase[2] != '\u0000') {
                if (false) {
                    when (val matchVal2 = Pair(lowercase1[1], '\u0000')) {
                        else -> {
                            val leftVal2 = matchVal2.first
                            val rightVal2 = matchVal2.second
                            if (!(leftVal2 == rightVal2)) {
                                val kind2 = AssertKind.Eq
                                assertFailed(kind2, leftVal2, rightVal2, Option.None)
                            }
                        }
                    }
                }
                val lowercase2 = toLower(uppercase[2])
                if (false) {
                    when (val matchVal3 = Pair(lowercase2[1], '\u0000')) {
                        else -> {
                            val leftVal3 = matchVal3.first
                            val rightVal3 = matchVal3.second
                            if (!(leftVal3 == rightVal3)) {
                                val kind3 = AssertKind.Eq
                                assertFailed(kind3, leftVal3, rightVal3, Option.None)
                            }
                        }
                    }
                }
                if (false) {
                    when (val matchVal4 = Pair(lowercase2[2], '\u0000')) {
                        else -> {
                            val leftVal4 = matchVal4.first
                            val rightVal4 = matchVal4.second
                            if (!(leftVal4 == rightVal4)) {
                                val kind4 = AssertKind.Eq
                                assertFailed(kind4, leftVal4, rightVal4, Option.None)
                            }
                        }
                    }
                }
                finalResult[2] = lowercase2[0]
            } else {
                if (false) {
                    when (val matchVal5 = Pair(lowercase1[1], '\u0000')) {
                        else -> {
                            val leftVal5 = matchVal5.first
                            val rightVal5 = matchVal5.second
                            if (!(leftVal5 == rightVal5)) {
                                val kind5 = AssertKind.Eq
                                assertFailed(kind5, leftVal5, rightVal5, Option.None)
                            }
                        }
                    }
                }
                // finalResult[2] = lowercase1[1];
            }
            // ) else (
            // finalResult[2] = lowercase1[0];
            // debugAssertEq(lowercase1[1], '\0');
            // debugAssertEq(uppercase[2], '\0')
            // )
        }
        finalResult
    }())
}

public var LOWERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x00c0u.toUShort(), 0x00d6u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x00d8u.toUShort(), 0x00deu.toUShort(), false)), 32), Pair(Range.stepBy2(RangeInclusive(0x0100u.toUShort(), 0x012eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0132u.toUShort(), 0x0136u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0139u.toUShort(), 0x0147u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x014au.toUShort(), 0x0176u.toUShort(), false)), 1), Pair(Range.singleton(0x0178u), -121), Pair(Range.stepBy2(RangeInclusive(0x0179u.toUShort(), 0x017du.toUShort(), false)), 1), Pair(Range.singleton(0x0181u), 210), Pair(Range.stepBy2(RangeInclusive(0x0182u.toUShort(), 0x0184u.toUShort(), false)), 1), Pair(Range.singleton(0x0186u), 206), Pair(Range.singleton(0x0187u), 1), Pair(Range.stepBy1(RangeInclusive(0x0189u.toUShort(), 0x018au.toUShort(), false)), 205), Pair(Range.singleton(0x018bu), 1), Pair(Range.singleton(0x018eu), 79), Pair(Range.singleton(0x018fu), 202), Pair(Range.singleton(0x0190u), 203), Pair(Range.singleton(0x0191u), 1), Pair(Range.singleton(0x0193u), 205), Pair(Range.singleton(0x0194u), 207), Pair(Range.singleton(0x0196u), 211), Pair(Range.singleton(0x0197u), 209), Pair(Range.singleton(0x0198u), 1), Pair(Range.singleton(0x019cu), 211), Pair(Range.singleton(0x019du), 213), Pair(Range.singleton(0x019fu), 214), Pair(Range.stepBy2(RangeInclusive(0x01a0u.toUShort(), 0x01a4u.toUShort(), false)), 1), Pair(Range.singleton(0x01a6u), 218), Pair(Range.singleton(0x01a7u), 1), Pair(Range.singleton(0x01a9u), 218), Pair(Range.singleton(0x01acu), 1), Pair(Range.singleton(0x01aeu), 218), Pair(Range.singleton(0x01afu), 1), Pair(Range.stepBy1(RangeInclusive(0x01b1u.toUShort(), 0x01b2u.toUShort(), false)), 217), Pair(Range.stepBy2(RangeInclusive(0x01b3u.toUShort(), 0x01b5u.toUShort(), false)), 1), Pair(Range.singleton(0x01b7u), 219), Pair(Range.singleton(0x01b8u), 1), Pair(Range.singleton(0x01bcu), 1), Pair(Range.singleton(0x01c4u), 2), Pair(Range.singleton(0x01c5u), 1), Pair(Range.singleton(0x01c7u), 2), Pair(Range.singleton(0x01c8u), 1), Pair(Range.singleton(0x01cau), 2), Pair(Range.stepBy2(RangeInclusive(0x01cbu.toUShort(), 0x01dbu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x01deu.toUShort(), 0x01eeu.toUShort(), false)), 1), Pair(Range.singleton(0x01f1u), 2), Pair(Range.stepBy2(RangeInclusive(0x01f2u.toUShort(), 0x01f4u.toUShort(), false)), 1), Pair(Range.singleton(0x01f6u), -97), Pair(Range.singleton(0x01f7u), -56), Pair(Range.stepBy2(RangeInclusive(0x01f8u.toUShort(), 0x021eu.toUShort(), false)), 1), Pair(Range.singleton(0x0220u), -130), Pair(Range.stepBy2(RangeInclusive(0x0222u.toUShort(), 0x0232u.toUShort(), false)), 1), Pair(Range.singleton(0x023au), 10795), Pair(Range.singleton(0x023bu), 1), Pair(Range.singleton(0x023du), -163), Pair(Range.singleton(0x023eu), 10792), Pair(Range.singleton(0x0241u), 1), Pair(Range.singleton(0x0243u), -195), Pair(Range.singleton(0x0244u), 69), Pair(Range.singleton(0x0245u), 71), Pair(Range.stepBy2(RangeInclusive(0x0246u.toUShort(), 0x024eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0370u.toUShort(), 0x0372u.toUShort(), false)), 1), Pair(Range.singleton(0x0376u), 1), Pair(Range.singleton(0x037fu), 116), Pair(Range.singleton(0x0386u), 38), Pair(Range.stepBy1(RangeInclusive(0x0388u.toUShort(), 0x038au.toUShort(), false)), 37), Pair(Range.singleton(0x038cu), 64), Pair(Range.stepBy1(RangeInclusive(0x038eu.toUShort(), 0x038fu.toUShort(), false)), 63), Pair(Range.stepBy1(RangeInclusive(0x0391u.toUShort(), 0x03a1u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x03a3u.toUShort(), 0x03abu.toUShort(), false)), 32), Pair(Range.singleton(0x03cfu), 8), Pair(Range.stepBy2(RangeInclusive(0x03d8u.toUShort(), 0x03eeu.toUShort(), false)), 1), Pair(Range.singleton(0x03f4u), -60), Pair(Range.singleton(0x03f7u), 1), Pair(Range.singleton(0x03f9u), -7), Pair(Range.singleton(0x03fau), 1), Pair(Range.stepBy1(RangeInclusive(0x03fdu.toUShort(), 0x03ffu.toUShort(), false)), -130), Pair(Range.stepBy1(RangeInclusive(0x0400u.toUShort(), 0x040fu.toUShort(), false)), 80), Pair(Range.stepBy1(RangeInclusive(0x0410u.toUShort(), 0x042fu.toUShort(), false)), 32), Pair(Range.stepBy2(RangeInclusive(0x0460u.toUShort(), 0x0480u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x048au.toUShort(), 0x04beu.toUShort(), false)), 1), Pair(Range.singleton(0x04c0u), 15), Pair(Range.stepBy2(RangeInclusive(0x04c1u.toUShort(), 0x04cdu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x04d0u.toUShort(), 0x052eu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0x0531u.toUShort(), 0x0556u.toUShort(), false)), 48), Pair(Range.stepBy1(RangeInclusive(0x10a0u.toUShort(), 0x10c5u.toUShort(), false)), 7264), Pair(Range.singleton(0x10c7u), 7264), Pair(Range.singleton(0x10cdu), 7264), Pair(Range.stepBy1(RangeInclusive(0x13a0u.toUShort(), 0x13efu.toUShort(), false)), -26672), Pair(Range.stepBy1(RangeInclusive(0x13f0u.toUShort(), 0x13f5u.toUShort(), false)), 8), Pair(Range.singleton(0x1c89u), 1), Pair(Range.stepBy1(RangeInclusive(0x1c90u.toUShort(), 0x1cbau.toUShort(), false)), -3008), Pair(Range.stepBy1(RangeInclusive(0x1cbdu.toUShort(), 0x1cbfu.toUShort(), false)), -3008), Pair(Range.stepBy2(RangeInclusive(0x1e00u.toUShort(), 0x1e94u.toUShort(), false)), 1), Pair(Range.singleton(0x1e9eu), -7615), Pair(Range.stepBy2(RangeInclusive(0x1ea0u.toUShort(), 0x1efeu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0x1f08u.toUShort(), 0x1f0fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f18u.toUShort(), 0x1f1du.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f28u.toUShort(), 0x1f2fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f38u.toUShort(), 0x1f3fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f48u.toUShort(), 0x1f4du.toUShort(), false)), -8), Pair(Range.stepBy2(RangeInclusive(0x1f59u.toUShort(), 0x1f5fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f68u.toUShort(), 0x1f6fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f88u.toUShort(), 0x1f8fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f98u.toUShort(), 0x1f9fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fa8u.toUShort(), 0x1fafu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fb8u.toUShort(), 0x1fb9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fbau.toUShort(), 0x1fbbu.toUShort(), false)), -74), Pair(Range.singleton(0x1fbcu), -9), Pair(Range.stepBy1(RangeInclusive(0x1fc8u.toUShort(), 0x1fcbu.toUShort(), false)), -86), Pair(Range.singleton(0x1fccu), -9), Pair(Range.stepBy1(RangeInclusive(0x1fd8u.toUShort(), 0x1fd9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fdau.toUShort(), 0x1fdbu.toUShort(), false)), -100), Pair(Range.stepBy1(RangeInclusive(0x1fe8u.toUShort(), 0x1fe9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1feau.toUShort(), 0x1febu.toUShort(), false)), -112), Pair(Range.singleton(0x1fecu), -7), Pair(Range.stepBy1(RangeInclusive(0x1ff8u.toUShort(), 0x1ff9u.toUShort(), false)), -128), Pair(Range.stepBy1(RangeInclusive(0x1ffau.toUShort(), 0x1ffbu.toUShort(), false)), -126), Pair(Range.singleton(0x1ffcu), -9), Pair(Range.singleton(0x2126u), -7517), Pair(Range.singleton(0x212au), -8383), Pair(Range.singleton(0x212bu), -8262), Pair(Range.singleton(0x2132u), 28), Pair(Range.stepBy1(RangeInclusive(0x2160u.toUShort(), 0x216fu.toUShort(), false)), 16), Pair(Range.singleton(0x2183u), 1), Pair(Range.stepBy1(RangeInclusive(0x24b6u.toUShort(), 0x24cfu.toUShort(), false)), 26), Pair(Range.stepBy1(RangeInclusive(0x2c00u.toUShort(), 0x2c2fu.toUShort(), false)), 48), Pair(Range.singleton(0x2c60u), 1), Pair(Range.singleton(0x2c62u), -10743), Pair(Range.singleton(0x2c63u), -3814), Pair(Range.singleton(0x2c64u), -10727), Pair(Range.stepBy2(RangeInclusive(0x2c67u.toUShort(), 0x2c6bu.toUShort(), false)), 1), Pair(Range.singleton(0x2c6du), -10780), Pair(Range.singleton(0x2c6eu), -10749), Pair(Range.singleton(0x2c6fu), -10783), Pair(Range.singleton(0x2c70u), -10782), Pair(Range.singleton(0x2c72u), 1), Pair(Range.singleton(0x2c75u), 1), Pair(Range.stepBy1(RangeInclusive(0x2c7eu.toUShort(), 0x2c7fu.toUShort(), false)), -10815), Pair(Range.stepBy2(RangeInclusive(0x2c80u.toUShort(), 0x2ce2u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x2cebu.toUShort(), 0x2cedu.toUShort(), false)), 1), Pair(Range.singleton(0x2cf2u), 1), Pair(Range.stepBy2(RangeInclusive(0xa640u.toUShort(), 0xa66cu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa680u.toUShort(), 0xa69au.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa722u.toUShort(), 0xa72eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa732u.toUShort(), 0xa76eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa779u.toUShort(), 0xa77bu.toUShort(), false)), 1), Pair(Range.singleton(0xa77du), 30204), Pair(Range.stepBy2(RangeInclusive(0xa77eu.toUShort(), 0xa786u.toUShort(), false)), 1), Pair(Range.singleton(0xa78bu), 1), Pair(Range.singleton(0xa78du), 23256), Pair(Range.stepBy2(RangeInclusive(0xa790u.toUShort(), 0xa792u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa796u.toUShort(), 0xa7a8u.toUShort(), false)), 1), Pair(Range.singleton(0xa7aau), 23228), Pair(Range.singleton(0xa7abu), 23217), Pair(Range.singleton(0xa7acu), 23221), Pair(Range.singleton(0xa7adu), 23231), Pair(Range.singleton(0xa7aeu), 23228), Pair(Range.singleton(0xa7b0u), 23278), Pair(Range.singleton(0xa7b1u), 23254), Pair(Range.singleton(0xa7b2u), 23275), Pair(Range.singleton(0xa7b3u), 928), Pair(Range.stepBy2(RangeInclusive(0xa7b4u.toUShort(), 0xa7c2u.toUShort(), false)), 1), Pair(Range.singleton(0xa7c4u), -48), Pair(Range.singleton(0xa7c5u), 23229), Pair(Range.singleton(0xa7c6u), 30152), Pair(Range.stepBy2(RangeInclusive(0xa7c7u.toUShort(), 0xa7c9u.toUShort(), false)), 1), Pair(Range.singleton(0xa7cbu), 23193), Pair(Range.stepBy2(RangeInclusive(0xa7ccu.toUShort(), 0xa7dau.toUShort(), false)), 1), Pair(Range.singleton(0xa7dcu), 22975), Pair(Range.singleton(0xa7ddu), 23194), Pair(Range.singleton(0xa7e2u), 23194), Pair(Range.singleton(0xa7f5u), 1), Pair(Range.stepBy1(RangeInclusive(0xab6cu.toUShort(), 0xab6du.toUShort(), false)), -33), Pair(Range.stepBy1(RangeInclusive(0xff21u.toUShort(), 0xff3au.toUShort(), false)), 32)), multis = listOf(Pair(0x0130, listOf(0x0069, 0x0307, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x0400u.toUShort(), 0x0427u.toUShort(), false)), 40), Pair(Range.stepBy1(RangeInclusive(0x04b0u.toUShort(), 0x04d3u.toUShort(), false)), 40), Pair(Range.stepBy1(RangeInclusive(0x0570u.toUShort(), 0x057au.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x057cu.toUShort(), 0x058au.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x058cu.toUShort(), 0x0592u.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x0594u.toUShort(), 0x0595u.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x0c80u.toUShort(), 0x0cb2u.toUShort(), false)), 64), Pair(Range.stepBy1(RangeInclusive(0x0d50u.toUShort(), 0x0d65u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x18a0u.toUShort(), 0x18bfu.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x6e40u.toUShort(), 0x6e5fu.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x6ea0u.toUShort(), 0x6eb8u.toUShort(), false)), 27), Pair(Range.singleton(0xdf40u), 1), Pair(Range.stepBy2(RangeInclusive(0xdf48u.toUShort(), 0xdf4au.toUShort(), false)), 1), Pair(Range.singleton(0xdf4du), 1), Pair(Range.singleton(0xdf51u), 1), Pair(Range.stepBy2(RangeInclusive(0xdf68u.toUShort(), 0xdf6eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xdf72u.toUShort(), 0xdf7eu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0xe900u.toUShort(), 0xe921u.toUShort(), false)), 34)), multis = listOf(), exceptions = listOf())))

public var UPPERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x00b5u), 743), Pair(Range.stepBy1(RangeInclusive(0x00e0u.toUShort(), 0x00f6u.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x00f8u.toUShort(), 0x00feu.toUShort(), false)), -32), Pair(Range.singleton(0x00ffu), 121), Pair(Range.stepBy2(RangeInclusive(0x0101u.toUShort(), 0x012fu.toUShort(), false)), -1), Pair(Range.singleton(0x0131u), -232), Pair(Range.stepBy2(RangeInclusive(0x0133u.toUShort(), 0x0137u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x013au.toUShort(), 0x0148u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x014bu.toUShort(), 0x0177u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x017au.toUShort(), 0x017eu.toUShort(), false)), -1), Pair(Range.singleton(0x017fu), -300), Pair(Range.singleton(0x0180u), 195), Pair(Range.stepBy2(RangeInclusive(0x0183u.toUShort(), 0x0185u.toUShort(), false)), -1), Pair(Range.singleton(0x0188u), -1), Pair(Range.singleton(0x018cu), -1), Pair(Range.singleton(0x0192u), -1), Pair(Range.singleton(0x0195u), 97), Pair(Range.singleton(0x0199u), -1), Pair(Range.singleton(0x019au), 163), Pair(Range.singleton(0x019bu), -22975), Pair(Range.singleton(0x019eu), 130), Pair(Range.stepBy2(RangeInclusive(0x01a1u.toUShort(), 0x01a5u.toUShort(), false)), -1), Pair(Range.singleton(0x01a8u), -1), Pair(Range.singleton(0x01adu), -1), Pair(Range.singleton(0x01b0u), -1), Pair(Range.stepBy2(RangeInclusive(0x01b4u.toUShort(), 0x01b6u.toUShort(), false)), -1), Pair(Range.singleton(0x01b9u), -1), Pair(Range.singleton(0x01bdu), -1), Pair(Range.singleton(0x01bfu), 56), Pair(Range.singleton(0x01c5u), -1), Pair(Range.singleton(0x01c6u), -2), Pair(Range.singleton(0x01c8u), -1), Pair(Range.singleton(0x01c9u), -2), Pair(Range.singleton(0x01cbu), -1), Pair(Range.singleton(0x01ccu), -2), Pair(Range.stepBy2(RangeInclusive(0x01ceu.toUShort(), 0x01dcu.toUShort(), false)), -1), Pair(Range.singleton(0x01ddu), -79), Pair(Range.stepBy2(RangeInclusive(0x01dfu.toUShort(), 0x01efu.toUShort(), false)), -1), Pair(Range.singleton(0x01f2u), -1), Pair(Range.singleton(0x01f3u), -2), Pair(Range.singleton(0x01f5u), -1), Pair(Range.stepBy2(RangeInclusive(0x01f9u.toUShort(), 0x021fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x0223u.toUShort(), 0x0233u.toUShort(), false)), -1), Pair(Range.singleton(0x023cu), -1), Pair(Range.stepBy1(RangeInclusive(0x023fu.toUShort(), 0x0240u.toUShort(), false)), 10815), Pair(Range.singleton(0x0242u), -1), Pair(Range.stepBy2(RangeInclusive(0x0247u.toUShort(), 0x024fu.toUShort(), false)), -1), Pair(Range.singleton(0x0250u), 10783), Pair(Range.singleton(0x0251u), 10780), Pair(Range.singleton(0x0252u), 10782), Pair(Range.singleton(0x0253u), -210), Pair(Range.singleton(0x0254u), -206), Pair(Range.stepBy1(RangeInclusive(0x0256u.toUShort(), 0x0257u.toUShort(), false)), -205), Pair(Range.singleton(0x0259u), -202), Pair(Range.singleton(0x025bu), -203), Pair(Range.singleton(0x025cu), -23217), Pair(Range.singleton(0x0260u), -205), Pair(Range.singleton(0x0261u), -23221), Pair(Range.singleton(0x0263u), -207), Pair(Range.singleton(0x0264u), -23193), Pair(Range.singleton(0x0265u), -23256), Pair(Range.singleton(0x0266u), -23228), Pair(Range.singleton(0x0268u), -209), Pair(Range.singleton(0x0269u), -211), Pair(Range.singleton(0x026au), -23228), Pair(Range.singleton(0x026bu), 10743), Pair(Range.singleton(0x026cu), -23231), Pair(Range.singleton(0x026fu), -211), Pair(Range.singleton(0x0271u), 10749), Pair(Range.singleton(0x0272u), -213), Pair(Range.singleton(0x0275u), -214), Pair(Range.singleton(0x0277u), -23194), Pair(Range.singleton(0x027cu), -23194), Pair(Range.singleton(0x027du), 10727), Pair(Range.singleton(0x0280u), -218), Pair(Range.singleton(0x0282u), -23229), Pair(Range.singleton(0x0283u), -218), Pair(Range.singleton(0x0287u), -23254), Pair(Range.singleton(0x0288u), -218), Pair(Range.singleton(0x0289u), -69), Pair(Range.stepBy1(RangeInclusive(0x028au.toUShort(), 0x028bu.toUShort(), false)), -217), Pair(Range.singleton(0x028cu), -71), Pair(Range.singleton(0x0292u), -219), Pair(Range.singleton(0x029du), -23275), Pair(Range.singleton(0x029eu), -23278), Pair(Range.singleton(0x0345u), 84), Pair(Range.stepBy2(RangeInclusive(0x0371u.toUShort(), 0x0373u.toUShort(), false)), -1), Pair(Range.singleton(0x0377u), -1), Pair(Range.stepBy1(RangeInclusive(0x037bu.toUShort(), 0x037du.toUShort(), false)), 130), Pair(Range.singleton(0x03acu), -38), Pair(Range.stepBy1(RangeInclusive(0x03adu.toUShort(), 0x03afu.toUShort(), false)), -37), Pair(Range.stepBy1(RangeInclusive(0x03b1u.toUShort(), 0x03c1u.toUShort(), false)), -32), Pair(Range.singleton(0x03c2u), -31), Pair(Range.stepBy1(RangeInclusive(0x03c3u.toUShort(), 0x03cbu.toUShort(), false)), -32), Pair(Range.singleton(0x03ccu), -64), Pair(Range.stepBy1(RangeInclusive(0x03cdu.toUShort(), 0x03ceu.toUShort(), false)), -63), Pair(Range.singleton(0x03d0u), -62), Pair(Range.singleton(0x03d1u), -57), Pair(Range.singleton(0x03d5u), -47), Pair(Range.singleton(0x03d6u), -54), Pair(Range.singleton(0x03d7u), -8), Pair(Range.stepBy2(RangeInclusive(0x03d9u.toUShort(), 0x03efu.toUShort(), false)), -1), Pair(Range.singleton(0x03f0u), -86), Pair(Range.singleton(0x03f1u), -80), Pair(Range.singleton(0x03f2u), 7), Pair(Range.singleton(0x03f3u), -116), Pair(Range.singleton(0x03f5u), -96), Pair(Range.singleton(0x03f8u), -1), Pair(Range.singleton(0x03fbu), -1), Pair(Range.stepBy1(RangeInclusive(0x0430u.toUShort(), 0x044fu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x0450u.toUShort(), 0x045fu.toUShort(), false)), -80), Pair(Range.stepBy2(RangeInclusive(0x0461u.toUShort(), 0x0481u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x048bu.toUShort(), 0x04bfu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x04c2u.toUShort(), 0x04ceu.toUShort(), false)), -1), Pair(Range.singleton(0x04cfu), -15), Pair(Range.stepBy2(RangeInclusive(0x04d1u.toUShort(), 0x052fu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0x0561u.toUShort(), 0x0586u.toUShort(), false)), -48), Pair(Range.stepBy1(RangeInclusive(0x10d0u.toUShort(), 0x10fau.toUShort(), false)), 3008), Pair(Range.stepBy1(RangeInclusive(0x10fdu.toUShort(), 0x10ffu.toUShort(), false)), 3008), Pair(Range.stepBy1(RangeInclusive(0x13f8u.toUShort(), 0x13fdu.toUShort(), false)), -8), Pair(Range.singleton(0x1c80u), -6254), Pair(Range.singleton(0x1c81u), -6253), Pair(Range.singleton(0x1c82u), -6244), Pair(Range.stepBy1(RangeInclusive(0x1c83u.toUShort(), 0x1c84u.toUShort(), false)), -6242), Pair(Range.singleton(0x1c85u), -6243), Pair(Range.singleton(0x1c86u), -6236), Pair(Range.singleton(0x1c87u), -6181), Pair(Range.singleton(0x1c88u), -30270), Pair(Range.singleton(0x1c8au), -1), Pair(Range.singleton(0x1d79u), -30204), Pair(Range.singleton(0x1d7du), 3814), Pair(Range.singleton(0x1d8eu), -30152), Pair(Range.stepBy2(RangeInclusive(0x1e01u.toUShort(), 0x1e95u.toUShort(), false)), -1), Pair(Range.singleton(0x1e9bu), -59), Pair(Range.stepBy2(RangeInclusive(0x1ea1u.toUShort(), 0x1effu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0x1f00u.toUShort(), 0x1f07u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f10u.toUShort(), 0x1f15u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f20u.toUShort(), 0x1f27u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f30u.toUShort(), 0x1f37u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f40u.toUShort(), 0x1f45u.toUShort(), false)), 8), Pair(Range.stepBy2(RangeInclusive(0x1f51u.toUShort(), 0x1f57u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f60u.toUShort(), 0x1f67u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f70u.toUShort(), 0x1f71u.toUShort(), false)), 74), Pair(Range.stepBy1(RangeInclusive(0x1f72u.toUShort(), 0x1f75u.toUShort(), false)), 86), Pair(Range.stepBy1(RangeInclusive(0x1f76u.toUShort(), 0x1f77u.toUShort(), false)), 100), Pair(Range.stepBy1(RangeInclusive(0x1f78u.toUShort(), 0x1f79u.toUShort(), false)), 128), Pair(Range.stepBy1(RangeInclusive(0x1f7au.toUShort(), 0x1f7bu.toUShort(), false)), 112), Pair(Range.stepBy1(RangeInclusive(0x1f7cu.toUShort(), 0x1f7du.toUShort(), false)), 126), Pair(Range.stepBy1(RangeInclusive(0x1fb0u.toUShort(), 0x1fb1u.toUShort(), false)), 8), Pair(Range.singleton(0x1fbeu), -7205), Pair(Range.stepBy1(RangeInclusive(0x1fd0u.toUShort(), 0x1fd1u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1fe0u.toUShort(), 0x1fe1u.toUShort(), false)), 8), Pair(Range.singleton(0x1fe5u), 7), Pair(Range.singleton(0x214eu), -28), Pair(Range.stepBy1(RangeInclusive(0x2170u.toUShort(), 0x217fu.toUShort(), false)), -16), Pair(Range.singleton(0x2184u), -1), Pair(Range.stepBy1(RangeInclusive(0x24d0u.toUShort(), 0x24e9u.toUShort(), false)), -26), Pair(Range.stepBy1(RangeInclusive(0x2c30u.toUShort(), 0x2c5fu.toUShort(), false)), -48), Pair(Range.singleton(0x2c61u), -1), Pair(Range.singleton(0x2c65u), -10795), Pair(Range.singleton(0x2c66u), -10792), Pair(Range.stepBy2(RangeInclusive(0x2c68u.toUShort(), 0x2c6cu.toUShort(), false)), -1), Pair(Range.singleton(0x2c73u), -1), Pair(Range.singleton(0x2c76u), -1), Pair(Range.stepBy2(RangeInclusive(0x2c81u.toUShort(), 0x2ce3u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x2cecu.toUShort(), 0x2ceeu.toUShort(), false)), -1), Pair(Range.singleton(0x2cf3u), -1), Pair(Range.stepBy1(RangeInclusive(0x2d00u.toUShort(), 0x2d25u.toUShort(), false)), -7264), Pair(Range.singleton(0x2d27u), -7264), Pair(Range.singleton(0x2d2du), -7264), Pair(Range.stepBy2(RangeInclusive(0xa641u.toUShort(), 0xa66du.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa681u.toUShort(), 0xa69bu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa723u.toUShort(), 0xa72fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa733u.toUShort(), 0xa76fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa77au.toUShort(), 0xa77cu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa77fu.toUShort(), 0xa787u.toUShort(), false)), -1), Pair(Range.singleton(0xa78cu), -1), Pair(Range.stepBy2(RangeInclusive(0xa791u.toUShort(), 0xa793u.toUShort(), false)), -1), Pair(Range.singleton(0xa794u), 48), Pair(Range.stepBy2(RangeInclusive(0xa797u.toUShort(), 0xa7a9u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7b5u.toUShort(), 0xa7c3u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7c8u.toUShort(), 0xa7cau.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7cdu.toUShort(), 0xa7dbu.toUShort(), false)), -1), Pair(Range.singleton(0xa7f6u), -1), Pair(Range.stepBy1(RangeInclusive(0xab4bu.toUShort(), 0xab4cu.toUShort(), false)), 33), Pair(Range.singleton(0xab53u), -928), Pair(Range.stepBy1(RangeInclusive(0xab70u.toUShort(), 0xabbfu.toUShort(), false)), 26672), Pair(Range.stepBy1(RangeInclusive(0xff41u.toUShort(), 0xff5au.toUShort(), false)), -32)), multis = listOf(Pair(0x00df, listOf(0x0053, 0x0053, 0x0000)), Pair(0x0149, listOf(0x02bc, 0x004e, 0x0000)), Pair(0x01f0, listOf(0x004a, 0x030c, 0x0000)), Pair(0x0390, listOf(0x0399, 0x0308, 0x0301)), Pair(0x03b0, listOf(0x03a5, 0x0308, 0x0301)), Pair(0x0587, listOf(0x0535, 0x0552, 0x0000)), Pair(0x1e96, listOf(0x0048, 0x0331, 0x0000)), Pair(0x1e97, listOf(0x0054, 0x0308, 0x0000)), Pair(0x1e98, listOf(0x0057, 0x030a, 0x0000)), Pair(0x1e99, listOf(0x0059, 0x030a, 0x0000)), Pair(0x1e9a, listOf(0x0041, 0x02be, 0x0000)), Pair(0x1f50, listOf(0x03a5, 0x0313, 0x0000)), Pair(0x1f52, listOf(0x03a5, 0x0313, 0x0300)), Pair(0x1f54, listOf(0x03a5, 0x0313, 0x0301)), Pair(0x1f56, listOf(0x03a5, 0x0313, 0x0342)), Pair(0x1f80, listOf(0x1f08, 0x0399, 0x0000)), Pair(0x1f81, listOf(0x1f09, 0x0399, 0x0000)), Pair(0x1f82, listOf(0x1f0a, 0x0399, 0x0000)), Pair(0x1f83, listOf(0x1f0b, 0x0399, 0x0000)), Pair(0x1f84, listOf(0x1f0c, 0x0399, 0x0000)), Pair(0x1f85, listOf(0x1f0d, 0x0399, 0x0000)), Pair(0x1f86, listOf(0x1f0e, 0x0399, 0x0000)), Pair(0x1f87, listOf(0x1f0f, 0x0399, 0x0000)), Pair(0x1f88, listOf(0x1f08, 0x0399, 0x0000)), Pair(0x1f89, listOf(0x1f09, 0x0399, 0x0000)), Pair(0x1f8a, listOf(0x1f0a, 0x0399, 0x0000)), Pair(0x1f8b, listOf(0x1f0b, 0x0399, 0x0000)), Pair(0x1f8c, listOf(0x1f0c, 0x0399, 0x0000)), Pair(0x1f8d, listOf(0x1f0d, 0x0399, 0x0000)), Pair(0x1f8e, listOf(0x1f0e, 0x0399, 0x0000)), Pair(0x1f8f, listOf(0x1f0f, 0x0399, 0x0000)), Pair(0x1f90, listOf(0x1f28, 0x0399, 0x0000)), Pair(0x1f91, listOf(0x1f29, 0x0399, 0x0000)), Pair(0x1f92, listOf(0x1f2a, 0x0399, 0x0000)), Pair(0x1f93, listOf(0x1f2b, 0x0399, 0x0000)), Pair(0x1f94, listOf(0x1f2c, 0x0399, 0x0000)), Pair(0x1f95, listOf(0x1f2d, 0x0399, 0x0000)), Pair(0x1f96, listOf(0x1f2e, 0x0399, 0x0000)), Pair(0x1f97, listOf(0x1f2f, 0x0399, 0x0000)), Pair(0x1f98, listOf(0x1f28, 0x0399, 0x0000)), Pair(0x1f99, listOf(0x1f29, 0x0399, 0x0000)), Pair(0x1f9a, listOf(0x1f2a, 0x0399, 0x0000)), Pair(0x1f9b, listOf(0x1f2b, 0x0399, 0x0000)), Pair(0x1f9c, listOf(0x1f2c, 0x0399, 0x0000)), Pair(0x1f9d, listOf(0x1f2d, 0x0399, 0x0000)), Pair(0x1f9e, listOf(0x1f2e, 0x0399, 0x0000)), Pair(0x1f9f, listOf(0x1f2f, 0x0399, 0x0000)), Pair(0x1fa0, listOf(0x1f68, 0x0399, 0x0000)), Pair(0x1fa1, listOf(0x1f69, 0x0399, 0x0000)), Pair(0x1fa2, listOf(0x1f6a, 0x0399, 0x0000)), Pair(0x1fa3, listOf(0x1f6b, 0x0399, 0x0000)), Pair(0x1fa4, listOf(0x1f6c, 0x0399, 0x0000)), Pair(0x1fa5, listOf(0x1f6d, 0x0399, 0x0000)), Pair(0x1fa6, listOf(0x1f6e, 0x0399, 0x0000)), Pair(0x1fa7, listOf(0x1f6f, 0x0399, 0x0000)), Pair(0x1fa8, listOf(0x1f68, 0x0399, 0x0000)), Pair(0x1fa9, listOf(0x1f69, 0x0399, 0x0000)), Pair(0x1faa, listOf(0x1f6a, 0x0399, 0x0000)), Pair(0x1fab, listOf(0x1f6b, 0x0399, 0x0000)), Pair(0x1fac, listOf(0x1f6c, 0x0399, 0x0000)), Pair(0x1fad, listOf(0x1f6d, 0x0399, 0x0000)), Pair(0x1fae, listOf(0x1f6e, 0x0399, 0x0000)), Pair(0x1faf, listOf(0x1f6f, 0x0399, 0x0000)), Pair(0x1fb2, listOf(0x1fba, 0x0399, 0x0000)), Pair(0x1fb3, listOf(0x0391, 0x0399, 0x0000)), Pair(0x1fb4, listOf(0x0386, 0x0399, 0x0000)), Pair(0x1fb6, listOf(0x0391, 0x0342, 0x0000)), Pair(0x1fb7, listOf(0x0391, 0x0342, 0x0399)), Pair(0x1fbc, listOf(0x0391, 0x0399, 0x0000)), Pair(0x1fc2, listOf(0x1fca, 0x0399, 0x0000)), Pair(0x1fc3, listOf(0x0397, 0x0399, 0x0000)), Pair(0x1fc4, listOf(0x0389, 0x0399, 0x0000)), Pair(0x1fc6, listOf(0x0397, 0x0342, 0x0000)), Pair(0x1fc7, listOf(0x0397, 0x0342, 0x0399)), Pair(0x1fcc, listOf(0x0397, 0x0399, 0x0000)), Pair(0x1fd2, listOf(0x0399, 0x0308, 0x0300)), Pair(0x1fd3, listOf(0x0399, 0x0308, 0x0301)), Pair(0x1fd6, listOf(0x0399, 0x0342, 0x0000)), Pair(0x1fd7, listOf(0x0399, 0x0308, 0x0342)), Pair(0x1fe2, listOf(0x03a5, 0x0308, 0x0300)), Pair(0x1fe3, listOf(0x03a5, 0x0308, 0x0301)), Pair(0x1fe4, listOf(0x03a1, 0x0313, 0x0000)), Pair(0x1fe6, listOf(0x03a5, 0x0342, 0x0000)), Pair(0x1fe7, listOf(0x03a5, 0x0308, 0x0342)), Pair(0x1ff2, listOf(0x1ffa, 0x0399, 0x0000)), Pair(0x1ff3, listOf(0x03a9, 0x0399, 0x0000)), Pair(0x1ff4, listOf(0x038f, 0x0399, 0x0000)), Pair(0x1ff6, listOf(0x03a9, 0x0342, 0x0000)), Pair(0x1ff7, listOf(0x03a9, 0x0342, 0x0399)), Pair(0x1ffc, listOf(0x03a9, 0x0399, 0x0000)), Pair(0xfb00, listOf(0x0046, 0x0046, 0x0000)), Pair(0xfb01, listOf(0x0046, 0x0049, 0x0000)), Pair(0xfb02, listOf(0x0046, 0x004c, 0x0000)), Pair(0xfb03, listOf(0x0046, 0x0046, 0x0049)), Pair(0xfb04, listOf(0x0046, 0x0046, 0x004c)), Pair(0xfb05, listOf(0x0053, 0x0054, 0x0000)), Pair(0xfb06, listOf(0x0053, 0x0054, 0x0000)), Pair(0xfb13, listOf(0x0544, 0x0546, 0x0000)), Pair(0xfb14, listOf(0x0544, 0x0535, 0x0000)), Pair(0xfb15, listOf(0x0544, 0x053b, 0x0000)), Pair(0xfb16, listOf(0x054e, 0x0546, 0x0000)), Pair(0xfb17, listOf(0x0544, 0x053d, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x0428u.toUShort(), 0x044fu.toUShort(), false)), -40), Pair(Range.stepBy1(RangeInclusive(0x04d8u.toUShort(), 0x04fbu.toUShort(), false)), -40), Pair(Range.stepBy1(RangeInclusive(0x0597u.toUShort(), 0x05a1u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05a3u.toUShort(), 0x05b1u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05b3u.toUShort(), 0x05b9u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05bbu.toUShort(), 0x05bcu.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x0cc0u.toUShort(), 0x0cf2u.toUShort(), false)), -64), Pair(Range.stepBy1(RangeInclusive(0x0d70u.toUShort(), 0x0d85u.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x18c0u.toUShort(), 0x18dfu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x6e60u.toUShort(), 0x6e7fu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x6ebbu.toUShort(), 0x6ed3u.toUShort(), false)), -27), Pair(Range.singleton(0xdf41u), -1), Pair(Range.stepBy2(RangeInclusive(0xdf49u.toUShort(), 0xdf4bu.toUShort(), false)), -1), Pair(Range.singleton(0xdf4eu), -1), Pair(Range.singleton(0xdf52u), -1), Pair(Range.stepBy2(RangeInclusive(0xdf69u.toUShort(), 0xdf6fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xdf73u.toUShort(), 0xdf7fu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0xe922u.toUShort(), 0xe943u.toUShort(), false)), -34)), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 'S', '\u0000'))))))

public var TITLECASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x01c4u), 1), Pair(Range.singleton(0x01c5u), 0), Pair(Range.singleton(0x01c6u), -1), Pair(Range.singleton(0x01c7u), 1), Pair(Range.singleton(0x01c8u), 0), Pair(Range.singleton(0x01c9u), -1), Pair(Range.singleton(0x01cau), 1), Pair(Range.singleton(0x01cbu), 0), Pair(Range.singleton(0x01ccu), -1), Pair(Range.singleton(0x01f1u), 1), Pair(Range.singleton(0x01f2u), 0), Pair(Range.singleton(0x01f3u), -1), Pair(Range.stepBy1(RangeInclusive(0x10d0u.toUShort(), 0x10fau.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x10fdu.toUShort(), 0x10ffu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1f80u.toUShort(), 0x1f87u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f88u.toUShort(), 0x1f8fu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1f90u.toUShort(), 0x1f97u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f98u.toUShort(), 0x1f9fu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1fa0u.toUShort(), 0x1fa7u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1fa8u.toUShort(), 0x1fafu.toUShort(), false)), 0), Pair(Range.singleton(0x1fb3u), 9), Pair(Range.singleton(0x1fbcu), 0), Pair(Range.singleton(0x1fc3u), 9), Pair(Range.singleton(0x1fccu), 0), Pair(Range.singleton(0x1ff3u), 9), Pair(Range.singleton(0x1ffcu), 0)), multis = listOf(Pair(0x00df, listOf(0x0053, 0x0073, 0x0000)), Pair(0x0587, listOf(0x0535, 0x0582, 0x0000)), Pair(0x1fb2, listOf(0x1fba, 0x0345, 0x0000)), Pair(0x1fb4, listOf(0x0386, 0x0345, 0x0000)), Pair(0x1fb7, listOf(0x0391, 0x0342, 0x0345)), Pair(0x1fc2, listOf(0x1fca, 0x0345, 0x0000)), Pair(0x1fc4, listOf(0x0389, 0x0345, 0x0000)), Pair(0x1fc7, listOf(0x0397, 0x0342, 0x0345)), Pair(0x1ff2, listOf(0x1ffa, 0x0345, 0x0000)), Pair(0x1ff4, listOf(0x038f, 0x0345, 0x0000)), Pair(0x1ff7, listOf(0x03a9, 0x0342, 0x0345)), Pair(0xfb00, listOf(0x0046, 0x0066, 0x0000)), Pair(0xfb01, listOf(0x0046, 0x0069, 0x0000)), Pair(0xfb02, listOf(0x0046, 0x006c, 0x0000)), Pair(0xfb03, listOf(0x0046, 0x0066, 0x0069)), Pair(0xfb04, listOf(0x0046, 0x0066, 0x006c)), Pair(0xfb05, listOf(0x0053, 0x0074, 0x0000)), Pair(0xfb06, listOf(0x0053, 0x0074, 0x0000)), Pair(0xfb13, listOf(0x0544, 0x0576, 0x0000)), Pair(0xfb14, listOf(0x0544, 0x0565, 0x0000)), Pair(0xfb15, listOf(0x0544, 0x056b, 0x0000)), Pair(0xfb16, listOf(0x054e, 0x0576, 0x0000)), Pair(0xfb17, listOf(0x0544, 0x056d, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 's', '\u0000'))))))

public var CASEFOLD_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x0131u), 0), Pair(Range.stepBy1(RangeInclusive(0x13a0u.toUShort(), 0x13f5u.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x13f8u.toUShort(), 0x13fdu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0xab70u.toUShort(), 0xabbfu.toUShort(), false)), 26672)), multis = listOf(Pair(0x1e9e, listOf(0x0073, 0x0073, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf())))
