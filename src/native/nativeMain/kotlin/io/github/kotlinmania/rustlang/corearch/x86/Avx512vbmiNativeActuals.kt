@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

public actual fun vpermi2b(a: I8x64, idx: I8x64, b: I8x64): I8x64 =
    null as I8x64

public actual fun vpermi2b256(a: I8x32, idx: I8x32, b: I8x32): I8x32 =
    null as I8x32

public actual fun vpermi2b128(a: I8x16, idx: I8x16, b: I8x16): I8x16 =
    null as I8x16

public actual fun vpermb(a: I8x64, idx: I8x64): I8x64 =
    null as I8x64

public actual fun vpermb256(a: I8x32, idx: I8x32): I8x32 =
    null as I8x32

public actual fun vpermb128(a: I8x16, idx: I8x16): I8x16 =
    null as I8x16

public actual fun vpmultishiftqb(a: I8x64, b: I8x64): I8x64 =
    null as I8x64

public actual fun vpmultishiftqb256(a: I8x32, b: I8x32): I8x32 =
    null as I8x32

public actual fun vpmultishiftqb128(a: I8x16, b: I8x16): I8x16 =
    null as I8x16
