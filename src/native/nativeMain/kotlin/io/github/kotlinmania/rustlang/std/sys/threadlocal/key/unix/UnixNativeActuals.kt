@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.ffi.CInt
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.cinterop.rustlangStdSysThreadlocalKeyUnixPthreadGetspecific
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.cinterop.rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.cinterop.rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.cinterop.rustlangStdSysThreadlocalKeyUnixPthreadSetspecific
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(key.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(key)?.toLong() ?: 0L)

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(key, value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key)
