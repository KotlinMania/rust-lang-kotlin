@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSsizeT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.CInt

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysFsUnixOpen")

public actual fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysFsUnixRead")

public actual fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysFsUnixWrite")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysFsUnixClose")
private external fun wasiRustlangStdSysFsUnixClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiRustlangStdSysFsUnixClose(fd).toInt()
