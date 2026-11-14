@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibril

import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun fibrilCreateGeneric(func: FibrilCreateGenericFuncCallback, arg: MutPtr, stacksize: SizeT): FidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilFibrilCreateGeneric")

public actual fun fibrilStart(f: FidT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilFibrilStart")
}

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosFibrilFibrilExit")
private external fun wasiLibcNewHelenosFibrilFibrilExit(retval: Long): Unit

public actual fun fibrilExit(retval: CLong): Nothing {
    wasiLibcNewHelenosFibrilFibrilExit(retval)
    error("C noreturn function libcNewHelenosFibrilFibrilExit returned")
}

public actual fun fibrilDetach(f: FidT) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilFibrilDetach")
}

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosFibrilFibrilYield")
private external fun wasiLibcNewHelenosFibrilFibrilYield(): Unit

public actual fun fibrilYield(): Unit =
    wasiLibcNewHelenosFibrilFibrilYield()

@kotlin.wasm.WasmImport("kmcore", "libcNewHelenosFibrilFibrilUsleep")
private external fun wasiLibcNewHelenosFibrilFibrilUsleep(usec: Long): Unit

public actual fun fibrilUsleep(usec: UsecT): Unit =
    wasiLibcNewHelenosFibrilFibrilUsleep(usec)

public actual fun fibrilGetId(): FidT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilFibrilGetId")
