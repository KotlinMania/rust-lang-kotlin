// port-lint: source detect/arch/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

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
     * FEAT_AES (AES SIMD instructions) & FEAT_PMULL (PMULL{2}, 64-bit operand variants)
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

    public open fun toStr(): String =
        when (this) {
            Feature.asimd -> "neon"
            Feature.pmull -> "pmull"
            Feature.fp -> "fp"
            Feature.aes -> "aes"
            Feature.bf16 -> "bf16"
            Feature.bti -> "bti"
            Feature.crc -> "crc"
            Feature.cssc -> "cssc"
            Feature.dit -> "dit"
            Feature.dpb -> "dpb"
            Feature.dpb2 -> "dpb2"
            Feature.dotprod -> "dotprod"
            Feature.ecv -> "ecv"
            Feature.f32mm -> "f32mm"
            Feature.f64mm -> "f64mm"
            Feature.faminmax -> "faminmax"
            Feature.fcma -> "fcma"
            Feature.fhm -> "fhm"
            Feature.flagm -> "flagm"
            Feature.flagm2 -> "flagm2"
            Feature.fp16 -> "fp16"
            Feature.fp8 -> "fp8"
            Feature.fp8dot2 -> "fp8dot2"
            Feature.fp8dot4 -> "fp8dot4"
            Feature.fp8fma -> "fp8fma"
            Feature.fpmr -> "fpmr"
            Feature.frintts -> "frintts"
            Feature.hbc -> "hbc"
            Feature.i8mm -> "i8mm"
            Feature.jsconv -> "jsconv"
            Feature.lse -> "lse"
            Feature.lse128 -> "lse128"
            Feature.lse2 -> "lse2"
            Feature.lut -> "lut"
            Feature.mops -> "mops"
            Feature.mte -> "mte"
            Feature.paca -> "paca"
            Feature.pacg -> "pacg"
            Feature.pauthLr -> "pauth-lr"
            Feature.rand -> "rand"
            Feature.rcpc -> "rcpc"
            Feature.rcpc2 -> "rcpc2"
            Feature.rcpc3 -> "rcpc3"
            Feature.rdm -> "rdm"
            Feature.sb -> "sb"
            Feature.sha2 -> "sha2"
            Feature.sha3 -> "sha3"
            Feature.sm4 -> "sm4"
            Feature.sme -> "sme"
            Feature.sme2 -> "sme2"
            Feature.sme2p1 -> "sme2p1"
            Feature.smeB16b16 -> "sme-b16b16"
            Feature.smeF16f16 -> "sme-f16f16"
            Feature.smeF64f64 -> "sme-f64f64"
            Feature.smeF8f16 -> "sme-f8f16"
            Feature.smeF8f32 -> "sme-f8f32"
            Feature.smeFa64 -> "sme-fa64"
            Feature.smeI16i64 -> "sme-i16i64"
            Feature.smeLutv2 -> "sme-lutv2"
            Feature.ssbs -> "ssbs"
            Feature.ssveFp8dot2 -> "ssve-fp8dot2"
            Feature.ssveFp8dot4 -> "ssve-fp8dot4"
            Feature.ssveFp8fma -> "ssve-fp8fma"
            Feature.sve -> "sve"
            Feature.sve2 -> "sve2"
            Feature.sve2p1 -> "sve2p1"
            Feature.sve2Aes -> "sve2-aes"
            Feature.sveB16b16 -> "sve-b16b16"
            Feature.sve2Bitperm -> "sve2-bitperm"
            Feature.sve2Sha3 -> "sve2-sha3"
            Feature.sve2Sm4 -> "sve2-sm4"
            Feature.tme -> "tme"
            Feature.wfxt -> "wfxt"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::asimd, Feature::pmull, Feature::fp, Feature::aes, Feature::bf16, Feature::bti, Feature::crc, Feature::cssc, Feature::dit, Feature::dpb, Feature::dpb2, Feature::dotprod, Feature::ecv, Feature::f32mm, Feature::f64mm, Feature::faminmax, Feature::fcma, Feature::fhm, Feature::flagm, Feature::flagm2, Feature::fp16, Feature::fp8, Feature::fp8dot2, Feature::fp8dot4, Feature::fp8fma, Feature::fpmr, Feature::frintts, Feature::hbc, Feature::i8mm, Feature::jsconv, Feature::lse, Feature::lse128, Feature::lse2, Feature::lut, Feature::mops, Feature::mte, Feature::paca, Feature::pacg, Feature::pauthLr, Feature::rand, Feature::rcpc, Feature::rcpc2, Feature::rcpc3, Feature::rdm, Feature::sb, Feature::sha2, Feature::sha3, Feature::sm4, Feature::sme, Feature::sme2, Feature::sme2p1, Feature::smeB16b16, Feature::smeF16f16, Feature::smeF64f64, Feature::smeF8f16, Feature::smeF8f32, Feature::smeFa64, Feature::smeI16i64, Feature::smeLutv2, Feature::ssbs, Feature::ssveFp8dot2, Feature::ssveFp8dot4, Feature::ssveFp8fma, Feature::sve, Feature::sve2, Feature::sve2p1, Feature::sve2Aes, Feature::sveB16b16, Feature::sve2Bitperm, Feature::sve2Sha3, Feature::sve2Sm4, Feature::tme, Feature::wfxt)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "neon" -> Result.Ok<Feature, Unit>(Feature::asimd)
                "pmull" -> Result.Ok<Feature, Unit>(Feature::pmull)
                "fp" -> Result.Ok<Feature, Unit>(Feature::fp)
                "aes" -> Result.Ok<Feature, Unit>(Feature::aes)
                "bf16" -> Result.Ok<Feature, Unit>(Feature::bf16)
                "bti" -> Result.Ok<Feature, Unit>(Feature::bti)
                "crc" -> Result.Ok<Feature, Unit>(Feature::crc)
                "cssc" -> Result.Ok<Feature, Unit>(Feature::cssc)
                "dit" -> Result.Ok<Feature, Unit>(Feature::dit)
                "dpb" -> Result.Ok<Feature, Unit>(Feature::dpb)
                "dpb2" -> Result.Ok<Feature, Unit>(Feature::dpb2)
                "dotprod" -> Result.Ok<Feature, Unit>(Feature::dotprod)
                "ecv" -> Result.Ok<Feature, Unit>(Feature::ecv)
                "f32mm" -> Result.Ok<Feature, Unit>(Feature::f32mm)
                "f64mm" -> Result.Ok<Feature, Unit>(Feature::f64mm)
                "faminmax" -> Result.Ok<Feature, Unit>(Feature::faminmax)
                "fcma" -> Result.Ok<Feature, Unit>(Feature::fcma)
                "fhm" -> Result.Ok<Feature, Unit>(Feature::fhm)
                "flagm" -> Result.Ok<Feature, Unit>(Feature::flagm)
                "flagm2" -> Result.Ok<Feature, Unit>(Feature::flagm2)
                "fp16" -> Result.Ok<Feature, Unit>(Feature::fp16)
                "fp8" -> Result.Ok<Feature, Unit>(Feature::fp8)
                "fp8dot2" -> Result.Ok<Feature, Unit>(Feature::fp8dot2)
                "fp8dot4" -> Result.Ok<Feature, Unit>(Feature::fp8dot4)
                "fp8fma" -> Result.Ok<Feature, Unit>(Feature::fp8fma)
                "fpmr" -> Result.Ok<Feature, Unit>(Feature::fpmr)
                "frintts" -> Result.Ok<Feature, Unit>(Feature::frintts)
                "hbc" -> Result.Ok<Feature, Unit>(Feature::hbc)
                "i8mm" -> Result.Ok<Feature, Unit>(Feature::i8mm)
                "jsconv" -> Result.Ok<Feature, Unit>(Feature::jsconv)
                "lse" -> Result.Ok<Feature, Unit>(Feature::lse)
                "lse128" -> Result.Ok<Feature, Unit>(Feature::lse128)
                "lse2" -> Result.Ok<Feature, Unit>(Feature::lse2)
                "lut" -> Result.Ok<Feature, Unit>(Feature::lut)
                "mops" -> Result.Ok<Feature, Unit>(Feature::mops)
                "mte" -> Result.Ok<Feature, Unit>(Feature::mte)
                "paca" -> Result.Ok<Feature, Unit>(Feature::paca)
                "pacg" -> Result.Ok<Feature, Unit>(Feature::pacg)
                "pauth-lr" -> Result.Ok<Feature, Unit>(Feature::pauthLr)
                "rand" -> Result.Ok<Feature, Unit>(Feature::rand)
                "rcpc" -> Result.Ok<Feature, Unit>(Feature::rcpc)
                "rcpc2" -> Result.Ok<Feature, Unit>(Feature::rcpc2)
                "rcpc3" -> Result.Ok<Feature, Unit>(Feature::rcpc3)
                "rdm" -> Result.Ok<Feature, Unit>(Feature::rdm)
                "sb" -> Result.Ok<Feature, Unit>(Feature::sb)
                "sha2" -> Result.Ok<Feature, Unit>(Feature::sha2)
                "sha3" -> Result.Ok<Feature, Unit>(Feature::sha3)
                "sm4" -> Result.Ok<Feature, Unit>(Feature::sm4)
                "sme" -> Result.Ok<Feature, Unit>(Feature::sme)
                "sme2" -> Result.Ok<Feature, Unit>(Feature::sme2)
                "sme2p1" -> Result.Ok<Feature, Unit>(Feature::sme2p1)
                "sme-b16b16" -> Result.Ok<Feature, Unit>(Feature::smeB16b16)
                "sme-f16f16" -> Result.Ok<Feature, Unit>(Feature::smeF16f16)
                "sme-f64f64" -> Result.Ok<Feature, Unit>(Feature::smeF64f64)
                "sme-f8f16" -> Result.Ok<Feature, Unit>(Feature::smeF8f16)
                "sme-f8f32" -> Result.Ok<Feature, Unit>(Feature::smeF8f32)
                "sme-fa64" -> Result.Ok<Feature, Unit>(Feature::smeFa64)
                "sme-i16i64" -> Result.Ok<Feature, Unit>(Feature::smeI16i64)
                "sme-lutv2" -> Result.Ok<Feature, Unit>(Feature::smeLutv2)
                "ssbs" -> Result.Ok<Feature, Unit>(Feature::ssbs)
                "ssve-fp8dot2" -> Result.Ok<Feature, Unit>(Feature::ssveFp8dot2)
                "ssve-fp8dot4" -> Result.Ok<Feature, Unit>(Feature::ssveFp8dot4)
                "ssve-fp8fma" -> Result.Ok<Feature, Unit>(Feature::ssveFp8fma)
                "sve" -> Result.Ok<Feature, Unit>(Feature::sve)
                "sve2" -> Result.Ok<Feature, Unit>(Feature::sve2)
                "sve2p1" -> Result.Ok<Feature, Unit>(Feature::sve2p1)
                "sve2-aes" -> Result.Ok<Feature, Unit>(Feature::sve2Aes)
                "sve-b16b16" -> Result.Ok<Feature, Unit>(Feature::sveB16b16)
                "sve2-bitperm" -> Result.Ok<Feature, Unit>(Feature::sve2Bitperm)
                "sve2-sha3" -> Result.Ok<Feature, Unit>(Feature::sve2Sha3)
                "sve2-sm4" -> Result.Ok<Feature, Unit>(Feature::sve2Sm4)
                "tme" -> Result.Ok<Feature, Unit>(Feature::tme)
                "wfxt" -> Result.Ok<Feature, Unit>(Feature::wfxt)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun asimd(): Boolean = checkFor(::asimd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pmull(): Boolean = checkFor(::pmull)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp(): Boolean = checkFor(Feature::fp)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun aes(): Boolean = checkFor(::aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bf16(): Boolean = checkFor(Feature::bf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bti(): Boolean = checkFor(::bti)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun crc(): Boolean = checkFor(::crc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun cssc(): Boolean = checkFor(::cssc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dit(): Boolean = checkFor(::dit)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dpb(): Boolean = checkFor(::dpb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dpb2(): Boolean = checkFor(::dpb2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun dotprod(): Boolean = checkFor(::dotprod)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ecv(): Boolean = checkFor(::ecv)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f32mm(): Boolean = checkFor(::f32mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f64mm(): Boolean = checkFor(::f64mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun faminmax(): Boolean = checkFor(::faminmax)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fcma(): Boolean = checkFor(::fcma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fhm(): Boolean = checkFor(::fhm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun flagm(): Boolean = checkFor(::flagm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun flagm2(): Boolean = checkFor(::flagm2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp16(): Boolean = checkFor(::fp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8(): Boolean = checkFor(::fp8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8dot2(): Boolean = checkFor(::fp8dot2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8dot4(): Boolean = checkFor(::fp8dot4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fp8fma(): Boolean = checkFor(::fp8fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fpmr(): Boolean = checkFor(::fpmr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun frintts(): Boolean = checkFor(::frintts)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun hbc(): Boolean = checkFor(::hbc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun i8mm(): Boolean = checkFor(::i8mm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun jsconv(): Boolean = checkFor(::jsconv)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse(): Boolean = checkFor(::lse)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse128(): Boolean = checkFor(::lse128)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lse2(): Boolean = checkFor(::lse2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lut(): Boolean = checkFor(::lut)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mops(): Boolean = checkFor(::mops)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mte(): Boolean = checkFor(::mte)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun paca(): Boolean = checkFor(::paca)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pacg(): Boolean = checkFor(::pacg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pauthLr(): Boolean = checkFor(::pauthLr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rand(): Boolean = checkFor(::rand)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc(): Boolean = checkFor(::rcpc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc2(): Boolean = checkFor(::rcpc2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rcpc3(): Boolean = checkFor(::rcpc3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdm(): Boolean = checkFor(::rdm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sb(): Boolean = checkFor(::sb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha2(): Boolean = checkFor(::sha2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha3(): Boolean = checkFor(::sha3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm4(): Boolean = checkFor(::sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme(): Boolean = checkFor(::sme)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme2(): Boolean = checkFor(::sme2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sme2p1(): Boolean = checkFor(::sme2p1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeB16b16(): Boolean = checkFor(::smeB16b16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF16f16(): Boolean = checkFor(::smeF16f16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF64f64(): Boolean = checkFor(::smeF64f64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF8f16(): Boolean = checkFor(::smeF8f16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeF8f32(): Boolean = checkFor(::smeF8f32)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeFa64(): Boolean = checkFor(::smeFa64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeI16i64(): Boolean = checkFor(::smeI16i64)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun smeLutv2(): Boolean = checkFor(::smeLutv2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssbs(): Boolean = checkFor(::ssbs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8dot2(): Boolean = checkFor(::ssveFp8dot2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8dot4(): Boolean = checkFor(::ssveFp8dot4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssveFp8fma(): Boolean = checkFor(::ssveFp8fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve(): Boolean = checkFor(::sve)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2(): Boolean = checkFor(::sve2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2p1(): Boolean = checkFor(::sve2p1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Aes(): Boolean = checkFor(::sve2Aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sveB16b16(): Boolean = checkFor(::sveB16b16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Bitperm(): Boolean = checkFor(::sve2Bitperm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Sha3(): Boolean = checkFor(::sve2Sha3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sve2Sm4(): Boolean = checkFor(::sve2Sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tme(): Boolean = checkFor(::tme)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun wfxt(): Boolean = checkFor(::wfxt)
