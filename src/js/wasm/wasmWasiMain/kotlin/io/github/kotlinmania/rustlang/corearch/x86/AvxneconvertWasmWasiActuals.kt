@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun bcstnebf162ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertBcstnebf162ps128")

public actual fun bcstnebf162ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertBcstnebf162ps256")

public actual fun bcstnesh2ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertBcstnesh2ps128")

public actual fun bcstnesh2ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertBcstnesh2ps256")

public actual fun cvtneebf162ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneebf162ps128")

public actual fun cvtneebf162ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneebf162ps256")

public actual fun cvtneeph2ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneeph2ps128")

public actual fun cvtneeph2ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneeph2ps256")

public actual fun cvtneobf162ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneobf162ps128")

public actual fun cvtneobf162ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneobf162ps256")

public actual fun cvtneoph2ps128(a: ConstPtr): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneoph2ps128")

public actual fun cvtneoph2ps256(a: ConstPtr): M256 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertCvtneoph2ps256")

public actual fun vcvtneps2bf16128(a: M128): M128bh =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertVcvtneps2bf16128")

public actual fun vcvtneps2bf16256(a: M256): M128bh =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86AvxneconvertVcvtneps2bf16256")
