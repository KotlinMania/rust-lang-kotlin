@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86RdtscRdtsc")
private external fun wasiRustlangCorearchX86RdtscRdtsc(): Long

public actual fun rdtsc(): ULong =
    wasiRustlangCorearchX86RdtscRdtsc().toULong()

public actual fun rdtscp(): Pair<ULong, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCorearchX86RdtscRdtscp")
