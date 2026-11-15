@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.spawn

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT

private object LibcNewAppleLibpthreadPthreadSpawnJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrGetQosClassNp(attr: Long, qosClass: Long): Int
}

public actual fun posixSpawnattrSetQosClassNp(attr: MutPtr, qosClass: QosClassT): CInt =
    0

public actual fun posixSpawnattrGetQosClassNp(attr: ConstPtr, qosClass: MutPtr): CInt =
    LibcNewAppleLibpthreadPthreadSpawnJniBridge.rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrGetQosClassNp(attr.rawAddress, qosClass.rawAddress)
