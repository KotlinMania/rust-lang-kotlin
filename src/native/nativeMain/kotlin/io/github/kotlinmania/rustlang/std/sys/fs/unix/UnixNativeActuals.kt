@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSsizeT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.CInt
import io.github.kotlinmania.rustlang.std.sys.fs.unix.cinterop.stdSysFsUnixClose
import io.github.kotlinmania.rustlang.std.sys.fs.unix.cinterop.stdSysFsUnixOpen
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    stdSysFsUnixOpen(pathname.rawAddress.toCPointer<ByteVar>(), flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT =
    null as CSsizeT

public actual fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT =
    null as CSsizeT

public actual fun close(fd: CInt): CInt =
    stdSysFsUnixClose(fd)
