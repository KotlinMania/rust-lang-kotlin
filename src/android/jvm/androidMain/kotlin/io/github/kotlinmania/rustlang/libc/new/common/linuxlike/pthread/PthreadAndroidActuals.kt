@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.PthreadT
import io.github.kotlinmania.rustlang.libc.unix.SizeT

private object NewCommonLinuxlikePthreadJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadGetattrNp(native: Long, attr: Long): Int

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadGetnameNp(thread: Long, name: Long, len: Long): Int

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadGettidNp(thread: Long): Int

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun libcNewCommonLinuxlikePthreadPthreadSetnameNp(thread: Long, name: Long): Int
}

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.rawAddress, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadGetattrNp(native.rawAddress, attr.rawAddress)

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.rawAddress, name.rawAddress, len.toLong())

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadGettidNp(thread.rawAddress)

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.rawAddress, cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    NewCommonLinuxlikePthreadJniBridge.libcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.rawAddress, name.rawAddress)
