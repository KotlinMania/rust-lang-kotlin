@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object CorearchX86RdtscJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86RdtscRdtsc(): Long
}

public actual fun rdtsc(): ULong =
    CorearchX86RdtscJniBridge.rustlangCorearchX86RdtscRdtsc().toULong()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
