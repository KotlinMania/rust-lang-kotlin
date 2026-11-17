@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86TbmExternBextriU32
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externBextriU32(a: UInt, control: UInt): UInt =
    corearchX86TbmExternBextriU32(a, control)
