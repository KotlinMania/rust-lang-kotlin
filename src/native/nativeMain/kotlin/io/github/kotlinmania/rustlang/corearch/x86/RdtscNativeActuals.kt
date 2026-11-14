@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RdtscRdtsc

public actual fun rdtsc(): ULong =
    corearchX86RdtscRdtsc()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
