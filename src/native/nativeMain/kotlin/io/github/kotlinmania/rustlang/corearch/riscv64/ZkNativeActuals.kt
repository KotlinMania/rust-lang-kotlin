@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternAes64ds
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternAes64dsm
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternAes64es
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternAes64esm
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternAes64im
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternSha512sig0
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternSha512sig1
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternSha512sum0
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkExternSha512sum1
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externAes64es(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkExternAes64es(rs1, rs2)

public actual fun externAes64esm(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkExternAes64esm(rs1, rs2)

public actual fun externAes64ds(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkExternAes64ds(rs1, rs2)

public actual fun externAes64dsm(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkExternAes64dsm(rs1, rs2)

public actual fun externAes64im(rs1: Long): Long =
    corearchRiscv64ZkExternAes64im(rs1)

public actual fun externSha512sig0(rs1: Long): Long =
    corearchRiscv64ZkExternSha512sig0(rs1)

public actual fun externSha512sig1(rs1: Long): Long =
    corearchRiscv64ZkExternSha512sig1(rs1)

public actual fun externSha512sum0(rs1: Long): Long =
    corearchRiscv64ZkExternSha512sum0(rs1)

public actual fun externSha512sum1(rs1: Long): Long =
    corearchRiscv64ZkExternSha512sum1(rs1)
