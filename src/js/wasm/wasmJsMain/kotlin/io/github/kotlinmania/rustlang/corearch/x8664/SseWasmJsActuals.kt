@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128

public actual fun cvtss2si64(a: M128): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttss2si64(a: M128): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")
