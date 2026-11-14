@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86ClflushoptClflushopt
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun clflushopt(p: ConstPtr) {
    corearchX86ClflushoptClflushopt(p.rawAddress.toCPointer<ByteVar>())
}
