@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664TbmExternBextriU64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externBextriU64(a: ULong, control: ULong): ULong =
    corearchX8664TbmExternBextriU64(a, control)
