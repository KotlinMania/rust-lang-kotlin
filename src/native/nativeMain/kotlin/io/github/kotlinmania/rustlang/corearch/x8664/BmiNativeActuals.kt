@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664BmiX86BmiBextr64

public actual fun x86BmiBextr64(x: ULong, y: ULong): ULong =
    corearchX8664BmiX86BmiBextr64(x, y)
