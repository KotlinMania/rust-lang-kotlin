@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private object LibcNewCommonLinuxlikePthreadJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native: Long, attr: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread: Long, name: Long, len: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread: Long, cpusetsize: Long, cpuset: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread: Long, name: Long): Int
}

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.toLong(), cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native.toLong(), attr.rawAddress)

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.toLong(), name.rawAddress, len.toLong())

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread.toLong())

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.toLong(), cpusetsize.toLong(), cpuset.rawAddress)

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    LibcNewCommonLinuxlikePthreadJniBridge.rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.toLong(), name.rawAddress)
