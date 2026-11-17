@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object RiscvsharedZkJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm3p0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm3p1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun externSm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm4ed(rs1, rs2, bs)

public actual fun externSm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm4ks(rs1, rs2, bs)

public actual fun externSm3p0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm3p0(rs1)

public actual fun externSm3p1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm3p1(rs1)

public actual fun externSha256sig0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sig0(rs1)

public actual fun externSha256sig1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sig1(rs1)

public actual fun externSha256sum0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sum0(rs1)

public actual fun externSha256sum1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm464(rs1, rs2)
