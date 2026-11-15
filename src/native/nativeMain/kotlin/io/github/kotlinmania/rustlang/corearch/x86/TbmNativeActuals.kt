@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86TbmBextriU32
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun bextriU32(a: UInt, control: UInt): UInt =
    rustlangCorearchX86TbmBextriU32(a, control)
