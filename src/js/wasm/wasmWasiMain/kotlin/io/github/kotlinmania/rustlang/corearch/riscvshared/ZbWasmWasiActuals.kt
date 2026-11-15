@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZbOrcB64")
private external fun wasiRustlangCorearchRiscvsharedZbOrcB64(rs1: Long): Long

public actual fun orcB64(rs1: Long): Long =
    wasiRustlangCorearchRiscvsharedZbOrcB64(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZbClmul64")
private external fun wasiRustlangCorearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscvsharedZbClmul64(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZbClmulh64")
private external fun wasiRustlangCorearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscvsharedZbClmulh64(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZbClmulr64")
private external fun wasiRustlangCorearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscvsharedZbClmulr64(rs1, rs2).toLong()
