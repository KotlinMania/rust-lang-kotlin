@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.CSsizeT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module std.
public actual fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixOpen")

public actual fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixRead")

public actual fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixWrite")

@kotlin.wasm.WasmImport("std", "stdSysFsUnixClose")
private external fun wasiStdSysFsUnixClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiStdSysFsUnixClose(fd).toInt()
