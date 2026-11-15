@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkAes64es")
private external fun wasiRustlangCorearchRiscv64ZkAes64es(rs1: Long, rs2: Long): Long

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscv64ZkAes64es(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkAes64esm")
private external fun wasiRustlangCorearchRiscv64ZkAes64esm(rs1: Long, rs2: Long): Long

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscv64ZkAes64esm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkAes64ds")
private external fun wasiRustlangCorearchRiscv64ZkAes64ds(rs1: Long, rs2: Long): Long

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscv64ZkAes64ds(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkAes64dsm")
private external fun wasiRustlangCorearchRiscv64ZkAes64dsm(rs1: Long, rs2: Long): Long

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    wasiRustlangCorearchRiscv64ZkAes64dsm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkAes64im")
private external fun wasiRustlangCorearchRiscv64ZkAes64im(rs1: Long): Long

public actual fun aes64im(rs1: Long): Long =
    wasiRustlangCorearchRiscv64ZkAes64im(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkSha512sig0")
private external fun wasiRustlangCorearchRiscv64ZkSha512sig0(rs1: Long): Long

public actual fun sha512sig0(rs1: Long): Long =
    wasiRustlangCorearchRiscv64ZkSha512sig0(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkSha512sig1")
private external fun wasiRustlangCorearchRiscv64ZkSha512sig1(rs1: Long): Long

public actual fun sha512sig1(rs1: Long): Long =
    wasiRustlangCorearchRiscv64ZkSha512sig1(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkSha512sum0")
private external fun wasiRustlangCorearchRiscv64ZkSha512sum0(rs1: Long): Long

public actual fun sha512sum0(rs1: Long): Long =
    wasiRustlangCorearchRiscv64ZkSha512sum0(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchRiscv64ZkSha512sum1")
private external fun wasiRustlangCorearchRiscv64ZkSha512sum1(rs1: Long): Long

public actual fun sha512sum1(rs1: Long): Long =
    wasiRustlangCorearchRiscv64ZkSha512sum1(rs1).toLong()
