// port-lint: source new/apple/libpthread/pthread_/stack_np.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.stacknp

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.UintptrT

// Header: `pthread / stackNp.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/pthread/stackNp.h>

public expect fun pthreadStackFrameDecodeNp(frameAddr: UintptrT, returnAddr: MutPtr): UintptrT
