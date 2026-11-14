// port-lint: source riscv_shared/zb.rs
package io.github.kotlinmania.rustlang.corearch.riscvshared

public expect fun orcB64(rs1: Long): Long

public expect fun clmul64(rs1: Long, rs2: Long): Long

public expect fun clmulh64(rs1: Long, rs2: Long): Long

public expect fun clmulr64(rs1: Long, rs2: Long): Long

/**
 * Bitwise OR-Combine, byte granule
 *
 * Combines the bits within every byte through a reciprocal bitwise logical OR. This sets the bits of each byte in
 * the result rd to all zeros if no bit within the respective byte of rs is set, or to all ones if any bit within the
 * respective byte of rs is set.
 *
 * Source: RISC-V Bit-Manipulation ISA-extensions
 *
 * Version: v1.0.0
 *
 * Section: 2.24
 */
public fun orcB(rs: Int): Int = orcB64(rs.toLong()).toInt()

/**
 * Carry-less multiply (low-part)
 *
 * clmul produces the lower half of the 2·XLEN carry-less product.
 *
 * Source: RISC-V Bit-Manipulation ISA-extensions
 *
 * Version: v1.0.0
 *
 * Section: 2.11
 */
public fun clmul(rs1: Int, rs2: Int): Int = clmul64(rs1.toLong(), rs2.toLong()).toInt()

/**
 * Carry-less multiply (high-part)
 *
 * clmulh produces the upper half of the 2·XLEN carry-less product.
 *
 * Source: RISC-V Bit-Manipulation ISA-extensions
 *
 * Version: v1.0.0
 *
 * Section: 2.12
 */
public fun clmulh(rs1: Int, rs2: Int): Int = clmulh64(rs1.toLong(), rs2.toLong()).toInt()

/**
 * Carry-less multiply (reversed)
 *
 * clmulr produces bits 2·XLEN−2:XLEN-1 of the 2·XLEN carry-less product.
 *
 * Source: RISC-V Bit-Manipulation ISA-extensions
 *
 * Version: v1.0.0
 *
 * Section: 2.13
 */
public fun clmulr(rs1: Int, rs2: Int): Int = clmulr64(rs1.toLong(), rs2.toLong()).toInt()
