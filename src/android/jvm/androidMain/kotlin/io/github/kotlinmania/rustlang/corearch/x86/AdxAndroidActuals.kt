@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

public actual fun llvmAddcarryU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    Pair<UByte, UInt>(0u, 0u)

public actual fun llvmSubborrowU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt> =
    Pair<UByte, UInt>(0u, 0u)
