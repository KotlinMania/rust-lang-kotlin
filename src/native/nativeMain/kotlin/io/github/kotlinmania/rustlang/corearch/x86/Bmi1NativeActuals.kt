@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Bmi1X86BmiBextr32

public actual fun x86BmiBextr32(x: UInt, y: UInt): UInt =
    corearchX86Bmi1X86BmiBextr32(x, y)
