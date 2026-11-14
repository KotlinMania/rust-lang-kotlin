@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

public actual fun x86Rdrand64Step(): Pair<ULong, Int> =
    Pair<ULong, Int>(0uL, 0)

public actual fun x86Rdseed64Step(): Pair<ULong, Int> =
    Pair<ULong, Int>(0uL, 0)
