@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private object HelenosJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcHelenosGetENVIRON(): Long

    @JvmStatic
    external fun libcHelenosStrError(err: Int): Long

    @JvmStatic
    external fun libcHelenosStrErrorName(err: Int): Long

    @JvmStatic
    external fun libcHelenosPthreadKeyCreate(key: Long, destructor: Long): Int

    @JvmStatic
    external fun libcHelenosPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun libcHelenosPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun libcHelenosPthreadKeyDelete(key: Int): Int

    @JvmStatic
    external fun libcHelenosClockGetres(clockId: Int, res: Long): Int

    @JvmStatic
    external fun libcHelenosClockGettime(clockId: Int, tp: Long): Int

    @JvmStatic
    external fun libcHelenosClockSettime(clockId: Int, tp: Long): Int

    @JvmStatic
    external fun libcHelenosClockNanosleep(clockId: Int, flags: Int, rqtp: Long, rmtp: Long): Int

    @JvmStatic
    external fun libcHelenosGetpid(): Int

    @JvmStatic
    external fun libcHelenosGetcwd(buf: Long, size: Long): Long

    @JvmStatic
    external fun libcHelenosChdir(buf: Long): Int

    @JvmStatic
    external fun libcHelenosIsatty(fd: Int): Int
}

public actual val ENVIRON: ConstPtr
    get() = ConstPtr(HelenosJniBridge.libcHelenosGetENVIRON())

public actual fun strError(err: ErrnoT): ConstPtr =
    ConstPtr(HelenosJniBridge.libcHelenosStrError(err))

public actual fun strErrorName(err: ErrnoT): ConstPtr =
    ConstPtr(HelenosJniBridge.libcHelenosStrErrorName(err))

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt =
    HelenosJniBridge.libcHelenosPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(HelenosJniBridge.libcHelenosPthreadGetspecific(key))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    HelenosJniBridge.libcHelenosPthreadSetspecific(key, value.rawAddress)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    HelenosJniBridge.libcHelenosPthreadKeyDelete(key)

public actual fun clockGetres(clockId: ClockidT, res: MutPtr): CInt =
    HelenosJniBridge.libcHelenosClockGetres(clockId, res.rawAddress)

public actual fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt =
    HelenosJniBridge.libcHelenosClockGettime(clockId, tp.rawAddress)

public actual fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt =
    HelenosJniBridge.libcHelenosClockSettime(clockId, tp.rawAddress)

public actual fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    HelenosJniBridge.libcHelenosClockNanosleep(clockId, flags, rqtp.rawAddress, rmtp.rawAddress)

public actual fun getpid(): PidT =
    HelenosJniBridge.libcHelenosGetpid()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(HelenosJniBridge.libcHelenosGetcwd(buf.rawAddress, size.toLong()))

public actual fun chdir(buf: ConstPtr): CInt =
    HelenosJniBridge.libcHelenosChdir(buf.rawAddress)

public actual fun isatty(fd: CInt): CInt =
    HelenosJniBridge.libcHelenosIsatty(fd)
