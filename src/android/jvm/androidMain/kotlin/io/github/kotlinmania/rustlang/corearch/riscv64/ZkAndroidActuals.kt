@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

private object Riscv64ZkJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchRiscv64ZkExternAes64es(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternAes64esm(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternAes64ds(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternAes64dsm(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternAes64im(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternSha512sig0(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternSha512sig1(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternSha512sum0(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkExternSha512sum1(rs1: Long): Long
}

public actual fun externAes64es(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternAes64es(rs1, rs2)

public actual fun externAes64esm(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternAes64esm(rs1, rs2)

public actual fun externAes64ds(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternAes64ds(rs1, rs2)

public actual fun externAes64dsm(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternAes64dsm(rs1, rs2)

public actual fun externAes64im(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternAes64im(rs1)

public actual fun externSha512sig0(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternSha512sig0(rs1)

public actual fun externSha512sig1(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternSha512sig1(rs1)

public actual fun externSha512sum0(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternSha512sum0(rs1)

public actual fun externSha512sum1(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkExternSha512sum1(rs1)
