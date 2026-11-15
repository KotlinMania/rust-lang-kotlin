@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664Sse42Crc326464
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun crc326464(crc: ULong, v: ULong): ULong =
    rustlangCorearchX8664Sse42Crc326464(crc, v)
