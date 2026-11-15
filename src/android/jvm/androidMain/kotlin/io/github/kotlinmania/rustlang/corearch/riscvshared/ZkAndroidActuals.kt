@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object CorearchRiscvsharedZkJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm3p0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm3p1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkXperm464(rs1, rs2)
