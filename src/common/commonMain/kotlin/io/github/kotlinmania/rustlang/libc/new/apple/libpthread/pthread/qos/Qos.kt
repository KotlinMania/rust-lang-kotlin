// port-lint: source new/apple/libpthread/pthread_/qos.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.qos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.PthreadT
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.qos.QosClassT

// Header: `pthread / qos.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/pthread/qos.h>

public expect fun pthreadAttrSetQosClassNp(attr: MutPtr, cls: QosClassT, priority: CInt): CInt

public expect fun pthreadAttrGetQosClassNp(attr: MutPtr, cls: MutPtr, priority: MutPtr): CInt

public expect fun pthreadSetQosClassSelfNp(cls: QosClassT, priority: CInt): CInt

public expect fun pthreadGetQosClassNp(thread: PthreadT, cls: MutPtr, priority: MutPtr): CInt
