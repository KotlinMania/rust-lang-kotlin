@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RdtscExternRdtsc
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externRdtsc(): ULong =
    corearchX86RdtscExternRdtsc()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
