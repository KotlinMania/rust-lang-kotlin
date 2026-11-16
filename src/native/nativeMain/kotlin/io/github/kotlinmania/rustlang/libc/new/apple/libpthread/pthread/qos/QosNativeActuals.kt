@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos.cinterop.libcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos.cinterop.libcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.qos.QosClassT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.PthreadT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt =
    libcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp(attr.rawAddress.toCPointer<ByteVar>(), cls.rawAddress.toCPointer<ByteVar>(), priority.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt =
    0

public actual fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt =
    libcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp(thread.toLong().toCPointer<ByteVar>(), cls.rawAddress.toCPointer<ByteVar>(), priority.rawAddress.toCPointer<ByteVar>())
