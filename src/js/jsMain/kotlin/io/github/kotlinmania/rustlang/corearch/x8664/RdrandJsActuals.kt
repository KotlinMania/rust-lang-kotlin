@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

public actual fun x86Rdrand64Step(): Pair<ULong, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Rdseed64Step(): Pair<ULong, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")
