@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

import io.github.kotlinmania.rustlang.panicunwind.seh.cinterop.panicunwindSehGetTYPEINFOVTABLE
import kotlinx.cinterop.ExperimentalForeignApi

public actual val TYPE_INFO_VTABLE: UByte
    get() = panicunwindSehGetTYPEINFOVTABLE()
