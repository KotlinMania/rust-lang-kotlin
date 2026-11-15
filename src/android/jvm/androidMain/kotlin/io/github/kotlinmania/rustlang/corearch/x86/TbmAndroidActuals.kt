@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object CorearchX86TbmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86TbmBextriU32(a: Int, control: Int): Int
}

public actual fun bextriU32(a: UInt, control: UInt): UInt =
    CorearchX86TbmJniBridge.rustlangCorearchX86TbmBextriU32(a.toInt(), control.toInt()).toUInt()
