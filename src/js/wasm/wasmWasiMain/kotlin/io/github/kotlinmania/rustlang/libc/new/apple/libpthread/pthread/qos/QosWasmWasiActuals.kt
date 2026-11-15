@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrSetQosClassNp")

public actual fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadQosPthreadAttrGetQosClassNp")

public actual fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadQosPthreadSetQosClassSelfNp")

public actual fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangLibcNewAppleLibpthreadPthreadQosPthreadGetQosClassNp")
