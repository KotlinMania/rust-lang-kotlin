@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86RdtscRdtsc
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun rdtsc(): ULong =
    rustlangCorearchX86RdtscRdtsc()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
