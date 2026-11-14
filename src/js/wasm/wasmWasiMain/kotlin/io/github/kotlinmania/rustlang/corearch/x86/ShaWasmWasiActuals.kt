@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4

// The host must provide the C companion wrapper exports in import module corearch.
public actual fun sha1msg1(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha1msg1")

public actual fun sha1msg2(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha1msg2")

public actual fun sha1nexte(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha1nexte")

public actual fun sha1rnds4(a: I32x4, b: I32x4, c: Byte): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha1rnds4")

public actual fun sha256msg1(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha256msg1")

public actual fun sha256msg2(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha256msg2")

public actual fun sha256rnds2(a: I32x4, b: I32x4, k: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaSha256rnds2")

public actual fun vsha512msg1(a: I64x4, b: I64x2): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsha512msg1")

public actual fun vsha512msg2(a: I64x4, b: I64x4): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsha512msg2")

public actual fun vsha512rnds2(a: I64x4, b: I64x4, k: I64x2): I64x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsha512rnds2")

public actual fun vsm3msg1(a: I32x4, b: I32x4, c: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm3msg1")

public actual fun vsm3msg2(a: I32x4, b: I32x4, c: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm3msg2")

public actual fun vsm3rnds2(a: I32x4, b: I32x4, c: I32x4, d: Int): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm3rnds2")

public actual fun vsm4key4128(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm4key4128")

public actual fun vsm4key4256(a: I32x8, b: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm4key4256")

public actual fun vsm4rnds4128(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm4rnds4128")

public actual fun vsm4rnds4256(a: I32x8, b: I32x8): I32x8 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86ShaVsm4rnds4256")
