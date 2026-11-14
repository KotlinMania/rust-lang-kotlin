@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosChdir
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosClockGetres
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosClockGettime
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosClockNanosleep
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosClockSettime
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosGetENVIRON
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosGetcwd
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosGetpid
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosIsatty
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosPthreadGetspecific
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosPthreadKeyCreate
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosPthreadKeyDelete
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosPthreadSetspecific
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosStrError
import io.github.kotlinmania.rustlang.libc.helenos.cinterop.libcHelenosStrErrorName
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual val ENVIRON: ConstPtr
    get() = ConstPtr(libcHelenosGetENVIRON()?.toLong() ?: 0L)

public actual fun strError(err: ErrnoT): ConstPtr =
    ConstPtr(libcHelenosStrError(err)?.toLong() ?: 0L)

public actual fun strErrorName(err: ErrnoT): ConstPtr =
    ConstPtr(libcHelenosStrErrorName(err)?.toLong() ?: 0L)

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt =
    libcHelenosPthreadKeyCreate(key.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(libcHelenosPthreadGetspecific(key)?.toLong() ?: 0L)

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    libcHelenosPthreadSetspecific(key, value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    libcHelenosPthreadKeyDelete(key)

public actual fun clockGetres(clockId: ClockidT, res: MutPtr): CInt =
    libcHelenosClockGetres(clockId, res.rawAddress.toCPointer<ByteVar>())

public actual fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt =
    libcHelenosClockGettime(clockId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt =
    libcHelenosClockSettime(clockId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    libcHelenosClockNanosleep(clockId, flags, rqtp.rawAddress.toCPointer<ByteVar>(), rmtp.rawAddress.toCPointer<ByteVar>())

public actual fun getpid(): PidT =
    libcHelenosGetpid()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(libcHelenosGetcwd(buf.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun chdir(buf: ConstPtr): CInt =
    libcHelenosChdir(buf.rawAddress.toCPointer<ByteVar>())

public actual fun isatty(fd: CInt): CInt =
    libcHelenosIsatty(fd)
