// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions

import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

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
    public open fun start(): UShort = this.start

    public open fun end(): UShort = this.start + this.len.toUShort()

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Range> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Range> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Range =
                    Range(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr + 2L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: Range) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr, value.start)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr + 2L, value.len)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.parity)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Range =
                    Range(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Range) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset, value.start)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.len)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.parity)
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

        public fun singleton(start: UShort): Range = Range.new(start..start, false)

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
    if (idx is Result.Ok<*>) {
        val idx1 = (idx as Result.Ok<*>).value
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
        is Ok -> {
            val idx2 = matchVal.value
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
                            val kind = eq
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
                            val kind1 = eq
                            assertFailed(kind1, leftVal1, rightVal1, Option.None)
                        }
                    }
                }
            }
            // if finalResult[1] == '\0' {
            finalResult[1] = lowercase1[0]
            if (uppercase[2] != '\u0000') {
                if (false) {
                    when (val matchVal2 = Pair(lowercase1[1], '\u0000')) {
                        else -> {
                            val leftVal2 = matchVal2.first
                            val rightVal2 = matchVal2.second
                            if (!(leftVal2 == rightVal2)) {
                                val kind2 = eq
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
                                val kind3 = eq
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
                                val kind4 = eq
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
                                val kind5 = eq
                                assertFailed(kind5, leftVal5, rightVal5, Option.None)
                            }
                        }
                    }
                }
                // finalResult[2] = lowercase1[1];
            }
            // } else {
            // finalResult[2] = lowercase1[0];
            // debugAssertEq(lowercase1[1], '\0');
            // debugAssertEq(uppercase[2], '\0')
            // }
        }
        finalResult
    }())
}

public var LOWERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.stepBy1(0x00c0..0x00d6), 32), Pair(Range.stepBy1(0x00d8..0x00de), 32), Pair(Range.stepBy2(0x0100..0x012e), 1), Pair(Range.stepBy2(0x0132..0x0136), 1), Pair(Range.stepBy2(0x0139..0x0147), 1), Pair(Range.stepBy2(0x014a..0x0176), 1), Pair(Range.singleton(0x0178u), -121), Pair(Range.stepBy2(0x0179..0x017d), 1), Pair(Range.singleton(0x0181u), 210), Pair(Range.stepBy2(0x0182..0x0184), 1), Pair(Range.singleton(0x0186u), 206), Pair(Range.singleton(0x0187u), 1), Pair(Range.stepBy1(0x0189..0x018a), 205), Pair(Range.singleton(0x018bu), 1), Pair(Range.singleton(0x018eu), 79), Pair(Range.singleton(0x018fu), 202), Pair(Range.singleton(0x0190u), 203), Pair(Range.singleton(0x0191u), 1), Pair(Range.singleton(0x0193u), 205), Pair(Range.singleton(0x0194u), 207), Pair(Range.singleton(0x0196u), 211), Pair(Range.singleton(0x0197u), 209), Pair(Range.singleton(0x0198u), 1), Pair(Range.singleton(0x019cu), 211), Pair(Range.singleton(0x019du), 213), Pair(Range.singleton(0x019fu), 214), Pair(Range.stepBy2(0x01a0..0x01a4), 1), Pair(Range.singleton(0x01a6u), 218), Pair(Range.singleton(0x01a7u), 1), Pair(Range.singleton(0x01a9u), 218), Pair(Range.singleton(0x01acu), 1), Pair(Range.singleton(0x01aeu), 218), Pair(Range.singleton(0x01afu), 1), Pair(Range.stepBy1(0x01b1..0x01b2), 217), Pair(Range.stepBy2(0x01b3..0x01b5), 1), Pair(Range.singleton(0x01b7u), 219), Pair(Range.singleton(0x01b8u), 1), Pair(Range.singleton(0x01bcu), 1), Pair(Range.singleton(0x01c4u), 2), Pair(Range.singleton(0x01c5u), 1), Pair(Range.singleton(0x01c7u), 2), Pair(Range.singleton(0x01c8u), 1), Pair(Range.singleton(0x01cau), 2), Pair(Range.stepBy2(0x01cb..0x01db), 1), Pair(Range.stepBy2(0x01de..0x01ee), 1), Pair(Range.singleton(0x01f1u), 2), Pair(Range.stepBy2(0x01f2..0x01f4), 1), Pair(Range.singleton(0x01f6u), -97), Pair(Range.singleton(0x01f7u), -56), Pair(Range.stepBy2(0x01f8..0x021e), 1), Pair(Range.singleton(0x0220u), -130), Pair(Range.stepBy2(0x0222..0x0232), 1), Pair(Range.singleton(0x023au), 10795), Pair(Range.singleton(0x023bu), 1), Pair(Range.singleton(0x023du), -163), Pair(Range.singleton(0x023eu), 10792), Pair(Range.singleton(0x0241u), 1), Pair(Range.singleton(0x0243u), -195), Pair(Range.singleton(0x0244u), 69), Pair(Range.singleton(0x0245u), 71), Pair(Range.stepBy2(0x0246..0x024e), 1), Pair(Range.stepBy2(0x0370..0x0372), 1), Pair(Range.singleton(0x0376u), 1), Pair(Range.singleton(0x037fu), 116), Pair(Range.singleton(0x0386u), 38), Pair(Range.stepBy1(0x0388..0x038a), 37), Pair(Range.singleton(0x038cu), 64), Pair(Range.stepBy1(0x038e..0x038f), 63), Pair(Range.stepBy1(0x0391..0x03a1), 32), Pair(Range.stepBy1(0x03a3..0x03ab), 32), Pair(Range.singleton(0x03cfu), 8), Pair(Range.stepBy2(0x03d8..0x03ee), 1), Pair(Range.singleton(0x03f4u), -60), Pair(Range.singleton(0x03f7u), 1), Pair(Range.singleton(0x03f9u), -7), Pair(Range.singleton(0x03fau), 1), Pair(Range.stepBy1(0x03fd..0x03ff), -130), Pair(Range.stepBy1(0x0400..0x040f), 80), Pair(Range.stepBy1(0x0410..0x042f), 32), Pair(Range.stepBy2(0x0460..0x0480), 1), Pair(Range.stepBy2(0x048a..0x04be), 1), Pair(Range.singleton(0x04c0u), 15), Pair(Range.stepBy2(0x04c1..0x04cd), 1), Pair(Range.stepBy2(0x04d0..0x052e), 1), Pair(Range.stepBy1(0x0531..0x0556), 48), Pair(Range.stepBy1(0x10a0..0x10c5), 7264), Pair(Range.singleton(0x10c7u), 7264), Pair(Range.singleton(0x10cdu), 7264), Pair(Range.stepBy1(0x13a0..0x13ef), -26672), Pair(Range.stepBy1(0x13f0..0x13f5), 8), Pair(Range.singleton(0x1c89u), 1), Pair(Range.stepBy1(0x1c90..0x1cba), -3008), Pair(Range.stepBy1(0x1cbd..0x1cbf), -3008), Pair(Range.stepBy2(0x1e00..0x1e94), 1), Pair(Range.singleton(0x1e9eu), -7615), Pair(Range.stepBy2(0x1ea0..0x1efe), 1), Pair(Range.stepBy1(0x1f08..0x1f0f), -8), Pair(Range.stepBy1(0x1f18..0x1f1d), -8), Pair(Range.stepBy1(0x1f28..0x1f2f), -8), Pair(Range.stepBy1(0x1f38..0x1f3f), -8), Pair(Range.stepBy1(0x1f48..0x1f4d), -8), Pair(Range.stepBy2(0x1f59..0x1f5f), -8), Pair(Range.stepBy1(0x1f68..0x1f6f), -8), Pair(Range.stepBy1(0x1f88..0x1f8f), -8), Pair(Range.stepBy1(0x1f98..0x1f9f), -8), Pair(Range.stepBy1(0x1fa8..0x1faf), -8), Pair(Range.stepBy1(0x1fb8..0x1fb9), -8), Pair(Range.stepBy1(0x1fba..0x1fbb), -74), Pair(Range.singleton(0x1fbcu), -9), Pair(Range.stepBy1(0x1fc8..0x1fcb), -86), Pair(Range.singleton(0x1fccu), -9), Pair(Range.stepBy1(0x1fd8..0x1fd9), -8), Pair(Range.stepBy1(0x1fda..0x1fdb), -100), Pair(Range.stepBy1(0x1fe8..0x1fe9), -8), Pair(Range.stepBy1(0x1fea..0x1feb), -112), Pair(Range.singleton(0x1fecu), -7), Pair(Range.stepBy1(0x1ff8..0x1ff9), -128), Pair(Range.stepBy1(0x1ffa..0x1ffb), -126), Pair(Range.singleton(0x1ffcu), -9), Pair(Range.singleton(0x2126u), -7517), Pair(Range.singleton(0x212au), -8383), Pair(Range.singleton(0x212bu), -8262), Pair(Range.singleton(0x2132u), 28), Pair(Range.stepBy1(0x2160..0x216f), 16), Pair(Range.singleton(0x2183u), 1), Pair(Range.stepBy1(0x24b6..0x24cf), 26), Pair(Range.stepBy1(0x2c00..0x2c2f), 48), Pair(Range.singleton(0x2c60u), 1), Pair(Range.singleton(0x2c62u), -10743), Pair(Range.singleton(0x2c63u), -3814), Pair(Range.singleton(0x2c64u), -10727), Pair(Range.stepBy2(0x2c67..0x2c6b), 1), Pair(Range.singleton(0x2c6du), -10780), Pair(Range.singleton(0x2c6eu), -10749), Pair(Range.singleton(0x2c6fu), -10783), Pair(Range.singleton(0x2c70u), -10782), Pair(Range.singleton(0x2c72u), 1), Pair(Range.singleton(0x2c75u), 1), Pair(Range.stepBy1(0x2c7e..0x2c7f), -10815), Pair(Range.stepBy2(0x2c80..0x2ce2), 1), Pair(Range.stepBy2(0x2ceb..0x2ced), 1), Pair(Range.singleton(0x2cf2u), 1), Pair(Range.stepBy2(0xa640..0xa66c), 1), Pair(Range.stepBy2(0xa680..0xa69a), 1), Pair(Range.stepBy2(0xa722..0xa72e), 1), Pair(Range.stepBy2(0xa732..0xa76e), 1), Pair(Range.stepBy2(0xa779..0xa77b), 1), Pair(Range.singleton(0xa77du), 30204), Pair(Range.stepBy2(0xa77e..0xa786), 1), Pair(Range.singleton(0xa78bu), 1), Pair(Range.singleton(0xa78du), 23256), Pair(Range.stepBy2(0xa790..0xa792), 1), Pair(Range.stepBy2(0xa796..0xa7a8), 1), Pair(Range.singleton(0xa7aau), 23228), Pair(Range.singleton(0xa7abu), 23217), Pair(Range.singleton(0xa7acu), 23221), Pair(Range.singleton(0xa7adu), 23231), Pair(Range.singleton(0xa7aeu), 23228), Pair(Range.singleton(0xa7b0u), 23278), Pair(Range.singleton(0xa7b1u), 23254), Pair(Range.singleton(0xa7b2u), 23275), Pair(Range.singleton(0xa7b3u), 928), Pair(Range.stepBy2(0xa7b4..0xa7c2), 1), Pair(Range.singleton(0xa7c4u), -48), Pair(Range.singleton(0xa7c5u), 23229), Pair(Range.singleton(0xa7c6u), 30152), Pair(Range.stepBy2(0xa7c7..0xa7c9), 1), Pair(Range.singleton(0xa7cbu), 23193), Pair(Range.stepBy2(0xa7cc..0xa7da), 1), Pair(Range.singleton(0xa7dcu), 22975), Pair(Range.singleton(0xa7ddu), 23194), Pair(Range.singleton(0xa7e2u), 23194), Pair(Range.singleton(0xa7f5u), 1), Pair(Range.stepBy1(0xab6c..0xab6d), -33), Pair(Range.stepBy1(0xff21..0xff3a), 32)), multis = listOf(Pair(0x0130, listOf(0x0069, 0x0307, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(0x0400..0x0427), 40), Pair(Range.stepBy1(0x04b0..0x04d3), 40), Pair(Range.stepBy1(0x0570..0x057a), 39), Pair(Range.stepBy1(0x057c..0x058a), 39), Pair(Range.stepBy1(0x058c..0x0592), 39), Pair(Range.stepBy1(0x0594..0x0595), 39), Pair(Range.stepBy1(0x0c80..0x0cb2), 64), Pair(Range.stepBy1(0x0d50..0x0d65), 32), Pair(Range.stepBy1(0x18a0..0x18bf), 32), Pair(Range.stepBy1(0x6e40..0x6e5f), 32), Pair(Range.stepBy1(0x6ea0..0x6eb8), 27), Pair(Range.singleton(0xdf40u), 1), Pair(Range.stepBy2(0xdf48..0xdf4a), 1), Pair(Range.singleton(0xdf4du), 1), Pair(Range.singleton(0xdf51u), 1), Pair(Range.stepBy2(0xdf68..0xdf6e), 1), Pair(Range.stepBy2(0xdf72..0xdf7e), 1), Pair(Range.stepBy1(0xe900..0xe921), 34)), multis = listOf(), exceptions = listOf())))

public var UPPERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x00b5u), 743), Pair(Range.stepBy1(0x00e0..0x00f6), -32), Pair(Range.stepBy1(0x00f8..0x00fe), -32), Pair(Range.singleton(0x00ffu), 121), Pair(Range.stepBy2(0x0101..0x012f), -1), Pair(Range.singleton(0x0131u), -232), Pair(Range.stepBy2(0x0133..0x0137), -1), Pair(Range.stepBy2(0x013a..0x0148), -1), Pair(Range.stepBy2(0x014b..0x0177), -1), Pair(Range.stepBy2(0x017a..0x017e), -1), Pair(Range.singleton(0x017fu), -300), Pair(Range.singleton(0x0180u), 195), Pair(Range.stepBy2(0x0183..0x0185), -1), Pair(Range.singleton(0x0188u), -1), Pair(Range.singleton(0x018cu), -1), Pair(Range.singleton(0x0192u), -1), Pair(Range.singleton(0x0195u), 97), Pair(Range.singleton(0x0199u), -1), Pair(Range.singleton(0x019au), 163), Pair(Range.singleton(0x019bu), -22975), Pair(Range.singleton(0x019eu), 130), Pair(Range.stepBy2(0x01a1..0x01a5), -1), Pair(Range.singleton(0x01a8u), -1), Pair(Range.singleton(0x01adu), -1), Pair(Range.singleton(0x01b0u), -1), Pair(Range.stepBy2(0x01b4..0x01b6), -1), Pair(Range.singleton(0x01b9u), -1), Pair(Range.singleton(0x01bdu), -1), Pair(Range.singleton(0x01bfu), 56), Pair(Range.singleton(0x01c5u), -1), Pair(Range.singleton(0x01c6u), -2), Pair(Range.singleton(0x01c8u), -1), Pair(Range.singleton(0x01c9u), -2), Pair(Range.singleton(0x01cbu), -1), Pair(Range.singleton(0x01ccu), -2), Pair(Range.stepBy2(0x01ce..0x01dc), -1), Pair(Range.singleton(0x01ddu), -79), Pair(Range.stepBy2(0x01df..0x01ef), -1), Pair(Range.singleton(0x01f2u), -1), Pair(Range.singleton(0x01f3u), -2), Pair(Range.singleton(0x01f5u), -1), Pair(Range.stepBy2(0x01f9..0x021f), -1), Pair(Range.stepBy2(0x0223..0x0233), -1), Pair(Range.singleton(0x023cu), -1), Pair(Range.stepBy1(0x023f..0x0240), 10815), Pair(Range.singleton(0x0242u), -1), Pair(Range.stepBy2(0x0247..0x024f), -1), Pair(Range.singleton(0x0250u), 10783), Pair(Range.singleton(0x0251u), 10780), Pair(Range.singleton(0x0252u), 10782), Pair(Range.singleton(0x0253u), -210), Pair(Range.singleton(0x0254u), -206), Pair(Range.stepBy1(0x0256..0x0257), -205), Pair(Range.singleton(0x0259u), -202), Pair(Range.singleton(0x025bu), -203), Pair(Range.singleton(0x025cu), -23217), Pair(Range.singleton(0x0260u), -205), Pair(Range.singleton(0x0261u), -23221), Pair(Range.singleton(0x0263u), -207), Pair(Range.singleton(0x0264u), -23193), Pair(Range.singleton(0x0265u), -23256), Pair(Range.singleton(0x0266u), -23228), Pair(Range.singleton(0x0268u), -209), Pair(Range.singleton(0x0269u), -211), Pair(Range.singleton(0x026au), -23228), Pair(Range.singleton(0x026bu), 10743), Pair(Range.singleton(0x026cu), -23231), Pair(Range.singleton(0x026fu), -211), Pair(Range.singleton(0x0271u), 10749), Pair(Range.singleton(0x0272u), -213), Pair(Range.singleton(0x0275u), -214), Pair(Range.singleton(0x0277u), -23194), Pair(Range.singleton(0x027cu), -23194), Pair(Range.singleton(0x027du), 10727), Pair(Range.singleton(0x0280u), -218), Pair(Range.singleton(0x0282u), -23229), Pair(Range.singleton(0x0283u), -218), Pair(Range.singleton(0x0287u), -23254), Pair(Range.singleton(0x0288u), -218), Pair(Range.singleton(0x0289u), -69), Pair(Range.stepBy1(0x028a..0x028b), -217), Pair(Range.singleton(0x028cu), -71), Pair(Range.singleton(0x0292u), -219), Pair(Range.singleton(0x029du), -23275), Pair(Range.singleton(0x029eu), -23278), Pair(Range.singleton(0x0345u), 84), Pair(Range.stepBy2(0x0371..0x0373), -1), Pair(Range.singleton(0x0377u), -1), Pair(Range.stepBy1(0x037b..0x037d), 130), Pair(Range.singleton(0x03acu), -38), Pair(Range.stepBy1(0x03ad..0x03af), -37), Pair(Range.stepBy1(0x03b1..0x03c1), -32), Pair(Range.singleton(0x03c2u), -31), Pair(Range.stepBy1(0x03c3..0x03cb), -32), Pair(Range.singleton(0x03ccu), -64), Pair(Range.stepBy1(0x03cd..0x03ce), -63), Pair(Range.singleton(0x03d0u), -62), Pair(Range.singleton(0x03d1u), -57), Pair(Range.singleton(0x03d5u), -47), Pair(Range.singleton(0x03d6u), -54), Pair(Range.singleton(0x03d7u), -8), Pair(Range.stepBy2(0x03d9..0x03ef), -1), Pair(Range.singleton(0x03f0u), -86), Pair(Range.singleton(0x03f1u), -80), Pair(Range.singleton(0x03f2u), 7), Pair(Range.singleton(0x03f3u), -116), Pair(Range.singleton(0x03f5u), -96), Pair(Range.singleton(0x03f8u), -1), Pair(Range.singleton(0x03fbu), -1), Pair(Range.stepBy1(0x0430..0x044f), -32), Pair(Range.stepBy1(0x0450..0x045f), -80), Pair(Range.stepBy2(0x0461..0x0481), -1), Pair(Range.stepBy2(0x048b..0x04bf), -1), Pair(Range.stepBy2(0x04c2..0x04ce), -1), Pair(Range.singleton(0x04cfu), -15), Pair(Range.stepBy2(0x04d1..0x052f), -1), Pair(Range.stepBy1(0x0561..0x0586), -48), Pair(Range.stepBy1(0x10d0..0x10fa), 3008), Pair(Range.stepBy1(0x10fd..0x10ff), 3008), Pair(Range.stepBy1(0x13f8..0x13fd), -8), Pair(Range.singleton(0x1c80u), -6254), Pair(Range.singleton(0x1c81u), -6253), Pair(Range.singleton(0x1c82u), -6244), Pair(Range.stepBy1(0x1c83..0x1c84), -6242), Pair(Range.singleton(0x1c85u), -6243), Pair(Range.singleton(0x1c86u), -6236), Pair(Range.singleton(0x1c87u), -6181), Pair(Range.singleton(0x1c88u), -30270), Pair(Range.singleton(0x1c8au), -1), Pair(Range.singleton(0x1d79u), -30204), Pair(Range.singleton(0x1d7du), 3814), Pair(Range.singleton(0x1d8eu), -30152), Pair(Range.stepBy2(0x1e01..0x1e95), -1), Pair(Range.singleton(0x1e9bu), -59), Pair(Range.stepBy2(0x1ea1..0x1eff), -1), Pair(Range.stepBy1(0x1f00..0x1f07), 8), Pair(Range.stepBy1(0x1f10..0x1f15), 8), Pair(Range.stepBy1(0x1f20..0x1f27), 8), Pair(Range.stepBy1(0x1f30..0x1f37), 8), Pair(Range.stepBy1(0x1f40..0x1f45), 8), Pair(Range.stepBy2(0x1f51..0x1f57), 8), Pair(Range.stepBy1(0x1f60..0x1f67), 8), Pair(Range.stepBy1(0x1f70..0x1f71), 74), Pair(Range.stepBy1(0x1f72..0x1f75), 86), Pair(Range.stepBy1(0x1f76..0x1f77), 100), Pair(Range.stepBy1(0x1f78..0x1f79), 128), Pair(Range.stepBy1(0x1f7a..0x1f7b), 112), Pair(Range.stepBy1(0x1f7c..0x1f7d), 126), Pair(Range.stepBy1(0x1fb0..0x1fb1), 8), Pair(Range.singleton(0x1fbeu), -7205), Pair(Range.stepBy1(0x1fd0..0x1fd1), 8), Pair(Range.stepBy1(0x1fe0..0x1fe1), 8), Pair(Range.singleton(0x1fe5u), 7), Pair(Range.singleton(0x214eu), -28), Pair(Range.stepBy1(0x2170..0x217f), -16), Pair(Range.singleton(0x2184u), -1), Pair(Range.stepBy1(0x24d0..0x24e9), -26), Pair(Range.stepBy1(0x2c30..0x2c5f), -48), Pair(Range.singleton(0x2c61u), -1), Pair(Range.singleton(0x2c65u), -10795), Pair(Range.singleton(0x2c66u), -10792), Pair(Range.stepBy2(0x2c68..0x2c6c), -1), Pair(Range.singleton(0x2c73u), -1), Pair(Range.singleton(0x2c76u), -1), Pair(Range.stepBy2(0x2c81..0x2ce3), -1), Pair(Range.stepBy2(0x2cec..0x2cee), -1), Pair(Range.singleton(0x2cf3u), -1), Pair(Range.stepBy1(0x2d00..0x2d25), -7264), Pair(Range.singleton(0x2d27u), -7264), Pair(Range.singleton(0x2d2du), -7264), Pair(Range.stepBy2(0xa641..0xa66d), -1), Pair(Range.stepBy2(0xa681..0xa69b), -1), Pair(Range.stepBy2(0xa723..0xa72f), -1), Pair(Range.stepBy2(0xa733..0xa76f), -1), Pair(Range.stepBy2(0xa77a..0xa77c), -1), Pair(Range.stepBy2(0xa77f..0xa787), -1), Pair(Range.singleton(0xa78cu), -1), Pair(Range.stepBy2(0xa791..0xa793), -1), Pair(Range.singleton(0xa794u), 48), Pair(Range.stepBy2(0xa797..0xa7a9), -1), Pair(Range.stepBy2(0xa7b5..0xa7c3), -1), Pair(Range.stepBy2(0xa7c8..0xa7ca), -1), Pair(Range.stepBy2(0xa7cd..0xa7db), -1), Pair(Range.singleton(0xa7f6u), -1), Pair(Range.stepBy1(0xab4b..0xab4c), 33), Pair(Range.singleton(0xab53u), -928), Pair(Range.stepBy1(0xab70..0xabbf), 26672), Pair(Range.stepBy1(0xff41..0xff5a), -32)), multis = listOf(Pair(0x00df, listOf(0x0053, 0x0053, 0x0000)), Pair(0x0149, listOf(0x02bc, 0x004e, 0x0000)), Pair(0x01f0, listOf(0x004a, 0x030c, 0x0000)), Pair(0x0390, listOf(0x0399, 0x0308, 0x0301)), Pair(0x03b0, listOf(0x03a5, 0x0308, 0x0301)), Pair(0x0587, listOf(0x0535, 0x0552, 0x0000)), Pair(0x1e96, listOf(0x0048, 0x0331, 0x0000)), Pair(0x1e97, listOf(0x0054, 0x0308, 0x0000)), Pair(0x1e98, listOf(0x0057, 0x030a, 0x0000)), Pair(0x1e99, listOf(0x0059, 0x030a, 0x0000)), Pair(0x1e9a, listOf(0x0041, 0x02be, 0x0000)), Pair(0x1f50, listOf(0x03a5, 0x0313, 0x0000)), Pair(0x1f52, listOf(0x03a5, 0x0313, 0x0300)), Pair(0x1f54, listOf(0x03a5, 0x0313, 0x0301)), Pair(0x1f56, listOf(0x03a5, 0x0313, 0x0342)), Pair(0x1f80, listOf(0x1f08, 0x0399, 0x0000)), Pair(0x1f81, listOf(0x1f09, 0x0399, 0x0000)), Pair(0x1f82, listOf(0x1f0a, 0x0399, 0x0000)), Pair(0x1f83, listOf(0x1f0b, 0x0399, 0x0000)), Pair(0x1f84, listOf(0x1f0c, 0x0399, 0x0000)), Pair(0x1f85, listOf(0x1f0d, 0x0399, 0x0000)), Pair(0x1f86, listOf(0x1f0e, 0x0399, 0x0000)), Pair(0x1f87, listOf(0x1f0f, 0x0399, 0x0000)), Pair(0x1f88, listOf(0x1f08, 0x0399, 0x0000)), Pair(0x1f89, listOf(0x1f09, 0x0399, 0x0000)), Pair(0x1f8a, listOf(0x1f0a, 0x0399, 0x0000)), Pair(0x1f8b, listOf(0x1f0b, 0x0399, 0x0000)), Pair(0x1f8c, listOf(0x1f0c, 0x0399, 0x0000)), Pair(0x1f8d, listOf(0x1f0d, 0x0399, 0x0000)), Pair(0x1f8e, listOf(0x1f0e, 0x0399, 0x0000)), Pair(0x1f8f, listOf(0x1f0f, 0x0399, 0x0000)), Pair(0x1f90, listOf(0x1f28, 0x0399, 0x0000)), Pair(0x1f91, listOf(0x1f29, 0x0399, 0x0000)), Pair(0x1f92, listOf(0x1f2a, 0x0399, 0x0000)), Pair(0x1f93, listOf(0x1f2b, 0x0399, 0x0000)), Pair(0x1f94, listOf(0x1f2c, 0x0399, 0x0000)), Pair(0x1f95, listOf(0x1f2d, 0x0399, 0x0000)), Pair(0x1f96, listOf(0x1f2e, 0x0399, 0x0000)), Pair(0x1f97, listOf(0x1f2f, 0x0399, 0x0000)), Pair(0x1f98, listOf(0x1f28, 0x0399, 0x0000)), Pair(0x1f99, listOf(0x1f29, 0x0399, 0x0000)), Pair(0x1f9a, listOf(0x1f2a, 0x0399, 0x0000)), Pair(0x1f9b, listOf(0x1f2b, 0x0399, 0x0000)), Pair(0x1f9c, listOf(0x1f2c, 0x0399, 0x0000)), Pair(0x1f9d, listOf(0x1f2d, 0x0399, 0x0000)), Pair(0x1f9e, listOf(0x1f2e, 0x0399, 0x0000)), Pair(0x1f9f, listOf(0x1f2f, 0x0399, 0x0000)), Pair(0x1fa0, listOf(0x1f68, 0x0399, 0x0000)), Pair(0x1fa1, listOf(0x1f69, 0x0399, 0x0000)), Pair(0x1fa2, listOf(0x1f6a, 0x0399, 0x0000)), Pair(0x1fa3, listOf(0x1f6b, 0x0399, 0x0000)), Pair(0x1fa4, listOf(0x1f6c, 0x0399, 0x0000)), Pair(0x1fa5, listOf(0x1f6d, 0x0399, 0x0000)), Pair(0x1fa6, listOf(0x1f6e, 0x0399, 0x0000)), Pair(0x1fa7, listOf(0x1f6f, 0x0399, 0x0000)), Pair(0x1fa8, listOf(0x1f68, 0x0399, 0x0000)), Pair(0x1fa9, listOf(0x1f69, 0x0399, 0x0000)), Pair(0x1faa, listOf(0x1f6a, 0x0399, 0x0000)), Pair(0x1fab, listOf(0x1f6b, 0x0399, 0x0000)), Pair(0x1fac, listOf(0x1f6c, 0x0399, 0x0000)), Pair(0x1fad, listOf(0x1f6d, 0x0399, 0x0000)), Pair(0x1fae, listOf(0x1f6e, 0x0399, 0x0000)), Pair(0x1faf, listOf(0x1f6f, 0x0399, 0x0000)), Pair(0x1fb2, listOf(0x1fba, 0x0399, 0x0000)), Pair(0x1fb3, listOf(0x0391, 0x0399, 0x0000)), Pair(0x1fb4, listOf(0x0386, 0x0399, 0x0000)), Pair(0x1fb6, listOf(0x0391, 0x0342, 0x0000)), Pair(0x1fb7, listOf(0x0391, 0x0342, 0x0399)), Pair(0x1fbc, listOf(0x0391, 0x0399, 0x0000)), Pair(0x1fc2, listOf(0x1fca, 0x0399, 0x0000)), Pair(0x1fc3, listOf(0x0397, 0x0399, 0x0000)), Pair(0x1fc4, listOf(0x0389, 0x0399, 0x0000)), Pair(0x1fc6, listOf(0x0397, 0x0342, 0x0000)), Pair(0x1fc7, listOf(0x0397, 0x0342, 0x0399)), Pair(0x1fcc, listOf(0x0397, 0x0399, 0x0000)), Pair(0x1fd2, listOf(0x0399, 0x0308, 0x0300)), Pair(0x1fd3, listOf(0x0399, 0x0308, 0x0301)), Pair(0x1fd6, listOf(0x0399, 0x0342, 0x0000)), Pair(0x1fd7, listOf(0x0399, 0x0308, 0x0342)), Pair(0x1fe2, listOf(0x03a5, 0x0308, 0x0300)), Pair(0x1fe3, listOf(0x03a5, 0x0308, 0x0301)), Pair(0x1fe4, listOf(0x03a1, 0x0313, 0x0000)), Pair(0x1fe6, listOf(0x03a5, 0x0342, 0x0000)), Pair(0x1fe7, listOf(0x03a5, 0x0308, 0x0342)), Pair(0x1ff2, listOf(0x1ffa, 0x0399, 0x0000)), Pair(0x1ff3, listOf(0x03a9, 0x0399, 0x0000)), Pair(0x1ff4, listOf(0x038f, 0x0399, 0x0000)), Pair(0x1ff6, listOf(0x03a9, 0x0342, 0x0000)), Pair(0x1ff7, listOf(0x03a9, 0x0342, 0x0399)), Pair(0x1ffc, listOf(0x03a9, 0x0399, 0x0000)), Pair(0xfb00, listOf(0x0046, 0x0046, 0x0000)), Pair(0xfb01, listOf(0x0046, 0x0049, 0x0000)), Pair(0xfb02, listOf(0x0046, 0x004c, 0x0000)), Pair(0xfb03, listOf(0x0046, 0x0046, 0x0049)), Pair(0xfb04, listOf(0x0046, 0x0046, 0x004c)), Pair(0xfb05, listOf(0x0053, 0x0054, 0x0000)), Pair(0xfb06, listOf(0x0053, 0x0054, 0x0000)), Pair(0xfb13, listOf(0x0544, 0x0546, 0x0000)), Pair(0xfb14, listOf(0x0544, 0x0535, 0x0000)), Pair(0xfb15, listOf(0x0544, 0x053b, 0x0000)), Pair(0xfb16, listOf(0x054e, 0x0546, 0x0000)), Pair(0xfb17, listOf(0x0544, 0x053d, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(0x0428..0x044f), -40), Pair(Range.stepBy1(0x04d8..0x04fb), -40), Pair(Range.stepBy1(0x0597..0x05a1), -39), Pair(Range.stepBy1(0x05a3..0x05b1), -39), Pair(Range.stepBy1(0x05b3..0x05b9), -39), Pair(Range.stepBy1(0x05bb..0x05bc), -39), Pair(Range.stepBy1(0x0cc0..0x0cf2), -64), Pair(Range.stepBy1(0x0d70..0x0d85), -32), Pair(Range.stepBy1(0x18c0..0x18df), -32), Pair(Range.stepBy1(0x6e60..0x6e7f), -32), Pair(Range.stepBy1(0x6ebb..0x6ed3), -27), Pair(Range.singleton(0xdf41u), -1), Pair(Range.stepBy2(0xdf49..0xdf4b), -1), Pair(Range.singleton(0xdf4eu), -1), Pair(Range.singleton(0xdf52u), -1), Pair(Range.stepBy2(0xdf69..0xdf6f), -1), Pair(Range.stepBy2(0xdf73..0xdf7f), -1), Pair(Range.stepBy1(0xe922..0xe943), -34)), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 'S', '\u0000'))))))

public var TITLECASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x01c4u), 1), Pair(Range.singleton(0x01c5u), 0), Pair(Range.singleton(0x01c6u), -1), Pair(Range.singleton(0x01c7u), 1), Pair(Range.singleton(0x01c8u), 0), Pair(Range.singleton(0x01c9u), -1), Pair(Range.singleton(0x01cau), 1), Pair(Range.singleton(0x01cbu), 0), Pair(Range.singleton(0x01ccu), -1), Pair(Range.singleton(0x01f1u), 1), Pair(Range.singleton(0x01f2u), 0), Pair(Range.singleton(0x01f3u), -1), Pair(Range.stepBy1(0x10d0..0x10fa), 0), Pair(Range.stepBy1(0x10fd..0x10ff), 0), Pair(Range.stepBy1(0x1f80..0x1f87), 8), Pair(Range.stepBy1(0x1f88..0x1f8f), 0), Pair(Range.stepBy1(0x1f90..0x1f97), 8), Pair(Range.stepBy1(0x1f98..0x1f9f), 0), Pair(Range.stepBy1(0x1fa0..0x1fa7), 8), Pair(Range.stepBy1(0x1fa8..0x1faf), 0), Pair(Range.singleton(0x1fb3u), 9), Pair(Range.singleton(0x1fbcu), 0), Pair(Range.singleton(0x1fc3u), 9), Pair(Range.singleton(0x1fccu), 0), Pair(Range.singleton(0x1ff3u), 9), Pair(Range.singleton(0x1ffcu), 0)), multis = listOf(Pair(0x00df, listOf(0x0053, 0x0073, 0x0000)), Pair(0x0587, listOf(0x0535, 0x0582, 0x0000)), Pair(0x1fb2, listOf(0x1fba, 0x0345, 0x0000)), Pair(0x1fb4, listOf(0x0386, 0x0345, 0x0000)), Pair(0x1fb7, listOf(0x0391, 0x0342, 0x0345)), Pair(0x1fc2, listOf(0x1fca, 0x0345, 0x0000)), Pair(0x1fc4, listOf(0x0389, 0x0345, 0x0000)), Pair(0x1fc7, listOf(0x0397, 0x0342, 0x0345)), Pair(0x1ff2, listOf(0x1ffa, 0x0345, 0x0000)), Pair(0x1ff4, listOf(0x038f, 0x0345, 0x0000)), Pair(0x1ff7, listOf(0x03a9, 0x0342, 0x0345)), Pair(0xfb00, listOf(0x0046, 0x0066, 0x0000)), Pair(0xfb01, listOf(0x0046, 0x0069, 0x0000)), Pair(0xfb02, listOf(0x0046, 0x006c, 0x0000)), Pair(0xfb03, listOf(0x0046, 0x0066, 0x0069)), Pair(0xfb04, listOf(0x0046, 0x0066, 0x006c)), Pair(0xfb05, listOf(0x0053, 0x0074, 0x0000)), Pair(0xfb06, listOf(0x0053, 0x0074, 0x0000)), Pair(0xfb13, listOf(0x0544, 0x0576, 0x0000)), Pair(0xfb14, listOf(0x0544, 0x0565, 0x0000)), Pair(0xfb15, listOf(0x0544, 0x056b, 0x0000)), Pair(0xfb16, listOf(0x054e, 0x0576, 0x0000)), Pair(0xfb17, listOf(0x0544, 0x056d, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 's', '\u0000'))))))

public var CASEFOLD_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x0131u), 0), Pair(Range.stepBy1(0x13a0..0x13f5), 0), Pair(Range.stepBy1(0x13f8..0x13fd), -8), Pair(Range.stepBy1(0xab70..0xabbf), 26672)), multis = listOf(Pair(0x1e9e, listOf(0x0073, 0x0073, 0x0000))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf())))
