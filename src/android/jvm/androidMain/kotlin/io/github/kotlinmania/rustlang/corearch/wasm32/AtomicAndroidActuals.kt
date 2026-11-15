@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object Wasm32AtomicJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchWasm32AtomicLlvmAtomicWaitI32(ptr: Long, exp: Int, timeout: Long): Int

    @JvmStatic
    external fun corearchWasm32AtomicLlvmAtomicWaitI64(ptr: Long, exp: Long, timeout: Long): Int

    @JvmStatic
    external fun corearchWasm32AtomicLlvmAtomicNotify(ptr: Long, cnt: Int): Int
}

public actual fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int =
    Wasm32AtomicJniBridge.corearchWasm32AtomicLlvmAtomicWaitI32(ptr.rawAddress, exp, timeout)

public actual fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int =
    Wasm32AtomicJniBridge.corearchWasm32AtomicLlvmAtomicWaitI64(ptr.rawAddress, exp, timeout)

public actual fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int =
    Wasm32AtomicJniBridge.corearchWasm32AtomicLlvmAtomicNotify(ptr.rawAddress, cnt)
