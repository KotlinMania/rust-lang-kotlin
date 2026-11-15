@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object CorearchX86RtmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86RtmX86Xbegin(): Int

    @JvmStatic
    external fun rustlangCorearchX86RtmX86Xend()

    @JvmStatic
    external fun rustlangCorearchX86RtmX86Xabort(imm8: Byte)

    @JvmStatic
    external fun rustlangCorearchX86RtmX86Xtest(): Int
}

public actual fun x86Xbegin(): Int =
    CorearchX86RtmJniBridge.rustlangCorearchX86RtmX86Xbegin()

public actual fun x86Xend() {
    CorearchX86RtmJniBridge.rustlangCorearchX86RtmX86Xend()
}

public actual fun x86Xabort(imm8: Byte) {
    CorearchX86RtmJniBridge.rustlangCorearchX86RtmX86Xabort(imm8)
}

public actual fun x86Xtest(): Int =
    CorearchX86RtmJniBridge.rustlangCorearchX86RtmX86Xtest()
