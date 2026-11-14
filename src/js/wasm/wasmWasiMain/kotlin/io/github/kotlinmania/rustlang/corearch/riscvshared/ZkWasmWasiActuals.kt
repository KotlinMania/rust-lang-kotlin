@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSm4ed")
private external fun wasiCorearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    wasiCorearchRiscvsharedZkSm4ed(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSm4ks")
private external fun wasiCorearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    wasiCorearchRiscvsharedZkSm4ks(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSm3p0")
private external fun wasiCorearchRiscvsharedZkSm3p0(rs1: Int): Int

public actual fun sm3p0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSm3p0(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSm3p1")
private external fun wasiCorearchRiscvsharedZkSm3p1(rs1: Int): Int

public actual fun sm3p1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSm3p1(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSha256sig0")
private external fun wasiCorearchRiscvsharedZkSha256sig0(rs1: Int): Int

public actual fun sha256sig0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSha256sig0(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSha256sig1")
private external fun wasiCorearchRiscvsharedZkSha256sig1(rs1: Int): Int

public actual fun sha256sig1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSha256sig1(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSha256sum0")
private external fun wasiCorearchRiscvsharedZkSha256sum0(rs1: Int): Int

public actual fun sha256sum0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSha256sum0(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkSha256sum1")
private external fun wasiCorearchRiscvsharedZkSha256sum1(rs1: Int): Int

public actual fun sha256sum1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkSha256sum1(rs1).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkXperm864")
private external fun wasiCorearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZkXperm864(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscvsharedZkXperm464")
private external fun wasiCorearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZkXperm464(rs1, rs2).toLong()
