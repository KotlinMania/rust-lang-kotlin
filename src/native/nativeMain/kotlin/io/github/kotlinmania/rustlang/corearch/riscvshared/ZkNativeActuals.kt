@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSha256sig0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSha256sig1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSha256sum0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSha256sum1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSm3p0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSm3p1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSm4ed
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkSm4ks
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkXperm464
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZkXperm864
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    rustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    rustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    rustlangCorearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    rustlangCorearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    rustlangCorearchRiscvsharedZkXperm464(rs1, rs2)
