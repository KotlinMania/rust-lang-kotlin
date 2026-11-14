@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86Bmi2JniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86Bmi2X86Bmi2Bzhi32(x: Int, y: Int): Int

    @JvmStatic
    external fun corearchX86Bmi2X86Bmi2Pdep32(x: Int, y: Int): Int

    @JvmStatic
    external fun corearchX86Bmi2X86Bmi2Pext32(x: Int, y: Int): Int
}

public actual fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt =
    X86Bmi2JniBridge.corearchX86Bmi2X86Bmi2Bzhi32(x.toInt(), y.toInt()).toUInt()

public actual fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt =
    X86Bmi2JniBridge.corearchX86Bmi2X86Bmi2Pdep32(x.toInt(), y.toInt()).toUInt()

public actual fun x86Bmi2Pext32(x: UInt, y: UInt): UInt =
    X86Bmi2JniBridge.corearchX86Bmi2X86Bmi2Pext32(x.toInt(), y.toInt()).toUInt()
