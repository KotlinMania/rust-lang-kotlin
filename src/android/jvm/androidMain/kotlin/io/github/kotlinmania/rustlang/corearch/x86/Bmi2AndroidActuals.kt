@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object CorearchX86Bmi2JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86Bmi2X86Bmi2Bzhi32(x: Int, y: Int): Int

    @JvmStatic
    external fun rustlangCorearchX86Bmi2X86Bmi2Pdep32(x: Int, y: Int): Int

    @JvmStatic
    external fun rustlangCorearchX86Bmi2X86Bmi2Pext32(x: Int, y: Int): Int
}

public actual fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt =
    CorearchX86Bmi2JniBridge.rustlangCorearchX86Bmi2X86Bmi2Bzhi32(x.toInt(), y.toInt()).toUInt()

public actual fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt =
    CorearchX86Bmi2JniBridge.rustlangCorearchX86Bmi2X86Bmi2Pdep32(x.toInt(), y.toInt()).toUInt()

public actual fun x86Bmi2Pext32(x: UInt, y: UInt): UInt =
    CorearchX86Bmi2JniBridge.rustlangCorearchX86Bmi2X86Bmi2Pext32(x.toInt(), y.toInt()).toUInt()
