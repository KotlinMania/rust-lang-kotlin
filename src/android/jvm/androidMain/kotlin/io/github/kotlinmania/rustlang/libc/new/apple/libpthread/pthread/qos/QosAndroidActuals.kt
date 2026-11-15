@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private object LibcNewAppleLibpthreadPthreadQosJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr: Long, cls: Long, priority: Long): Int

    @JvmStatic
    external fun rustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread: Long, cls: Long, priority: Long): Int
}

public actual fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt =
    LibcNewAppleLibpthreadPthreadQosJniBridge.rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr.rawAddress, cls.rawAddress, priority.rawAddress)

public actual fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt =
    LibcNewAppleLibpthreadPthreadQosJniBridge.rustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread.toLong(), cls.rawAddress, priority.rawAddress)
