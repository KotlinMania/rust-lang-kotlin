@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.spawn

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun posixSpawnattrSetQosClassNp(attr: MutPtr, qosClass: QosClassT): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrSetQosClassNp")

public actual fun posixSpawnattrGetQosClassNp(attr: ConstPtr, qosClass: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadSpawnPosixSpawnattrGetQosClassNp")
