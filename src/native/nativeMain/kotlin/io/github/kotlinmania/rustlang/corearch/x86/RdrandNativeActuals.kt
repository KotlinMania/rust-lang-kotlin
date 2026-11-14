@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import kotlinx.cinterop.ExperimentalForeignApi

public actual fun x86Rdrand16Step(): Pair<UShort, Int> =
    Pair<UShort, Int>(0u, 0)

public actual fun x86Rdrand32Step(): Pair<UInt, Int> =
    Pair<UInt, Int>(0u, 0)

public actual fun x86Rdseed16Step(): Pair<UShort, Int> =
    Pair<UShort, Int>(0u, 0)

public actual fun x86Rdseed32Step(): Pair<UInt, Int> =
    Pair<UInt, Int>(0u, 0)
