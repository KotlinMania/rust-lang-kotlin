@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.simd.I8x16

public actual fun lddqu(memAddr: ConstPtr): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")
