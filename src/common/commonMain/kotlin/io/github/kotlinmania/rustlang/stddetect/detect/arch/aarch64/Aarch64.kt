// port-lint: source detect/arch/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch.aarch64

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// Aarch64 run-time features.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * FeatAdvSimd (Advanced SIMD/NEON)
     */
    public data object Asimd : Feature()
    /**
     * FEAT_PMULL (Polynomial Multiply) - Implied by `aes` targetFeature
     */
    public data object Pmull : Feature()
    /**
     * FEAT_FP (Floating point support) - Implied by `neon` targetFeature
     */
    public data object Fp : Feature()
    /**
     * FEAT_AES (AES SIMD instructions) & FEAT_PMULL (PMULL(2), 64-bit operand variants)
     */
    public data object Aes : Feature()
    /**
     * FEAT_BF16 (BFloat16 type, plus MM instructions, plus ASIMD support)
     */
    public data object Bf16 : Feature()
    /**
     * FEAT_BTI (Branch Target Identification)
     */
    public data object Bti : Feature()
    /**
     * FEAT_CRC32 (Cyclic Redundancy Check)
     */
    public data object Crc : Feature()
    /**
     * FEAT_CSSC (Common Short Sequence Compression instructions)
     */
    public data object Cssc : Feature()
    /**
     * FEAT_DIT (Data Independent Timing instructions)
     */
    public data object Dit : Feature()
    /**
     * FEAT_DPB (aka dcpop - data cache clean to point of persistence)
     */
    public data object Dpb : Feature()
    /**
     * FEAT_DPB2 (aka dcpodp - data cache clean to point of deep persistence)
     */
    public data object Dpb2 : Feature()
    /**
     * FeatDotProd (Vector Dot-Product - ASIMDDP)
     */
    public data object Dotprod : Feature()
    /**
     * FEAT_ECV (Enhanced Counter Virtualization)
     */
    public data object Ecv : Feature()
    /**
     * FEAT_F32MM (single-precision matrix multiplication)
     */
    public data object F32mm : Feature()
    /**
     * FEAT_F64MM (double-precision matrix multiplication)
     */
    public data object F64mm : Feature()
    /**
     * FEAT_FAMINMAX (FAMIN and FAMAX SIMD/SVE/SME instructions)
     */
    public data object Faminmax : Feature()
    /**
     * FEAT_FCMA (float complex number operations)
     */
    public data object Fcma : Feature()
    /**
     * FEAT_FHM (fp16 multiplication instructions)
     */
    public data object Fhm : Feature()
    /**
     * FEAT_FLAGM (flag manipulation instructions)
     */
    public data object Flagm : Feature()
    /**
     * FEAT_FLAGM2 (flag manipulation instructions)
     */
    public data object Flagm2 : Feature()
    /**
     * FEAT_FP16 (Half-float support)
     */
    public data object Fp16 : Feature()
    /**
     * FEAT_FP8 (F8CVT Instructions)
     */
    public data object Fp8 : Feature()
    /**
     * FEAT_FP8DOT2 (F8DP2 Instructions)
     */
    public data object Fp8dot2 : Feature()
    /**
     * FEAT_FP8DOT4 (F8DP4 Instructions)
     */
    public data object Fp8dot4 : Feature()
    /**
     * FEAT_FP8FMA (F8FMA Instructions)
     */
    public data object Fp8fma : Feature()
    /**
     * FEAT_FPMR (Special-purpose AArch64-FPMR register)
     */
    public data object Fpmr : Feature()
    /**
     * FEAT_FRINTTS (float to integer rounding instructions)
     */
    public data object Frintts : Feature()
    /**
     * FEAT_HBC (Hinted conditional branches)
     */
    public data object Hbc : Feature()
    /**
     * FEAT_I8MM (integer matrix multiplication, plus ASIMD support)
     */
    public data object I8mm : Feature()
    /**
     * FEAT_JSCVT (JavaScript float conversion instructions)
     */
    public data object Jsconv : Feature()
    /**
     * FEAT_LSE (Large System Extension - atomics)
     */
    public data object Lse : Feature()
    /**
     * FEAT_LSE128 (128-bit atomics)
     */
    public data object Lse128 : Feature()
    /**
     * FEAT_LSE2 (unaligned and register-pair atomics)
     */
    public data object Lse2 : Feature()
    /**
     * FEAT_LUT (Lookup Table Instructions)
     */
    public data object Lut : Feature()
    /**
     * FEAT_MOPS (Standardization of memory operations)
     */
    public data object Mops : Feature()
    /**
     * FEAT_MTE & FEAT_MTE2 (Memory Tagging Extension)
     */
    public data object Mte : Feature()
    /**
     * FeatPauth (address authentication)
     */
    public data object Paca : Feature()
    /**
     * FeatPauth (generic authentication)
     */
    public data object Pacg : Feature()
    /**
     * FeatPauthLr
     */
    public data object PauthLr : Feature()
    /**
     * FEAT_RNG (Random Number Generator)
     */
    public data object Rand : Feature()
    /**
     * FEAT_LRCPC (Release consistent Processor consistent)
     */
    public data object Rcpc : Feature()
    /**
     * FEAT_LRCPC2 (RCPC with immediate offsets)
     */
    public data object Rcpc2 : Feature()
    /**
     * FEAT_LRCPC3 (RCPC Instructions v3)
     */
    public data object Rcpc3 : Feature()
    /**
     * FEAT_RDM (Rounding Doubling Multiply - ASIMDRDM)
     */
    public data object Rdm : Feature()
    /**
     * FEAT_SB (speculation barrier)
     */
    public data object Sb : Feature()
    /**
     * FEAT_SHA1 & FEAT_SHA256 (SHA1 & SHA2-256 instructions)
     */
    public data object Sha2 : Feature()
    /**
     * FEAT_SHA512 & FEAT_SHA3 (SHA2-512 & SHA3 instructions)
     */
    public data object Sha3 : Feature()
    /**
     * FEAT_SM3 & FEAT_SM4 (SM3 & SM4 instructions)
     */
    public data object Sm4 : Feature()
    /**
     * FEAT_SME (Scalable Matrix Extension)
     */
    public data object Sme : Feature()
    /**
     * FEAT_SME2 (SME Version 2)
     */
    public data object Sme2 : Feature()
    /**
     * FeatSme2p1 (SME Version 2.1)
     */
    public data object Sme2p1 : Feature()
    /**
     * FEAT_SME_B16B16
     */
    public data object SmeB16b16 : Feature()
    /**
     * FEAT_SME_F16F16 (Non-widening half-precision FP16 to FP16 arithmetic for SME2)
     */
    public data object SmeF16f16 : Feature()
    /**
     * FEAT_SME_F64F64 (Double-precision floating-point outer product instructions)
     */
    public data object SmeF64f64 : Feature()
    /**
     * FEAT_SME_F8F16
     */
    public data object SmeF8f16 : Feature()
    /**
     * FEAT_SME_F8F32
     */
    public data object SmeF8f32 : Feature()
    /**
     * FEAT_SME_FA64 (Full A64 instruction set support in Streaming SVE mode)
     */
    public data object SmeFa64 : Feature()
    /**
     * FEAT_SME_I16I64 (16-bit to 64-bit integer widening outer product instructions)
     */
    public data object SmeI16i64 : Feature()
    /**
     * FeatSmeLutv2 (LUTI4 Instruction)
     */
    public data object SmeLutv2 : Feature()
    /**
     * FEAT_SSBS & FEAT_SSBS2 (speculative store bypass safe)
     */
    public data object Ssbs : Feature()
    /**
     * FEAT_SSVE_FP8DOT2
     */
    public data object SsveFp8dot2 : Feature()
    /**
     * FEAT_SSVE_FP8DOT4
     */
    public data object SsveFp8dot4 : Feature()
    /**
     * FEAT_SSVE_FP8FMA
     */
    public data object SsveFp8fma : Feature()
    /**
     * FEAT_SVE (Scalable Vector Extension)
     */
    public data object Sve : Feature()
    /**
     * FEAT_SVE2 (Scalable Vector Extension 2)
     */
    public data object Sve2 : Feature()
    /**
     * FeatSve2p1 (Scalable Vector Extension 2.1)
     */
    public data object Sve2p1 : Feature()
    /**
     * FEAT_SVE_AES & FEAT_SVE_PMULL128 (SVE2 AES crypto)
     */
    public data object Sve2Aes : Feature()
    /**
     * FEAT_SVE_B16B16 (SVE or SME Z-targeting instructions)
     */
    public data object SveB16b16 : Feature()
    /**
     * FeatSveBitPerm (SVE2 bit permutation instructions)
     */
    public data object Sve2Bitperm : Feature()
    /**
     * FEAT_SVE_SHA3 (SVE2 SHA3 crypto)
     */
    public data object Sve2Sha3 : Feature()
    /**
     * FEAT_SVE_SM4 (SVE2 SM4 crypto)
     */
    public data object Sve2Sm4 : Feature()
    /**
     * FEAT_TME (Transactional Memory Extensions)
     */
    public data object Tme : Feature()
    /**
     * FeatWfxT (WFET and WFIT Instructions)
     */
    public data object Wfxt : Feature()

    private fun kmDiscriminant(): Int =
        when (this) {
        Asimd -> 0
        Pmull -> 1
        Fp -> 2
        Aes -> 3
        Bf16 -> 4
        Bti -> 5
        Crc -> 6
        Cssc -> 7
        Dit -> 8
        Dpb -> 9
        Dpb2 -> 10
        Dotprod -> 11
        Ecv -> 12
        F32mm -> 13
        F64mm -> 14
        Faminmax -> 15
        Fcma -> 16
        Fhm -> 17
        Flagm -> 18
        Flagm2 -> 19
        Fp16 -> 20
        Fp8 -> 21
        Fp8dot2 -> 22
        Fp8dot4 -> 23
        Fp8fma -> 24
        Fpmr -> 25
        Frintts -> 26
        Hbc -> 27
        I8mm -> 28
        Jsconv -> 29
        Lse -> 30
        Lse128 -> 31
        Lse2 -> 32
        Lut -> 33
        Mops -> 34
        Mte -> 35
        Paca -> 36
        Pacg -> 37
        PauthLr -> 38
        Rand -> 39
        Rcpc -> 40
        Rcpc2 -> 41
        Rcpc3 -> 42
        Rdm -> 43
        Sb -> 44
        Sha2 -> 45
        Sha3 -> 46
        Sm4 -> 47
        Sme -> 48
        Sme2 -> 49
        Sme2p1 -> 50
        SmeB16b16 -> 51
        SmeF16f16 -> 52
        SmeF64f64 -> 53
        SmeF8f16 -> 54
        SmeF8f32 -> 55
        SmeFa64 -> 56
        SmeI16i64 -> 57
        SmeLutv2 -> 58
        Ssbs -> 59
        SsveFp8dot2 -> 60
        SsveFp8dot4 -> 61
        SsveFp8fma -> 62
        Sve -> 63
        Sve2 -> 64
        Sve2p1 -> 65
        Sve2Aes -> 66
        SveB16b16 -> 67
        Sve2Bitperm -> 68
        Sve2Sha3 -> 69
        Sve2Sm4 -> 70
        Tme -> 71
        Wfxt -> 72
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun toStr(): String =
        when (this) {
            Feature.Asimd -> "neon"
            Feature.Pmull -> "pmull"
            Feature.Fp -> "fp"
            Feature.Aes -> "aes"
            Feature.Bf16 -> "bf16"
            Feature.Bti -> "bti"
            Feature.Crc -> "crc"
            Feature.Cssc -> "cssc"
            Feature.Dit -> "dit"
            Feature.Dpb -> "dpb"
            Feature.Dpb2 -> "dpb2"
            Feature.Dotprod -> "dotprod"
            Feature.Ecv -> "ecv"
            Feature.F32mm -> "f32mm"
            Feature.F64mm -> "f64mm"
            Feature.Faminmax -> "faminmax"
            Feature.Fcma -> "fcma"
            Feature.Fhm -> "fhm"
            Feature.Flagm -> "flagm"
            Feature.Flagm2 -> "flagm2"
            Feature.Fp16 -> "fp16"
            Feature.Fp8 -> "fp8"
            Feature.Fp8dot2 -> "fp8dot2"
            Feature.Fp8dot4 -> "fp8dot4"
            Feature.Fp8fma -> "fp8fma"
            Feature.Fpmr -> "fpmr"
            Feature.Frintts -> "frintts"
            Feature.Hbc -> "hbc"
            Feature.I8mm -> "i8mm"
            Feature.Jsconv -> "jsconv"
            Feature.Lse -> "lse"
            Feature.Lse128 -> "lse128"
            Feature.Lse2 -> "lse2"
            Feature.Lut -> "lut"
            Feature.Mops -> "mops"
            Feature.Mte -> "mte"
            Feature.Paca -> "paca"
            Feature.Pacg -> "pacg"
            Feature.PauthLr -> "pauth-lr"
            Feature.Rand -> "rand"
            Feature.Rcpc -> "rcpc"
            Feature.Rcpc2 -> "rcpc2"
            Feature.Rcpc3 -> "rcpc3"
            Feature.Rdm -> "rdm"
            Feature.Sb -> "sb"
            Feature.Sha2 -> "sha2"
            Feature.Sha3 -> "sha3"
            Feature.Sm4 -> "sm4"
            Feature.Sme -> "sme"
            Feature.Sme2 -> "sme2"
            Feature.Sme2p1 -> "sme2p1"
            Feature.SmeB16b16 -> "sme-b16b16"
            Feature.SmeF16f16 -> "sme-f16f16"
            Feature.SmeF64f64 -> "sme-f64f64"
            Feature.SmeF8f16 -> "sme-f8f16"
            Feature.SmeF8f32 -> "sme-f8f32"
            Feature.SmeFa64 -> "sme-fa64"
            Feature.SmeI16i64 -> "sme-i16i64"
            Feature.SmeLutv2 -> "sme-lutv2"
            Feature.Ssbs -> "ssbs"
            Feature.SsveFp8dot2 -> "ssve-fp8dot2"
            Feature.SsveFp8dot4 -> "ssve-fp8dot4"
            Feature.SsveFp8fma -> "ssve-fp8fma"
            Feature.Sve -> "sve"
            Feature.Sve2 -> "sve2"
            Feature.Sve2p1 -> "sve2p1"
            Feature.Sve2Aes -> "sve2-aes"
            Feature.SveB16b16 -> "sve-b16b16"
            Feature.Sve2Bitperm -> "sve2-bitperm"
            Feature.Sve2Sha3 -> "sve2-sha3"
            Feature.Sve2Sm4 -> "sve2-sm4"
            Feature.Tme -> "tme"
            Feature.Wfxt -> "wfxt"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.Asimd, Feature.Pmull, Feature.Fp, Feature.Aes, Feature.Bf16, Feature.Bti, Feature.Crc, Feature.Cssc, Feature.Dit, Feature.Dpb, Feature.Dpb2, Feature.Dotprod, Feature.Ecv, Feature.F32mm, Feature.F64mm, Feature.Faminmax, Feature.Fcma, Feature.Fhm, Feature.Flagm, Feature.Flagm2, Feature.Fp16, Feature.Fp8, Feature.Fp8dot2, Feature.Fp8dot4, Feature.Fp8fma, Feature.Fpmr, Feature.Frintts, Feature.Hbc, Feature.I8mm, Feature.Jsconv, Feature.Lse, Feature.Lse128, Feature.Lse2, Feature.Lut, Feature.Mops, Feature.Mte, Feature.Paca, Feature.Pacg, Feature.PauthLr, Feature.Rand, Feature.Rcpc, Feature.Rcpc2, Feature.Rcpc3, Feature.Rdm, Feature.Sb, Feature.Sha2, Feature.Sha3, Feature.Sm4, Feature.Sme, Feature.Sme2, Feature.Sme2p1, Feature.SmeB16b16, Feature.SmeF16f16, Feature.SmeF64f64, Feature.SmeF8f16, Feature.SmeF8f32, Feature.SmeFa64, Feature.SmeI16i64, Feature.SmeLutv2, Feature.Ssbs, Feature.SsveFp8dot2, Feature.SsveFp8dot4, Feature.SsveFp8fma, Feature.Sve, Feature.Sve2, Feature.Sve2p1, Feature.Sve2Aes, Feature.SveB16b16, Feature.Sve2Bitperm, Feature.Sve2Sha3, Feature.Sve2Sm4, Feature.Tme, Feature.Wfxt)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "neon" -> Result.Ok<Feature, Unit>(Feature.Asimd)
                "pmull" -> Result.Ok<Feature, Unit>(Feature.Pmull)
                "fp" -> Result.Ok<Feature, Unit>(Feature.Fp)
                "aes" -> Result.Ok<Feature, Unit>(Feature.Aes)
                "bf16" -> Result.Ok<Feature, Unit>(Feature.Bf16)
                "bti" -> Result.Ok<Feature, Unit>(Feature.Bti)
                "crc" -> Result.Ok<Feature, Unit>(Feature.Crc)
                "cssc" -> Result.Ok<Feature, Unit>(Feature.Cssc)
                "dit" -> Result.Ok<Feature, Unit>(Feature.Dit)
                "dpb" -> Result.Ok<Feature, Unit>(Feature.Dpb)
                "dpb2" -> Result.Ok<Feature, Unit>(Feature.Dpb2)
                "dotprod" -> Result.Ok<Feature, Unit>(Feature.Dotprod)
                "ecv" -> Result.Ok<Feature, Unit>(Feature.Ecv)
                "f32mm" -> Result.Ok<Feature, Unit>(Feature.F32mm)
                "f64mm" -> Result.Ok<Feature, Unit>(Feature.F64mm)
                "faminmax" -> Result.Ok<Feature, Unit>(Feature.Faminmax)
                "fcma" -> Result.Ok<Feature, Unit>(Feature.Fcma)
                "fhm" -> Result.Ok<Feature, Unit>(Feature.Fhm)
                "flagm" -> Result.Ok<Feature, Unit>(Feature.Flagm)
                "flagm2" -> Result.Ok<Feature, Unit>(Feature.Flagm2)
                "fp16" -> Result.Ok<Feature, Unit>(Feature.Fp16)
                "fp8" -> Result.Ok<Feature, Unit>(Feature.Fp8)
                "fp8dot2" -> Result.Ok<Feature, Unit>(Feature.Fp8dot2)
                "fp8dot4" -> Result.Ok<Feature, Unit>(Feature.Fp8dot4)
                "fp8fma" -> Result.Ok<Feature, Unit>(Feature.Fp8fma)
                "fpmr" -> Result.Ok<Feature, Unit>(Feature.Fpmr)
                "frintts" -> Result.Ok<Feature, Unit>(Feature.Frintts)
                "hbc" -> Result.Ok<Feature, Unit>(Feature.Hbc)
                "i8mm" -> Result.Ok<Feature, Unit>(Feature.I8mm)
                "jsconv" -> Result.Ok<Feature, Unit>(Feature.Jsconv)
                "lse" -> Result.Ok<Feature, Unit>(Feature.Lse)
                "lse128" -> Result.Ok<Feature, Unit>(Feature.Lse128)
                "lse2" -> Result.Ok<Feature, Unit>(Feature.Lse2)
                "lut" -> Result.Ok<Feature, Unit>(Feature.Lut)
                "mops" -> Result.Ok<Feature, Unit>(Feature.Mops)
                "mte" -> Result.Ok<Feature, Unit>(Feature.Mte)
                "paca" -> Result.Ok<Feature, Unit>(Feature.Paca)
                "pacg" -> Result.Ok<Feature, Unit>(Feature.Pacg)
                "pauth-lr" -> Result.Ok<Feature, Unit>(Feature.PauthLr)
                "rand" -> Result.Ok<Feature, Unit>(Feature.Rand)
                "rcpc" -> Result.Ok<Feature, Unit>(Feature.Rcpc)
                "rcpc2" -> Result.Ok<Feature, Unit>(Feature.Rcpc2)
                "rcpc3" -> Result.Ok<Feature, Unit>(Feature.Rcpc3)
                "rdm" -> Result.Ok<Feature, Unit>(Feature.Rdm)
                "sb" -> Result.Ok<Feature, Unit>(Feature.Sb)
                "sha2" -> Result.Ok<Feature, Unit>(Feature.Sha2)
                "sha3" -> Result.Ok<Feature, Unit>(Feature.Sha3)
                "sm4" -> Result.Ok<Feature, Unit>(Feature.Sm4)
                "sme" -> Result.Ok<Feature, Unit>(Feature.Sme)
                "sme2" -> Result.Ok<Feature, Unit>(Feature.Sme2)
                "sme2p1" -> Result.Ok<Feature, Unit>(Feature.Sme2p1)
                "sme-b16b16" -> Result.Ok<Feature, Unit>(Feature.SmeB16b16)
                "sme-f16f16" -> Result.Ok<Feature, Unit>(Feature.SmeF16f16)
                "sme-f64f64" -> Result.Ok<Feature, Unit>(Feature.SmeF64f64)
                "sme-f8f16" -> Result.Ok<Feature, Unit>(Feature.SmeF8f16)
                "sme-f8f32" -> Result.Ok<Feature, Unit>(Feature.SmeF8f32)
                "sme-fa64" -> Result.Ok<Feature, Unit>(Feature.SmeFa64)
                "sme-i16i64" -> Result.Ok<Feature, Unit>(Feature.SmeI16i64)
                "sme-lutv2" -> Result.Ok<Feature, Unit>(Feature.SmeLutv2)
                "ssbs" -> Result.Ok<Feature, Unit>(Feature.Ssbs)
                "ssve-fp8dot2" -> Result.Ok<Feature, Unit>(Feature.SsveFp8dot2)
                "ssve-fp8dot4" -> Result.Ok<Feature, Unit>(Feature.SsveFp8dot4)
                "ssve-fp8fma" -> Result.Ok<Feature, Unit>(Feature.SsveFp8fma)
                "sve" -> Result.Ok<Feature, Unit>(Feature.Sve)
                "sve2" -> Result.Ok<Feature, Unit>(Feature.Sve2)
                "sve2p1" -> Result.Ok<Feature, Unit>(Feature.Sve2p1)
                "sve2-aes" -> Result.Ok<Feature, Unit>(Feature.Sve2Aes)
                "sve-b16b16" -> Result.Ok<Feature, Unit>(Feature.SveB16b16)
                "sve2-bitperm" -> Result.Ok<Feature, Unit>(Feature.Sve2Bitperm)
                "sve2-sha3" -> Result.Ok<Feature, Unit>(Feature.Sve2Sha3)
                "sve2-sm4" -> Result.Ok<Feature, Unit>(Feature.Sve2Sm4)
                "tme" -> Result.Ok<Feature, Unit>(Feature.Tme)
                "wfxt" -> Result.Ok<Feature, Unit>(Feature.Wfxt)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun asimd(): Boolean = checkFor(Feature.Asimd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pmull(): Boolean = checkFor(Feature.Pmull)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp(): Boolean = checkFor(Feature.Fp)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun aes(): Boolean = checkFor(Feature.Aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bf16(): Boolean = checkFor(Feature.Bf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bti(): Boolean = checkFor(Feature.Bti)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun crc(): Boolean = checkFor(Feature.Crc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun cssc(): Boolean = checkFor(Feature.Cssc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dit(): Boolean = checkFor(Feature.Dit)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dpb(): Boolean = checkFor(Feature.Dpb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dpb2(): Boolean = checkFor(Feature.Dpb2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dotprod(): Boolean = checkFor(Feature.Dotprod)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ecv(): Boolean = checkFor(Feature.Ecv)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f32mm(): Boolean = checkFor(Feature.F32mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f64mm(): Boolean = checkFor(Feature.F64mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun faminmax(): Boolean = checkFor(Feature.Faminmax)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fcma(): Boolean = checkFor(Feature.Fcma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fhm(): Boolean = checkFor(Feature.Fhm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun flagm(): Boolean = checkFor(Feature.Flagm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun flagm2(): Boolean = checkFor(Feature.Flagm2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp16(): Boolean = checkFor(Feature.Fp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8(): Boolean = checkFor(Feature.Fp8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8dot2(): Boolean = checkFor(Feature.Fp8dot2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8dot4(): Boolean = checkFor(Feature.Fp8dot4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8fma(): Boolean = checkFor(Feature.Fp8fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fpmr(): Boolean = checkFor(Feature.Fpmr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun frintts(): Boolean = checkFor(Feature.Frintts)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun hbc(): Boolean = checkFor(Feature.Hbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun i8mm(): Boolean = checkFor(Feature.I8mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun jsconv(): Boolean = checkFor(Feature.Jsconv)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse(): Boolean = checkFor(Feature.Lse)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse128(): Boolean = checkFor(Feature.Lse128)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse2(): Boolean = checkFor(Feature.Lse2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lut(): Boolean = checkFor(Feature.Lut)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mops(): Boolean = checkFor(Feature.Mops)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mte(): Boolean = checkFor(Feature.Mte)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun paca(): Boolean = checkFor(Feature.Paca)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pacg(): Boolean = checkFor(Feature.Pacg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pauthLr(): Boolean = checkFor(Feature.PauthLr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rand(): Boolean = checkFor(Feature.Rand)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc(): Boolean = checkFor(Feature.Rcpc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc2(): Boolean = checkFor(Feature.Rcpc2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc3(): Boolean = checkFor(Feature.Rcpc3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdm(): Boolean = checkFor(Feature.Rdm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sb(): Boolean = checkFor(Feature.Sb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha2(): Boolean = checkFor(Feature.Sha2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha3(): Boolean = checkFor(Feature.Sha3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm4(): Boolean = checkFor(Feature.Sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme(): Boolean = checkFor(Feature.Sme)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme2(): Boolean = checkFor(Feature.Sme2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme2p1(): Boolean = checkFor(Feature.Sme2p1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeB16b16(): Boolean = checkFor(Feature.SmeB16b16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF16f16(): Boolean = checkFor(Feature.SmeF16f16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF64f64(): Boolean = checkFor(Feature.SmeF64f64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF8f16(): Boolean = checkFor(Feature.SmeF8f16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF8f32(): Boolean = checkFor(Feature.SmeF8f32)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeFa64(): Boolean = checkFor(Feature.SmeFa64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeI16i64(): Boolean = checkFor(Feature.SmeI16i64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeLutv2(): Boolean = checkFor(Feature.SmeLutv2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssbs(): Boolean = checkFor(Feature.Ssbs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8dot2(): Boolean = checkFor(Feature.SsveFp8dot2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8dot4(): Boolean = checkFor(Feature.SsveFp8dot4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8fma(): Boolean = checkFor(Feature.SsveFp8fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve(): Boolean = checkFor(Feature.Sve)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2(): Boolean = checkFor(Feature.Sve2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2p1(): Boolean = checkFor(Feature.Sve2p1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Aes(): Boolean = checkFor(Feature.Sve2Aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sveB16b16(): Boolean = checkFor(Feature.SveB16b16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Bitperm(): Boolean = checkFor(Feature.Sve2Bitperm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Sha3(): Boolean = checkFor(Feature.Sve2Sha3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Sm4(): Boolean = checkFor(Feature.Sve2Sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tme(): Boolean = checkFor(Feature.Tme)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun wfxt(): Boolean = checkFor(Feature.Wfxt)
