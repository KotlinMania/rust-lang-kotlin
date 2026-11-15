@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object CorearchRiscvsharedZbJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbOrcB64(rs1: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long
}

public actual fun orcB64(rs1: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmulr64(rs1, rs2)
