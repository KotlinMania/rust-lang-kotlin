@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZbClmul64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZbClmulh64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZbClmulr64
import io.github.kotlinmania.rustlang.corearch.riscvshared.cinterop.rustlangCorearchRiscvsharedZbOrcB64
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun orcB64(rs1: Long): Long =
    rustlangCorearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    rustlangCorearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    rustlangCorearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    rustlangCorearchRiscvsharedZbClmulr64(rs1, rs2)
