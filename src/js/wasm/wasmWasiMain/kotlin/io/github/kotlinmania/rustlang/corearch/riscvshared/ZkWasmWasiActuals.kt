@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSm4ed")
private external fun wasiRustlangCorearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSm4ks")
private external fun wasiRustlangCorearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSm3p0")
private external fun wasiRustlangCorearchRiscvsharedZkSm3p0(rs1: Int): Int

public actual fun sm3p0(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSm3p0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSm3p1")
private external fun wasiRustlangCorearchRiscvsharedZkSm3p1(rs1: Int): Int

public actual fun sm3p1(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSm3p1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSha256sig0")
private external fun wasiRustlangCorearchRiscvsharedZkSha256sig0(rs1: Int): Int

public actual fun sha256sig0(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSha256sig0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSha256sig1")
private external fun wasiRustlangCorearchRiscvsharedZkSha256sig1(rs1: Int): Int

public actual fun sha256sig1(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSha256sig1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSha256sum0")
private external fun wasiRustlangCorearchRiscvsharedZkSha256sum0(rs1: Int): Int

public actual fun sha256sum0(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSha256sum0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkSha256sum1")
private external fun wasiRustlangCorearchRiscvsharedZkSha256sum1(rs1: Int): Int

public actual fun sha256sum1(rs1: Int): Int =
    wasiRustlangCorearchRiscvsharedZkSha256sum1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkXperm864")
private external fun wasiRustlangCorearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscvsharedZkXperm864(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscvsharedZkXperm464")
private external fun wasiRustlangCorearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscvsharedZkXperm464(rs1, rs2).toLong()
