@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX86RdtscRdtsc")
private external fun wasiCorearchX86RdtscRdtsc(): Long

public actual fun rdtsc(): ULong =
    wasiCorearchX86RdtscRdtsc().toULong()

public actual fun rdtscp(): Pair<ULong, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdtscRdtscp")
