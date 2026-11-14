@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXsave")
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXrstor")
}

@kotlin.wasm.WasmImport("kmcore", "corearchX86XsaveXsetbv")
private external fun wasiCorearchX86XsaveXsetbv(v: Int, hi: Int, lo: Int): Unit

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt): Unit =
    wasiCorearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())

@kotlin.wasm.WasmImport("kmcore", "corearchX86XsaveXgetbv")
private external fun wasiCorearchX86XsaveXgetbv(v: Int): Long

public actual fun xgetbv(v: UInt): Long =
    wasiCorearchX86XsaveXgetbv(v.toInt()).toLong()

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXsaveopt")
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXsavec")
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXsaves")
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86XsaveXrstors")
}
