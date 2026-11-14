@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object X8664MovrsJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664MovrsMovrsqi(src: Long): Byte

    @JvmStatic
    external fun corearchX8664MovrsMovrshi(src: Long): Short

    @JvmStatic
    external fun corearchX8664MovrsMovrssi(src: Long): Int

    @JvmStatic
    external fun corearchX8664MovrsMovrsdi(src: Long): Long
}

public actual fun movrsqi(src: ConstPtr): Byte =
    X8664MovrsJniBridge.corearchX8664MovrsMovrsqi(src.rawAddress)

public actual fun movrshi(src: ConstPtr): Short =
    X8664MovrsJniBridge.corearchX8664MovrsMovrshi(src.rawAddress)

public actual fun movrssi(src: ConstPtr): Int =
    X8664MovrsJniBridge.corearchX8664MovrsMovrssi(src.rawAddress)

public actual fun movrsdi(src: ConstPtr): Long =
    X8664MovrsJniBridge.corearchX8664MovrsMovrsdi(src.rawAddress)
