@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Bmi2X86Bmi2Bzhi32
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Bmi2X86Bmi2Pdep32
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86Bmi2X86Bmi2Pext32

public actual fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt =
    corearchX86Bmi2X86Bmi2Bzhi32(x, y)

public actual fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt =
    corearchX86Bmi2X86Bmi2Pdep32(x, y)

public actual fun x86Bmi2Pext32(x: UInt, y: UInt): UInt =
    corearchX86Bmi2X86Bmi2Pext32(x, y)
