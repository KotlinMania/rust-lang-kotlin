// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.caseignorable

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.unicode.unicodedata.ShortOffsetRunHeader
import io.github.kotlinmania.rustlang.unicode.unicodedata.skipSearch

public var SHORT_OFFSET_RUNS: List<ShortOffsetRunHeader> = listOf(ShortOffsetRunHeader.new(0, 688), ShortOffsetRunHeader.new(11, 4957), ShortOffsetRunHeader.new(265, 5906), ShortOffsetRunHeader.new(267, 8125), ShortOffsetRunHeader.new(377, 11388), ShortOffsetRunHeader.new(411, 12293), ShortOffsetRunHeader.new(423, 40981), ShortOffsetRunHeader.new(435, 42232), ShortOffsetRunHeader.new(437, 42508), ShortOffsetRunHeader.new(439, 64286), ShortOffsetRunHeader.new(535, 65024), ShortOffsetRunHeader.new(539, 66045), ShortOffsetRunHeader.new(569, 67456), ShortOffsetRunHeader.new(575, 68097), ShortOffsetRunHeader.new(581, 68900), ShortOffsetRunHeader.new(593, 69291), ShortOffsetRunHeader.new(601, 71727), ShortOffsetRunHeader.new(729, 71995), ShortOffsetRunHeader.new(733, 73459), ShortOffsetRunHeader.new(801, 78896), ShortOffsetRunHeader.new(813, 90398), ShortOffsetRunHeader.new(817, 92912), ShortOffsetRunHeader.new(821, 93504), ShortOffsetRunHeader.new(827, 94031), ShortOffsetRunHeader.new(831, 110576), ShortOffsetRunHeader.new(841, 113821), ShortOffsetRunHeader.new(847, 118528), ShortOffsetRunHeader.new(851, 119079), ShortOffsetRunHeader.new(855, 121344), ShortOffsetRunHeader.new(869, 122829), ShortOffsetRunHeader.new(881, 123566), ShortOffsetRunHeader.new(897, 124139), ShortOffsetRunHeader.new(901, 125136), ShortOffsetRunHeader.new(915, 127995), ShortOffsetRunHeader.new(919, 917505), ShortOffsetRunHeader.new(921, 2032112))

public var OFFSETS: UByteArray = ubyteArrayOf(168u, 1u, 4u, 1u, 1u, 1u, 4u, 1u, 2u, 2u, 0u, 192u, 4u, 2u, 4u, 1u, 9u, 2u, 1u, 1u, 251u, 7u, 206u, 2u, 5u, 1u, 43u, 2u, 4u, 45u, 1u, 1u, 1u, 2u, 1u, 2u, 1u, 3u, 42u, 1u, 11u, 6u, 10u, 11u, 1u, 1u, 35u, 1u, 10u, 21u, 16u, 1u, 101u, 8u, 1u, 10u, 1u, 4u, 33u, 1u, 1u, 1u, 30u, 27u, 91u, 11u, 58u, 11u, 4u, 1u, 2u, 1u, 24u, 24u, 43u, 3u, 44u, 1u, 7u, 2u, 5u, 9u, 41u, 58u, 55u, 1u, 1u, 1u, 4u, 8u, 4u, 1u, 3u, 7u, 10u, 2u, 13u, 1u, 15u, 1u, 58u, 1u, 4u, 4u, 8u, 1u, 20u, 2u, 26u, 1u, 2u, 2u, 57u, 1u, 4u, 2u, 4u, 2u, 2u, 3u, 3u, 1u, 30u, 2u, 3u, 1u, 11u, 2u, 57u, 1u, 4u, 5u, 1u, 2u, 4u, 1u, 20u, 2u, 22u, 6u, 1u, 1u, 58u, 1u, 2u, 1u, 1u, 4u, 8u, 1u, 5u, 4u, 11u, 2u, 30u, 1u, 61u, 1u, 12u, 1u, 50u, 1u, 3u, 1u, 55u, 1u, 1u, 3u, 5u, 3u, 1u, 4u, 7u, 2u, 11u, 2u, 29u, 1u, 58u, 1u, 2u, 1u, 6u, 1u, 5u, 2u, 20u, 2u, 28u, 2u, 57u, 2u, 4u, 4u, 8u, 1u, 20u, 2u, 29u, 1u, 72u, 1u, 7u, 3u, 1u, 1u, 90u, 1u, 2u, 7u, 11u, 9u, 98u, 1u, 2u, 9u, 9u, 1u, 1u, 7u, 73u, 2u, 27u, 1u, 1u, 1u, 1u, 1u, 55u, 14u, 1u, 5u, 1u, 2u, 5u, 11u, 1u, 36u, 9u, 1u, 102u, 4u, 1u, 6u, 1u, 2u, 2u, 2u, 25u, 2u, 4u, 3u, 16u, 4u, 13u, 1u, 2u, 2u, 6u, 1u, 15u, 1u, 94u, 1u, 0u, 3u, 0u, 3u, 29u, 2u, 30u, 2u, 30u, 2u, 64u, 2u, 1u, 7u, 8u, 1u, 2u, 11u, 3u, 1u, 5u, 1u, 45u, 5u, 51u, 1u, 65u, 2u, 34u, 1u, 118u, 3u, 4u, 2u, 9u, 1u, 6u, 3u, 219u, 2u, 2u, 1u, 58u, 1u, 1u, 7u, 1u, 1u, 1u, 1u, 2u, 8u, 6u, 10u, 2u, 1u, 39u, 1u, 8u, 65u, 15u, 4u, 48u, 1u, 1u, 5u, 1u, 1u, 5u, 1u, 40u, 9u, 12u, 2u, 32u, 4u, 2u, 2u, 1u, 3u, 56u, 1u, 1u, 2u, 3u, 1u, 1u, 3u, 58u, 8u, 2u, 2u, 64u, 6u, 82u, 3u, 1u, 13u, 1u, 7u, 4u, 1u, 6u, 1u, 3u, 2u, 50u, 63u, 13u, 1u, 34u, 101u, 0u, 1u, 1u, 3u, 11u, 3u, 13u, 3u, 13u, 3u, 13u, 2u, 12u, 5u, 8u, 2u, 10u, 1u, 2u, 1u, 2u, 5u, 49u, 5u, 1u, 10u, 1u, 1u, 13u, 1u, 15u, 17u, 48u, 33u, 0u, 2u, 113u, 3u, 125u, 1u, 15u, 1u, 96u, 32u, 47u, 1u, 0u, 1u, 36u, 4u, 3u, 5u, 5u, 1u, 93u, 6u, 93u, 3u, 0u, 1u, 0u, 6u, 0u, 1u, 98u, 4u, 1u, 10u, 1u, 1u, 28u, 4u, 80u, 2u, 14u, 34u, 78u, 1u, 23u, 3u, 102u, 4u, 3u, 2u, 8u, 1u, 3u, 1u, 4u, 1u, 25u, 2u, 5u, 1u, 151u, 2u, 26u, 18u, 13u, 1u, 38u, 8u, 25u, 11u, 46u, 3u, 48u, 1u, 2u, 4u, 2u, 2u, 17u, 1u, 21u, 2u, 66u, 6u, 2u, 2u, 2u, 2u, 12u, 1u, 8u, 1u, 35u, 1u, 11u, 1u, 51u, 1u, 1u, 3u, 2u, 2u, 5u, 2u, 1u, 1u, 27u, 1u, 14u, 2u, 5u, 2u, 1u, 1u, 100u, 5u, 9u, 3u, 121u, 1u, 2u, 1u, 4u, 1u, 0u, 1u, 147u, 17u, 0u, 16u, 3u, 1u, 12u, 16u, 34u, 1u, 2u, 1u, 169u, 1u, 7u, 1u, 6u, 1u, 11u, 1u, 35u, 1u, 1u, 1u, 47u, 1u, 45u, 2u, 67u, 1u, 21u, 3u, 0u, 1u, 226u, 1u, 149u, 5u, 0u, 6u, 1u, 42u, 1u, 14u, 0u, 3u, 1u, 2u, 5u, 4u, 40u, 3u, 4u, 1u, 165u, 2u, 0u, 4u, 38u, 1u, 26u, 5u, 1u, 1u, 0u, 2u, 24u, 1u, 3u, 7u, 32u, 16u, 70u, 11u, 49u, 4u, 123u, 1u, 54u, 15u, 41u, 1u, 2u, 2u, 10u, 3u, 49u, 4u, 2u, 2u, 2u, 1u, 4u, 1u, 10u, 1u, 50u, 3u, 36u, 5u, 1u, 8u, 62u, 1u, 12u, 2u, 52u, 9u, 10u, 4u, 2u, 1u, 95u, 3u, 2u, 1u, 1u, 2u, 6u, 1u, 2u, 1u, 157u, 1u, 3u, 8u, 21u, 2u, 57u, 2u, 3u, 1u, 37u, 7u, 3u, 5u, 70u, 6u, 13u, 1u, 1u, 1u, 1u, 1u, 14u, 2u, 85u, 8u, 2u, 3u, 1u, 1u, 23u, 1u, 84u, 6u, 1u, 1u, 4u, 2u, 1u, 2u, 238u, 4u, 6u, 2u, 1u, 2u, 27u, 2u, 85u, 8u, 2u, 1u, 1u, 2u, 106u, 1u, 1u, 1u, 2u, 6u, 1u, 1u, 101u, 1u, 1u, 1u, 2u, 4u, 1u, 5u, 0u, 9u, 1u, 2u, 0u, 2u, 1u, 1u, 4u, 1u, 144u, 4u, 2u, 2u, 4u, 1u, 32u, 10u, 40u, 6u, 2u, 4u, 8u, 1u, 9u, 6u, 2u, 3u, 46u, 13u, 1u, 2u, 198u, 1u, 1u, 3u, 1u, 1u, 201u, 7u, 1u, 6u, 1u, 1u, 82u, 22u, 2u, 7u, 1u, 2u, 1u, 2u, 122u, 6u, 3u, 1u, 1u, 2u, 1u, 7u, 1u, 1u, 72u, 2u, 3u, 1u, 1u, 1u, 65u, 1u, 22u, 1u, 0u, 2u, 11u, 2u, 52u, 5u, 5u, 1u, 1u, 1u, 23u, 1u, 0u, 17u, 6u, 15u, 0u, 12u, 3u, 3u, 0u, 5u, 59u, 7u, 9u, 4u, 0u, 3u, 40u, 2u, 0u, 1u, 63u, 17u, 64u, 2u, 1u, 2u, 13u, 2u, 0u, 4u, 1u, 7u, 1u, 2u, 0u, 2u, 1u, 4u, 0u, 46u, 2u, 23u, 0u, 2u, 62u, 3u, 9u, 16u, 2u, 7u, 30u, 4u, 148u, 3u, 22u, 2u, 0u, 55u, 4u, 50u, 8u, 1u, 14u, 1u, 22u, 5u, 1u, 15u, 0u, 58u, 1u, 17u, 2u, 7u, 1u, 2u, 1u, 5u, 5u, 62u, 33u, 1u, 160u, 14u, 0u, 1u, 61u, 4u, 0u, 5u, 254u, 2u, 243u, 1u, 2u, 1u, 7u, 2u, 5u, 1u, 9u, 1u, 0u, 7u, 109u, 8u, 0u, 5u, 0u, 1u, 30u, 96u, 128u, 240u, 0u)

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.toUInt()) >= 0xa8u && lookupSlow(c)
}

public fun lookupSlow(c: Char): Boolean {
    run {
        check(SHORT_OFFSET_RUNS.last()!!.first > Char.MAX_VALUE.toUInt())
        var i = 0
        while (i < SHORT_OFFSET_RUNS.size) {
            check(SHORT_OFFSET_RUNS[i].startIndex() < OFFSETS.size)
            i += 1
        }
    }
    // SAFETY: We just ensured the last element of `SHORT_OFFSET_RUNS` is greater than `std.char.MAX`
    // and the start indices of all elements in `SHORT_OFFSET_RUNS` are smaller than `OFFSETS.len()`.
    return skipSearch(c, SHORT_OFFSET_RUNS, OFFSETS)
}
