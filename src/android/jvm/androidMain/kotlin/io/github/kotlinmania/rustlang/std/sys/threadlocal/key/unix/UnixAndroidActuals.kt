@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CInt

private object SysThreadlocalKeyUnixJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun stdSysThreadlocalKeyUnixPthreadKeyCreate(key: Long, destructor: Long): Int

    @JvmStatic
    external fun stdSysThreadlocalKeyUnixPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun stdSysThreadlocalKeyUnixPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun stdSysThreadlocalKeyUnixPthreadKeyDelete(key: Int): Int
}

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    SysThreadlocalKeyUnixJniBridge.stdSysThreadlocalKeyUnixPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(SysThreadlocalKeyUnixJniBridge.stdSysThreadlocalKeyUnixPthreadGetspecific(key.toInt()))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    SysThreadlocalKeyUnixJniBridge.stdSysThreadlocalKeyUnixPthreadSetspecific(key.toInt(), value.rawAddress)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    SysThreadlocalKeyUnixJniBridge.stdSysThreadlocalKeyUnixPthreadKeyDelete(key.toInt())
