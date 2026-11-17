@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86RdtscJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86RdtscExternRdtsc(): Long
}

public actual fun externRdtsc(): ULong =
    X86RdtscJniBridge.corearchX86RdtscExternRdtsc().toULong()

public actual fun rdtscp(): Pair<ULong, UInt> =
    Pair<ULong, UInt>(0uL, 0u)
