@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchX86RdtscExternRdtsc")
private external fun wasiCorearchX86RdtscExternRdtsc(): Long

public actual fun externRdtsc(): ULong =
    wasiCorearchX86RdtscExternRdtsc().toULong()

public actual fun externRdtscp(): Pair<ULong, UInt> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for corearchX86RdtscExternRdtscp")
