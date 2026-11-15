@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664Bmi2X86Bmi2Bzhi64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664Bmi2X86Bmi2Pdep64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664Bmi2X86Bmi2Pext64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    rustlangCorearchX8664Bmi2X86Bmi2Bzhi64(x, y)

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    rustlangCorearchX8664Bmi2X86Bmi2Pdep64(x, y)

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    rustlangCorearchX8664Bmi2X86Bmi2Pext64(x, y)
