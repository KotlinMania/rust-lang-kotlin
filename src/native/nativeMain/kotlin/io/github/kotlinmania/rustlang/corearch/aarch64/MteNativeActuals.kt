@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteAddg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteGmi
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteIrg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteLdg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteStg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.rustlangCorearchAarch64MteSubp
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    ConstPtr(rustlangCorearchAarch64MteIrg(ptr.rawAddress.toCPointer<ByteVar>(), exclude)?.toLong() ?: 0L)

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    rustlangCorearchAarch64MteGmi(ptr.rawAddress.toCPointer<ByteVar>(), exclude)

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    ConstPtr(rustlangCorearchAarch64MteLdg(ptr.rawAddress.toCPointer<ByteVar>(), tagPtr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    rustlangCorearchAarch64MteStg(taggedPtr.rawAddress.toCPointer<ByteVar>(), addrToTag.rawAddress.toCPointer<ByteVar>())
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    ConstPtr(rustlangCorearchAarch64MteAddg(ptr.rawAddress.toCPointer<ByteVar>(), value)?.toLong() ?: 0L)

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    rustlangCorearchAarch64MteSubp(ptrA.rawAddress.toCPointer<ByteVar>(), ptrB.rawAddress.toCPointer<ByteVar>())
