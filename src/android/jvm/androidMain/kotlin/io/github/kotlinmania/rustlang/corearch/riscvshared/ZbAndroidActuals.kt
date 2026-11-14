@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object RiscvsharedZbJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchRiscvsharedZbOrcB64(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long
}

public actual fun orcB64(rs1: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmulr64(rs1, rs2)
