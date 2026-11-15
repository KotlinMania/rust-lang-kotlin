@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vpdpwssd(src: I32x16, a: I16x32, b: I16x32): I32x16 =
    null as I32x16

public actual fun vpdpwssd256(src: I32x8, a: I16x16, b: I16x16): I32x8 =
    null as I32x8

public actual fun vpdpwssd128(src: I32x4, a: I16x8, b: I16x8): I32x4 =
    null as I32x4

public actual fun vpdpwssds(src: I32x16, a: I16x32, b: I16x32): I32x16 =
    null as I32x16

public actual fun vpdpwssds256(src: I32x8, a: I16x16, b: I16x16): I32x8 =
    null as I32x8

public actual fun vpdpwssds128(src: I32x4, a: I16x8, b: I16x8): I32x4 =
    null as I32x4

public actual fun vpdpbusd(src: I32x16, a: U8x64, b: I8x64): I32x16 =
    null as I32x16

public actual fun vpdpbusd256(src: I32x8, a: U8x32, b: I8x32): I32x8 =
    null as I32x8

public actual fun vpdpbusd128(src: I32x4, a: U8x16, b: I8x16): I32x4 =
    null as I32x4

public actual fun vpdpbusds(src: I32x16, a: U8x64, b: I8x64): I32x16 =
    null as I32x16

public actual fun vpdpbusds256(src: I32x8, a: U8x32, b: I8x32): I32x8 =
    null as I32x8

public actual fun vpdpbusds128(src: I32x4, a: U8x16, b: I8x16): I32x4 =
    null as I32x4

public actual fun vpdpbssd128(src: I32x4, a: I8x16, b: I8x16): I32x4 =
    null as I32x4

public actual fun vpdpbssd256(src: I32x8, a: I8x32, b: I8x32): I32x8 =
    null as I32x8

public actual fun vpdpbssds128(src: I32x4, a: I8x16, b: I8x16): I32x4 =
    null as I32x4

public actual fun vpdpbssds256(src: I32x8, a: I8x32, b: I8x32): I32x8 =
    null as I32x8

public actual fun vpdpbsud128(src: I32x4, a: I8x16, b: U8x16): I32x4 =
    null as I32x4

public actual fun vpdpbsud256(src: I32x8, a: I8x32, b: U8x32): I32x8 =
    null as I32x8

public actual fun vpdpbsuds128(src: I32x4, a: I8x16, b: U8x16): I32x4 =
    null as I32x4

public actual fun vpdpbsuds256(src: I32x8, a: I8x32, b: U8x32): I32x8 =
    null as I32x8

public actual fun vpdpbuud128(src: I32x4, a: U8x16, b: U8x16): I32x4 =
    null as I32x4

public actual fun vpdpbuud256(src: I32x8, a: U8x32, b: U8x32): I32x8 =
    null as I32x8

public actual fun vpdpbuuds128(src: I32x4, a: U8x16, b: U8x16): I32x4 =
    null as I32x4

public actual fun vpdpbuuds256(src: I32x8, a: U8x32, b: U8x32): I32x8 =
    null as I32x8

public actual fun vpdpwsud128(src: I32x4, a: I16x8, b: U16x8): I32x4 =
    null as I32x4

public actual fun vpdpwsud256(src: I32x8, a: I16x16, b: U16x16): I32x8 =
    null as I32x8

public actual fun vpdpwsuds128(src: I32x4, a: I16x8, b: U16x8): I32x4 =
    null as I32x4

public actual fun vpdpwsuds256(src: I32x8, a: I16x16, b: U16x16): I32x8 =
    null as I32x8

public actual fun vpdpwusd128(src: I32x4, a: U16x8, b: I16x8): I32x4 =
    null as I32x4

public actual fun vpdpwusd256(src: I32x8, a: U16x16, b: I16x16): I32x8 =
    null as I32x8

public actual fun vpdpwusds128(src: I32x4, a: U16x8, b: I16x8): I32x4 =
    null as I32x4

public actual fun vpdpwusds256(src: I32x8, a: U16x16, b: I16x16): I32x8 =
    null as I32x8

public actual fun vpdpwuud128(src: I32x4, a: U16x8, b: U16x8): I32x4 =
    null as I32x4

public actual fun vpdpwuud256(src: I32x8, a: U16x16, b: U16x16): I32x8 =
    null as I32x8

public actual fun vpdpwuuds128(src: I32x4, a: U16x8, b: U16x8): I32x4 =
    null as I32x4

public actual fun vpdpwuuds256(src: I32x8, a: U16x16, b: U16x16): I32x8 =
    null as I32x8
