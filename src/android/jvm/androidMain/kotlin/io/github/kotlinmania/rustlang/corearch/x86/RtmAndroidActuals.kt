@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

private object X86RtmJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX86RtmX86Xbegin(): Int

    @JvmStatic
    external fun corearchX86RtmX86Xend()

    @JvmStatic
    external fun corearchX86RtmX86Xabort(imm8: Byte)

    @JvmStatic
    external fun corearchX86RtmX86Xtest(): Int
}

public actual fun x86Xbegin(): Int =
    X86RtmJniBridge.corearchX86RtmX86Xbegin()

public actual fun x86Xend() {
    X86RtmJniBridge.corearchX86RtmX86Xend()
}

public actual fun x86Xabort(imm8: Byte) {
    X86RtmJniBridge.corearchX86RtmX86Xabort(imm8)
}

public actual fun x86Xtest(): Int =
    X86RtmJniBridge.corearchX86RtmX86Xtest()
