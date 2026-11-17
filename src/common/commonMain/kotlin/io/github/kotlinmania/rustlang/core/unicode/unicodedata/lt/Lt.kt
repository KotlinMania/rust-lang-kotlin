// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.lt

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.ShortOffsetRunHeader
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.skipSearch

public var SHORT_OFFSET_RUNS: List<ShortOffsetRunHeader> = listOf(ShortOffsetRunHeader.new(0, 453), ShortOffsetRunHeader.new(1, 8072), ShortOffsetRunHeader.new(9, 1122301))

public var OFFSETS: UByteArray = ubyteArrayOf(0u, 1u, 2u, 1u, 2u, 1u, 38u, 1u, 0u, 8u, 8u, 8u, 8u, 8u, 12u, 1u, 15u, 1u, 47u, 1u, 0u)

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.toUInt()) >= 0x1c5u && lookupSlow(c)
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
