@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object X8664TbmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX8664TbmExternBextriU64(a: Long, control: Long): Long
}

public actual fun externBextriU64(a: ULong, control: ULong): ULong =
    X8664TbmJniBridge.corearchX8664TbmExternBextriU64(a.toLong(), control.toLong()).toULong()
