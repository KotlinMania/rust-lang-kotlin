@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object X8664Sse42JniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664Sse42Crc326464(crc: Long, v: Long): Long
}

public actual fun crc326464(crc: ULong, v: ULong): ULong =
    X8664Sse42JniBridge.corearchX8664Sse42Crc326464(crc.toLong(), v.toLong()).toULong()
