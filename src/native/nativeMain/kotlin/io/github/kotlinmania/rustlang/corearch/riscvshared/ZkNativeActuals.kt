@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSha256sig0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSha256sig1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSha256sum0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSha256sum1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm3p0
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm3p1
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm4ed
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkSm4ks
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkXperm464
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZkXperm864

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    corearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    corearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    corearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    corearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    corearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    corearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    corearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    corearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZkXperm464(rs1, rs2)
