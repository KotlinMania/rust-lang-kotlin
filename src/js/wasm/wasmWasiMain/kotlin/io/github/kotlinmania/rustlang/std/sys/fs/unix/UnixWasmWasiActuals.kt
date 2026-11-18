@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSsizeT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlin.experimental.or

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixOpen")

public actual fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixRead")

public actual fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysFsUnixWrite")

@kotlin.wasm.WasmImport("kmcore", "stdSysFsUnixClose")
private external fun wasiStdSysFsUnixClose(fd: Int): Int

public actual fun close(fd: CInt): CInt =
    wasiStdSysFsUnixClose(fd).toInt()
