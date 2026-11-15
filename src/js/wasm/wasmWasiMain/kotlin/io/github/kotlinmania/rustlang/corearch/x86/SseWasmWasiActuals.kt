@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun rcpss(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseRcpss")

public actual fun rcpps(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseRcpps")

public actual fun rsqrtss(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseRsqrtss")

public actual fun rsqrtps(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseRsqrtps")

public actual fun minss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseMinss")

public actual fun minps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseMinps")

public actual fun maxss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseMaxss")

public actual fun maxps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseMaxps")

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseCmpps")

public actual fun comieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComieqSs")

public actual fun comiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComiltSs")

public actual fun comileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComileSs")

public actual fun comigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComigtSs")

public actual fun comigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComigeSs")

public actual fun comineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseComineqSs")

public actual fun ucomieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomieqSs")

public actual fun ucomiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomiltSs")

public actual fun ucomileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomileSs")

public actual fun ucomigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomigtSs")

public actual fun ucomigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomigeSs")

public actual fun ucomineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseUcomineqSs")

public actual fun cvtss2si(a: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseCvtss2si")

public actual fun cvttss2si(a: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseCvttss2si")

@kotlin.wasm.WasmImport("kmcore", "corearchX86SseSfence")
private external fun wasiCorearchX86SseSfence(): Unit

public actual fun sfence(): Unit =
    wasiCorearchX86SseSfence()

public actual fun stmxcsr(p: MutPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseStmxcsr")

public actual fun ldmxcsr(p: ConstPtr): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseLdmxcsr")

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int): Unit = throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SsePrefetch")

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86SseCmpss")
