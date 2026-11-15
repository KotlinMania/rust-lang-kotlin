@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.corearchWasm32AtomicLlvmAtomicNotify
import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.corearchWasm32AtomicLlvmAtomicWaitI32
import io.github.kotlinmania.rustlang.corearch.wasm32.cinterop.corearchWasm32AtomicLlvmAtomicWaitI64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun llvmAtomicWaitI32(ptr: MutPtr, exp: Int, timeout: Long): Int =
    corearchWasm32AtomicLlvmAtomicWaitI32(ptr.rawAddress.toCPointer<ByteVar>(), exp, timeout)

public actual fun llvmAtomicWaitI64(ptr: MutPtr, exp: Long, timeout: Long): Int =
    corearchWasm32AtomicLlvmAtomicWaitI64(ptr.rawAddress.toCPointer<ByteVar>(), exp, timeout)

public actual fun llvmAtomicNotify(ptr: MutPtr, cnt: Int): Int =
    corearchWasm32AtomicLlvmAtomicNotify(ptr.rawAddress.toCPointer<ByteVar>(), cnt)
