@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object CorearchX8664Bmi2JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664Bmi2X86Bmi2Bzhi64(x: Long, y: Long): Long

    @JvmStatic
    external fun rustlangCorearchX8664Bmi2X86Bmi2Pdep64(x: Long, y: Long): Long

    @JvmStatic
    external fun rustlangCorearchX8664Bmi2X86Bmi2Pext64(x: Long, y: Long): Long
}

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    CorearchX8664Bmi2JniBridge.rustlangCorearchX8664Bmi2X86Bmi2Bzhi64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    CorearchX8664Bmi2JniBridge.rustlangCorearchX8664Bmi2X86Bmi2Pdep64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    CorearchX8664Bmi2JniBridge.rustlangCorearchX8664Bmi2X86Bmi2Pext64(x.toLong(), y.toLong()).toULong()
