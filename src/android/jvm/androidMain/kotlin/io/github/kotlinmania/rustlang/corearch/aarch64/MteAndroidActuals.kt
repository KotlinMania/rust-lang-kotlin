@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object Aarch64MteJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchAarch64MteIrg(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun corearchAarch64MteGmi(ptr: Long, exclude: Long): Long

    @JvmStatic
    external fun corearchAarch64MteLdg(ptr: Long, tagPtr: Long): Long

    @JvmStatic
    external fun corearchAarch64MteStg(taggedPtr: Long, addrToTag: Long)

    @JvmStatic
    external fun corearchAarch64MteAddg(ptr: Long, value: Long): Long

    @JvmStatic
    external fun corearchAarch64MteSubp(ptrA: Long, ptrB: Long): Long
}

public actual fun irg(ptr: ConstPtr, exclude: Long): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteIrg(ptr.rawAddress, exclude))

public actual fun gmi(ptr: ConstPtr, exclude: Long): Long =
    Aarch64MteJniBridge.corearchAarch64MteGmi(ptr.rawAddress, exclude)

public actual fun ldg(ptr: ConstPtr, tagPtr: ConstPtr): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteLdg(ptr.rawAddress, tagPtr.rawAddress))

public actual fun stg(taggedPtr: ConstPtr, addrToTag: ConstPtr) {
    Aarch64MteJniBridge.corearchAarch64MteStg(taggedPtr.rawAddress, addrToTag.rawAddress)
}

public actual fun addg(ptr: ConstPtr, value: Long): ConstPtr =
    ConstPtr(Aarch64MteJniBridge.corearchAarch64MteAddg(ptr.rawAddress, value))

public actual fun subp(ptrA: ConstPtr, ptrB: ConstPtr): Long =
    Aarch64MteJniBridge.corearchAarch64MteSubp(ptrA.rawAddress, ptrB.rawAddress)
