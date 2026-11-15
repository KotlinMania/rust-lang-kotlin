@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I32x4
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.U16x8
import io.github.kotlinmania.rustlang.simd.U8x16
import io.github.kotlinmania.rustlang.x86.M128
import io.github.kotlinmania.rustlang.x86.M128d
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun insertps(a: M128, b: M128, imm8: UByte): M128 =
    M128()

public actual fun packusdw(a: I32x4, b: I32x4): U16x8 =
    null as U16x8

public actual fun dppd(a: M128d, b: M128d, imm8: UByte): M128d =
    M128d()

public actual fun dpps(a: M128, b: M128, imm8: UByte): M128 =
    M128()

public actual fun roundpd(a: M128d, rounding: Int): M128d =
    M128d()

public actual fun roundps(a: M128, rounding: Int): M128 =
    M128()

public actual fun roundsd(a: M128d, b: M128d, rounding: Int): M128d =
    M128d()

public actual fun roundss(a: M128, b: M128, rounding: Int): M128 =
    M128()

public actual fun phminposuw(a: U16x8): U16x8 =
    null as U16x8

public actual fun mpsadbw(a: U8x16, b: U8x16, imm8: UByte): U16x8 =
    null as U16x8

public actual fun ptestnzc(a: I64x2, mask: I64x2): Int =
    0
