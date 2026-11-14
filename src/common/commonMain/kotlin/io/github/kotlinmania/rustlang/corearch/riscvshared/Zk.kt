// port-lint: source riscv_shared/zk.rs
package io.github.kotlinmania.rustlang.corearch.riscvshared

public expect fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int

public expect fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int

public expect fun sm3p0(rs1: Int): Int

public expect fun sm3p1(rs1: Int): Int

public expect fun sha256sig0(rs1: Int): Int

public expect fun sha256sig1(rs1: Int): Int

public expect fun sha256sum0(rs1: Int): Int

public expect fun sha256sum1(rs1: Int): Int

public expect fun xperm864(rs1: Long, rs2: Long): Long

public expect fun xperm464(rs1: Long, rs2: Long): Long

/**
 * Byte-wise lookup of indicies into a vector in registers.
 *
 * The xperm8 instruction operates on bytes. The rs1 register contains a vector of XLEN/8
 * 8-bit elements. The rs2 register contains a vector of XLEN/8 8-bit indexes. The result is
 * each element in rs2 replaced by the indexed element in rs1, or zero if the index into rs2
 * is out of bounds.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.47
 */
public fun xperm8(rs1: Int, rs2: Int): Int = xperm864(rs1.toLong(), rs2.toLong()).toInt()

/**
 * Nibble-wise lookup of indicies into a vector.
 *
 * The xperm4 instruction operates on nibbles. The rs1 register contains a vector of XLEN/4
 * 4-bit elements. The rs2 register contains a vector of XLEN/4 4-bit indexes. The result is
 * each element in rs2 replaced by the indexed element in rs1, or zero if the index into rs2
 * is out of bounds.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.48
 */
public fun xperm4(rs1: Int, rs2: Int): Int = xperm464(rs1.toLong(), rs2.toLong()).toInt()

/**
 * Implements the Sigma0 transformation function as used in the SHA2-256 hash function \[49\]
 * (Section 4.1.2).
 *
 * This instruction is supported for both RV32 and RV64 base architectures. For RV32, the
 * entire XLEN source register is operated on. For RV64, the low 32 bits of the source
 * register are operated on, and the result sign extended to XLEN bits. Though named for
 * SHA2-256, the instruction works for both the SHA2-224 and SHA2-256 parameterisations as
 * described in \[49\]. This instruction must always be implemented such that its execution
 * latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.27
 */
public fun sha256sig0(rs1: UInt): UInt = sha256sig0(rs1.toInt()).toUInt()

/**
 * Implements the Sigma1 transformation function as used in the SHA2-256 hash function \[49\]
 * (Section 4.1.2).
 *
 * This instruction is supported for both RV32 and RV64 base architectures. For RV32, the
 * entire XLEN source register is operated on. For RV64, the low 32 bits of the source
 * register are operated on, and the result sign extended to XLEN bits. Though named for
 * SHA2-256, the instruction works for both the SHA2-224 and SHA2-256 parameterisations as
 * described in \[49\]. This instruction must always be implemented such that its execution
 * latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.28
 */
public fun sha256sig1(rs1: UInt): UInt = sha256sig1(rs1.toInt()).toUInt()

/**
 * Implements the Sum0 transformation function as used in the SHA2-256 hash function \[49\]
 * (Section 4.1.2).
 *
 * This instruction is supported for both RV32 and RV64 base architectures. For RV32, the
 * entire XLEN source register is operated on. For RV64, the low 32 bits of the source
 * register are operated on, and the result sign extended to XLEN bits. Though named for
 * SHA2-256, the instruction works for both the SHA2-224 and SHA2-256 parameterisations as
 * described in \[49\]. This instruction must always be implemented such that its execution
 * latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.29
 */
public fun sha256sum0(rs1: UInt): UInt = sha256sum0(rs1.toInt()).toUInt()

/**
 * Implements the Sum1 transformation function as used in the SHA2-256 hash function \[49\]
 * (Section 4.1.2).
 *
 * This instruction is supported for both RV32 and RV64 base architectures. For RV32, the
 * entire XLEN source register is operated on. For RV64, the low 32 bits of the source
 * register are operated on, and the result sign extended to XLEN bits. Though named for
 * SHA2-256, the instruction works for both the SHA2-224 and SHA2-256 parameterisations as
 * described in \[49\]. This instruction must always be implemented such that its execution
 * latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.30
 */
public fun sha256sum1(rs1: UInt): UInt = sha256sum1(rs1.toInt()).toUInt()

/**
 * Accelerates the block encrypt/decrypt operation of the SM4 block cipher \[5, 31\].
 *
 * Implements a T-tables in hardware style approach to accelerating the SM4 round function. A
 * byte is extracted from rs2 based on bs, to which the SBox and linear layer transforms are
 * applied, before the result is XOR’d with rs1 and written back to rd. This instruction
 * exists on RV32 and RV64 base architectures. On RV64, the 32-bit result is sign extended to
 * XLEN bits. This instruction must always be implemented such that its execution latency does
 * not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.43
 *
 * # Note
 *
 * The `BS` parameter is expected to be a constant value and only the bottom 2 bits of `bs` are
 * used.
 *
 * # Details
 *
 * Accelerates the round function `F` in the SM4 block cipher algorithm
 *
 * This instruction is included in extension `Zksed`. It's defined as:
 *
 * ```text
 * SM4ED(x, a, BS) = x ⊕ T(ai)
 * ... where
 * ai = a.bytes[BS]
 * T(ai) = L(τ(ai))
 * bi = τ(ai) = SM4-S-Holder(ai)
 * ci = L(bi) = bi ⊕ (bi ≪ 2) ⊕ (bi ≪ 10) ⊕ (bi ≪ 18) ⊕ (bi ≪ 24)
 * SM4ED = (ci ≪ (BS * 8)) ⊕ x
 * ```
 *
 * where `⊕` represents 32-bit xor, and `≪ k` represents rotate left by `k` bits.
 * As is defined above, `T` is a combined transformation of non linear S-Holder transform `τ`
 * and linear layer transform `L`.
 *
 * In the SM4 algorithm, the round function `F` is defined as:
 *
 * ```text
 * F(x0, x1, x2, x3, rk) = x0 ⊕ T(x1 ⊕ x2 ⊕ x3 ⊕ rk)
 * ... where
 * T(A) = L(τ(A))
 * B = τ(A) = (SM4-S-Holder(a0), SM4-S-Holder(a1), SM4-S-Holder(a2), SM4-S-Holder(a3))
 * C = L(B) = B ⊕ (B ≪ 2) ⊕ (B ≪ 10) ⊕ (B ≪ 18) ⊕ (B ≪ 24)
 * ```
 *
 * It can be implemented by `sm4ed` instruction like:
 *
 * ```kotlin
 * val a = x1 xor x2 xor x3 xor rk
 * val c0 = sm4ed(x0, a, 0)
 * val c1 = sm4ed(c0, a, 1)
 * // c1 represents c[0..=1], etc.
 * val c2 = sm4ed(c1, a, 2)
 * val c3 = sm4ed(c2, a, 3)
 * return c3
 * // c3 represents c[0..=3]
 * ```
 */
public fun sm4ed(rs1: UInt, rs2: UInt, bs: UByte): UInt {
    run {
        check(bs < 4u.toUByte())
    }
    return sm4ed(rs1.toInt(), rs2.toInt(), bs.toInt()).toUInt()
}

/**
 * Accelerates the Key Schedule operation of the SM4 block cipher \[5, 31\] with `bs = 0`.
 *
 * Implements a T-tables in hardware style approach to accelerating the SM4 Key Schedule. A
 * byte is extracted from rs2 based on bs, to which the SBox and linear layer transforms are
 * applied, before the result is XOR’d with rs1 and written back to rd. This instruction
 * exists on RV32 and RV64 base architectures. On RV64, the 32-bit result is sign extended to
 * XLEN bits. This instruction must always be implemented such that its execution latency does
 * not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.44
 *
 * # Note
 *
 * The `BS` parameter is expected to be a constant value and only the bottom 2 bits of `bs` are
 * used.
 *
 * # Details
 *
 * Accelerates the round function `F` in the SM4 block cipher algorithm
 *
 * This instruction is included in extension `Zksed`. It's defined as:
 *
 * ```text
 * SM4ED(x, a, BS) = x ⊕ T(ai)
 * ... where
 * ai = a.bytes[BS]
 * T(ai) = L(τ(ai))
 * bi = τ(ai) = SM4-S-Holder(ai)
 * ci = L(bi) = bi ⊕ (bi ≪ 2) ⊕ (bi ≪ 10) ⊕ (bi ≪ 18) ⊕ (bi ≪ 24)
 * SM4ED = (ci ≪ (BS * 8)) ⊕ x
 * ```
 *
 * where `⊕` represents 32-bit xor, and `≪ k` represents rotate left by `k` bits.
 * As is defined above, `T` is a combined transformation of non linear S-Holder transform `τ`
 * and linear layer transform `L`.
 *
 * In the SM4 algorithm, the round function `F` is defined as:
 *
 * ```text
 * F(x0, x1, x2, x3, rk) = x0 ⊕ T(x1 ⊕ x2 ⊕ x3 ⊕ rk)
 * ... where
 * T(A) = L(τ(A))
 * B = τ(A) = (SM4-S-Holder(a0), SM4-S-Holder(a1), SM4-S-Holder(a2), SM4-S-Holder(a3))
 * C = L(B) = B ⊕ (B ≪ 2) ⊕ (B ≪ 10) ⊕ (B ≪ 18) ⊕ (B ≪ 24)
 * ```
 *
 * It can be implemented by `sm4ed` instruction like:
 *
 * ```kotlin
 * val a = x1 xor x2 xor x3 xor rk
 * val c0 = sm4ed(x0, a, 0)
 * val c1 = sm4ed(c0, a, 1)
 * // c1 represents c[0..=1], etc.
 * val c2 = sm4ed(c1, a, 2)
 * val c3 = sm4ed(c2, a, 3)
 * return c3
 * // c3 represents c[0..=3]
 * ```
 */
public fun sm4ks(rs1: UInt, rs2: UInt, bs: UByte): UInt {
    run {
        check(bs < 4u.toUByte())
    }
    return sm4ks(rs1.toInt(), rs2.toInt(), bs.toInt()).toUInt()
}

/**
 * Implements the P0 transformation function as used in the SM3 hash function [4, 30].
 *
 * This instruction is supported for the RV32 and RV64 base architectures. It implements the
 * P0 transform of the SM3 hash function [4, 30]. This instruction must always be implemented
 * such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.41
 *
 * # Details
 *
 * `P0` transformation function as is used in the SM3 hash algorithm
 *
 * This function is included in `Zksh` extension. It's defined as:
 *
 * ```text
 * P0(X) = X ⊕ (X ≪ 9) ⊕ (X ≪ 17)
 * ```
 *
 * where `⊕` represents 32-bit xor, and `≪ k` represents rotate left by `k` bits.
 *
 * In the SM3 algorithm, the `P0` transformation is used as `E ← P0(TT2)` when the
 * compression function `CF` uses the intermediate value `TT2` to calculate
 * the variable `E` in one iteration for subsequent processes.
 */
public fun sm3p0(rs1: UInt): UInt = sm3p0(rs1.toInt()).toUInt()

/**
 * Implements the P1 transformation function as used in the SM3 hash function [4, 30].
 *
 * This instruction is supported for the RV32 and RV64 base architectures. It implements the
 * P1 transform of the SM3 hash function [4, 30]. This instruction must always be implemented
 * such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.42
 *
 * # Details
 *
 * `P1` transformation function as is used in the SM3 hash algorithm
 *
 * This function is included in `Zksh` extension. It's defined as:
 *
 * ```text
 * P1(X) = X ⊕ (X ≪ 15) ⊕ (X ≪ 23)
 * ```
 *
 * where `⊕` represents 32-bit xor, and `≪ k` represents rotate left by `k` bits.
 *
 * In the SM3 algorithm, the `P1` transformation is used to expand message,
 * where expanded word `Wj` can be generated from the previous words.
 * The whole process can be described as the following pseudocode:
 *
 * ```text
 * FOR j=16 TO 67
 *     Wj ← P1(Wj−16 ⊕ Wj−9 ⊕ (Wj−3 ≪ 15)) ⊕ (Wj−13 ≪ 7) ⊕ Wj−6
 * ENDFOR
 * ```
 */
public fun sm3p1(rs1: UInt): UInt = sm3p1(rs1.toInt()).toUInt()
