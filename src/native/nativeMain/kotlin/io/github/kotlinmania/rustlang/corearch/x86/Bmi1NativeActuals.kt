@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86Bmi1X86BmiBextr32
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun x86BmiBextr32(x: UInt, y: UInt): UInt =
    rustlangCorearchX86Bmi1X86BmiBextr32(x, y)
