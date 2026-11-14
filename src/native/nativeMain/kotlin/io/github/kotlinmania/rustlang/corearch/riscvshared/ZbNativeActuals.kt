@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZbClmul64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZbClmulh64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZbClmulr64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.corearchRiscvsharedZbOrcB64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun orcB64(rs1: Long): Long =
    corearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    corearchRiscvsharedZbClmulr64(rs1, rs2)
