@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86MovrsPrefetchrs
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun prefetchrs(p: ConstPtr) {
    corearchX86MovrsPrefetchrs(p.rawAddress.toCPointer<ByteVar>())
}
