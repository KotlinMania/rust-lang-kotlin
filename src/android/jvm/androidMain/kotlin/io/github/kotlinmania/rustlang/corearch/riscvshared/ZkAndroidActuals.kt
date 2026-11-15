@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object RiscvsharedZkJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm3p0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm3p1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm464(rs1, rs2)
