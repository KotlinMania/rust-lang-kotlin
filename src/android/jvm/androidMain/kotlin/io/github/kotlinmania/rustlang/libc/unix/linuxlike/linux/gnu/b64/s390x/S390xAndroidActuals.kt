@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.s390x

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.SizeT

private object LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSysctl(name: Long, namelen: Int, oldp: Long, oldlenp: Long, newp: Long, newlen: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuB64S390xGetcontext(ucp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSetcontext(ucp: Long): Int

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuB64S390xMakecontext(ucp: Long, func: Long, argc: Int, kmVac: Int, kmVa0: Long, kmVa1: Long, kmVa2: Long, kmVa3: Long)

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSwapcontext(uocp: Long, ucp: Long): Int
}

public actual fun sysctl(name: MutPtr, namelen: CInt, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSysctl(name.rawAddress, namelen, oldp.rawAddress, oldlenp.rawAddress, newp.rawAddress, newlen.toLong())

public actual fun getcontext(ucp: MutPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuB64S390xGetcontext(ucp.rawAddress)

public actual fun setcontext(ucp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSetcontext(ucp.rawAddress)

public actual fun makecontext(ucp: MutPtr, func: MakecontextFuncCallback, argc: CInt, vararg variadicArgs: Long) {
    LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuB64S390xMakecontext(ucp.rawAddress, 0L, argc, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
}

public actual fun swapcontext(uocp: MutPtr, ucp: ConstPtr): CInt =
    LibcUnixLinuxlikeLinuxGnuB64S390xJniBridge.rustlangLibcUnixLinuxlikeLinuxGnuB64S390xSwapcontext(uocp.rawAddress, ucp.rawAddress)
