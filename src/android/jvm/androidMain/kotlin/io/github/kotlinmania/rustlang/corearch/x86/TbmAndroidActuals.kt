@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86TbmJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86TbmBextriU32(a: Int, control: Int): Int
}

public actual fun bextriU32(a: UInt, control: UInt): UInt =
    X86TbmJniBridge.corearchX86TbmBextriU32(a.toInt(), control.toInt()).toUInt()
