@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64

public actual fun vgf2p8affineinvqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8affineinvqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8affineinvqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8affineqb512(x: I8x64, a: I8x64, imm8: UByte): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8affineqb256(x: I8x32, a: I8x32, imm8: UByte): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8affineqb128(x: I8x16, a: I8x16, imm8: UByte): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8mulb512(a: I8x64, b: I8x64): I8x64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8mulb256(a: I8x32, b: I8x32): I8x32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgf2p8mulb128(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")
