// port-lint: source new/apple/libpthread/pthread_/pthread_impl.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.pthreadimpl

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.libc.*

// Note(apple): these should all be `public(crate)`
public val PTHREAD_MUTEX_SIG_INIT: CLong = 0x32AAABA7L

public val PTHREAD_COND_SIG_INIT: CLong = 0x3CB0B1BBL

public val PTHREAD_ONCE_SIG_INIT: CLong = 0x30B1BCBAL

public val PTHREAD_RWLOCK_SIG_INIT: CLong = 0x2DA8B3B4L

public val SCHED_OTHER: CInt = 1

public val SCHED_FIFO: CInt = 4

public val SCHED_RR: CInt = 2
