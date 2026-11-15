@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86RtmX86Xabort
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86RtmX86Xbegin
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86RtmX86Xend
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86RtmX86Xtest
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun x86Xbegin(): Int =
    rustlangCorearchX86RtmX86Xbegin()

public actual fun x86Xend() {
    rustlangCorearchX86RtmX86Xend()
}

public actual fun x86Xabort(imm8: Byte) {
    rustlangCorearchX86RtmX86Xabort(imm8)
}

public actual fun x86Xtest(): Int =
    rustlangCorearchX86RtmX86Xtest()
