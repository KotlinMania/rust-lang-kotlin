package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

public fun interface MakecontextFuncCallback {
    public operator fun invoke()
}

public expect fun getcontext(ucp: MutPtr): CInt

public expect fun setcontext(ucp: ConstPtr): CInt

public expect fun makecontext(ucp: MutPtr, func: MakecontextFuncCallback, argc: CInt, vararg variadicArgs: Long)

public expect fun swapcontext(uocp: MutPtr, ucp: ConstPtr): CInt
