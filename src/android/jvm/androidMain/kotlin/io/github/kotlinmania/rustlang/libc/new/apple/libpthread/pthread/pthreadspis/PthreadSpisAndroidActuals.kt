@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.pthreadspis

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object LibcNewAppleLibpthreadPthreadPthreadspisPthreadSpisJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadPthreadspisPthreadSpisPthreadCreateFromMachThread(thread: Long, attr: Long, f: Long, value: Long): Int
}

public actual fun pthreadCreateFromMachThread(thread: MutPtr, attr: ConstPtr, f: PthreadCreateFromMachThreadFCallback, value: MutPtr): CInt =
    LibcNewAppleLibpthreadPthreadPthreadspisPthreadSpisJniBridge.rustlangLibcNewAppleLibpthreadPthreadPthreadspisPthreadSpisPthreadCreateFromMachThread(thread.rawAddress, attr.rawAddress, 0L, value.rawAddress)
