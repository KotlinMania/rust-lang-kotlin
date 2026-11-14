@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse42Crc323216
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse42Crc323232
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Sse42Crc32328

public actual fun pcmpestrm128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): U8x16 =
    null as U8x16

public actual fun pcmpestri128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestriz128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestric128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestris128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestrio128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpestria128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    0

public actual fun pcmpistrm128(a: I8x16, b: I8x16, imm8: Byte): I8x16 =
    null as I8x16

public actual fun pcmpistri128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistriz128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistric128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistris128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistrio128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun pcmpistria128(a: I8x16, b: I8x16, imm8: Byte): Int =
    0

public actual fun crc32328(crc: UInt, v: UByte): UInt =
    corearchX86Sse42Crc32328(crc, v)

public actual fun crc323216(crc: UInt, v: UShort): UInt =
    corearchX86Sse42Crc323216(crc, v)

public actual fun crc323232(crc: UInt, v: UInt): UInt =
    corearchX86Sse42Crc323232(crc, v)
