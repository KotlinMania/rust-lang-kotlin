// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions

import io.github.kotlinmania.rustlang.core.char.methods.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.num.wrappingAddSigned
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.result.Result
import kotlin.experimental.and
import kotlin.experimental.or

public open class L1Lut(
    public var l2Luts: List<L2Lut>,
) {
    public companion object
}

public open class L2Lut(
    public var singles: List<Pair<Range, Short>>,
    public var multis: List<Pair<UShort, UShortArray>>,
    public var exceptions: List<Pair<UShort, List<Char>>>,
) {
    public companion object
}

public open class Range(
    public var start: UShort,
    public var len: UByte,
    public var parity: Boolean,
) {
    public open fun clone(): Range = Range(start, len, parity)

    public open fun start(): UShort = this.start

    public open fun end(): UShort = this.start + this.len.toUShort()

    public companion object {
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
    val c1 = c.code.toUInt()
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
                listOf(Char.fromU32Unchecked(c.code.toUInt() + 0x20u), '\u0000', '\u0000')
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
                listOf(Char.fromU32Unchecked(c.code.toUInt() - 0x20u), '\u0000', '\u0000')
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
                            assertFailed(kind, leftVal, rightVal, Option.None())
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
                            assertFailed(kind1, leftVal1, rightVal1, Option.None())
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
                                assertFailed(kind2, leftVal2, rightVal2, Option.None())
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
                                assertFailed(kind3, leftVal3, rightVal3, Option.None())
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
                                assertFailed(kind4, leftVal4, rightVal4, Option.None())
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
                                assertFailed(kind5, leftVal5, rightVal5, Option.None())
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

public var LOWERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x00c0u.toUShort(), 0x00d6u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x00d8u.toUShort(), 0x00deu.toUShort(), false)), 32), Pair(Range.stepBy2(RangeInclusive(0x0100u.toUShort(), 0x012eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0132u.toUShort(), 0x0136u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0139u.toUShort(), 0x0147u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x014au.toUShort(), 0x0176u.toUShort(), false)), 1), Pair(Range.singleton(0x0178u.toUShort()), -121), Pair(Range.stepBy2(RangeInclusive(0x0179u.toUShort(), 0x017du.toUShort(), false)), 1), Pair(Range.singleton(0x0181u.toUShort()), 210), Pair(Range.stepBy2(RangeInclusive(0x0182u.toUShort(), 0x0184u.toUShort(), false)), 1), Pair(Range.singleton(0x0186u.toUShort()), 206), Pair(Range.singleton(0x0187u.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x0189u.toUShort(), 0x018au.toUShort(), false)), 205), Pair(Range.singleton(0x018bu.toUShort()), 1), Pair(Range.singleton(0x018eu.toUShort()), 79), Pair(Range.singleton(0x018fu.toUShort()), 202), Pair(Range.singleton(0x0190u.toUShort()), 203), Pair(Range.singleton(0x0191u.toUShort()), 1), Pair(Range.singleton(0x0193u.toUShort()), 205), Pair(Range.singleton(0x0194u.toUShort()), 207), Pair(Range.singleton(0x0196u.toUShort()), 211), Pair(Range.singleton(0x0197u.toUShort()), 209), Pair(Range.singleton(0x0198u.toUShort()), 1), Pair(Range.singleton(0x019cu.toUShort()), 211), Pair(Range.singleton(0x019du.toUShort()), 213), Pair(Range.singleton(0x019fu.toUShort()), 214), Pair(Range.stepBy2(RangeInclusive(0x01a0u.toUShort(), 0x01a4u.toUShort(), false)), 1), Pair(Range.singleton(0x01a6u.toUShort()), 218), Pair(Range.singleton(0x01a7u.toUShort()), 1), Pair(Range.singleton(0x01a9u.toUShort()), 218), Pair(Range.singleton(0x01acu.toUShort()), 1), Pair(Range.singleton(0x01aeu.toUShort()), 218), Pair(Range.singleton(0x01afu.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x01b1u.toUShort(), 0x01b2u.toUShort(), false)), 217), Pair(Range.stepBy2(RangeInclusive(0x01b3u.toUShort(), 0x01b5u.toUShort(), false)), 1), Pair(Range.singleton(0x01b7u.toUShort()), 219), Pair(Range.singleton(0x01b8u.toUShort()), 1), Pair(Range.singleton(0x01bcu.toUShort()), 1), Pair(Range.singleton(0x01c4u.toUShort()), 2), Pair(Range.singleton(0x01c5u.toUShort()), 1), Pair(Range.singleton(0x01c7u.toUShort()), 2), Pair(Range.singleton(0x01c8u.toUShort()), 1), Pair(Range.singleton(0x01cau.toUShort()), 2), Pair(Range.stepBy2(RangeInclusive(0x01cbu.toUShort(), 0x01dbu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x01deu.toUShort(), 0x01eeu.toUShort(), false)), 1), Pair(Range.singleton(0x01f1u.toUShort()), 2), Pair(Range.stepBy2(RangeInclusive(0x01f2u.toUShort(), 0x01f4u.toUShort(), false)), 1), Pair(Range.singleton(0x01f6u.toUShort()), -97), Pair(Range.singleton(0x01f7u.toUShort()), -56), Pair(Range.stepBy2(RangeInclusive(0x01f8u.toUShort(), 0x021eu.toUShort(), false)), 1), Pair(Range.singleton(0x0220u.toUShort()), -130), Pair(Range.stepBy2(RangeInclusive(0x0222u.toUShort(), 0x0232u.toUShort(), false)), 1), Pair(Range.singleton(0x023au.toUShort()), 10795), Pair(Range.singleton(0x023bu.toUShort()), 1), Pair(Range.singleton(0x023du.toUShort()), -163), Pair(Range.singleton(0x023eu.toUShort()), 10792), Pair(Range.singleton(0x0241u.toUShort()), 1), Pair(Range.singleton(0x0243u.toUShort()), -195), Pair(Range.singleton(0x0244u.toUShort()), 69), Pair(Range.singleton(0x0245u.toUShort()), 71), Pair(Range.stepBy2(RangeInclusive(0x0246u.toUShort(), 0x024eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x0370u.toUShort(), 0x0372u.toUShort(), false)), 1), Pair(Range.singleton(0x0376u.toUShort()), 1), Pair(Range.singleton(0x037fu.toUShort()), 116), Pair(Range.singleton(0x0386u.toUShort()), 38), Pair(Range.stepBy1(RangeInclusive(0x0388u.toUShort(), 0x038au.toUShort(), false)), 37), Pair(Range.singleton(0x038cu.toUShort()), 64), Pair(Range.stepBy1(RangeInclusive(0x038eu.toUShort(), 0x038fu.toUShort(), false)), 63), Pair(Range.stepBy1(RangeInclusive(0x0391u.toUShort(), 0x03a1u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x03a3u.toUShort(), 0x03abu.toUShort(), false)), 32), Pair(Range.singleton(0x03cfu.toUShort()), 8), Pair(Range.stepBy2(RangeInclusive(0x03d8u.toUShort(), 0x03eeu.toUShort(), false)), 1), Pair(Range.singleton(0x03f4u.toUShort()), -60), Pair(Range.singleton(0x03f7u.toUShort()), 1), Pair(Range.singleton(0x03f9u.toUShort()), -7), Pair(Range.singleton(0x03fau.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x03fdu.toUShort(), 0x03ffu.toUShort(), false)), -130), Pair(Range.stepBy1(RangeInclusive(0x0400u.toUShort(), 0x040fu.toUShort(), false)), 80), Pair(Range.stepBy1(RangeInclusive(0x0410u.toUShort(), 0x042fu.toUShort(), false)), 32), Pair(Range.stepBy2(RangeInclusive(0x0460u.toUShort(), 0x0480u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x048au.toUShort(), 0x04beu.toUShort(), false)), 1), Pair(Range.singleton(0x04c0u.toUShort()), 15), Pair(Range.stepBy2(RangeInclusive(0x04c1u.toUShort(), 0x04cdu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x04d0u.toUShort(), 0x052eu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0x0531u.toUShort(), 0x0556u.toUShort(), false)), 48), Pair(Range.stepBy1(RangeInclusive(0x10a0u.toUShort(), 0x10c5u.toUShort(), false)), 7264), Pair(Range.singleton(0x10c7u.toUShort()), 7264), Pair(Range.singleton(0x10cdu.toUShort()), 7264), Pair(Range.stepBy1(RangeInclusive(0x13a0u.toUShort(), 0x13efu.toUShort(), false)), -26672), Pair(Range.stepBy1(RangeInclusive(0x13f0u.toUShort(), 0x13f5u.toUShort(), false)), 8), Pair(Range.singleton(0x1c89u.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x1c90u.toUShort(), 0x1cbau.toUShort(), false)), -3008), Pair(Range.stepBy1(RangeInclusive(0x1cbdu.toUShort(), 0x1cbfu.toUShort(), false)), -3008), Pair(Range.stepBy2(RangeInclusive(0x1e00u.toUShort(), 0x1e94u.toUShort(), false)), 1), Pair(Range.singleton(0x1e9eu.toUShort()), -7615), Pair(Range.stepBy2(RangeInclusive(0x1ea0u.toUShort(), 0x1efeu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0x1f08u.toUShort(), 0x1f0fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f18u.toUShort(), 0x1f1du.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f28u.toUShort(), 0x1f2fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f38u.toUShort(), 0x1f3fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f48u.toUShort(), 0x1f4du.toUShort(), false)), -8), Pair(Range.stepBy2(RangeInclusive(0x1f59u.toUShort(), 0x1f5fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f68u.toUShort(), 0x1f6fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f88u.toUShort(), 0x1f8fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1f98u.toUShort(), 0x1f9fu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fa8u.toUShort(), 0x1fafu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fb8u.toUShort(), 0x1fb9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fbau.toUShort(), 0x1fbbu.toUShort(), false)), -74), Pair(Range.singleton(0x1fbcu.toUShort()), -9), Pair(Range.stepBy1(RangeInclusive(0x1fc8u.toUShort(), 0x1fcbu.toUShort(), false)), -86), Pair(Range.singleton(0x1fccu.toUShort()), -9), Pair(Range.stepBy1(RangeInclusive(0x1fd8u.toUShort(), 0x1fd9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1fdau.toUShort(), 0x1fdbu.toUShort(), false)), -100), Pair(Range.stepBy1(RangeInclusive(0x1fe8u.toUShort(), 0x1fe9u.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0x1feau.toUShort(), 0x1febu.toUShort(), false)), -112), Pair(Range.singleton(0x1fecu.toUShort()), -7), Pair(Range.stepBy1(RangeInclusive(0x1ff8u.toUShort(), 0x1ff9u.toUShort(), false)), -128), Pair(Range.stepBy1(RangeInclusive(0x1ffau.toUShort(), 0x1ffbu.toUShort(), false)), -126), Pair(Range.singleton(0x1ffcu.toUShort()), -9), Pair(Range.singleton(0x2126u.toUShort()), -7517), Pair(Range.singleton(0x212au.toUShort()), -8383), Pair(Range.singleton(0x212bu.toUShort()), -8262), Pair(Range.singleton(0x2132u.toUShort()), 28), Pair(Range.stepBy1(RangeInclusive(0x2160u.toUShort(), 0x216fu.toUShort(), false)), 16), Pair(Range.singleton(0x2183u.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x24b6u.toUShort(), 0x24cfu.toUShort(), false)), 26), Pair(Range.stepBy1(RangeInclusive(0x2c00u.toUShort(), 0x2c2fu.toUShort(), false)), 48), Pair(Range.singleton(0x2c60u.toUShort()), 1), Pair(Range.singleton(0x2c62u.toUShort()), -10743), Pair(Range.singleton(0x2c63u.toUShort()), -3814), Pair(Range.singleton(0x2c64u.toUShort()), -10727), Pair(Range.stepBy2(RangeInclusive(0x2c67u.toUShort(), 0x2c6bu.toUShort(), false)), 1), Pair(Range.singleton(0x2c6du.toUShort()), -10780), Pair(Range.singleton(0x2c6eu.toUShort()), -10749), Pair(Range.singleton(0x2c6fu.toUShort()), -10783), Pair(Range.singleton(0x2c70u.toUShort()), -10782), Pair(Range.singleton(0x2c72u.toUShort()), 1), Pair(Range.singleton(0x2c75u.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0x2c7eu.toUShort(), 0x2c7fu.toUShort(), false)), -10815), Pair(Range.stepBy2(RangeInclusive(0x2c80u.toUShort(), 0x2ce2u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0x2cebu.toUShort(), 0x2cedu.toUShort(), false)), 1), Pair(Range.singleton(0x2cf2u.toUShort()), 1), Pair(Range.stepBy2(RangeInclusive(0xa640u.toUShort(), 0xa66cu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa680u.toUShort(), 0xa69au.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa722u.toUShort(), 0xa72eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa732u.toUShort(), 0xa76eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa779u.toUShort(), 0xa77bu.toUShort(), false)), 1), Pair(Range.singleton(0xa77du.toUShort()), 30204), Pair(Range.stepBy2(RangeInclusive(0xa77eu.toUShort(), 0xa786u.toUShort(), false)), 1), Pair(Range.singleton(0xa78bu.toUShort()), 1), Pair(Range.singleton(0xa78du.toUShort()), 23256), Pair(Range.stepBy2(RangeInclusive(0xa790u.toUShort(), 0xa792u.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xa796u.toUShort(), 0xa7a8u.toUShort(), false)), 1), Pair(Range.singleton(0xa7aau.toUShort()), 23228), Pair(Range.singleton(0xa7abu.toUShort()), 23217), Pair(Range.singleton(0xa7acu.toUShort()), 23221), Pair(Range.singleton(0xa7adu.toUShort()), 23231), Pair(Range.singleton(0xa7aeu.toUShort()), 23228), Pair(Range.singleton(0xa7b0u.toUShort()), 23278), Pair(Range.singleton(0xa7b1u.toUShort()), 23254), Pair(Range.singleton(0xa7b2u.toUShort()), 23275), Pair(Range.singleton(0xa7b3u.toUShort()), 928), Pair(Range.stepBy2(RangeInclusive(0xa7b4u.toUShort(), 0xa7c2u.toUShort(), false)), 1), Pair(Range.singleton(0xa7c4u.toUShort()), -48), Pair(Range.singleton(0xa7c5u.toUShort()), 23229), Pair(Range.singleton(0xa7c6u.toUShort()), 30152), Pair(Range.stepBy2(RangeInclusive(0xa7c7u.toUShort(), 0xa7c9u.toUShort(), false)), 1), Pair(Range.singleton(0xa7cbu.toUShort()), 23193), Pair(Range.stepBy2(RangeInclusive(0xa7ccu.toUShort(), 0xa7dau.toUShort(), false)), 1), Pair(Range.singleton(0xa7dcu.toUShort()), 22975), Pair(Range.singleton(0xa7ddu.toUShort()), 23194), Pair(Range.singleton(0xa7e2u.toUShort()), 23194), Pair(Range.singleton(0xa7f5u.toUShort()), 1), Pair(Range.stepBy1(RangeInclusive(0xab6cu.toUShort(), 0xab6du.toUShort(), false)), -33), Pair(Range.stepBy1(RangeInclusive(0xff21u.toUShort(), 0xff3au.toUShort(), false)), 32)), multis = listOf(Pair(0x0130, ushortArrayOf(0x0069u.toUShort(), 0x0307u.toUShort(), 0x0000u.toUShort()))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x0400u.toUShort(), 0x0427u.toUShort(), false)), 40), Pair(Range.stepBy1(RangeInclusive(0x04b0u.toUShort(), 0x04d3u.toUShort(), false)), 40), Pair(Range.stepBy1(RangeInclusive(0x0570u.toUShort(), 0x057au.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x057cu.toUShort(), 0x058au.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x058cu.toUShort(), 0x0592u.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x0594u.toUShort(), 0x0595u.toUShort(), false)), 39), Pair(Range.stepBy1(RangeInclusive(0x0c80u.toUShort(), 0x0cb2u.toUShort(), false)), 64), Pair(Range.stepBy1(RangeInclusive(0x0d50u.toUShort(), 0x0d65u.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x18a0u.toUShort(), 0x18bfu.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x6e40u.toUShort(), 0x6e5fu.toUShort(), false)), 32), Pair(Range.stepBy1(RangeInclusive(0x6ea0u.toUShort(), 0x6eb8u.toUShort(), false)), 27), Pair(Range.singleton(0xdf40u.toUShort()), 1), Pair(Range.stepBy2(RangeInclusive(0xdf48u.toUShort(), 0xdf4au.toUShort(), false)), 1), Pair(Range.singleton(0xdf4du.toUShort()), 1), Pair(Range.singleton(0xdf51u.toUShort()), 1), Pair(Range.stepBy2(RangeInclusive(0xdf68u.toUShort(), 0xdf6eu.toUShort(), false)), 1), Pair(Range.stepBy2(RangeInclusive(0xdf72u.toUShort(), 0xdf7eu.toUShort(), false)), 1), Pair(Range.stepBy1(RangeInclusive(0xe900u.toUShort(), 0xe921u.toUShort(), false)), 34)), multis = listOf(), exceptions = listOf())))

public var UPPERCASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x00b5u.toUShort()), 743), Pair(Range.stepBy1(RangeInclusive(0x00e0u.toUShort(), 0x00f6u.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x00f8u.toUShort(), 0x00feu.toUShort(), false)), -32), Pair(Range.singleton(0x00ffu.toUShort()), 121), Pair(Range.stepBy2(RangeInclusive(0x0101u.toUShort(), 0x012fu.toUShort(), false)), -1), Pair(Range.singleton(0x0131u.toUShort()), -232), Pair(Range.stepBy2(RangeInclusive(0x0133u.toUShort(), 0x0137u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x013au.toUShort(), 0x0148u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x014bu.toUShort(), 0x0177u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x017au.toUShort(), 0x017eu.toUShort(), false)), -1), Pair(Range.singleton(0x017fu.toUShort()), -300), Pair(Range.singleton(0x0180u.toUShort()), 195), Pair(Range.stepBy2(RangeInclusive(0x0183u.toUShort(), 0x0185u.toUShort(), false)), -1), Pair(Range.singleton(0x0188u.toUShort()), -1), Pair(Range.singleton(0x018cu.toUShort()), -1), Pair(Range.singleton(0x0192u.toUShort()), -1), Pair(Range.singleton(0x0195u.toUShort()), 97), Pair(Range.singleton(0x0199u.toUShort()), -1), Pair(Range.singleton(0x019au.toUShort()), 163), Pair(Range.singleton(0x019bu.toUShort()), -22975), Pair(Range.singleton(0x019eu.toUShort()), 130), Pair(Range.stepBy2(RangeInclusive(0x01a1u.toUShort(), 0x01a5u.toUShort(), false)), -1), Pair(Range.singleton(0x01a8u.toUShort()), -1), Pair(Range.singleton(0x01adu.toUShort()), -1), Pair(Range.singleton(0x01b0u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0x01b4u.toUShort(), 0x01b6u.toUShort(), false)), -1), Pair(Range.singleton(0x01b9u.toUShort()), -1), Pair(Range.singleton(0x01bdu.toUShort()), -1), Pair(Range.singleton(0x01bfu.toUShort()), 56), Pair(Range.singleton(0x01c5u.toUShort()), -1), Pair(Range.singleton(0x01c6u.toUShort()), -2), Pair(Range.singleton(0x01c8u.toUShort()), -1), Pair(Range.singleton(0x01c9u.toUShort()), -2), Pair(Range.singleton(0x01cbu.toUShort()), -1), Pair(Range.singleton(0x01ccu.toUShort()), -2), Pair(Range.stepBy2(RangeInclusive(0x01ceu.toUShort(), 0x01dcu.toUShort(), false)), -1), Pair(Range.singleton(0x01ddu.toUShort()), -79), Pair(Range.stepBy2(RangeInclusive(0x01dfu.toUShort(), 0x01efu.toUShort(), false)), -1), Pair(Range.singleton(0x01f2u.toUShort()), -1), Pair(Range.singleton(0x01f3u.toUShort()), -2), Pair(Range.singleton(0x01f5u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0x01f9u.toUShort(), 0x021fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x0223u.toUShort(), 0x0233u.toUShort(), false)), -1), Pair(Range.singleton(0x023cu.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x023fu.toUShort(), 0x0240u.toUShort(), false)), 10815), Pair(Range.singleton(0x0242u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0x0247u.toUShort(), 0x024fu.toUShort(), false)), -1), Pair(Range.singleton(0x0250u.toUShort()), 10783), Pair(Range.singleton(0x0251u.toUShort()), 10780), Pair(Range.singleton(0x0252u.toUShort()), 10782), Pair(Range.singleton(0x0253u.toUShort()), -210), Pair(Range.singleton(0x0254u.toUShort()), -206), Pair(Range.stepBy1(RangeInclusive(0x0256u.toUShort(), 0x0257u.toUShort(), false)), -205), Pair(Range.singleton(0x0259u.toUShort()), -202), Pair(Range.singleton(0x025bu.toUShort()), -203), Pair(Range.singleton(0x025cu.toUShort()), -23217), Pair(Range.singleton(0x0260u.toUShort()), -205), Pair(Range.singleton(0x0261u.toUShort()), -23221), Pair(Range.singleton(0x0263u.toUShort()), -207), Pair(Range.singleton(0x0264u.toUShort()), -23193), Pair(Range.singleton(0x0265u.toUShort()), -23256), Pair(Range.singleton(0x0266u.toUShort()), -23228), Pair(Range.singleton(0x0268u.toUShort()), -209), Pair(Range.singleton(0x0269u.toUShort()), -211), Pair(Range.singleton(0x026au.toUShort()), -23228), Pair(Range.singleton(0x026bu.toUShort()), 10743), Pair(Range.singleton(0x026cu.toUShort()), -23231), Pair(Range.singleton(0x026fu.toUShort()), -211), Pair(Range.singleton(0x0271u.toUShort()), 10749), Pair(Range.singleton(0x0272u.toUShort()), -213), Pair(Range.singleton(0x0275u.toUShort()), -214), Pair(Range.singleton(0x0277u.toUShort()), -23194), Pair(Range.singleton(0x027cu.toUShort()), -23194), Pair(Range.singleton(0x027du.toUShort()), 10727), Pair(Range.singleton(0x0280u.toUShort()), -218), Pair(Range.singleton(0x0282u.toUShort()), -23229), Pair(Range.singleton(0x0283u.toUShort()), -218), Pair(Range.singleton(0x0287u.toUShort()), -23254), Pair(Range.singleton(0x0288u.toUShort()), -218), Pair(Range.singleton(0x0289u.toUShort()), -69), Pair(Range.stepBy1(RangeInclusive(0x028au.toUShort(), 0x028bu.toUShort(), false)), -217), Pair(Range.singleton(0x028cu.toUShort()), -71), Pair(Range.singleton(0x0292u.toUShort()), -219), Pair(Range.singleton(0x029du.toUShort()), -23275), Pair(Range.singleton(0x029eu.toUShort()), -23278), Pair(Range.singleton(0x0345u.toUShort()), 84), Pair(Range.stepBy2(RangeInclusive(0x0371u.toUShort(), 0x0373u.toUShort(), false)), -1), Pair(Range.singleton(0x0377u.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x037bu.toUShort(), 0x037du.toUShort(), false)), 130), Pair(Range.singleton(0x03acu.toUShort()), -38), Pair(Range.stepBy1(RangeInclusive(0x03adu.toUShort(), 0x03afu.toUShort(), false)), -37), Pair(Range.stepBy1(RangeInclusive(0x03b1u.toUShort(), 0x03c1u.toUShort(), false)), -32), Pair(Range.singleton(0x03c2u.toUShort()), -31), Pair(Range.stepBy1(RangeInclusive(0x03c3u.toUShort(), 0x03cbu.toUShort(), false)), -32), Pair(Range.singleton(0x03ccu.toUShort()), -64), Pair(Range.stepBy1(RangeInclusive(0x03cdu.toUShort(), 0x03ceu.toUShort(), false)), -63), Pair(Range.singleton(0x03d0u.toUShort()), -62), Pair(Range.singleton(0x03d1u.toUShort()), -57), Pair(Range.singleton(0x03d5u.toUShort()), -47), Pair(Range.singleton(0x03d6u.toUShort()), -54), Pair(Range.singleton(0x03d7u.toUShort()), -8), Pair(Range.stepBy2(RangeInclusive(0x03d9u.toUShort(), 0x03efu.toUShort(), false)), -1), Pair(Range.singleton(0x03f0u.toUShort()), -86), Pair(Range.singleton(0x03f1u.toUShort()), -80), Pair(Range.singleton(0x03f2u.toUShort()), 7), Pair(Range.singleton(0x03f3u.toUShort()), -116), Pair(Range.singleton(0x03f5u.toUShort()), -96), Pair(Range.singleton(0x03f8u.toUShort()), -1), Pair(Range.singleton(0x03fbu.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x0430u.toUShort(), 0x044fu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x0450u.toUShort(), 0x045fu.toUShort(), false)), -80), Pair(Range.stepBy2(RangeInclusive(0x0461u.toUShort(), 0x0481u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x048bu.toUShort(), 0x04bfu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x04c2u.toUShort(), 0x04ceu.toUShort(), false)), -1), Pair(Range.singleton(0x04cfu.toUShort()), -15), Pair(Range.stepBy2(RangeInclusive(0x04d1u.toUShort(), 0x052fu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0x0561u.toUShort(), 0x0586u.toUShort(), false)), -48), Pair(Range.stepBy1(RangeInclusive(0x10d0u.toUShort(), 0x10fau.toUShort(), false)), 3008), Pair(Range.stepBy1(RangeInclusive(0x10fdu.toUShort(), 0x10ffu.toUShort(), false)), 3008), Pair(Range.stepBy1(RangeInclusive(0x13f8u.toUShort(), 0x13fdu.toUShort(), false)), -8), Pair(Range.singleton(0x1c80u.toUShort()), -6254), Pair(Range.singleton(0x1c81u.toUShort()), -6253), Pair(Range.singleton(0x1c82u.toUShort()), -6244), Pair(Range.stepBy1(RangeInclusive(0x1c83u.toUShort(), 0x1c84u.toUShort(), false)), -6242), Pair(Range.singleton(0x1c85u.toUShort()), -6243), Pair(Range.singleton(0x1c86u.toUShort()), -6236), Pair(Range.singleton(0x1c87u.toUShort()), -6181), Pair(Range.singleton(0x1c88u.toUShort()), -30270), Pair(Range.singleton(0x1c8au.toUShort()), -1), Pair(Range.singleton(0x1d79u.toUShort()), -30204), Pair(Range.singleton(0x1d7du.toUShort()), 3814), Pair(Range.singleton(0x1d8eu.toUShort()), -30152), Pair(Range.stepBy2(RangeInclusive(0x1e01u.toUShort(), 0x1e95u.toUShort(), false)), -1), Pair(Range.singleton(0x1e9bu.toUShort()), -59), Pair(Range.stepBy2(RangeInclusive(0x1ea1u.toUShort(), 0x1effu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0x1f00u.toUShort(), 0x1f07u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f10u.toUShort(), 0x1f15u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f20u.toUShort(), 0x1f27u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f30u.toUShort(), 0x1f37u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f40u.toUShort(), 0x1f45u.toUShort(), false)), 8), Pair(Range.stepBy2(RangeInclusive(0x1f51u.toUShort(), 0x1f57u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f60u.toUShort(), 0x1f67u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f70u.toUShort(), 0x1f71u.toUShort(), false)), 74), Pair(Range.stepBy1(RangeInclusive(0x1f72u.toUShort(), 0x1f75u.toUShort(), false)), 86), Pair(Range.stepBy1(RangeInclusive(0x1f76u.toUShort(), 0x1f77u.toUShort(), false)), 100), Pair(Range.stepBy1(RangeInclusive(0x1f78u.toUShort(), 0x1f79u.toUShort(), false)), 128), Pair(Range.stepBy1(RangeInclusive(0x1f7au.toUShort(), 0x1f7bu.toUShort(), false)), 112), Pair(Range.stepBy1(RangeInclusive(0x1f7cu.toUShort(), 0x1f7du.toUShort(), false)), 126), Pair(Range.stepBy1(RangeInclusive(0x1fb0u.toUShort(), 0x1fb1u.toUShort(), false)), 8), Pair(Range.singleton(0x1fbeu.toUShort()), -7205), Pair(Range.stepBy1(RangeInclusive(0x1fd0u.toUShort(), 0x1fd1u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1fe0u.toUShort(), 0x1fe1u.toUShort(), false)), 8), Pair(Range.singleton(0x1fe5u.toUShort()), 7), Pair(Range.singleton(0x214eu.toUShort()), -28), Pair(Range.stepBy1(RangeInclusive(0x2170u.toUShort(), 0x217fu.toUShort(), false)), -16), Pair(Range.singleton(0x2184u.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x24d0u.toUShort(), 0x24e9u.toUShort(), false)), -26), Pair(Range.stepBy1(RangeInclusive(0x2c30u.toUShort(), 0x2c5fu.toUShort(), false)), -48), Pair(Range.singleton(0x2c61u.toUShort()), -1), Pair(Range.singleton(0x2c65u.toUShort()), -10795), Pair(Range.singleton(0x2c66u.toUShort()), -10792), Pair(Range.stepBy2(RangeInclusive(0x2c68u.toUShort(), 0x2c6cu.toUShort(), false)), -1), Pair(Range.singleton(0x2c73u.toUShort()), -1), Pair(Range.singleton(0x2c76u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0x2c81u.toUShort(), 0x2ce3u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0x2cecu.toUShort(), 0x2ceeu.toUShort(), false)), -1), Pair(Range.singleton(0x2cf3u.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x2d00u.toUShort(), 0x2d25u.toUShort(), false)), -7264), Pair(Range.singleton(0x2d27u.toUShort()), -7264), Pair(Range.singleton(0x2d2du.toUShort()), -7264), Pair(Range.stepBy2(RangeInclusive(0xa641u.toUShort(), 0xa66du.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa681u.toUShort(), 0xa69bu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa723u.toUShort(), 0xa72fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa733u.toUShort(), 0xa76fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa77au.toUShort(), 0xa77cu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa77fu.toUShort(), 0xa787u.toUShort(), false)), -1), Pair(Range.singleton(0xa78cu.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0xa791u.toUShort(), 0xa793u.toUShort(), false)), -1), Pair(Range.singleton(0xa794u.toUShort()), 48), Pair(Range.stepBy2(RangeInclusive(0xa797u.toUShort(), 0xa7a9u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7b5u.toUShort(), 0xa7c3u.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7c8u.toUShort(), 0xa7cau.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xa7cdu.toUShort(), 0xa7dbu.toUShort(), false)), -1), Pair(Range.singleton(0xa7f6u.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0xab4bu.toUShort(), 0xab4cu.toUShort(), false)), 33), Pair(Range.singleton(0xab53u.toUShort()), -928), Pair(Range.stepBy1(RangeInclusive(0xab70u.toUShort(), 0xabbfu.toUShort(), false)), 26672), Pair(Range.stepBy1(RangeInclusive(0xff41u.toUShort(), 0xff5au.toUShort(), false)), -32)), multis = listOf(Pair(0x00df, ushortArrayOf(0x0053u.toUShort(), 0x0053u.toUShort(), 0x0000u.toUShort())), Pair(0x0149, ushortArrayOf(0x02bcu.toUShort(), 0x004eu.toUShort(), 0x0000u.toUShort())), Pair(0x01f0, ushortArrayOf(0x004au.toUShort(), 0x030cu.toUShort(), 0x0000u.toUShort())), Pair(0x0390, ushortArrayOf(0x0399u.toUShort(), 0x0308u.toUShort(), 0x0301u.toUShort())), Pair(0x03b0, ushortArrayOf(0x03a5u.toUShort(), 0x0308u.toUShort(), 0x0301u.toUShort())), Pair(0x0587, ushortArrayOf(0x0535u.toUShort(), 0x0552u.toUShort(), 0x0000u.toUShort())), Pair(0x1e96, ushortArrayOf(0x0048u.toUShort(), 0x0331u.toUShort(), 0x0000u.toUShort())), Pair(0x1e97, ushortArrayOf(0x0054u.toUShort(), 0x0308u.toUShort(), 0x0000u.toUShort())), Pair(0x1e98, ushortArrayOf(0x0057u.toUShort(), 0x030au.toUShort(), 0x0000u.toUShort())), Pair(0x1e99, ushortArrayOf(0x0059u.toUShort(), 0x030au.toUShort(), 0x0000u.toUShort())), Pair(0x1e9a, ushortArrayOf(0x0041u.toUShort(), 0x02beu.toUShort(), 0x0000u.toUShort())), Pair(0x1f50, ushortArrayOf(0x03a5u.toUShort(), 0x0313u.toUShort(), 0x0000u.toUShort())), Pair(0x1f52, ushortArrayOf(0x03a5u.toUShort(), 0x0313u.toUShort(), 0x0300u.toUShort())), Pair(0x1f54, ushortArrayOf(0x03a5u.toUShort(), 0x0313u.toUShort(), 0x0301u.toUShort())), Pair(0x1f56, ushortArrayOf(0x03a5u.toUShort(), 0x0313u.toUShort(), 0x0342u.toUShort())), Pair(0x1f80, ushortArrayOf(0x1f08u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f81, ushortArrayOf(0x1f09u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f82, ushortArrayOf(0x1f0au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f83, ushortArrayOf(0x1f0bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f84, ushortArrayOf(0x1f0cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f85, ushortArrayOf(0x1f0du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f86, ushortArrayOf(0x1f0eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f87, ushortArrayOf(0x1f0fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f88, ushortArrayOf(0x1f08u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f89, ushortArrayOf(0x1f09u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8a, ushortArrayOf(0x1f0au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8b, ushortArrayOf(0x1f0bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8c, ushortArrayOf(0x1f0cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8d, ushortArrayOf(0x1f0du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8e, ushortArrayOf(0x1f0eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f8f, ushortArrayOf(0x1f0fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f90, ushortArrayOf(0x1f28u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f91, ushortArrayOf(0x1f29u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f92, ushortArrayOf(0x1f2au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f93, ushortArrayOf(0x1f2bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f94, ushortArrayOf(0x1f2cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f95, ushortArrayOf(0x1f2du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f96, ushortArrayOf(0x1f2eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f97, ushortArrayOf(0x1f2fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f98, ushortArrayOf(0x1f28u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f99, ushortArrayOf(0x1f29u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9a, ushortArrayOf(0x1f2au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9b, ushortArrayOf(0x1f2bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9c, ushortArrayOf(0x1f2cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9d, ushortArrayOf(0x1f2du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9e, ushortArrayOf(0x1f2eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1f9f, ushortArrayOf(0x1f2fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa0, ushortArrayOf(0x1f68u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa1, ushortArrayOf(0x1f69u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa2, ushortArrayOf(0x1f6au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa3, ushortArrayOf(0x1f6bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa4, ushortArrayOf(0x1f6cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa5, ushortArrayOf(0x1f6du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa6, ushortArrayOf(0x1f6eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa7, ushortArrayOf(0x1f6fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa8, ushortArrayOf(0x1f68u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fa9, ushortArrayOf(0x1f69u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1faa, ushortArrayOf(0x1f6au.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fab, ushortArrayOf(0x1f6bu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fac, ushortArrayOf(0x1f6cu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fad, ushortArrayOf(0x1f6du.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fae, ushortArrayOf(0x1f6eu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1faf, ushortArrayOf(0x1f6fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb2, ushortArrayOf(0x1fbau.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb3, ushortArrayOf(0x0391u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb4, ushortArrayOf(0x0386u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb6, ushortArrayOf(0x0391u.toUShort(), 0x0342u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb7, ushortArrayOf(0x0391u.toUShort(), 0x0342u.toUShort(), 0x0399u.toUShort())), Pair(0x1fbc, ushortArrayOf(0x0391u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc2, ushortArrayOf(0x1fcau.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc3, ushortArrayOf(0x0397u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc4, ushortArrayOf(0x0389u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc6, ushortArrayOf(0x0397u.toUShort(), 0x0342u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc7, ushortArrayOf(0x0397u.toUShort(), 0x0342u.toUShort(), 0x0399u.toUShort())), Pair(0x1fcc, ushortArrayOf(0x0397u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1fd2, ushortArrayOf(0x0399u.toUShort(), 0x0308u.toUShort(), 0x0300u.toUShort())), Pair(0x1fd3, ushortArrayOf(0x0399u.toUShort(), 0x0308u.toUShort(), 0x0301u.toUShort())), Pair(0x1fd6, ushortArrayOf(0x0399u.toUShort(), 0x0342u.toUShort(), 0x0000u.toUShort())), Pair(0x1fd7, ushortArrayOf(0x0399u.toUShort(), 0x0308u.toUShort(), 0x0342u.toUShort())), Pair(0x1fe2, ushortArrayOf(0x03a5u.toUShort(), 0x0308u.toUShort(), 0x0300u.toUShort())), Pair(0x1fe3, ushortArrayOf(0x03a5u.toUShort(), 0x0308u.toUShort(), 0x0301u.toUShort())), Pair(0x1fe4, ushortArrayOf(0x03a1u.toUShort(), 0x0313u.toUShort(), 0x0000u.toUShort())), Pair(0x1fe6, ushortArrayOf(0x03a5u.toUShort(), 0x0342u.toUShort(), 0x0000u.toUShort())), Pair(0x1fe7, ushortArrayOf(0x03a5u.toUShort(), 0x0308u.toUShort(), 0x0342u.toUShort())), Pair(0x1ff2, ushortArrayOf(0x1ffau.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff3, ushortArrayOf(0x03a9u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff4, ushortArrayOf(0x038fu.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff6, ushortArrayOf(0x03a9u.toUShort(), 0x0342u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff7, ushortArrayOf(0x03a9u.toUShort(), 0x0342u.toUShort(), 0x0399u.toUShort())), Pair(0x1ffc, ushortArrayOf(0x03a9u.toUShort(), 0x0399u.toUShort(), 0x0000u.toUShort())), Pair(0xfb00, ushortArrayOf(0x0046u.toUShort(), 0x0046u.toUShort(), 0x0000u.toUShort())), Pair(0xfb01, ushortArrayOf(0x0046u.toUShort(), 0x0049u.toUShort(), 0x0000u.toUShort())), Pair(0xfb02, ushortArrayOf(0x0046u.toUShort(), 0x004cu.toUShort(), 0x0000u.toUShort())), Pair(0xfb03, ushortArrayOf(0x0046u.toUShort(), 0x0046u.toUShort(), 0x0049u.toUShort())), Pair(0xfb04, ushortArrayOf(0x0046u.toUShort(), 0x0046u.toUShort(), 0x004cu.toUShort())), Pair(0xfb05, ushortArrayOf(0x0053u.toUShort(), 0x0054u.toUShort(), 0x0000u.toUShort())), Pair(0xfb06, ushortArrayOf(0x0053u.toUShort(), 0x0054u.toUShort(), 0x0000u.toUShort())), Pair(0xfb13, ushortArrayOf(0x0544u.toUShort(), 0x0546u.toUShort(), 0x0000u.toUShort())), Pair(0xfb14, ushortArrayOf(0x0544u.toUShort(), 0x0535u.toUShort(), 0x0000u.toUShort())), Pair(0xfb15, ushortArrayOf(0x0544u.toUShort(), 0x053bu.toUShort(), 0x0000u.toUShort())), Pair(0xfb16, ushortArrayOf(0x054eu.toUShort(), 0x0546u.toUShort(), 0x0000u.toUShort())), Pair(0xfb17, ushortArrayOf(0x0544u.toUShort(), 0x053du.toUShort(), 0x0000u.toUShort()))), exceptions = listOf()), L2Lut(singles = listOf(Pair(Range.stepBy1(RangeInclusive(0x0428u.toUShort(), 0x044fu.toUShort(), false)), -40), Pair(Range.stepBy1(RangeInclusive(0x04d8u.toUShort(), 0x04fbu.toUShort(), false)), -40), Pair(Range.stepBy1(RangeInclusive(0x0597u.toUShort(), 0x05a1u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05a3u.toUShort(), 0x05b1u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05b3u.toUShort(), 0x05b9u.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x05bbu.toUShort(), 0x05bcu.toUShort(), false)), -39), Pair(Range.stepBy1(RangeInclusive(0x0cc0u.toUShort(), 0x0cf2u.toUShort(), false)), -64), Pair(Range.stepBy1(RangeInclusive(0x0d70u.toUShort(), 0x0d85u.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x18c0u.toUShort(), 0x18dfu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x6e60u.toUShort(), 0x6e7fu.toUShort(), false)), -32), Pair(Range.stepBy1(RangeInclusive(0x6ebbu.toUShort(), 0x6ed3u.toUShort(), false)), -27), Pair(Range.singleton(0xdf41u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0xdf49u.toUShort(), 0xdf4bu.toUShort(), false)), -1), Pair(Range.singleton(0xdf4eu.toUShort()), -1), Pair(Range.singleton(0xdf52u.toUShort()), -1), Pair(Range.stepBy2(RangeInclusive(0xdf69u.toUShort(), 0xdf6fu.toUShort(), false)), -1), Pair(Range.stepBy2(RangeInclusive(0xdf73u.toUShort(), 0xdf7fu.toUShort(), false)), -1), Pair(Range.stepBy1(RangeInclusive(0xe922u.toUShort(), 0xe943u.toUShort(), false)), -34)), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 'S', '\u0000'))))))

public var TITLECASE_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x01c4u.toUShort()), 1), Pair(Range.singleton(0x01c5u.toUShort()), 0), Pair(Range.singleton(0x01c6u.toUShort()), -1), Pair(Range.singleton(0x01c7u.toUShort()), 1), Pair(Range.singleton(0x01c8u.toUShort()), 0), Pair(Range.singleton(0x01c9u.toUShort()), -1), Pair(Range.singleton(0x01cau.toUShort()), 1), Pair(Range.singleton(0x01cbu.toUShort()), 0), Pair(Range.singleton(0x01ccu.toUShort()), -1), Pair(Range.singleton(0x01f1u.toUShort()), 1), Pair(Range.singleton(0x01f2u.toUShort()), 0), Pair(Range.singleton(0x01f3u.toUShort()), -1), Pair(Range.stepBy1(RangeInclusive(0x10d0u.toUShort(), 0x10fau.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x10fdu.toUShort(), 0x10ffu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1f80u.toUShort(), 0x1f87u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f88u.toUShort(), 0x1f8fu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1f90u.toUShort(), 0x1f97u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1f98u.toUShort(), 0x1f9fu.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x1fa0u.toUShort(), 0x1fa7u.toUShort(), false)), 8), Pair(Range.stepBy1(RangeInclusive(0x1fa8u.toUShort(), 0x1fafu.toUShort(), false)), 0), Pair(Range.singleton(0x1fb3u.toUShort()), 9), Pair(Range.singleton(0x1fbcu.toUShort()), 0), Pair(Range.singleton(0x1fc3u.toUShort()), 9), Pair(Range.singleton(0x1fccu.toUShort()), 0), Pair(Range.singleton(0x1ff3u.toUShort()), 9), Pair(Range.singleton(0x1ffcu.toUShort()), 0)), multis = listOf(Pair(0x00df, ushortArrayOf(0x0053u.toUShort(), 0x0073u.toUShort(), 0x0000u.toUShort())), Pair(0x0587, ushortArrayOf(0x0535u.toUShort(), 0x0582u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb2, ushortArrayOf(0x1fbau.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb4, ushortArrayOf(0x0386u.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1fb7, ushortArrayOf(0x0391u.toUShort(), 0x0342u.toUShort(), 0x0345u.toUShort())), Pair(0x1fc2, ushortArrayOf(0x1fcau.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc4, ushortArrayOf(0x0389u.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1fc7, ushortArrayOf(0x0397u.toUShort(), 0x0342u.toUShort(), 0x0345u.toUShort())), Pair(0x1ff2, ushortArrayOf(0x1ffau.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff4, ushortArrayOf(0x038fu.toUShort(), 0x0345u.toUShort(), 0x0000u.toUShort())), Pair(0x1ff7, ushortArrayOf(0x03a9u.toUShort(), 0x0342u.toUShort(), 0x0345u.toUShort())), Pair(0xfb00, ushortArrayOf(0x0046u.toUShort(), 0x0066u.toUShort(), 0x0000u.toUShort())), Pair(0xfb01, ushortArrayOf(0x0046u.toUShort(), 0x0069u.toUShort(), 0x0000u.toUShort())), Pair(0xfb02, ushortArrayOf(0x0046u.toUShort(), 0x006cu.toUShort(), 0x0000u.toUShort())), Pair(0xfb03, ushortArrayOf(0x0046u.toUShort(), 0x0066u.toUShort(), 0x0069u.toUShort())), Pair(0xfb04, ushortArrayOf(0x0046u.toUShort(), 0x0066u.toUShort(), 0x006cu.toUShort())), Pair(0xfb05, ushortArrayOf(0x0053u.toUShort(), 0x0074u.toUShort(), 0x0000u.toUShort())), Pair(0xfb06, ushortArrayOf(0x0053u.toUShort(), 0x0074u.toUShort(), 0x0000u.toUShort())), Pair(0xfb13, ushortArrayOf(0x0544u.toUShort(), 0x0576u.toUShort(), 0x0000u.toUShort())), Pair(0xfb14, ushortArrayOf(0x0544u.toUShort(), 0x0565u.toUShort(), 0x0000u.toUShort())), Pair(0xfb15, ushortArrayOf(0x0544u.toUShort(), 0x056bu.toUShort(), 0x0000u.toUShort())), Pair(0xfb16, ushortArrayOf(0x054eu.toUShort(), 0x0576u.toUShort(), 0x0000u.toUShort())), Pair(0xfb17, ushortArrayOf(0x0544u.toUShort(), 0x056du.toUShort(), 0x0000u.toUShort()))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf(Pair(0xdf95, listOf('S', 's', '\u0000'))))))

public var CASEFOLD_LUT: L1Lut = L1Lut(l2Luts = listOf(L2Lut(singles = listOf(Pair(Range.singleton(0x0131u.toUShort()), 0), Pair(Range.stepBy1(RangeInclusive(0x13a0u.toUShort(), 0x13f5u.toUShort(), false)), 0), Pair(Range.stepBy1(RangeInclusive(0x13f8u.toUShort(), 0x13fdu.toUShort(), false)), -8), Pair(Range.stepBy1(RangeInclusive(0xab70u.toUShort(), 0xabbfu.toUShort(), false)), 26672)), multis = listOf(Pair(0x1e9e, ushortArrayOf(0x0073u.toUShort(), 0x0073u.toUShort(), 0x0000u.toUShort()))), exceptions = listOf()), L2Lut(singles = listOf(), multis = listOf(), exceptions = listOf())))
