// port-lint: source new/apple/libpthread/pthread_/introspection.rs
package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.introspection

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.sys.pthread.pthreadtypes.PthreadKeyT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.PthreadT

// Header: `pthread / introspection.h`
//
// <https://github.com/apple-oss-distributions/libpthread/blob/main/include/pthread/introspection.h>

public fun interface FnCallback {
    public operator fun invoke(event: CUint, thread: PthreadT, addr: MutPtr, size: SizeT)
}

public val PTHREAD_INTROSPECTION_THREAD_CREATE: CUint = 1u

public val PTHREAD_INTROSPECTION_THREAD_START: CUint = PTHREAD_INTROSPECTION_THREAD_CREATE + 1u

public val PTHREAD_INTROSPECTION_THREAD_TERMINATE: CUint = PTHREAD_INTROSPECTION_THREAD_START + 1u

public val PTHREAD_INTROSPECTION_THREAD_DESTROY: CUint = PTHREAD_INTROSPECTION_THREAD_TERMINATE + 1u

public fun interface PthreadIntrospectionHookT {
    public operator fun invoke(event: CUint, thread: PthreadT, addr: MutPtr, size: SizeT)
}

// Available from Big Sur
public expect fun pthreadIntrospectionHookInstall(hook: PthreadIntrospectionHookT): PthreadIntrospectionHookT

public expect fun pthreadIntrospectionSetspecificNp(thread: PthreadT, key: PthreadKeyT, value: ConstPtr): CInt

public expect fun pthreadIntrospectionGetspecificNp(thread: PthreadT, key: PthreadKeyT): MutPtr
