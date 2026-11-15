@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.x86.M128h
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun vcvtsi642sh(a: M128h, b: Long, rounding: Int): M128h =
    M128h()

public actual fun vcvtusi642sh(a: M128h, b: ULong, rounding: Int): M128h =
    M128h()

public actual fun vcvtsh2si64(a: M128h, rounding: Int): Long =
    0L

public actual fun vcvtsh2usi64(a: M128h, rounding: Int): ULong =
    0uL

public actual fun vcvttsh2si64(a: M128h, sae: Int): Long =
    0L

public actual fun vcvttsh2usi64(a: M128h, sae: Int): ULong =
    0uL
