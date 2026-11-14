@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.ClockidT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc.cinterop.stdSysSyncFutexWasilibcWasilibcFutexWait
import io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc.cinterop.stdSysSyncFutexWasilibcWasilibcFutexWake
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun wasilibcFutexWait(addr: MutPtr, value: CInt, clock: ClockidT, at: ConstPtr, flags: CUint): CInt =
    stdSysSyncFutexWasilibcWasilibcFutexWait(addr.rawAddress.toCPointer<ByteVar>(), value, clock, at.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcFutexWake(addr: ConstPtr, count: CInt, flags: CUint): CInt =
    stdSysSyncFutexWasilibcWasilibcFutexWake(addr.rawAddress.toCPointer<ByteVar>(), count, flags)
