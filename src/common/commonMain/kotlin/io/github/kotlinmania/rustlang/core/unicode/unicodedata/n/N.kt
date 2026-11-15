// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.n

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.unicode.unicodedata.ShortOffsetRunHeader
import io.github.kotlinmania.rustlang.unicode.unicodedata.skipSearch

public var SHORT_OFFSET_RUNS: List<ShortOffsetRunHeader> = listOf(ShortOffsetRunHeader.new(0, 1632), ShortOffsetRunHeader.new(7, 2406), ShortOffsetRunHeader.new(13, 4160), ShortOffsetRunHeader.new(47, 4969), ShortOffsetRunHeader.new(51, 5870), ShortOffsetRunHeader.new(53, 6470), ShortOffsetRunHeader.new(61, 8304), ShortOffsetRunHeader.new(77, 9312), ShortOffsetRunHeader.new(87, 10102), ShortOffsetRunHeader.new(91, 11517), ShortOffsetRunHeader.new(93, 12295), ShortOffsetRunHeader.new(95, 12690), ShortOffsetRunHeader.new(101, 42528), ShortOffsetRunHeader.new(113, 43056), ShortOffsetRunHeader.new(117, 44016), ShortOffsetRunHeader.new(129, 65296), ShortOffsetRunHeader.new(131, 65799), ShortOffsetRunHeader.new(133, 66273), ShortOffsetRunHeader.new(139, 67672), ShortOffsetRunHeader.new(151, 68858), ShortOffsetRunHeader.new(181, 69216), ShortOffsetRunHeader.new(187, 70736), ShortOffsetRunHeader.new(207, 71248), ShortOffsetRunHeader.new(211, 71904), ShortOffsetRunHeader.new(219, 72688), ShortOffsetRunHeader.new(223, 73552), ShortOffsetRunHeader.new(233, 74752), ShortOffsetRunHeader.new(237, 75399), ShortOffsetRunHeader.new(242, 90416), ShortOffsetRunHeader.new(243, 92768), ShortOffsetRunHeader.new(245, 93552), ShortOffsetRunHeader.new(253, 93824), ShortOffsetRunHeader.new(255, 94196), ShortOffsetRunHeader.new(257, 118000), ShortOffsetRunHeader.new(259, 119488), ShortOffsetRunHeader.new(261, 120782), ShortOffsetRunHeader.new(267, 123200), ShortOffsetRunHeader.new(269, 123632), ShortOffsetRunHeader.new(271, 124144), ShortOffsetRunHeader.new(273, 125127), ShortOffsetRunHeader.new(277, 126065), ShortOffsetRunHeader.new(281, 127232), ShortOffsetRunHeader.new(291, 130032), ShortOffsetRunHeader.new(293, 1244154))

public var OFFSETS: UByteArray = ubyteArrayOf(178u, 2u, 5u, 1u, 2u, 3u, 0u, 10u, 134u, 10u, 198u, 10u, 0u, 10u, 118u, 10u, 4u, 6u, 108u, 10u, 118u, 10u, 118u, 10u, 2u, 6u, 110u, 13u, 115u, 10u, 8u, 7u, 103u, 10u, 104u, 7u, 7u, 19u, 109u, 10u, 96u, 10u, 118u, 10u, 70u, 20u, 0u, 10u, 70u, 10u, 0u, 20u, 0u, 3u, 239u, 10u, 6u, 10u, 22u, 10u, 0u, 10u, 128u, 11u, 165u, 10u, 6u, 10u, 182u, 10u, 86u, 10u, 134u, 10u, 6u, 10u, 0u, 1u, 3u, 6u, 6u, 10u, 198u, 51u, 2u, 5u, 0u, 60u, 78u, 22u, 0u, 30u, 0u, 1u, 0u, 1u, 25u, 9u, 14u, 3u, 0u, 4u, 138u, 10u, 30u, 8u, 1u, 15u, 32u, 10u, 39u, 15u, 0u, 10u, 188u, 10u, 0u, 6u, 154u, 10u, 38u, 10u, 198u, 10u, 22u, 10u, 86u, 10u, 0u, 10u, 0u, 10u, 0u, 45u, 12u, 57u, 17u, 2u, 0u, 27u, 36u, 4u, 29u, 1u, 8u, 1u, 134u, 5u, 202u, 10u, 0u, 8u, 25u, 7u, 39u, 9u, 75u, 5u, 22u, 6u, 160u, 2u, 2u, 16u, 2u, 46u, 64u, 9u, 52u, 2u, 30u, 3u, 75u, 5u, 104u, 8u, 24u, 8u, 41u, 7u, 0u, 6u, 48u, 10u, 6u, 10u, 0u, 31u, 158u, 10u, 42u, 4u, 112u, 7u, 134u, 30u, 128u, 10u, 60u, 10u, 144u, 10u, 7u, 20u, 251u, 10u, 0u, 10u, 118u, 10u, 0u, 10u, 102u, 10u, 6u, 20u, 76u, 12u, 0u, 19u, 93u, 10u, 0u, 10u, 86u, 29u, 227u, 10u, 70u, 10u, 54u, 10u, 0u, 10u, 102u, 21u, 0u, 112u, 5u, 11u, 208u, 0u, 0u, 10u, 0u, 10u, 86u, 10u, 134u, 10u, 1u, 7u, 0u, 10u, 0u, 23u, 0u, 3u, 0u, 10u, 0u, 20u, 12u, 20u, 108u, 25u, 0u, 50u, 0u, 10u, 0u, 10u, 0u, 10u, 247u, 10u, 0u, 9u, 128u, 10u, 0u, 59u, 1u, 3u, 1u, 4u, 76u, 45u, 1u, 15u, 0u, 13u, 0u, 10u, 0u)

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.toUInt()) >= 0xb2u && lookupSlow(c)
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
