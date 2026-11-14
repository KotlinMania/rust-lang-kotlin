@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664Sse42Crc326464

public actual fun crc326464(crc: ULong, v: ULong): ULong =
    corearchX8664Sse42Crc326464(crc, v)
