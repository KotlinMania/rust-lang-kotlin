@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscv64

private object Riscv64ZkJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchRiscv64ZkAes64es(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkAes64esm(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkAes64ds(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkAes64dsm(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkAes64im(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkSha512sig0(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkSha512sig1(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkSha512sum0(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscv64ZkSha512sum1(rs1: Long): Long
}

public actual fun aes64es(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkAes64es(rs1, rs2)

public actual fun aes64esm(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkAes64esm(rs1, rs2)

public actual fun aes64ds(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkAes64ds(rs1, rs2)

public actual fun aes64dsm(rs1: Long, rs2: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkAes64dsm(rs1, rs2)

public actual fun aes64im(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkAes64im(rs1)

public actual fun sha512sig0(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkSha512sig0(rs1)

public actual fun sha512sig1(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkSha512sig1(rs1)

public actual fun sha512sum0(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkSha512sum0(rs1)

public actual fun sha512sum1(rs1: Long): Long =
    Riscv64ZkJniBridge.corearchRiscv64ZkSha512sum1(rs1)
