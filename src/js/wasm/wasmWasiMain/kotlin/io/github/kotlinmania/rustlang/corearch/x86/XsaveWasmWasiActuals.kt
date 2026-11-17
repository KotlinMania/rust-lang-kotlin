@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun externXsave(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXsave")
}

public actual fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXrstor")
}

@kotlin.wasm.WasmImport("kmcore", "corearchX86XsaveExternXsetbv")
private external fun wasiCorearchX86XsaveExternXsetbv(v: Int, hi: Int, lo: Int): Unit

public actual fun externXsetbv(v: UInt, hi: UInt, lo: UInt): Unit =
    wasiCorearchX86XsaveExternXsetbv(v.toInt(), hi.toInt(), lo.toInt())

@kotlin.wasm.WasmImport("kmcore", "corearchX86XsaveExternXgetbv")
private external fun wasiCorearchX86XsaveExternXgetbv(v: Int): Long

public actual fun externXgetbv(v: UInt): Long =
    wasiCorearchX86XsaveExternXgetbv(v.toInt()).toLong()

public actual fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXsaveopt")
}

public actual fun externXsavec(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXsavec")
}

public actual fun externXsaves(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXsaves")
}

public actual fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveExternXrstors")
}
