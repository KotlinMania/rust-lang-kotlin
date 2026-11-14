@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZbOrcB64")
private external fun wasiCorearchRiscvsharedZbOrcB64(rs1: Long): Long

public actual fun orcB64(rs1: Long): Long =
    wasiCorearchRiscvsharedZbOrcB64(rs1).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZbClmul64")
private external fun wasiCorearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZbClmul64(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZbClmulh64")
private external fun wasiCorearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZbClmulh64(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZbClmulr64")
private external fun wasiCorearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZbClmulr64(rs1, rs2).toLong()
