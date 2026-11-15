@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.x86.M128

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun rcpss(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseRcpss")

public actual fun rcpps(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseRcpps")

public actual fun rsqrtss(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseRsqrtss")

public actual fun rsqrtps(a: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseRsqrtps")

public actual fun minss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseMinss")

public actual fun minps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseMinps")

public actual fun maxss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseMaxss")

public actual fun maxps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseMaxps")

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseCmpps")

public actual fun comieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComieqSs")

public actual fun comiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComiltSs")

public actual fun comileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComileSs")

public actual fun comigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComigtSs")

public actual fun comigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComigeSs")

public actual fun comineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseComineqSs")

public actual fun ucomieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomieqSs")

public actual fun ucomiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomiltSs")

public actual fun ucomileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomileSs")

public actual fun ucomigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomigtSs")

public actual fun ucomigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomigeSs")

public actual fun ucomineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseUcomineqSs")

public actual fun cvtss2si(a: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseCvtss2si")

public actual fun cvttss2si(a: M128): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseCvttss2si")

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86SseSfence")
private external fun wasiRustlangCorearchX86SseSfence(): Unit

public actual fun sfence(): Unit =
    wasiRustlangCorearchX86SseSfence()

public actual fun stmxcsr(p: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseStmxcsr")
}

public actual fun ldmxcsr(p: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseLdmxcsr")
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SsePrefetch")
}

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86SseCmpss")
