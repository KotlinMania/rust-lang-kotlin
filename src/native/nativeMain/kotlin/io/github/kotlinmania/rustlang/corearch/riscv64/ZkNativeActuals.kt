@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkAes64ds
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkAes64dsm
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkAes64es
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkAes64esm
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkAes64im
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkSha512sig0
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkSha512sig1
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkSha512sum0
import io.github.kotlinmania.rustlang.corearch.riscv64.cinterop.corearchRiscv64ZkSha512sum1

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkAes64es(rs1, rs2)

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkAes64esm(rs1, rs2)

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkAes64ds(rs1, rs2)

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    corearchRiscv64ZkAes64dsm(rs1, rs2)

public actual fun aes64im(rs1: Long): Long =
    corearchRiscv64ZkAes64im(rs1)

public actual fun sha512sig0(rs1: Long): Long =
    corearchRiscv64ZkSha512sig0(rs1)

public actual fun sha512sig1(rs1: Long): Long =
    corearchRiscv64ZkSha512sig1(rs1)

public actual fun sha512sum0(rs1: Long): Long =
    corearchRiscv64ZkSha512sum0(rs1)

public actual fun sha512sum1(rs1: Long): Long =
    corearchRiscv64ZkSha512sum1(rs1)
