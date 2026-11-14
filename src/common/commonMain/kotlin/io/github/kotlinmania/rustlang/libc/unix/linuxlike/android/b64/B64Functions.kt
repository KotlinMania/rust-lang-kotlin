package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.syscall
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.aarch64.SYS_ACCEPT4

public fun accept4(fd: CInt, addr: MutPtr, len: MutPtr, flg: CInt): CInt = syscall(SYS_ACCEPT4, fd.toLong(), addr.rawAddress, len.rawAddress, flg.toLong()).toInt()

public expect fun systemPropertyWait(pi: ConstPtr, oldSerial: UInt, newSerialPtr: MutPtr, relativeTimeout: ConstPtr): Boolean
