@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object X8664Bmi2JniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Bzhi64(x: Long, y: Long): Long

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Pdep64(x: Long, y: Long): Long

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Pext64(x: Long, y: Long): Long
}

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Bzhi64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Pdep64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Pext64(x.toLong(), y.toLong()).toULong()
