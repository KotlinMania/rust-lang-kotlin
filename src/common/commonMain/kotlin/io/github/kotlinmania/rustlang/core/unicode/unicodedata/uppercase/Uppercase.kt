// port-lint: source unicode/unicode_data.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.unicode.unicodedata.uppercase

import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.unicode.unicodedata.bitsetSearch

public var BITSET_CHUNKS_MAP: UByteArray = ubyteArrayOf(3u, 16u, 8u, 8u, 0u, 8u, 8u, 2u, 5u, 14u, 8u, 17u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 11u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 9u, 8u, 15u, 8u, 13u, 8u, 8u, 1u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 10u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 18u, 8u, 6u, 8u, 8u, 12u, 8u, 4u)

public var BITSET_INDEX_CHUNKS: List<UByteArray> = listOf(ubyteArrayOf(46u, 46u, 5u, 37u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 5u, 0u), ubyteArrayOf(46u, 46u, 5u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 42u, 46u, 46u, 46u, 46u, 46u, 17u, 17u, 68u, 17u, 45u, 30u, 25u, 24u), ubyteArrayOf(46u, 46u, 46u, 34u, 38u, 22u, 23u, 15u, 13u, 36u, 46u, 46u, 46u, 11u, 32u, 41u), ubyteArrayOf(46u, 46u, 46u, 46u, 9u, 8u, 47u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 39u, 29u, 69u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 21u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 31u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 59u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 51u, 65u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 67u, 66u, 46u, 20u, 14u, 16u, 4u), ubyteArrayOf(46u, 46u, 46u, 46u, 52u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 55u, 46u, 46u, 33u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(46u, 46u, 56u, 48u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(53u, 46u, 9u, 49u, 46u, 44u, 35u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(54u, 19u, 3u, 18u, 10u, 50u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(54u, 40u, 17u, 28u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u, 46u), ubyteArrayOf(60u, 1u, 27u, 57u, 12u, 7u, 26u, 58u, 43u, 61u, 6u, 2u, 64u, 63u, 62u, 70u))

public var BITSET_CANONICAL: ULongArray = ulongArrayOf(0x3FFFFFFFFFFFFFuL, 0xFFFFFF0000003FFFuL, 0x7FFFFFC00000001uL, 0x5555555555555401uL, 0x20000435555AF5uL, 0xFFFFFFFF00000000uL, 0xFFFFFF00000003FFuL, 0xFFFFF0000001FC5FuL, 0xFFFF03FFFFFF03FFuL, 0xFFFF000000000000uL, 0xFFFE555555555555uL, 0x8045000000000000uL, 0x7B0000001FDFE7B0uL, 0x6C05555555555555uL, 0x6A00555555555555uL, 0x55D255554AAAA490uL, 0x555F7D5555452855uL, 0x5555555555555555uL, 0x5555555555552AABuL, 0x5555555500000000uL, 0x5554555400000000uL, 0x5554550000022501uL, 0x2B555555555554AAuL, 0x11AED2D5B1DBCED6uL, 0xF001F000F000F00uL, 0xF00000000000000uL, 0x3FFFFFF0000003FuL, 0x3FDE64D0000003uL, 0x4280555555555uL, 0xFFFF00000020uL, 0xFF00AA003F00uL, 0x300000000000uL, 0xFFBFFFED740uL, 0x3FFFFF0000uL, 0x7F7FFFFFuL, 0x37F7FFuL, 0x557AuL, 0x20BFuL, 0xAA55555555555555uL, 0xC00F3D503E273884uL, 0xC025EA9D00000000uL, 0xE6905555551C8000uL, 0xE7FFFFFFFFFF0200uL, 0xF0000003FFFFFF00uL, 0xF7FF000000000000uL, 0xFF00FF003F00FF00uL)

public var BITSET_MAPPING: List<Pair<UByte, UByte>> = listOf(Pair(0, 182), Pair(0, 74), Pair(0, 166), Pair(0, 162), Pair(0, 159), Pair(0, 150), Pair(0, 148), Pair(0, 142), Pair(0, 134), Pair(0, 131), Pair(0, 64), Pair(1, 66), Pair(1, 70), Pair(1, 83), Pair(1, 12), Pair(1, 8), Pair(2, 146), Pair(2, 140), Pair(2, 134), Pair(2, 130), Pair(3, 164), Pair(3, 146), Pair(3, 20), Pair(4, 178), Pair(4, 171))

public fun lookup(c: Char): Boolean {
    if (false) {
        check(!c.isAscii())
    }
    return (c.toUInt()) >= 0xc0u && bitsetSearch(c.toUInt(), BITSET_CHUNKS_MAP, BITSET_INDEX_CHUNKS, BITSET_CANONICAL, BITSET_MAPPING)
}
