@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object CorearchX8664Sse42JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664Sse42Crc326464(crc: Long, v: Long): Long
}

public actual fun crc326464(crc: ULong, v: ULong): ULong =
    CorearchX8664Sse42JniBridge.rustlangCorearchX8664Sse42Crc326464(crc.toLong(), v.toLong()).toULong()
