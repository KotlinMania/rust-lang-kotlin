@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.simd.I8x16
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun lddqu(memAddr: ConstPtr): I8x16 =
    null as I8x16
