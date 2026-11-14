@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86TbmBextriU32

public actual fun bextriU32(a: UInt, control: UInt): UInt =
    corearchX86TbmBextriU32(a, control)
