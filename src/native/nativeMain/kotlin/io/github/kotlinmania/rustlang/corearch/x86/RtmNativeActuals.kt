@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RtmX86Xabort
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RtmX86Xbegin
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RtmX86Xend
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86RtmX86Xtest

public actual fun x86Xbegin(): Int =
    corearchX86RtmX86Xbegin()

public actual fun x86Xend() {
    corearchX86RtmX86Xend()
}

public actual fun x86Xabort(imm8: Byte) {
    corearchX86RtmX86Xabort(imm8)
}

public actual fun x86Xtest(): Int =
    corearchX86RtmX86Xtest()
