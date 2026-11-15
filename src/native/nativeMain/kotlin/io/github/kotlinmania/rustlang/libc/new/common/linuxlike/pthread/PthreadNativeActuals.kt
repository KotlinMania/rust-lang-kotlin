@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp
import io.github.kotlinmania.rustlang.libc.new.common.linuxlike.pthread.cinterop.rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun pthreadGetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: MutPtr): CInt =
    rustlangLibcNewCommonLinuxlikePthreadPthreadGetaffinityNp(thread.toLong().toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetattrNp(native: PthreadT, attr: MutPtr): CInt =
    rustlangLibcNewCommonLinuxlikePthreadPthreadGetattrNp(native.toLong().toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    rustlangLibcNewCommonLinuxlikePthreadPthreadGetnameNp(thread.toLong().toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun pthreadGettidNp(thread: PthreadT): PidT =
    rustlangLibcNewCommonLinuxlikePthreadPthreadGettidNp(thread.toLong().toCPointer<ByteVar>())

public actual fun pthreadSetaffinityNp(thread: PthreadT, cpusetsize: SizeT, cpuset: ConstPtr): CInt =
    rustlangLibcNewCommonLinuxlikePthreadPthreadSetaffinityNp(thread.toLong().toCPointer<ByteVar>(), cpusetsize, cpuset.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetnameNp(thread: PthreadT, name: ConstPtr): CInt =
    rustlangLibcNewCommonLinuxlikePthreadPthreadSetnameNp(thread.toLong().toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>())
