@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object CorearchX8664MovrsJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664MovrsMovrsqi(src: Long): Byte

    @JvmStatic
    external fun rustlangCorearchX8664MovrsMovrshi(src: Long): Short

    @JvmStatic
    external fun rustlangCorearchX8664MovrsMovrssi(src: Long): Int

    @JvmStatic
    external fun rustlangCorearchX8664MovrsMovrsdi(src: Long): Long
}

public actual fun movrsqi(src: ConstPtr): Byte =
    CorearchX8664MovrsJniBridge.rustlangCorearchX8664MovrsMovrsqi(src.rawAddress)

public actual fun movrshi(src: ConstPtr): Short =
    CorearchX8664MovrsJniBridge.rustlangCorearchX8664MovrsMovrshi(src.rawAddress)

public actual fun movrssi(src: ConstPtr): Int =
    CorearchX8664MovrsJniBridge.rustlangCorearchX8664MovrsMovrssi(src.rawAddress)

public actual fun movrsdi(src: ConstPtr): Long =
    CorearchX8664MovrsJniBridge.rustlangCorearchX8664MovrsMovrsdi(src.rawAddress)
