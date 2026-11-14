package io.github.kotlinmania.rustlang.libm.math

public fun getHighWord(x: Double): UInt = (x.toRawBits().toULong() shr 32).toUInt()

public fun getLowWord(x: Double): UInt = x.toRawBits().toULong().toUInt()

public fun withSetHighWord(f: Double, hi: UInt): Double {
    var tmp = f.toRawBits().toULong()
    tmp = tmp and 0x00000000ffffffffuL
    tmp = tmp or ((hi.toULong()) shl 32)
    return Double.fromBits(tmp.toLong())
}

public fun withSetLowWord(f: Double, lo: UInt): Double {
    var tmp = f.toRawBits().toULong()
    tmp = tmp and 0xffffffff00000000uL
    tmp = tmp or (lo.toULong())
    return Double.fromBits(tmp.toLong())
}

public fun combineWords(hi: UInt, lo: UInt): Double = Double.fromBits((((hi.toULong()) shl 32) or lo.toULong()).toLong())
