package io.github.kotlinmania.rustlang.std.sys.random

import io.github.kotlinmania.rustlang.std.sys.random.linux.fillBytes

public fun hashmapRandomKeys(): Pair<ULong, ULong> {
    val buf = MutableList(16) { 0 }
    fillBytes(buf)
    val k1 = run { val bytesTmp0 = buf[0 until 8]; ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }
    val k2 = run { val bytesTmp1 = buf[8 until buf.size]; ((bytesTmp1[7].toULong() shl 56) or (bytesTmp1[6].toULong() shl 48) or (bytesTmp1[5].toULong() shl 40) or (bytesTmp1[4].toULong() shl 32) or (bytesTmp1[3].toULong() shl 24) or (bytesTmp1[2].toULong() shl 16) or (bytesTmp1[1].toULong() shl 8) or bytesTmp1[0].toULong()) }
    return Pair(k1, k2)
}
