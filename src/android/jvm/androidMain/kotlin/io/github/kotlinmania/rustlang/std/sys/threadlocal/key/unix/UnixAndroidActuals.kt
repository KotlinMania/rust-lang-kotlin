@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.ffi.CInt

private object StdSysThreadlocalKeyUnixJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(key: Long, destructor: Long): Int

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key: Int): Int
}

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(key.toInt()))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(key.toInt(), value.rawAddress)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key.toInt())
