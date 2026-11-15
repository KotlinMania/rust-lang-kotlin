@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86Bmi1JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86Bmi1X86BmiBextr32(x: Int, y: Int): Int
}

public actual fun x86BmiBextr32(x: UInt, y: UInt): UInt =
    X86Bmi1JniBridge.corearchX86Bmi1X86BmiBextr32(x.toInt(), y.toInt()).toUInt()
