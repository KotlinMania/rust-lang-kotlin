@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object X8664BmiJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664BmiX86BmiBextr64(x: Long, y: Long): Long
}

public actual fun x86BmiBextr64(x: ULong, y: ULong): ULong =
    X8664BmiJniBridge.corearchX8664BmiX86BmiBextr64(x.toLong(), y.toLong()).toULong()
