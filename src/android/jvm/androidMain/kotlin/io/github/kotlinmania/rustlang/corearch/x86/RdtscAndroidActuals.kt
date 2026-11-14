@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86RdtscJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86RdtscRdtsc(): Long
}

public actual fun rdtsc(): ULong =
    X86RdtscJniBridge.corearchX86RdtscRdtsc().toULong()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
