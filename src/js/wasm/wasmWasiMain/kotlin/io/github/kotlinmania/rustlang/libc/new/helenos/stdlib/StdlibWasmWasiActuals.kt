@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun malloc(size: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibMalloc")

public actual fun calloc(nmemb: ULong, size: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibCalloc")

public actual fun realloc(addr: MutPtr, size: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibRealloc")

public actual fun free(addr: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibFree")
}

public actual fun memalign(align: ULong, size: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibMemalign")

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosStdlibRand")
private external fun wasiLibcNewHelenosStdlibRand(): Int

public actual fun rand(): CInt =
    wasiLibcNewHelenosStdlibRand().toInt()

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosStdlibSrand")
private external fun wasiLibcNewHelenosStdlibSrand(seed: Int): Unit

public actual fun srand(seed: CUint): Unit =
    wasiLibcNewHelenosStdlibSrand(seed.toInt())

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosStdlibExit")
private external fun wasiLibcNewHelenosStdlibExit(code: Int): Unit

public actual fun exit(code: CInt): Nothing {
    wasiLibcNewHelenosStdlibExit(code)
    error("C noreturn function libcNewHelenosStdlibExit returned")
}

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosStdlibAbort")
private external fun wasiLibcNewHelenosStdlibAbort(): Unit

public actual fun abort(): Nothing {
    wasiLibcNewHelenosStdlibAbort()
    error("C noreturn function libcNewHelenosStdlibAbort returned")
}

public actual fun getenv(env: ConstPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosStdlibGetenv")
