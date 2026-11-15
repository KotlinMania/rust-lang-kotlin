@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.loongarch64.lsx.M128d

public actual fun cvtsd2si64(a: M128d): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttsd2si64(a: M128d): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")
