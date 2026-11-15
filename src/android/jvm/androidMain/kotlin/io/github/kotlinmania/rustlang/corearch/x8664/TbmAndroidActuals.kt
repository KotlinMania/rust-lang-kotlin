@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object CorearchX8664TbmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664TbmBextriU64(a: Long, control: Long): Long
}

public actual fun bextriU64(a: ULong, control: ULong): ULong =
    CorearchX8664TbmJniBridge.rustlangCorearchX8664TbmBextriU64(a.toLong(), control.toLong()).toULong()
