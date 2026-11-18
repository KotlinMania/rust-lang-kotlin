// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.defaultignorablecodepoint

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.ShortOffsetRunHeader
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.skipSearch

public var SHORT_OFFSET_RUNS: List<ShortOffsetRunHeader> = listOf(ShortOffsetRunHeader.new(0, 847), ShortOffsetRunHeader.new(3, 1564), ShortOffsetRunHeader.new(5, 4447), ShortOffsetRunHeader.new(7, 6068), ShortOffsetRunHeader.new(9, 8203), ShortOffsetRunHeader.new(13, 12644), ShortOffsetRunHeader.new(19, 65024), ShortOffsetRunHeader.new(21, 113824), ShortOffsetRunHeader.new(29, 119155), ShortOffsetRunHeader.new(31, 917504), ShortOffsetRunHeader.new(33, 921600), ShortOffsetRunHeader.new(34, 2035712))

public var OFFSETS: UByteArray = ubyteArrayOf(173u, 1u, 0u, 1u, 0u, 1u, 0u, 2u, 0u, 2u, 85u, 5u, 0u, 5u, 26u, 5u, 49u, 16u, 0u, 1u, 0u, 16u, 239u, 1u, 160u, 1u, 79u, 9u, 0u, 4u, 0u, 8u, 0u, 0u, 0u)

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.toUInt()) >= 0xadu && lookupSlow(c)
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
