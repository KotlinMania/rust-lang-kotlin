@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.qos.QosClassT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.PthreadT

private object NewAppleLibpthreadPthreadQosJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr: Long, cls: Long, priority: Long): Int

    @JvmStatic
    external fun libcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread: Long, cls: Long, priority: Long): Int
}

public actual fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt =
    NewAppleLibpthreadPthreadQosJniBridge.libcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr.rawAddress, cls.rawAddress, priority.rawAddress)

public actual fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt =
    NewAppleLibpthreadPthreadQosJniBridge.libcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread.toLong(), cls.rawAddress, priority.rawAddress)
