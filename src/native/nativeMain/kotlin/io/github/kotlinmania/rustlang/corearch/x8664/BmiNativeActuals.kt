@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664BmiX86BmiBextr64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun x86BmiBextr64(x: ULong, y: ULong): ULong =
    rustlangCorearchX8664BmiX86BmiBextr64(x, y)
