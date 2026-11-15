@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object CorearchX86Bmi1JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86Bmi1X86BmiBextr32(x: Int, y: Int): Int
}

public actual fun x86BmiBextr32(x: UInt, y: UInt): UInt =
    CorearchX86Bmi1JniBridge.rustlangCorearchX86Bmi1X86BmiBextr32(x.toInt(), y.toInt()).toUInt()
