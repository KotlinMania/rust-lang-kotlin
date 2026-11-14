// port-lint: source riscv64/zk.rs
package io.github.kotlinmania.rustlang.corearch.riscv64

public expect fun aes64es(rs1: Long, rs2: Long): Long

public expect fun aes64esm(rs1: Long, rs2: Long): Long

public expect fun aes64ds(rs1: Long, rs2: Long): Long

public expect fun aes64dsm(rs1: Long, rs2: Long): Long

public expect fun aes64im(rs1: Long): Long

public expect fun sha512sig0(rs1: Long): Long

public expect fun sha512sig1(rs1: Long): Long

public expect fun sha512sum0(rs1: Long): Long

public expect fun sha512sum1(rs1: Long): Long

/**
 * AES final round encryption instruction for RV64.
 *
 * Uses the two 64-bit source registers to represent the entire AES state, and produces half
 * of the next round output, applying the ShiftRows and SubBytes steps. This instruction must
 * always be implemented such that its execution latency does not depend on the data being
 * operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.7
 */
public fun aes64es(rs1: ULong, rs2: ULong): ULong = aes64es(rs1.toLong(), rs2.toLong()).toULong()

/**
 * AES middle round encryption instruction for RV64.
 *
 * Uses the two 64-bit source registers to represent the entire AES state, and produces half
 * of the next round output, applying the ShiftRows, SubBytes and MixColumns steps. This
 * instruction must always be implemented such that its execution latency does not depend on
 * the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.8
 */
public fun aes64esm(rs1: ULong, rs2: ULong): ULong = aes64esm(rs1.toLong(), rs2.toLong()).toULong()

/**
 * AES final round decryption instruction for RV64.
 *
 * Uses the two 64-bit source registers to represent the entire AES state, and produces half
 * of the next round output, applying the Inverse ShiftRows and SubBytes steps. This
 * instruction must always be implemented such that its execution latency does not depend on
 * the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.5
 */
public fun aes64ds(rs1: ULong, rs2: ULong): ULong = aes64ds(rs1.toLong(), rs2.toLong()).toULong()

/**
 * AES middle round decryption instruction for RV64.
 *
 * Uses the two 64-bit source registers to represent the entire AES state, and produces half
 * of the next round output, applying the Inverse ShiftRows, SubBytes and MixColumns steps.
 * This instruction must always be implemented such that its execution latency does not depend
 * on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.6
 */
public fun aes64dsm(rs1: ULong, rs2: ULong): ULong = aes64dsm(rs1.toLong(), rs2.toLong()).toULong()

/**
 * This instruction implements part of the KeySchedule operation for the AES Block cipher
 * involving the SBox operation.
 *
 * This instruction implements the rotation, SubBytes and Round Constant addition steps of the
 * AES block cipher Key Schedule. This instruction must always be implemented such that its
 * execution latency does not depend on the data being operated on. Note that rnum must be in
 * the range 0x0..0xA. The values 0xB..0xF are reserved.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.10
 *
 * # Note
 *
 * The `RNUM` parameter is expected to be a constant value inside the range of `0 ..= 10`.
 */
public fun aes64ks1i(rs1: ULong, rnum: UByte): ULong {
    run {
        check(rnum <= 10u.toUByte())
    }
    return run {
        val rd: ULong
        Unit
        rd
    }
}

/**
 * This instruction implements part of the KeySchedule operation for the AES Block cipher.
 *
 * This instruction implements the additional XOR’ing of key words as part of the AES block
 * cipher Key Schedule. This instruction must always be implemented such that its execution
 * latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.11
 */
public fun aes64ks2(rs1: ULong, rs2: ULong): ULong =
    run {
        val rd: ULong
        Unit
        rd
    }

/**
 * This instruction accelerates the inverse MixColumns step of the AES Block Cipher, and is used to aid creation of
 * the decryption KeySchedule.
 *
 * The instruction applies the inverse MixColumns transformation to two columns of the state array, packed
 * into a single 64-bit register. It is used to create the inverse cipher KeySchedule, according to the equivalent
 * inverse cipher construction in (Page 23, Section 5.3.5). This instruction must always be implemented
 * such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.9
 */
public fun aes64im(rs1: ULong): ULong = aes64im(rs1.toLong()).toULong()

/**
 * Implements the Sigma0 transformation function as used in the SHA2-512 hash function \[49\]
 * (Section 4.1.3).
 *
 * This instruction is supported for the RV64 base architecture. It implements the Sigma0
 * transform of the SHA2-512 hash function. \[49\]. This instruction must always be
 * implemented such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.37
 */
public fun sha512sig0(rs1: ULong): ULong = sha512sig0(rs1.toLong()).toULong()

/**
 * Implements the Sigma1 transformation function as used in the SHA2-512 hash function \[49\]
 * (Section 4.1.3).
 *
 * This instruction is supported for the RV64 base architecture. It implements the Sigma1
 * transform of the SHA2-512 hash function. \[49\]. This instruction must always be
 * implemented such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.38
 */
public fun sha512sig1(rs1: ULong): ULong = sha512sig1(rs1.toLong()).toULong()

/**
 * Implements the Sum0 transformation function as used in the SHA2-512 hash function \[49\]
 * (Section 4.1.3).
 *
 * This instruction is supported for the RV64 base architecture. It implements the Sum0
 * transform of the SHA2-512 hash function. \[49\]. This instruction must always be
 * implemented such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.39
 */
public fun sha512sum0(rs1: ULong): ULong = sha512sum0(rs1.toLong()).toULong()

/**
 * Implements the Sum1 transformation function as used in the SHA2-512 hash function \[49\]
 * (Section 4.1.3).
 *
 * This instruction is supported for the RV64 base architecture. It implements the Sum1
 * transform of the SHA2-512 hash function. \[49\]. This instruction must always be
 * implemented such that its execution latency does not depend on the data being operated on.
 *
 * Source: RISC-V Cryptography Extensions Volume I: Scalar & Entropy Source Instructions
 *
 * Version: v1.0.1
 *
 * Section: 3.40
 */
public fun sha512sum1(rs1: ULong): ULong = sha512sum1(rs1.toLong()).toULong()
