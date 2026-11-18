// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.cf

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.ShortOffsetRunHeader
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.skipSearch

public var SHORT_OFFSET_RUNS: List<ShortOffsetRunHeader> = listOf(ShortOffsetRunHeader.new(0, 1536), ShortOffsetRunHeader.new(3, 2192), ShortOffsetRunHeader.new(11, 6158), ShortOffsetRunHeader.new(15, 8203), ShortOffsetRunHeader.new(17, 65279), ShortOffsetRunHeader.new(25, 69821), ShortOffsetRunHeader.new(29, 78896), ShortOffsetRunHeader.new(33, 113824), ShortOffsetRunHeader.new(35, 119155), ShortOffsetRunHeader.new(37, 917505), ShortOffsetRunHeader.new(39, 2031744))

public var OFFSETS: UByteArray = ubyteArrayOf(173u.toUByte(), 1u.toUByte(), 0u.toUByte(), 6u.toUByte(), 22u.toUByte(), 1u.toUByte(), 192u.toUByte(), 1u.toUByte(), 49u.toUByte(), 1u.toUByte(), 0u.toUByte(), 2u.toUByte(), 80u.toUByte(), 1u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 5u.toUByte(), 26u.toUByte(), 5u.toUByte(), 49u.toUByte(), 5u.toUByte(), 1u.toUByte(), 10u.toUByte(), 0u.toUByte(), 1u.toUByte(), 249u.toUByte(), 3u.toUByte(), 0u.toUByte(), 1u.toUByte(), 15u.toUByte(), 1u.toUByte(), 0u.toUByte(), 16u.toUByte(), 0u.toUByte(), 4u.toUByte(), 0u.toUByte(), 8u.toUByte(), 0u.toUByte(), 1u.toUByte(), 30u.toUByte(), 96u.toUByte(), 0u.toUByte())

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.code.toUInt()) >= 0xadu && lookupSlow(c)
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
