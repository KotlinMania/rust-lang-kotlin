@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkAes64es")
private external fun wasiCorearchRiscv64ZkAes64es(rs1: Long, rs2: Long): Long

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkAes64es(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkAes64esm")
private external fun wasiCorearchRiscv64ZkAes64esm(rs1: Long, rs2: Long): Long

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkAes64esm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkAes64ds")
private external fun wasiCorearchRiscv64ZkAes64ds(rs1: Long, rs2: Long): Long

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkAes64ds(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkAes64dsm")
private external fun wasiCorearchRiscv64ZkAes64dsm(rs1: Long, rs2: Long): Long

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkAes64dsm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkAes64im")
private external fun wasiCorearchRiscv64ZkAes64im(rs1: Long): Long

public actual fun aes64im(rs1: Long): Long =
    wasiCorearchRiscv64ZkAes64im(rs1).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkSha512sig0")
private external fun wasiCorearchRiscv64ZkSha512sig0(rs1: Long): Long

public actual fun sha512sig0(rs1: Long): Long =
    wasiCorearchRiscv64ZkSha512sig0(rs1).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkSha512sig1")
private external fun wasiCorearchRiscv64ZkSha512sig1(rs1: Long): Long

public actual fun sha512sig1(rs1: Long): Long =
    wasiCorearchRiscv64ZkSha512sig1(rs1).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkSha512sum0")
private external fun wasiCorearchRiscv64ZkSha512sum0(rs1: Long): Long

public actual fun sha512sum0(rs1: Long): Long =
    wasiCorearchRiscv64ZkSha512sum0(rs1).toLong()

@kotlin.wasm.WasmImport("corearch", "corearchRiscv64ZkSha512sum1")
private external fun wasiCorearchRiscv64ZkSha512sum1(rs1: Long): Long

public actual fun sha512sum1(rs1: Long): Long =
    wasiCorearchRiscv64ZkSha512sum1(rs1).toLong()
