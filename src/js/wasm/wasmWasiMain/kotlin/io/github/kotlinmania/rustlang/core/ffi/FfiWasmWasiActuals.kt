@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiMemcpy")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiMemmove")

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiMemset")

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiMemcmp")

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiBcmp")

public actual fun strlen(s: ConstPtr): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreFfiStrlen")
