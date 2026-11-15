// port-lint: source new/glibc/sysdeps/nptl/pthread.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.nptl.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.new.apple.libpthread.sys.pthread.pthreadtypes.PthreadMutexT
import io.github.kotlinmania.rustlang.new.glibc.sysdeps.nptl.bits.structmutex.pthreadMutexInitializer

// Source header: `sysdeps / nptl / pthread.h`
//
// <https://github.com/sailfishos-mirror/glibc/blob/master/sysdeps/nptl/pthread.h>

public val PTHREAD_MUTEX_TIMED_NP: CInt = 0

public val PTHREAD_MUTEX_RECURSIVE_NP: CInt = PTHREAD_MUTEX_TIMED_NP + 1

public val PTHREAD_MUTEX_ERRORCHECK_NP: CInt = PTHREAD_MUTEX_RECURSIVE_NP + 1

public val PTHREAD_MUTEX_ADAPTIVE_NP: CInt = PTHREAD_MUTEX_ERRORCHECK_NP + 1

public val PTHREAD_MUTEX_INITIALIZER: PthreadMutexT = pthreadMutexInitializer(PTHREAD_MUTEX_TIMED_NP)

public val PTHREAD_RECURSIVE_MUTEX_INITIALIZER_NP: PthreadMutexT = pthreadMutexInitializer(PTHREAD_MUTEX_RECURSIVE_NP)

public val PTHREAD_ERRORCHECK_MUTEX_INITIALIZER_NP: PthreadMutexT = pthreadMutexInitializer(PTHREAD_MUTEX_ERRORCHECK_NP)

public val PTHREAD_ADAPTIVE_MUTEX_INITIALIZER_NP: PthreadMutexT = pthreadMutexInitializer(PTHREAD_MUTEX_ADAPTIVE_NP)
