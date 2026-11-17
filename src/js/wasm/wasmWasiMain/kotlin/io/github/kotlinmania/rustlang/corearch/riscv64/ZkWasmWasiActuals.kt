@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternAes64es")
private external fun wasiCorearchRiscv64ZkExternAes64es(rs1: Long, rs2: Long): Long

public actual fun externAes64es(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkExternAes64es(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternAes64esm")
private external fun wasiCorearchRiscv64ZkExternAes64esm(rs1: Long, rs2: Long): Long

public actual fun externAes64esm(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkExternAes64esm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternAes64ds")
private external fun wasiCorearchRiscv64ZkExternAes64ds(rs1: Long, rs2: Long): Long

public actual fun externAes64ds(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkExternAes64ds(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternAes64dsm")
private external fun wasiCorearchRiscv64ZkExternAes64dsm(rs1: Long, rs2: Long): Long

public actual fun externAes64dsm(rs1: Long, rs2: Long): Long =
    wasiCorearchRiscv64ZkExternAes64dsm(rs1, rs2).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternAes64im")
private external fun wasiCorearchRiscv64ZkExternAes64im(rs1: Long): Long

public actual fun externAes64im(rs1: Long): Long =
    wasiCorearchRiscv64ZkExternAes64im(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternSha512sig0")
private external fun wasiCorearchRiscv64ZkExternSha512sig0(rs1: Long): Long

public actual fun externSha512sig0(rs1: Long): Long =
    wasiCorearchRiscv64ZkExternSha512sig0(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternSha512sig1")
private external fun wasiCorearchRiscv64ZkExternSha512sig1(rs1: Long): Long

public actual fun externSha512sig1(rs1: Long): Long =
    wasiCorearchRiscv64ZkExternSha512sig1(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternSha512sum0")
private external fun wasiCorearchRiscv64ZkExternSha512sum0(rs1: Long): Long

public actual fun externSha512sum0(rs1: Long): Long =
    wasiCorearchRiscv64ZkExternSha512sum0(rs1).toLong()

@kotlin.wasm.WasmImport("kmcore", "corearchRiscv64ZkExternSha512sum1")
private external fun wasiCorearchRiscv64ZkExternSha512sum1(rs1: Long): Long

public actual fun externSha512sum1(rs1: Long): Long =
    wasiCorearchRiscv64ZkExternSha512sum1(rs1).toLong()
