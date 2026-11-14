@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysPalSgxAbiMemGetENCLAVESIZE")
private external fun wasiStdSysPalSgxAbiMemGetENCLAVESIZE(): Long

public actual val ENCLAVE_SIZE: ULong
    get() = wasiStdSysPalSgxAbiMemGetENCLAVESIZE().toULong()

@kotlin.wasm.WasmImport("std", "stdSysPalSgxAbiMemGetHEAPBASE")
private external fun wasiStdSysPalSgxAbiMemGetHEAPBASE(): Long

public actual val HEAP_BASE: ULong
    get() = wasiStdSysPalSgxAbiMemGetHEAPBASE().toULong()

@kotlin.wasm.WasmImport("std", "stdSysPalSgxAbiMemGetHEAPSIZE")
private external fun wasiStdSysPalSgxAbiMemGetHEAPSIZE(): Long

public actual val HEAP_SIZE: ULong
    get() = wasiStdSysPalSgxAbiMemGetHEAPSIZE().toULong()
