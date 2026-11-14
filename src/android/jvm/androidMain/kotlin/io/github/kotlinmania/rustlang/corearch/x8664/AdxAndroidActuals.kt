@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

public actual fun llvmAddcarryU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    Pair<UByte, ULong>(0u, 0uL)

public actual fun llvmSubborrowU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong> =
    Pair<UByte, ULong>(0u, 0uL)
