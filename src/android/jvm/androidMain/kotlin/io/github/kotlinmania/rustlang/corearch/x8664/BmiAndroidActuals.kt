@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object CorearchX8664BmiJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664BmiX86BmiBextr64(x: Long, y: Long): Long
}

public actual fun x86BmiBextr64(x: ULong, y: ULong): ULong =
    CorearchX8664BmiJniBridge.rustlangCorearchX8664BmiX86BmiBextr64(x.toLong(), y.toLong()).toULong()
