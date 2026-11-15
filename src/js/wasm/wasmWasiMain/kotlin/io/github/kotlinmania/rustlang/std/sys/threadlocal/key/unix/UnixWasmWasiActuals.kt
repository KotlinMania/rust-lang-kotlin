@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.ffi.CInt

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysThreadlocalKeyUnixPthreadGetspecific")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysThreadlocalKeyUnixPthreadSetspecific")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete")
private external fun wasiRustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key: Int): Int

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    wasiRustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key.toInt()).toInt()
