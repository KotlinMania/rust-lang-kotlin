@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86TbmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86TbmExternBextriU32(a: Int, control: Int): Int
}

public actual fun externBextriU32(a: UInt, control: UInt): UInt =
    X86TbmJniBridge.corearchX86TbmExternBextriU32(a.toInt(), control.toInt()).toUInt()
