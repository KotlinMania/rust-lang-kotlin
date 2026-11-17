@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSha256sig0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSha256sig1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSha256sum0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSha256sum1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSm3p0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkExternSm3p1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm4ed
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm4ks
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkXperm464
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkXperm864
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    corearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    corearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun externSm3p0(rs1: Int): Int =
    corearchRiscvsharedZkExternSm3p0(rs1)

public actual fun externSm3p1(rs1: Int): Int =
    corearchRiscvsharedZkExternSm3p1(rs1)

public actual fun externSha256sig0(rs1: Int): Int =
    corearchRiscvsharedZkExternSha256sig0(rs1)

public actual fun externSha256sig1(rs1: Int): Int =
    corearchRiscvsharedZkExternSha256sig1(rs1)

public actual fun externSha256sum0(rs1: Int): Int =
    corearchRiscvsharedZkExternSha256sum0(rs1)

public actual fun externSha256sum1(rs1: Int): Int =
    corearchRiscvsharedZkExternSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZkXperm464(rs1, rs2)
