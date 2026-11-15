@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.I8x32
import io.github.kotlinmania.rustlang.simd.I8x64
import io.github.kotlinmania.rustlang.x86.Mmask16
import io.github.kotlinmania.rustlang.x86.Mmask32
import io.github.kotlinmania.rustlang.x86.Mmask64

public actual fun bitshuffle512(data: I8x64, indices: I8x64): Mmask64 =
    0uL

public actual fun bitshuffle256(data: I8x32, indices: I8x32): Mmask32 =
    0u

public actual fun bitshuffle128(data: I8x16, indices: I8x16): Mmask16 =
    0u
