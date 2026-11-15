// port-lint: source new/apple/libpthread/pthread_/spawn.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.spawn

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.qos.QosClassT

// Header: `pthread / spawn.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/pthread/spawn.h>

public expect fun posixSpawnattrSetQosClassNp(attr: MutPtr, qosClass: QosClassT): CInt

public expect fun posixSpawnattrGetQosClassNp(attr: ConstPtr, qosClass: MutPtr): CInt
