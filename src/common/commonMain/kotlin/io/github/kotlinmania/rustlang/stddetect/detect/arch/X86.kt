// port-lint: source detect/arch/x86.rs
package io.github.kotlinmania.rustlang.stddetect.detect.arch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.checkFor

// This module implements minimal run-time feature detection for x86.
//
// The features are detected using the `detectFeatures` function below.
// This function uses the CPUID instruction to read the feature flags from the
// CPU and encodes them in a `Int` where each bit position represents
// whether a feature is available (bit is set) or unavailable (bit is cleared).
//
// The enum `Feature` is used to map bit positions to feature names, and the
// the `__crate.detect.checkFor` macro is used to map string literals (e.g.,
// "avx") to these bit positions (e.g., `Feature.avx`).
//
// The run-time feature detection is performed by the
// `__crate.detect.checkFor(Feature) -> Boolean` function. On its first call,
// this functions queries the CPU for the available features and stores them
// in a global `AtomicUsize` variable. The query is performed by just checking
// whether the feature bit in this global variable is set or cleared.

/**
 * Each variant denotes a position in a bitset for a particular feature.
 *
 * PLEASE: do not use this, it is an implementation detail subject
 * to change.
 */
public sealed class Feature {
    /**
     * AES (Advanced Encryption Standard New Instructions AES-NI)
     */
    public data object Aes : Feature()
    /**
     * CLMUL (Carry-less Multiplication)
     */
    public data object Pclmulqdq : Feature()
    /**
     * RDRAND
     */
    public data object Rdrand : Feature()
    /**
     * RDSEED
     */
    public data object Rdseed : Feature()
    /**
     * TSC (Time Stamp Counter)
     */
    public data object Tsc : Feature()
    /**
     * MMX (MultiMedia eXtensions)
     */
    public data object Mmx : Feature()
    /**
     * SSE (Streaming SIMD Extensions)
     */
    public data object Sse : Feature()
    /**
     * SSE2 (Streaming SIMD Extensions 2)
     */
    public data object Sse2 : Feature()
    /**
     * SSE3 (Streaming SIMD Extensions 3)
     */
    public data object Sse3 : Feature()
    /**
     * SSSE3 (Supplemental Streaming SIMD Extensions 3)
     */
    public data object Ssse3 : Feature()
    /**
     * SSE4.1 (Streaming SIMD Extensions 4.1)
     */
    public data object Sse41 : Feature()
    /**
     * SSE4.2 (Streaming SIMD Extensions 4.2)
     */
    public data object Sse42 : Feature()
    /**
     * SSE4a (Streaming SIMD Extensions 4a)
     */
    public data object Sse4a : Feature()
    /**
     * SHA
     */
    public data object Sha : Feature()
    /**
     * AVX (Advanced Vector Extensions)
     */
    public data object Avx : Feature()
    /**
     * AVX2 (Advanced Vector Extensions 2)
     */
    public data object Avx2 : Feature()
    /**
     * SHA512
     */
    public data object Sha512 : Feature()
    /**
     * SM3
     */
    public data object Sm3 : Feature()
    /**
     * SM4
     */
    public data object Sm4 : Feature()
    /**
     * AVX-512 F (Foundation)
     */
    public data object Avx512f : Feature()
    /**
     * AVX-512 CD (Conflict Detection Instructions)
     */
    public data object Avx512cd : Feature()
    /**
     * AVX-512 ER (Expo nential and Reciprocal Instructions)
     */
    public data object Avx512er : Feature()
    /**
     * AVX-512 PF (Prefetch Instructions)
     */
    public data object Avx512pf : Feature()
    /**
     * AVX-512 BW (Byte and Word Instructions)
     */
    public data object Avx512bw : Feature()
    /**
     * AVX-512 DQ (Doubleword and Quadword)
     */
    public data object Avx512dq : Feature()
    /**
     * AVX-512 VL (Vector Length Extensions)
     */
    public data object Avx512vl : Feature()
    /**
     * AVX-512 IFMA (Integer Fused Multiply Add)
     */
    public data object Avx512ifma : Feature()
    /**
     * AVX-512 VBMI (Vector Byte Manipulation Instructions)
     */
    public data object Avx512vbmi : Feature()
    /**
     * AVX-512 VPOPCNTDQ (Vector Population Count Doubleword and Quadword)
     */
    public data object Avx512vpopcntdq : Feature()
    /**
     * AVX-512 VBMI2 (Additional byte, word, dword and qword capabilities)
     */
    public data object Avx512vbmi2 : Feature()
    /**
     * AVX-512 GFNI (Galois Field New Instruction)
     */
    public data object Gfni : Feature()
    /**
     * AVX-512 VAES (Vector AES instruction)
     */
    public data object Vaes : Feature()
    /**
     * AVX-512 VPCLMULQDQ (Vector PCLMULQDQ instructions)
     */
    public data object Vpclmulqdq : Feature()
    /**
     * AVX-512 VNNI (Vector Neural Network Instructions)
     */
    public data object Avx512vnni : Feature()
    /**
     * AVX-512 BITALG (Support for VPOPCNT\[B,W\] and VPSHUFBITQMB)
     */
    public data object Avx512bitalg : Feature()
    /**
     * AVX-512 BF16 (BFLOAT16 instructions)
     */
    public data object Avx512bf16 : Feature()
    /**
     * AVX-512 P2INTERSECT
     */
    public data object Avx512vp2intersect : Feature()
    /**
     * AVX-512 FP16 (FLOAT16 instructions)
     */
    public data object Avx512fp16 : Feature()
    /**
     * AVX-IFMA (Integer Fused Multiply Add)
     */
    public data object Avxifma : Feature()
    /**
     * AVX-NE-CONVERT (Exceptionless Convert)
     */
    public data object Avxneconvert : Feature()
    /**
     * AVX-VNNI (Vector Neural Network Instructions)
     */
    public data object Avxvnni : Feature()
    /**
     * AVX-VNNI_INT8 (VNNI with 16-bit Integers)
     */
    public data object Avxvnniint16 : Feature()
    /**
     * AVX-VNNI_INT16 (VNNI with 8-bit integers)
     */
    public data object Avxvnniint8 : Feature()
    /**
     * AMX (Advanced Matrix Extensions) - Tile load/store
     */
    public data object AmxTile : Feature()
    /**
     * AMX-INT8 (Operations on 8-bit integers)
     */
    public data object AmxInt8 : Feature()
    /**
     * AMX-BF16 (BFloat16 Operations)
     */
    public data object AmxBf16 : Feature()
    /**
     * AMX-FP16 (Float16 Operations)
     */
    public data object AmxFp16 : Feature()
    /**
     * AMX-COMPLEX (Complex number Operations)
     */
    public data object AmxComplex : Feature()
    /**
     * AMX-AVX512 (AVX512 operations extended to matrices)
     */
    public data object AmxAvx512 : Feature()
    /**
     * AMX-FP8 (Float8 Operations)
     */
    public data object AmxFp8 : Feature()
    /**
     * AMX-MOVRS (Matrix MOVERS operations)
     */
    public data object AmxMovrs : Feature()
    /**
     * APX-F (Advanced Performance Extensions - Foundation)
     */
    public data object Apxf : Feature()
    /**
     * AVX10.1
     */
    public data object Avx101 : Feature()
    /**
     * AVX10.2
     */
    public data object Avx102 : Feature()
    /**
     * F16C (Conversions between IEEE-754 `binary16` and `binary32` formats)
     */
    public data object F16c : Feature()
    /**
     * FMA (Fused Multiply Add)
     */
    public data object Fma : Feature()
    /**
     * BMI1 (Bit Manipulation Instructions 1)
     */
    public data object Bmi1 : Feature()
    /**
     * BMI2 (Bit Manipulation Instructions 2)
     */
    public data object Bmi2 : Feature()
    /**
     * ABM (Advanced Bit Manipulation) / LZCNT (Leading Zero Count)
     */
    public data object Lzcnt : Feature()
    /**
     * TBM (Trailing Bit Manipulation)
     */
    public data object Tbm : Feature()
    /**
     * POPCNT (Population Count)
     */
    public data object Popcnt : Feature()
    /**
     * FXSR (Floating-point context fast save and restore)
     */
    public data object Fxsr : Feature()
    /**
     * XSAVE (Save Processor Extended States)
     */
    public data object Xsave : Feature()
    /**
     * XSAVEOPT (Save Processor Extended States Optimized)
     */
    public data object Xsaveopt : Feature()
    /**
     * XSAVES (Save Processor Extended States Supervisor)
     */
    public data object Xsaves : Feature()
    /**
     * XSAVEC (Save Processor Extended States Compacted)
     */
    public data object Xsavec : Feature()
    /**
     * CMPXCH16B (16-byte compare-and-swap instruction)
     */
    public data object Cmpxchg16b : Feature()
    /**
     * CLFLUSHOPT (Cache Line Flush Optimized)
     */
    public data object Clflushopt : Feature()
    /**
     * Intel Key Locker
     */
    public data object Kl : Feature()
    /**
     * Intel Key Locker Wide
     */
    public data object Widekl : Feature()
    /**
     * ADX, Intel ADX (Multi-Precision Add-Carry Instruction Extensions)
     */
    public data object Adx : Feature()
    /**
     * RTM, Intel (Restricted Transactional Memory)
     */
    public data object Rtm : Feature()
    /**
     * MOVBE (Move Data After Swapping Bytes)
     */
    public data object Movbe : Feature()
    /**
     * MOVDIR64B (Move 64 Bytes as Direct Store)
     */
    public data object Movdir64b : Feature()
    /**
     * MOVDIRI (Move Doubleword as Direct Store)
     */
    public data object Movdiri : Feature()
    /**
     * MOVRS (Move data with the read-shared hint)
     */
    public data object Movrs : Feature()
    /**
     * ERMSB, Enhanced REP MOVSB and STOSB
     */
    public data object Ermsb : Feature()
    /**
     * XOP: eXtended Operations (AMD)
     */
    public data object Xop : Feature()

    public open fun toStr(): String =
        when (this) {
            Feature.aes -> "aes"
            Feature.pclmulqdq -> "pclmulqdq"
            Feature.rdrand -> "rdrand"
            Feature.rdseed -> "rdseed"
            Feature.tsc -> "tsc"
            Feature.mmx -> "mmx"
            Feature.sse -> "sse"
            Feature.sse2 -> "sse2"
            Feature.sse3 -> "sse3"
            Feature.ssse3 -> "ssse3"
            Feature.sse41 -> "sse4.1"
            Feature.sse42 -> "sse4.2"
            Feature.sse4a -> "sse4a"
            Feature.sha -> "sha"
            Feature.avx -> "avx"
            Feature.avx2 -> "avx2"
            Feature.sha512 -> "sha512"
            Feature.sm3 -> "sm3"
            Feature.sm4 -> "sm4"
            Feature.avx512f -> "avx512f"
            Feature.avx512cd -> "avx512cd"
            Feature.avx512er -> "avx512er"
            Feature.avx512pf -> "avx512pf"
            Feature.avx512bw -> "avx512bw"
            Feature.avx512dq -> "avx512dq"
            Feature.avx512vl -> "avx512vl"
            Feature.avx512ifma -> "avx512ifma"
            Feature.avx512vbmi -> "avx512vbmi"
            Feature.avx512vpopcntdq -> "avx512vpopcntdq"
            Feature.avx512vbmi2 -> "avx512vbmi2"
            Feature.gfni -> "gfni"
            Feature.vaes -> "vaes"
            Feature.vpclmulqdq -> "vpclmulqdq"
            Feature.avx512vnni -> "avx512vnni"
            Feature.avx512bitalg -> "avx512bitalg"
            Feature.avx512bf16 -> "avx512bf16"
            Feature.avx512vp2intersect -> "avx512vp2intersect"
            Feature.avx512fp16 -> "avx512fp16"
            Feature.avxifma -> "avxifma"
            Feature.avxneconvert -> "avxneconvert"
            Feature.avxvnni -> "avxvnni"
            Feature.avxvnniint16 -> "avxvnniint16"
            Feature.avxvnniint8 -> "avxvnniint8"
            Feature.amxTile -> "amx-tile"
            Feature.amxInt8 -> "amx-int8"
            Feature.amxBf16 -> "amx-bf16"
            Feature.amxFp16 -> "amx-fp16"
            Feature.amxComplex -> "amx-complex"
            Feature.amxAvx512 -> "amx-avx512"
            Feature.amxFp8 -> "amx-fp8"
            Feature.amxMovrs -> "amx-movrs"
            Feature.apxf -> "apxf"
            Feature.avx101 -> "avx10.1"
            Feature.avx102 -> "avx10.2"
            Feature.f16c -> "f16c"
            Feature.fma -> "fma"
            Feature.bmi1 -> "bmi1"
            Feature.bmi2 -> "bmi2"
            Feature.lzcnt -> "lzcnt"
            Feature.tbm -> "tbm"
            Feature.popcnt -> "popcnt"
            Feature.fxsr -> "fxsr"
            Feature.xsave -> "xsave"
            Feature.xsaveopt -> "xsaveopt"
            Feature.xsaves -> "xsaves"
            Feature.xsavec -> "xsavec"
            Feature.cmpxchg16b -> "cmpxchg16b"
            Feature.clflushopt -> "clflushopt"
            Feature.kl -> "kl"
            Feature.widekl -> "widekl"
            Feature.adx -> "adx"
            Feature.rtm -> "rtm"
            Feature.movbe -> "movbe"
            Feature.movdir64b -> "movdir64b"
            Feature.movdiri -> "movdiri"
            Feature.movrs -> "movrs"
            Feature.ermsb -> "ermsb"
            Feature.xop -> "xop"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature::aes, Feature::pclmulqdq, Feature::rdrand, Feature::rdseed, Feature::tsc, Feature::mmx, Feature::sse, Feature::sse2, Feature::sse3, Feature::ssse3, Feature::sse41, Feature::sse42, Feature::sse4a, Feature::sha, Feature::avx, Feature::avx2, Feature::sha512, Feature::sm3, Feature::sm4, Feature::avx512f, Feature::avx512cd, Feature::avx512er, Feature::avx512pf, Feature::avx512bw, Feature::avx512dq, Feature::avx512vl, Feature::avx512ifma, Feature::avx512vbmi, Feature::avx512vpopcntdq, Feature::avx512vbmi2, Feature::gfni, Feature::vaes, Feature::vpclmulqdq, Feature::avx512vnni, Feature::avx512bitalg, Feature::avx512bf16, Feature::avx512vp2intersect, Feature::avx512fp16, Feature::avxifma, Feature::avxneconvert, Feature::avxvnni, Feature::avxvnniint16, Feature::avxvnniint8, Feature::amxTile, Feature::amxInt8, Feature::amxBf16, Feature::amxFp16, Feature::amxComplex, Feature::amxAvx512, Feature::amxFp8, Feature::amxMovrs, Feature::apxf, Feature::avx101, Feature::avx102, Feature::f16c, Feature::fma, Feature::bmi1, Feature::bmi2, Feature::lzcnt, Feature::tbm, Feature::popcnt, Feature::fxsr, Feature::xsave, Feature::xsaveopt, Feature::xsaves, Feature::xsavec, Feature::cmpxchg16b, Feature::clflushopt, Feature::kl, Feature::widekl, Feature::adx, Feature::rtm, Feature::movbe, Feature::movdir64b, Feature::movdiri, Feature::movrs, Feature::ermsb, Feature::xop)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "aes" -> Result.Ok<Feature, Unit>(Feature::aes)
                "pclmulqdq" -> Result.Ok<Feature, Unit>(Feature::pclmulqdq)
                "rdrand" -> Result.Ok<Feature, Unit>(Feature::rdrand)
                "rdseed" -> Result.Ok<Feature, Unit>(Feature::rdseed)
                "tsc" -> Result.Ok<Feature, Unit>(Feature::tsc)
                "mmx" -> Result.Ok<Feature, Unit>(Feature::mmx)
                "sse" -> Result.Ok<Feature, Unit>(Feature::sse)
                "sse2" -> Result.Ok<Feature, Unit>(Feature::sse2)
                "sse3" -> Result.Ok<Feature, Unit>(Feature::sse3)
                "ssse3" -> Result.Ok<Feature, Unit>(Feature::ssse3)
                "sse4.1" -> Result.Ok<Feature, Unit>(Feature::sse41)
                "sse4.2" -> Result.Ok<Feature, Unit>(Feature::sse42)
                "sse4a" -> Result.Ok<Feature, Unit>(Feature::sse4a)
                "sha" -> Result.Ok<Feature, Unit>(Feature::sha)
                "avx" -> Result.Ok<Feature, Unit>(Feature::avx)
                "avx2" -> Result.Ok<Feature, Unit>(Feature::avx2)
                "sha512" -> Result.Ok<Feature, Unit>(Feature::sha512)
                "sm3" -> Result.Ok<Feature, Unit>(Feature::sm3)
                "sm4" -> Result.Ok<Feature, Unit>(Feature::sm4)
                "avx512f" -> Result.Ok<Feature, Unit>(Feature::avx512f)
                "avx512cd" -> Result.Ok<Feature, Unit>(Feature::avx512cd)
                "avx512er" -> Result.Ok<Feature, Unit>(Feature::avx512er)
                "avx512pf" -> Result.Ok<Feature, Unit>(Feature::avx512pf)
                "avx512bw" -> Result.Ok<Feature, Unit>(Feature::avx512bw)
                "avx512dq" -> Result.Ok<Feature, Unit>(Feature::avx512dq)
                "avx512vl" -> Result.Ok<Feature, Unit>(Feature::avx512vl)
                "avx512ifma" -> Result.Ok<Feature, Unit>(Feature::avx512ifma)
                "avx512vbmi" -> Result.Ok<Feature, Unit>(Feature::avx512vbmi)
                "avx512vpopcntdq" -> Result.Ok<Feature, Unit>(Feature::avx512vpopcntdq)
                "avx512vbmi2" -> Result.Ok<Feature, Unit>(Feature::avx512vbmi2)
                "gfni" -> Result.Ok<Feature, Unit>(Feature::gfni)
                "vaes" -> Result.Ok<Feature, Unit>(Feature::vaes)
                "vpclmulqdq" -> Result.Ok<Feature, Unit>(Feature::vpclmulqdq)
                "avx512vnni" -> Result.Ok<Feature, Unit>(Feature::avx512vnni)
                "avx512bitalg" -> Result.Ok<Feature, Unit>(Feature::avx512bitalg)
                "avx512bf16" -> Result.Ok<Feature, Unit>(Feature::avx512bf16)
                "avx512vp2intersect" -> Result.Ok<Feature, Unit>(Feature::avx512vp2intersect)
                "avx512fp16" -> Result.Ok<Feature, Unit>(Feature::avx512fp16)
                "avxifma" -> Result.Ok<Feature, Unit>(Feature::avxifma)
                "avxneconvert" -> Result.Ok<Feature, Unit>(Feature::avxneconvert)
                "avxvnni" -> Result.Ok<Feature, Unit>(Feature::avxvnni)
                "avxvnniint16" -> Result.Ok<Feature, Unit>(Feature::avxvnniint16)
                "avxvnniint8" -> Result.Ok<Feature, Unit>(Feature::avxvnniint8)
                "amx-tile" -> Result.Ok<Feature, Unit>(Feature::amxTile)
                "amx-int8" -> Result.Ok<Feature, Unit>(Feature::amxInt8)
                "amx-bf16" -> Result.Ok<Feature, Unit>(Feature::amxBf16)
                "amx-fp16" -> Result.Ok<Feature, Unit>(Feature::amxFp16)
                "amx-complex" -> Result.Ok<Feature, Unit>(Feature::amxComplex)
                "amx-avx512" -> Result.Ok<Feature, Unit>(Feature::amxAvx512)
                "amx-fp8" -> Result.Ok<Feature, Unit>(Feature::amxFp8)
                "amx-movrs" -> Result.Ok<Feature, Unit>(Feature::amxMovrs)
                "apxf" -> Result.Ok<Feature, Unit>(Feature::apxf)
                "avx10.1" -> Result.Ok<Feature, Unit>(Feature::avx101)
                "avx10.2" -> Result.Ok<Feature, Unit>(Feature::avx102)
                "f16c" -> Result.Ok<Feature, Unit>(Feature::f16c)
                "fma" -> Result.Ok<Feature, Unit>(Feature::fma)
                "bmi1" -> Result.Ok<Feature, Unit>(Feature::bmi1)
                "bmi2" -> Result.Ok<Feature, Unit>(Feature::bmi2)
                "lzcnt" -> Result.Ok<Feature, Unit>(Feature::lzcnt)
                "tbm" -> Result.Ok<Feature, Unit>(Feature::tbm)
                "popcnt" -> Result.Ok<Feature, Unit>(Feature::popcnt)
                "fxsr" -> Result.Ok<Feature, Unit>(Feature::fxsr)
                "xsave" -> Result.Ok<Feature, Unit>(Feature::xsave)
                "xsaveopt" -> Result.Ok<Feature, Unit>(Feature::xsaveopt)
                "xsaves" -> Result.Ok<Feature, Unit>(Feature::xsaves)
                "xsavec" -> Result.Ok<Feature, Unit>(Feature::xsavec)
                "cmpxchg16b" -> Result.Ok<Feature, Unit>(Feature::cmpxchg16b)
                "clflushopt" -> Result.Ok<Feature, Unit>(Feature::clflushopt)
                "kl" -> Result.Ok<Feature, Unit>(Feature::kl)
                "widekl" -> Result.Ok<Feature, Unit>(Feature::widekl)
                "adx" -> Result.Ok<Feature, Unit>(Feature::adx)
                "rtm" -> Result.Ok<Feature, Unit>(Feature::rtm)
                "movbe" -> Result.Ok<Feature, Unit>(Feature::movbe)
                "movdir64b" -> Result.Ok<Feature, Unit>(Feature::movdir64b)
                "movdiri" -> Result.Ok<Feature, Unit>(Feature::movdiri)
                "movrs" -> Result.Ok<Feature, Unit>(Feature::movrs)
                "ermsb" -> Result.Ok<Feature, Unit>(Feature::ermsb)
                "xop" -> Result.Ok<Feature, Unit>(Feature::xop)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun aes(): Boolean = checkFor(::aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pclmulqdq(): Boolean = checkFor(::pclmulqdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdrand(): Boolean = checkFor(::rdrand)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdseed(): Boolean = checkFor(::rdseed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tsc(): Boolean = checkFor(::tsc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mmx(): Boolean = checkFor(::mmx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse(): Boolean = checkFor(::sse)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse2(): Boolean = checkFor(::sse2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse3(): Boolean = checkFor(::sse3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssse3(): Boolean = checkFor(::ssse3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse41(): Boolean = checkFor(::sse41)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse42(): Boolean = checkFor(::sse42)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse4a(): Boolean = checkFor(::sse4a)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha(): Boolean = checkFor(::sha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx(): Boolean = checkFor(::avx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx2(): Boolean = checkFor(::avx2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha512(): Boolean = checkFor(::sha512)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm3(): Boolean = checkFor(::sm3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm4(): Boolean = checkFor(::sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512f(): Boolean = checkFor(::avx512f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512cd(): Boolean = checkFor(::avx512cd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512er(): Boolean = checkFor(::avx512er)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512pf(): Boolean = checkFor(::avx512pf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bw(): Boolean = checkFor(::avx512bw)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512dq(): Boolean = checkFor(::avx512dq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vl(): Boolean = checkFor(::avx512vl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512ifma(): Boolean = checkFor(::avx512ifma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vbmi(): Boolean = checkFor(::avx512vbmi)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vpopcntdq(): Boolean = checkFor(::avx512vpopcntdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vbmi2(): Boolean = checkFor(::avx512vbmi2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun gfni(): Boolean = checkFor(::gfni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vaes(): Boolean = checkFor(::vaes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vpclmulqdq(): Boolean = checkFor(::vpclmulqdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vnni(): Boolean = checkFor(::avx512vnni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bitalg(): Boolean = checkFor(::avx512bitalg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bf16(): Boolean = checkFor(::avx512bf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vp2intersect(): Boolean = checkFor(::avx512vp2intersect)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512fp16(): Boolean = checkFor(::avx512fp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxifma(): Boolean = checkFor(::avxifma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxneconvert(): Boolean = checkFor(::avxneconvert)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnni(): Boolean = checkFor(::avxvnni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnniint16(): Boolean = checkFor(::avxvnniint16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnniint8(): Boolean = checkFor(::avxvnniint8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxTile(): Boolean = checkFor(::amxTile)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxInt8(): Boolean = checkFor(::amxInt8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxBf16(): Boolean = checkFor(::amxBf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxFp16(): Boolean = checkFor(::amxFp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxComplex(): Boolean = checkFor(::amxComplex)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxAvx512(): Boolean = checkFor(::amxAvx512)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxFp8(): Boolean = checkFor(::amxFp8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxMovrs(): Boolean = checkFor(::amxMovrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun apxf(): Boolean = checkFor(::apxf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx101(): Boolean = checkFor(::avx101)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx102(): Boolean = checkFor(::avx102)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f16c(): Boolean = checkFor(::f16c)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fma(): Boolean = checkFor(::fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bmi1(): Boolean = checkFor(::bmi1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bmi2(): Boolean = checkFor(::bmi2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lzcnt(): Boolean = checkFor(::lzcnt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tbm(): Boolean = checkFor(::tbm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun popcnt(): Boolean = checkFor(::popcnt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fxsr(): Boolean = checkFor(::fxsr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsave(): Boolean = checkFor(::xsave)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsaveopt(): Boolean = checkFor(::xsaveopt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsaves(): Boolean = checkFor(::xsaves)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsavec(): Boolean = checkFor(::xsavec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun cmpxchg16b(): Boolean = checkFor(::cmpxchg16b)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun clflushopt(): Boolean = checkFor(::clflushopt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun kl(): Boolean = checkFor(::kl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun widekl(): Boolean = checkFor(::widekl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun adx(): Boolean = checkFor(::adx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rtm(): Boolean = checkFor(::rtm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movbe(): Boolean = checkFor(::movbe)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movdir64b(): Boolean = checkFor(::movdir64b)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movdiri(): Boolean = checkFor(::movdiri)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movrs(): Boolean = checkFor(::movrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ermsb(): Boolean = checkFor(::ermsb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xop(): Boolean = checkFor(::xop)
