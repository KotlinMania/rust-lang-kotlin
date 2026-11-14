@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteAddg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteGmi
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteIrg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteLdg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteStg
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64MteSubp
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    ConstPtr(corearchAarch64MteIrg(ptr.rawAddress.toCPointer<ByteVar>(), exclude)?.toLong() ?: 0L)

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    corearchAarch64MteGmi(ptr.rawAddress.toCPointer<ByteVar>(), exclude)

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    ConstPtr(corearchAarch64MteLdg(ptr.rawAddress.toCPointer<ByteVar>(), tagPtr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    corearchAarch64MteStg(taggedPtr.rawAddress.toCPointer<ByteVar>(), addrToTag.rawAddress.toCPointer<ByteVar>())
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    ConstPtr(corearchAarch64MteAddg(ptr.rawAddress.toCPointer<ByteVar>(), value)?.toLong() ?: 0L)

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    corearchAarch64MteSubp(ptrA.rawAddress.toCPointer<ByteVar>(), ptrB.rawAddress.toCPointer<ByteVar>())
