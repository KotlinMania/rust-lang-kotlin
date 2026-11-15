@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.I16x16
import io.github.kotlinmania.rustlang.simd.I16x32
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.simd.I32x16
import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.I8x64
import io.github.kotlinmania.rustlang.simd.U16x16
import io.github.kotlinmania.rustlang.simd.U16x32
import io.github.kotlinmania.rustlang.simd.U16x8
import io.github.kotlinmania.rustlang.simd.U64x8
import io.github.kotlinmania.rustlang.simd.U8x16
import io.github.kotlinmania.rustlang.simd.U8x32
import io.github.kotlinmania.rustlang.simd.U8x64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vpmulhrsw(a: I16x32, b: I16x32): I16x32 =
    null as I16x32

public actual fun vpmulhuw(a: U16x32, b: U16x32): U16x32 =
    null as U16x32

public actual fun vpmulhw(a: I16x32, b: I16x32): I16x32 =
    null as I16x32

public actual fun vpmaddwd(a: I16x32, b: I16x32): I32x16 =
    null as I32x16

public actual fun vpmaddubsw(a: U8x64, b: I8x64): I16x32 =
    null as I16x32

public actual fun vpackssdw(a: I32x16, b: I32x16): I16x32 =
    null as I16x32

public actual fun vpacksswb(a: I16x32, b: I16x32): I8x64 =
    null as I8x64

public actual fun vpackusdw(a: I32x16, b: I32x16): U16x32 =
    null as U16x32

public actual fun vpackuswb(a: I16x32, b: I16x32): U8x64 =
    null as U8x64

public actual fun vpsllw(a: I16x32, count: I16x8): I16x32 =
    null as I16x32

public actual fun vpsllvw(a: I16x32, b: I16x32): I16x32 =
    null as I16x32

public actual fun vpsllvw256(a: I16x16, b: I16x16): I16x16 =
    null as I16x16

public actual fun vpsllvw128(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vpsrlw(a: I16x32, count: I16x8): I16x32 =
    null as I16x32

public actual fun vpsrlvw(a: I16x32, b: I16x32): I16x32 =
    null as I16x32

public actual fun vpsrlvw256(a: I16x16, b: I16x16): I16x16 =
    null as I16x16

public actual fun vpsrlvw128(a: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vpsraw(a: I16x32, count: I16x8): I16x32 =
    null as I16x32

public actual fun vpsravw(a: I16x32, count: I16x32): I16x32 =
    null as I16x32

public actual fun vpsravw256(a: I16x16, count: I16x16): I16x16 =
    null as I16x16

public actual fun vpsravw128(a: I16x8, count: I16x8): I16x8 =
    null as I16x8

public actual fun vpermi2w(a: I16x32, idx: I16x32, b: I16x32): I16x32 =
    null as I16x32

public actual fun vpermi2w256(a: I16x16, idx: I16x16, b: I16x16): I16x16 =
    null as I16x16

public actual fun vpermi2w128(a: I16x8, idx: I16x8, b: I16x8): I16x8 =
    null as I16x8

public actual fun vpermw(a: I16x32, idx: I16x32): I16x32 =
    null as I16x32

public actual fun vpermw256(a: I16x16, idx: I16x16): I16x16 =
    null as I16x16

public actual fun vpermw128(a: I16x8, idx: I16x8): I16x8 =
    null as I16x8

public actual fun vpshufb(a: I8x64, b: I8x64): I8x64 =
    null as I8x64

public actual fun vpsadbw(a: U8x64, b: U8x64): U64x8 =
    null as U64x8

public actual fun vdbpsadbw(a: U8x64, b: U8x64, imm8: Int): U16x32 =
    null as U16x32

public actual fun vdbpsadbw256(a: U8x32, b: U8x32, imm8: Int): U16x16 =
    null as U16x16

public actual fun vdbpsadbw128(a: U8x16, b: U8x16, imm8: Int): U16x8 =
    null as U16x8

public actual fun vpmovswb128(a: I16x8, src: I8x16, mask: UByte): I8x16 =
    null as I8x16

public actual fun vpmovuswb128(a: U16x8, src: U8x16, mask: UByte): U8x16 =
    null as U8x16

public actual fun vpmovswbmem(memAddr: MutPtr, a: I16x32, mask: UInt) {
}

public actual fun vpmovuswbmem(memAddr: MutPtr, a: I16x32, mask: UInt) {
}

public actual fun vpavgb(a: U8x64, b: U8x64): U8x64 =
    null as U8x64

public actual fun vpavgw(a: U16x32, b: U16x32): U16x32 =
    null as U16x32
