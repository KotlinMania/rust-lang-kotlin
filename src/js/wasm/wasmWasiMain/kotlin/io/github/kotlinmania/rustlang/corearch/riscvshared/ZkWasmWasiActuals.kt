@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkSm4ed")
private external fun wasiCorearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    wasiCorearchRiscvsharedZkSm4ed(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkSm4ks")
private external fun wasiCorearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    wasiCorearchRiscvsharedZkSm4ks(rs1, rs2, bs).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSm3p0")
private external fun wasiCorearchRiscvsharedZkExternSm3p0(rs1: Int): Int

public actual fun externSm3p0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSm3p0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSm3p1")
private external fun wasiCorearchRiscvsharedZkExternSm3p1(rs1: Int): Int

public actual fun externSm3p1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSm3p1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSha256sig0")
private external fun wasiCorearchRiscvsharedZkExternSha256sig0(rs1: Int): Int

public actual fun externSha256sig0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSha256sig0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSha256sig1")
private external fun wasiCorearchRiscvsharedZkExternSha256sig1(rs1: Int): Int

public actual fun externSha256sig1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSha256sig1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSha256sum0")
private external fun wasiCorearchRiscvsharedZkExternSha256sum0(rs1: Int): Int

public actual fun externSha256sum0(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSha256sum0(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkExternSha256sum1")
private external fun wasiCorearchRiscvsharedZkExternSha256sum1(rs1: Int): Int

public actual fun externSha256sum1(rs1: Int): Int =
    wasiCorearchRiscvsharedZkExternSha256sum1(rs1).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkXperm864")
private external fun wasiCorearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZkXperm864(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscvsharedZkXperm464")
private external fun wasiCorearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscvsharedZkXperm464(rs1, rs2).toLong()
