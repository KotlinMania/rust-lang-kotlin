@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadGetaffinityNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadGetattrNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadGetnameNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadGettidNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadSetaffinityNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.libcNewCommonLinuxlikePthreadPthreadSetnameNp
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.PthreadT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    libcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.toLong().toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    libcNewCommonLinuxlikePthreadPthreadGetattrNp(native.toLong().toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    libcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.toLong().toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    libcNewCommonLinuxlikePthreadPthreadGettidNp(thread.toLong().toCPointer<ByteVar>())

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    libcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.toLong().toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    libcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.toLong().toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())
