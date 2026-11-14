@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.helenos

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual val ENVIRON: ConstPtr
    get() = throw UnsupportedOperationException("WasmWasi foreign static environ requires a host memory/aggregate ABI adapter")

public actual fun strError(err: ErrnoT): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosStrError")

public actual fun strErrorName(err: ErrnoT): ConstPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosStrErrorName")

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback?): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosPthreadKeyCreate")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosPthreadGetspecific")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosPthreadSetspecific")

@kotlin.wasm.WasmImport("kmcore", "libcHelenosPthreadKeyDelete")
private external fun wasiLibcHelenosPthreadKeyDelete(key: Int): Int

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    wasiLibcHelenosPthreadKeyDelete(key).toInt()

public actual fun clockGetres(clockId: ClockidT, res: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosClockGetres")

public actual fun clockGettime(clockId: ClockidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosClockGettime")

public actual fun clockSettime(clockId: ClockidT, tp: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosClockSettime")

public actual fun clockNanosleep(clockId: ClockidT, flags: CInt, rqtp: ConstPtr, rmtp: MutPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosClockNanosleep")

@kotlin.wasm.WasmImport("kmcore", "libcHelenosGetpid")
private external fun wasiLibcHelenosGetpid(): Int

public actual fun getpid(): PidT =
    wasiLibcHelenosGetpid().toInt()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosGetcwd")

public actual fun chdir(buf: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcHelenosChdir")

@kotlin.wasm.WasmImport("kmcore", "libcHelenosIsatty")
private external fun wasiLibcHelenosIsatty(fd: Int): Int

public actual fun isatty(fd: CInt): CInt =
    wasiLibcHelenosIsatty(fd).toInt()
