@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.time

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.time.cinterop.libcNewHelenosTimeGetuptime
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun getuptime(tp: MutPtr) {
    libcNewHelenosTimeGetuptime(tp.rawAddress.toCPointer<ByteVar>())
}
