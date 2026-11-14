@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664TbmBextriU64

public actual fun bextriU64(a: ULong, control: ULong): ULong =
    corearchX8664TbmBextriU64(a, control)
