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

    private fun kmDiscriminant(): Int =
        when (this) {
        Aes -> 0
        Pclmulqdq -> 1
        Rdrand -> 2
        Rdseed -> 3
        Tsc -> 4
        Mmx -> 5
        Sse -> 6
        Sse2 -> 7
        Sse3 -> 8
        Ssse3 -> 9
        Sse41 -> 10
        Sse42 -> 11
        Sse4a -> 12
        Sha -> 13
        Avx -> 14
        Avx2 -> 15
        Sha512 -> 16
        Sm3 -> 17
        Sm4 -> 18
        Avx512f -> 19
        Avx512cd -> 20
        Avx512er -> 21
        Avx512pf -> 22
        Avx512bw -> 23
        Avx512dq -> 24
        Avx512vl -> 25
        Avx512ifma -> 26
        Avx512vbmi -> 27
        Avx512vpopcntdq -> 28
        Avx512vbmi2 -> 29
        Gfni -> 30
        Vaes -> 31
        Vpclmulqdq -> 32
        Avx512vnni -> 33
        Avx512bitalg -> 34
        Avx512bf16 -> 35
        Avx512vp2intersect -> 36
        Avx512fp16 -> 37
        Avxifma -> 38
        Avxneconvert -> 39
        Avxvnni -> 40
        Avxvnniint16 -> 41
        Avxvnniint8 -> 42
        AmxTile -> 43
        AmxInt8 -> 44
        AmxBf16 -> 45
        AmxFp16 -> 46
        AmxComplex -> 47
        AmxAvx512 -> 48
        AmxFp8 -> 49
        AmxMovrs -> 50
        Apxf -> 51
        Avx101 -> 52
        Avx102 -> 53
        F16c -> 54
        Fma -> 55
        Bmi1 -> 56
        Bmi2 -> 57
        Lzcnt -> 58
        Tbm -> 59
        Popcnt -> 60
        Fxsr -> 61
        Xsave -> 62
        Xsaveopt -> 63
        Xsaves -> 64
        Xsavec -> 65
        Cmpxchg16b -> 66
        Clflushopt -> 67
        Kl -> 68
        Widekl -> 69
        Adx -> 70
        Rtm -> 71
        Movbe -> 72
        Movdir64b -> 73
        Movdiri -> 74
        Movrs -> 75
        Ermsb -> 76
        Xop -> 77
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
            Feature.Aes -> "aes"
            Feature.Pclmulqdq -> "pclmulqdq"
            Feature.Rdrand -> "rdrand"
            Feature.Rdseed -> "rdseed"
            Feature.Tsc -> "tsc"
            Feature.Mmx -> "mmx"
            Feature.Sse -> "sse"
            Feature.Sse2 -> "sse2"
            Feature.Sse3 -> "sse3"
            Feature.Ssse3 -> "ssse3"
            Feature.Sse41 -> "sse4.1"
            Feature.Sse42 -> "sse4.2"
            Feature.Sse4a -> "sse4a"
            Feature.Sha -> "sha"
            Feature.Avx -> "avx"
            Feature.Avx2 -> "avx2"
            Feature.Sha512 -> "sha512"
            Feature.Sm3 -> "sm3"
            Feature.Sm4 -> "sm4"
            Feature.Avx512f -> "avx512f"
            Feature.Avx512cd -> "avx512cd"
            Feature.Avx512er -> "avx512er"
            Feature.Avx512pf -> "avx512pf"
            Feature.Avx512bw -> "avx512bw"
            Feature.Avx512dq -> "avx512dq"
            Feature.Avx512vl -> "avx512vl"
            Feature.Avx512ifma -> "avx512ifma"
            Feature.Avx512vbmi -> "avx512vbmi"
            Feature.Avx512vpopcntdq -> "avx512vpopcntdq"
            Feature.Avx512vbmi2 -> "avx512vbmi2"
            Feature.Gfni -> "gfni"
            Feature.Vaes -> "vaes"
            Feature.Vpclmulqdq -> "vpclmulqdq"
            Feature.Avx512vnni -> "avx512vnni"
            Feature.Avx512bitalg -> "avx512bitalg"
            Feature.Avx512bf16 -> "avx512bf16"
            Feature.Avx512vp2intersect -> "avx512vp2intersect"
            Feature.Avx512fp16 -> "avx512fp16"
            Feature.Avxifma -> "avxifma"
            Feature.Avxneconvert -> "avxneconvert"
            Feature.Avxvnni -> "avxvnni"
            Feature.Avxvnniint16 -> "avxvnniint16"
            Feature.Avxvnniint8 -> "avxvnniint8"
            Feature.AmxTile -> "amx-tile"
            Feature.AmxInt8 -> "amx-int8"
            Feature.AmxBf16 -> "amx-bf16"
            Feature.AmxFp16 -> "amx-fp16"
            Feature.AmxComplex -> "amx-complex"
            Feature.AmxAvx512 -> "amx-avx512"
            Feature.AmxFp8 -> "amx-fp8"
            Feature.AmxMovrs -> "amx-movrs"
            Feature.Apxf -> "apxf"
            Feature.Avx101 -> "avx10.1"
            Feature.Avx102 -> "avx10.2"
            Feature.F16c -> "f16c"
            Feature.Fma -> "fma"
            Feature.Bmi1 -> "bmi1"
            Feature.Bmi2 -> "bmi2"
            Feature.Lzcnt -> "lzcnt"
            Feature.Tbm -> "tbm"
            Feature.Popcnt -> "popcnt"
            Feature.Fxsr -> "fxsr"
            Feature.Xsave -> "xsave"
            Feature.Xsaveopt -> "xsaveopt"
            Feature.Xsaves -> "xsaves"
            Feature.Xsavec -> "xsavec"
            Feature.Cmpxchg16b -> "cmpxchg16b"
            Feature.Clflushopt -> "clflushopt"
            Feature.Kl -> "kl"
            Feature.Widekl -> "widekl"
            Feature.Adx -> "adx"
            Feature.Rtm -> "rtm"
            Feature.Movbe -> "movbe"
            Feature.Movdir64b -> "movdir64b"
            Feature.Movdiri -> "movdiri"
            Feature.Movrs -> "movrs"
            Feature.Ermsb -> "ermsb"
            Feature.Xop -> "xop"
        }

    public companion object {
        public val ALL: List<Feature> = listOf(Feature.Aes, Feature.Pclmulqdq, Feature.Rdrand, Feature.Rdseed, Feature.Tsc, Feature.Mmx, Feature.Sse, Feature.Sse2, Feature.Sse3, Feature.Ssse3, Feature.Sse41, Feature.Sse42, Feature.Sse4a, Feature.Sha, Feature.Avx, Feature.Avx2, Feature.Sha512, Feature.Sm3, Feature.Sm4, Feature.Avx512f, Feature.Avx512cd, Feature.Avx512er, Feature.Avx512pf, Feature.Avx512bw, Feature.Avx512dq, Feature.Avx512vl, Feature.Avx512ifma, Feature.Avx512vbmi, Feature.Avx512vpopcntdq, Feature.Avx512vbmi2, Feature.Gfni, Feature.Vaes, Feature.Vpclmulqdq, Feature.Avx512vnni, Feature.Avx512bitalg, Feature.Avx512bf16, Feature.Avx512vp2intersect, Feature.Avx512fp16, Feature.Avxifma, Feature.Avxneconvert, Feature.Avxvnni, Feature.Avxvnniint16, Feature.Avxvnniint8, Feature.AmxTile, Feature.AmxInt8, Feature.AmxBf16, Feature.AmxFp16, Feature.AmxComplex, Feature.AmxAvx512, Feature.AmxFp8, Feature.AmxMovrs, Feature.Apxf, Feature.Avx101, Feature.Avx102, Feature.F16c, Feature.Fma, Feature.Bmi1, Feature.Bmi2, Feature.Lzcnt, Feature.Tbm, Feature.Popcnt, Feature.Fxsr, Feature.Xsave, Feature.Xsaveopt, Feature.Xsaves, Feature.Xsavec, Feature.Cmpxchg16b, Feature.Clflushopt, Feature.Kl, Feature.Widekl, Feature.Adx, Feature.Rtm, Feature.Movbe, Feature.Movdir64b, Feature.Movdiri, Feature.Movrs, Feature.Ermsb, Feature.Xop)

        public fun fromStr(s: String): Result<Feature, Unit> =
            when (s) {
                "aes" -> Result.Ok<Feature, Unit>(Feature.Aes)
                "pclmulqdq" -> Result.Ok<Feature, Unit>(Feature.Pclmulqdq)
                "rdrand" -> Result.Ok<Feature, Unit>(Feature.Rdrand)
                "rdseed" -> Result.Ok<Feature, Unit>(Feature.Rdseed)
                "tsc" -> Result.Ok<Feature, Unit>(Feature.Tsc)
                "mmx" -> Result.Ok<Feature, Unit>(Feature.Mmx)
                "sse" -> Result.Ok<Feature, Unit>(Feature.Sse)
                "sse2" -> Result.Ok<Feature, Unit>(Feature.Sse2)
                "sse3" -> Result.Ok<Feature, Unit>(Feature.Sse3)
                "ssse3" -> Result.Ok<Feature, Unit>(Feature.Ssse3)
                "sse4.1" -> Result.Ok<Feature, Unit>(Feature.Sse41)
                "sse4.2" -> Result.Ok<Feature, Unit>(Feature.Sse42)
                "sse4a" -> Result.Ok<Feature, Unit>(Feature.Sse4a)
                "sha" -> Result.Ok<Feature, Unit>(Feature.Sha)
                "avx" -> Result.Ok<Feature, Unit>(Feature.Avx)
                "avx2" -> Result.Ok<Feature, Unit>(Feature.Avx2)
                "sha512" -> Result.Ok<Feature, Unit>(Feature.Sha512)
                "sm3" -> Result.Ok<Feature, Unit>(Feature.Sm3)
                "sm4" -> Result.Ok<Feature, Unit>(Feature.Sm4)
                "avx512f" -> Result.Ok<Feature, Unit>(Feature.Avx512f)
                "avx512cd" -> Result.Ok<Feature, Unit>(Feature.Avx512cd)
                "avx512er" -> Result.Ok<Feature, Unit>(Feature.Avx512er)
                "avx512pf" -> Result.Ok<Feature, Unit>(Feature.Avx512pf)
                "avx512bw" -> Result.Ok<Feature, Unit>(Feature.Avx512bw)
                "avx512dq" -> Result.Ok<Feature, Unit>(Feature.Avx512dq)
                "avx512vl" -> Result.Ok<Feature, Unit>(Feature.Avx512vl)
                "avx512ifma" -> Result.Ok<Feature, Unit>(Feature.Avx512ifma)
                "avx512vbmi" -> Result.Ok<Feature, Unit>(Feature.Avx512vbmi)
                "avx512vpopcntdq" -> Result.Ok<Feature, Unit>(Feature.Avx512vpopcntdq)
                "avx512vbmi2" -> Result.Ok<Feature, Unit>(Feature.Avx512vbmi2)
                "gfni" -> Result.Ok<Feature, Unit>(Feature.Gfni)
                "vaes" -> Result.Ok<Feature, Unit>(Feature.Vaes)
                "vpclmulqdq" -> Result.Ok<Feature, Unit>(Feature.Vpclmulqdq)
                "avx512vnni" -> Result.Ok<Feature, Unit>(Feature.Avx512vnni)
                "avx512bitalg" -> Result.Ok<Feature, Unit>(Feature.Avx512bitalg)
                "avx512bf16" -> Result.Ok<Feature, Unit>(Feature.Avx512bf16)
                "avx512vp2intersect" -> Result.Ok<Feature, Unit>(Feature.Avx512vp2intersect)
                "avx512fp16" -> Result.Ok<Feature, Unit>(Feature.Avx512fp16)
                "avxifma" -> Result.Ok<Feature, Unit>(Feature.Avxifma)
                "avxneconvert" -> Result.Ok<Feature, Unit>(Feature.Avxneconvert)
                "avxvnni" -> Result.Ok<Feature, Unit>(Feature.Avxvnni)
                "avxvnniint16" -> Result.Ok<Feature, Unit>(Feature.Avxvnniint16)
                "avxvnniint8" -> Result.Ok<Feature, Unit>(Feature.Avxvnniint8)
                "amx-tile" -> Result.Ok<Feature, Unit>(Feature.AmxTile)
                "amx-int8" -> Result.Ok<Feature, Unit>(Feature.AmxInt8)
                "amx-bf16" -> Result.Ok<Feature, Unit>(Feature.AmxBf16)
                "amx-fp16" -> Result.Ok<Feature, Unit>(Feature.AmxFp16)
                "amx-complex" -> Result.Ok<Feature, Unit>(Feature.AmxComplex)
                "amx-avx512" -> Result.Ok<Feature, Unit>(Feature.AmxAvx512)
                "amx-fp8" -> Result.Ok<Feature, Unit>(Feature.AmxFp8)
                "amx-movrs" -> Result.Ok<Feature, Unit>(Feature.AmxMovrs)
                "apxf" -> Result.Ok<Feature, Unit>(Feature.Apxf)
                "avx10.1" -> Result.Ok<Feature, Unit>(Feature.Avx101)
                "avx10.2" -> Result.Ok<Feature, Unit>(Feature.Avx102)
                "f16c" -> Result.Ok<Feature, Unit>(Feature.F16c)
                "fma" -> Result.Ok<Feature, Unit>(Feature.Fma)
                "bmi1" -> Result.Ok<Feature, Unit>(Feature.Bmi1)
                "bmi2" -> Result.Ok<Feature, Unit>(Feature.Bmi2)
                "lzcnt" -> Result.Ok<Feature, Unit>(Feature.Lzcnt)
                "tbm" -> Result.Ok<Feature, Unit>(Feature.Tbm)
                "popcnt" -> Result.Ok<Feature, Unit>(Feature.Popcnt)
                "fxsr" -> Result.Ok<Feature, Unit>(Feature.Fxsr)
                "xsave" -> Result.Ok<Feature, Unit>(Feature.Xsave)
                "xsaveopt" -> Result.Ok<Feature, Unit>(Feature.Xsaveopt)
                "xsaves" -> Result.Ok<Feature, Unit>(Feature.Xsaves)
                "xsavec" -> Result.Ok<Feature, Unit>(Feature.Xsavec)
                "cmpxchg16b" -> Result.Ok<Feature, Unit>(Feature.Cmpxchg16b)
                "clflushopt" -> Result.Ok<Feature, Unit>(Feature.Clflushopt)
                "kl" -> Result.Ok<Feature, Unit>(Feature.Kl)
                "widekl" -> Result.Ok<Feature, Unit>(Feature.Widekl)
                "adx" -> Result.Ok<Feature, Unit>(Feature.Adx)
                "rtm" -> Result.Ok<Feature, Unit>(Feature.Rtm)
                "movbe" -> Result.Ok<Feature, Unit>(Feature.Movbe)
                "movdir64b" -> Result.Ok<Feature, Unit>(Feature.Movdir64b)
                "movdiri" -> Result.Ok<Feature, Unit>(Feature.Movdiri)
                "movrs" -> Result.Ok<Feature, Unit>(Feature.Movrs)
                "ermsb" -> Result.Ok<Feature, Unit>(Feature.Ermsb)
                "xop" -> Result.Ok<Feature, Unit>(Feature.Xop)
                else -> Result.Err<Feature, Unit>(Unit)
            }
    }
}

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun aes(): Boolean = checkFor(Feature.Aes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun pclmulqdq(): Boolean = checkFor(Feature.Pclmulqdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdrand(): Boolean = checkFor(Feature.Rdrand)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rdseed(): Boolean = checkFor(Feature.Rdseed)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tsc(): Boolean = checkFor(Feature.Tsc)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun mmx(): Boolean = checkFor(Feature.Mmx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse(): Boolean = checkFor(Feature.Sse)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse2(): Boolean = checkFor(Feature.Sse2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse3(): Boolean = checkFor(Feature.Sse3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ssse3(): Boolean = checkFor(Feature.Ssse3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse41(): Boolean = checkFor(Feature.Sse41)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse42(): Boolean = checkFor(Feature.Sse42)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sse4a(): Boolean = checkFor(Feature.Sse4a)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha(): Boolean = checkFor(Feature.Sha)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx(): Boolean = checkFor(Feature.Avx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx2(): Boolean = checkFor(Feature.Avx2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sha512(): Boolean = checkFor(Feature.Sha512)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm3(): Boolean = checkFor(Feature.Sm3)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun sm4(): Boolean = checkFor(Feature.Sm4)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512f(): Boolean = checkFor(Feature.Avx512f)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512cd(): Boolean = checkFor(Feature.Avx512cd)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512er(): Boolean = checkFor(Feature.Avx512er)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512pf(): Boolean = checkFor(Feature.Avx512pf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bw(): Boolean = checkFor(Feature.Avx512bw)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512dq(): Boolean = checkFor(Feature.Avx512dq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vl(): Boolean = checkFor(Feature.Avx512vl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512ifma(): Boolean = checkFor(Feature.Avx512ifma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vbmi(): Boolean = checkFor(Feature.Avx512vbmi)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vpopcntdq(): Boolean = checkFor(Feature.Avx512vpopcntdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vbmi2(): Boolean = checkFor(Feature.Avx512vbmi2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun gfni(): Boolean = checkFor(Feature.Gfni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vaes(): Boolean = checkFor(Feature.Vaes)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun vpclmulqdq(): Boolean = checkFor(Feature.Vpclmulqdq)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vnni(): Boolean = checkFor(Feature.Avx512vnni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bitalg(): Boolean = checkFor(Feature.Avx512bitalg)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512bf16(): Boolean = checkFor(Feature.Avx512bf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512vp2intersect(): Boolean = checkFor(Feature.Avx512vp2intersect)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx512fp16(): Boolean = checkFor(Feature.Avx512fp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxifma(): Boolean = checkFor(Feature.Avxifma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxneconvert(): Boolean = checkFor(Feature.Avxneconvert)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnni(): Boolean = checkFor(Feature.Avxvnni)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnniint16(): Boolean = checkFor(Feature.Avxvnniint16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avxvnniint8(): Boolean = checkFor(Feature.Avxvnniint8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxTile(): Boolean = checkFor(Feature.AmxTile)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxInt8(): Boolean = checkFor(Feature.AmxInt8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxBf16(): Boolean = checkFor(Feature.AmxBf16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxFp16(): Boolean = checkFor(Feature.AmxFp16)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxComplex(): Boolean = checkFor(Feature.AmxComplex)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxAvx512(): Boolean = checkFor(Feature.AmxAvx512)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxFp8(): Boolean = checkFor(Feature.AmxFp8)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun amxMovrs(): Boolean = checkFor(Feature.AmxMovrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun apxf(): Boolean = checkFor(Feature.Apxf)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx101(): Boolean = checkFor(Feature.Avx101)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun avx102(): Boolean = checkFor(Feature.Avx102)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun f16c(): Boolean = checkFor(Feature.F16c)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fma(): Boolean = checkFor(Feature.Fma)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bmi1(): Boolean = checkFor(Feature.Bmi1)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun bmi2(): Boolean = checkFor(Feature.Bmi2)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun lzcnt(): Boolean = checkFor(Feature.Lzcnt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun tbm(): Boolean = checkFor(Feature.Tbm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun popcnt(): Boolean = checkFor(Feature.Popcnt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun fxsr(): Boolean = checkFor(Feature.Fxsr)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsave(): Boolean = checkFor(Feature.Xsave)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsaveopt(): Boolean = checkFor(Feature.Xsaveopt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsaves(): Boolean = checkFor(Feature.Xsaves)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xsavec(): Boolean = checkFor(Feature.Xsavec)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun cmpxchg16b(): Boolean = checkFor(Feature.Cmpxchg16b)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun clflushopt(): Boolean = checkFor(Feature.Clflushopt)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun kl(): Boolean = checkFor(Feature.Kl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun widekl(): Boolean = checkFor(Feature.Widekl)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun adx(): Boolean = checkFor(Feature.Adx)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun rtm(): Boolean = checkFor(Feature.Rtm)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movbe(): Boolean = checkFor(Feature.Movbe)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movdir64b(): Boolean = checkFor(Feature.Movdir64b)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movdiri(): Boolean = checkFor(Feature.Movdiri)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun movrs(): Boolean = checkFor(Feature.Movrs)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun ermsb(): Boolean = checkFor(Feature.Ermsb)

/**
 * PLEASE: do not use this, it is an implementation detail
 * subject to change.
 */
public fun xop(): Boolean = checkFor(Feature.Xop)
