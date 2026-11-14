@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.ClockidT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysSyncFutexWasilibcJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysSyncFutexWasilibcWasilibcFutexWait(addr: Long, value: Int, clock: Int, at: Long, flags: Int): Int

    @JvmStatic
    external fun stdSysSyncFutexWasilibcWasilibcFutexWake(addr: Long, count: Int, flags: Int): Int
}

public actual fun wasilibcFutexWait(addr: MutPtr, value: CInt, clock: ClockidT, at: ConstPtr, flags: CUint): CInt =
    SysSyncFutexWasilibcJniBridge.stdSysSyncFutexWasilibcWasilibcFutexWait(addr.rawAddress, value, clock, at.rawAddress, flags.toInt())

public actual fun wasilibcFutexWake(addr: ConstPtr, count: CInt, flags: CUint): CInt =
    SysSyncFutexWasilibcJniBridge.stdSysSyncFutexWasilibcWasilibcFutexWake(addr.rawAddress, count, flags.toInt())
