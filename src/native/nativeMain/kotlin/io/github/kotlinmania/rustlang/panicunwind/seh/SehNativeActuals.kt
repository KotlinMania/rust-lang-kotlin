@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.panicunwind.seh

import io.github.kotlinmania.rustlang.panicunwind.seh.cinterop.panicunwindSehGetTYPEINFOVTABLE

public actual val TYPE_INFO_VTABLE: UByte
    get() = panicunwindSehGetTYPEINFOVTABLE()
