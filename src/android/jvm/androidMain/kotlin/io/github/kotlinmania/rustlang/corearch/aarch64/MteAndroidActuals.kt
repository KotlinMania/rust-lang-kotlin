@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object CorearchAarch64MteJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchAarch64MteIrg(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun rustlangCorearchAarch64MteGmi(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun rustlangCorearchAarch64MteLdg(ptr: Long, tagPtr: Long): Long

    @JvmStatic
    external fun rustlangCorearchAarch64MteStg(taggedPtr: Long, addrToTag: Long)

    @JvmStatic
    external fun rustlangCorearchAarch64MteAddg(ptr: Long, value: Long): Long

    @JvmStatic
    external fun rustlangCorearchAarch64MteSubp(ptrA: Long, ptrB: Long): Long
}

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    ConstPtr(CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteIrg(ptr.rawAddress, exclude))

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteGmi(ptr.rawAddress, exclude)

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    ConstPtr(CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteLdg(ptr.rawAddress, tagPtr.rawAddress))

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteStg(taggedPtr.rawAddress, addrToTag.rawAddress)
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    ConstPtr(CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteAddg(ptr.rawAddress, value))

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    CorearchAarch64MteJniBridge.rustlangCorearchAarch64MteSubp(ptrA.rawAddress, ptrB.rawAddress)
