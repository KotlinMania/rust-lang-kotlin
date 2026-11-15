@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.spawn

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.spawn.cinterop.rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrGetQosClassNp
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun posixSpawnattrSetQosClassNp(attr: MutPtr, qosClass: QosClassT): CInt =
    0

public actual fun posixSpawnattrGetQosClassNp(attr: ConstPtr, qosClass: MutPtr): CInt =
    rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrGetQosClassNp(attr.rawAddress.toCPointer<ByteVar>(), qosClass.rawAddress.toCPointer<ByteVar>())
