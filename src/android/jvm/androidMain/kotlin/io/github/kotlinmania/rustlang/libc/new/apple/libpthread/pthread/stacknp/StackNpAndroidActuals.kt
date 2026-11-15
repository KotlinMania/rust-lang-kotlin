@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.stacknp

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.UintptrT

private object LibcNewAppleLibpthreadPthreadStacknpStackNpJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadStacknpStackNpPthreadStackFrameDecodeNp(frameAddr: Long, returnAddr: Long): Long
}

public actual fun pthreadStackFrameDecodeNp(frameAddr: UintptrT, returnAddr: MutPtr): UintptrT =
    LibcNewAppleLibpthreadPthreadStacknpStackNpJniBridge.rustlangLibcNewAppleLibpthreadPthreadStacknpStackNpPthreadStackFrameDecodeNp(frameAddr.toLong(), returnAddr.rawAddress).toULong()
