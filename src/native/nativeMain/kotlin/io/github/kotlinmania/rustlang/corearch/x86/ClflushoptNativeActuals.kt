@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86ClflushoptClflushopt
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun clflushopt(p: ConstPtr) {
    rustlangCorearchX86ClflushoptClflushopt(p.rawAddress.toCPointer<ByteVar>())
}
