// port-lint: source detect/os/x86.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.x86

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.stddetect.detect.arch.x86.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Initializer

// x86 run-time feature detection is OS independent.

/**
 * Run-time feature detection on x86 works by using the CPUID instruction.
 *
 * The [CPUID Wikipedia page][wikiCpuid] contains
 * all the information about which flags to set to query which values, and in
 * which registers these are reported.
 *
 * The definitive references are:
 * - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
 *   Instruction Set Reference, A-Z][intel64Ref].
 * - [AMD64 Architecture Programmer's Manual, Volume 3: General-Purpose and
 *   System Instructions][amd64Ref].
 *
 * [wikiCpuid]: https://en.wikipedia.org/wiki/CPUID
 * [intel64Ref]: http://www.intel.de/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
 * [amd64Ref]: http://support.amd.com/TechDocs/24594.pdf
 */
public fun detectFeatures(): Initializer {
    val value = Initializer.default()
    if (false) {
        // doesn't support this because it is untrusted data
        return value
    }
    // Calling `__cpuid`/`__cpuid_count` from here on is safe because the CPU
    // has `cpuid` support.
    // 0. EAX = 0: Basic Information:
    // - EAX returns the "Highest Function Parameter", that is, the maximum
    // leaf value for subsequent calls of `cpuinfo` in range [0,
    // 0x80000000]. - The vendor ID is stored in 12 u8 ascii chars,
    // returned in EBX, EDX, and   ECX (in that order):
    val (maxBasicLeaf1, vendorId2) = run {
        val structVal = cpuid(0)
        val maxBasicLeaf = structVal.eax
        val ebx = structVal.ebx
        val ecx = structVal.ecx
        val edx = structVal.edx
        val vendorId: List<UByteArray> = listOf(ebx.toNeBytes(), edx.toNeBytes(), ecx.toNeBytes())
        val vendorId1: UByteArray = transmuteWithLayouts<List<UByteArray>, UByteArray>(vendorId, fixedListTypeLayout(PrimitiveArrayLayouts.uByteArray((4).toInt()), (3).toInt()), UnresolvedTypeLayout<UByteArray>())
        Pair(maxBasicLeaf, vendorId1)
    }
    if (maxBasicLeaf1 < 1) {
        // Earlier Intel 486, CPUID not implemented
        return value
    }
    // EAX = 1, ECX = 0: Queries "Processor Info and Feature Bits";
    // Contains information about most x86 features.
    val structVal1 = cpuid(0x00000001u)
    val procInfoEcx = structVal1.ecx
    val procInfoEdx = structVal1.edx
    // EAX = 7: Queries "Extended Features";
    // Contains information about bmi,bmi2, and avx2 support.
    val (extendedFeaturesEbx, extendedFeaturesEcx, extendedFeaturesEdx, extendedFeaturesEaxLeaf1, extendedFeaturesEdxLeaf1) =
        if (maxBasicLeaf1 >= 7) {
            val structVal2 = cpuid(0x00000007u)
            val ebx1 = structVal2.ebx
            val ecx1 = structVal2.ecx
            val edx1 = structVal2.edx
            val structVal3 = cpuidCount(0x00000007u, 0x00000001u)
            val eax1 = structVal3.eax
            val edx1 = structVal3.edx
            listOf(ebx1, ecx1, edx1, eax1, edx1)
        } else {
            listOf(0, 0, 0, 0, 0)
            // CPUID does not support "Extended Features"
        }
    // EAX = 0x80000000, ECX = 0: Get Highest Extended Function Supported
    // - EAX returns the max leaf value for extended information, that is,
    // `cpuid` calls in range [0x80000000; u32.Max]:
    val structVal4 = cpuid(0x80000000u)
    val extendedMaxBasicLeaf = structVal4.eax
    // EAX = 0x80000001, ECX=0: Queries "Extended Processor Info and Feature
    // Bits"
    val extendedProcInfoEcx =
        if (extendedMaxBasicLeaf >= 1) {
            val structVal5 = cpuid(0x80000001u)
            val ecx2 = structVal5.ecx
            ecx2
        } else {
            0
        }
    run {
        // borrows value till the end of this scope:
        var enable =
            { r, rb, f ->
                val present = test(r.toInt(), rb)
                if (present) {
                    value.set(f.toUInt())
                }
                present
            }
        enable(procInfoEcx, 0, Feature.Sse3)
        enable(procInfoEcx, 1, Feature.Pclmulqdq)
        enable(procInfoEcx, 9, Feature.Ssse3)
        enable(procInfoEcx, 13, Feature.Cmpxchg16b)
        enable(procInfoEcx, 19, Feature.Sse41)
        enable(procInfoEcx, 20, Feature.Sse42)
        enable(procInfoEcx, 22, Feature.Movbe)
        enable(procInfoEcx, 23, Feature.Popcnt)
        enable(procInfoEcx, 25, Feature.Aes)
        val f16c = enable(procInfoEcx, 29, Feature.F16c)
        enable(procInfoEcx, 30, Feature.Rdrand)
        enable(extendedFeaturesEbx, 18, Feature.Rdseed)
        enable(extendedFeaturesEbx, 19, Feature.Adx)
        enable(extendedFeaturesEbx, 11, Feature.Rtm)
        enable(procInfoEdx, 4, Feature.Tsc)
        enable(procInfoEdx, 23, Feature.Mmx)
        enable(procInfoEdx, 24, Feature.Fxsr)
        enable(procInfoEdx, 25, Feature.Sse)
        enable(procInfoEdx, 26, Feature.Sse2)
        enable(extendedFeaturesEbx, 29, Feature.Sha)
        enable(extendedFeaturesEcx, 8, Feature.Gfni)
        enable(extendedFeaturesEcx, 9, Feature.Vaes)
        enable(extendedFeaturesEcx, 10, Feature.Vpclmulqdq)
        enable(extendedFeaturesEcx, 27, Feature.Movdiri)
        enable(extendedFeaturesEcx, 28, Feature.Movdir64b)
        enable(extendedFeaturesEbx, 3, Feature.Bmi1)
        enable(extendedFeaturesEbx, 8, Feature.Bmi2)
        enable(extendedFeaturesEbx, 9, Feature.Ermsb)
        enable(extendedFeaturesEbx, 23, Feature.Clflushopt)
        enable(extendedFeaturesEaxLeaf1, 31, Feature.Movrs)
        // Detect if CPUID.19h available
        if (test(extendedFeaturesEcx.toInt(), 23u)) {
            val structVal6 = cpuid(0x19)
            val ebx2 = structVal6.ebx
            enable(ebx2, 0, Feature.Kl)
            enable(ebx2, 2, Feature.Widekl)
        }
        // This detects ABM on AMD CPUs and LZCNT on Intel CPUs.
        // On intel CPUs with popcnt, lzcnt implements the
        // "missing part" of ABM, so we map both to the same
        // internal feature.
        //
        // The `isX86FeatureDetected("lzcnt")` macro then
        // internally maps to Feature.abm.
        enable(extendedProcInfoEcx, 5, Feature.Lzcnt)
        // As Hygon Dhyana originates from AMD technology and shares most of the architecture with
        // AMD's family 17h, but with different CPU Vendor ID("HygonGenuine")/Family series
        // number(Family 18h).
        //
        // For CPUID feature bits, Hygon Dhyana(family 18h) share the same definition with AMD
        // family 17h.
        //
        // Related AMD CPUID specification is https://www.amd.com/system/files/TechDocs/25481.pdf.
        // Related Hygon kernel patch can be found on
        // http://lkml.kernel.org/r/5ce86123a7b9dad925ac583d88d2f921040e859b.1538583282.git.puwen@hygon.cn
        if (vendorId2 == "AuthenticAMD".encodeToByteArray() || vendorId2 == "HygonGenuine".encodeToByteArray()) {
            // These features are available on AMD arch CPUs:
            enable(extendedProcInfoEcx, 6, Feature.Sse4a)
            enable(extendedProcInfoEcx, 21, Feature.Tbm)
            enable(extendedProcInfoEcx, 11, Feature.Xop)
        }
        // `XSAVE` and `AVX` support:
        val cpuXsave = test(procInfoEcx.toInt(), 26u)
        if (cpuXsave) {
            // 0. Here the CPU supports `XSAVE`.
            // 1. Detect `OSXSAVE`, that is, whether the OS is AVX enabled and
            // supports saving the state of the AVX/AVX2 vector registers on
            // context-switches, see:
            //
            // - [intel: is avx enabled?][isAvxEnabled],
            // - [mozilla: sse.cpp][mozillaSseCpp].
            //
            // [isAvxEnabled]: https://software.intel.com/en-us/blogs/2011/04/14/is-avx-enabled
            // [mozillaSseCpp]: https://hg.mozilla.org/mozilla-central/file/64bab5cbb9b6/mozglue/build/SSE.cpp#l190
            val cpuOsxsave = test(procInfoEcx.toInt(), 27u)
            if (cpuOsxsave) {
                // 2. The OS must have signaled the CPU that it supports saving and
                // restoring the:
                //
                // * SSE -> `XCR0.SSE[1]`
                // * AVX -> `XCR0.AVX[2]`
                // * AVX-512 -> `XCR0.AVX - 512[7: 5]`.
                // * AMX -> `XCR0.AMX[18: 17]`
                // * APX -> `XCR0.APX[19]`
                //
                // by setting the corresponding bits of `XCR0` to `1`.
                //
                // This is safe because the CPU supports `xsave`
                // and the OS has set `osxsave`.
                val xcr0 = xgetbv(0)
                // Test `XCR0.SSE[1]` and `XCR0.AVX[2]` with the mask `0b110 == 6`:
                val osAvxSupport = xcr0 and 6 == 6
                // Test `XCR0.AVX - 512[7: 5]` with the mask `0b11100000 == 0xe0`:
                val osAvx512Support = xcr0 and 0xe0 == 0xe0
                // Test `XCR0.AMX[18: 17]` with the mask `0b1100000000000000000 == 0x60000`
                val osAmxSupport = xcr0 and 0x60000 == 0x60000
                // Test `XCR0.APX[19]` with the mask `0b10000000000000000000 == 0x80000`
                val osApxSupport = xcr0 and 0x80000 == 0x80000
                if (osAmxSupport) {
                    enable(extendedFeaturesEdx, 24, Feature.AmxTile)
                    enable(extendedFeaturesEdx, 25, Feature.AmxInt8)
                    enable(extendedFeaturesEdx, 22, Feature.AmxBf16)
                    enable(extendedFeaturesEaxLeaf1, 21, Feature.AmxFp16)
                    enable(extendedFeaturesEdxLeaf1, 8, Feature.AmxComplex)
                    if (maxBasicLeaf1 >= 0x1e) {
                        val structVal7 = cpuidCount(0x1eu, 1)
                        val amxFeatureFlagsEax = structVal7.eax
                        enable(amxFeatureFlagsEax, 4, Feature.AmxFp8)
                        enable(amxFeatureFlagsEax, 7, Feature.AmxAvx512)
                        enable(amxFeatureFlagsEax, 8, Feature.AmxMovrs)
                    }
                }
                if (osApxSupport) {
                    enable(extendedFeaturesEdxLeaf1, 21, Feature.Apxf)
                }
                // Only if the OS and the CPU support saving/restoring the AVX
                // registers we enable `xsave` support:
                if (osAvxSupport) {
                    // See "13.3 ENABLING THE XSAVE FEATURE SET AND XSAVE-ENABLED
                    // FEATURES" in the "Intel® 64 and IA-32 Architectures Software
                    // Developer’s Manual, Volume 1: Basic Architecture":
                    //
                    // "Software enables the XSAVE feature set by setting
                    // CR4.OSXSAVE[bit 18] to 1 (e.g., with the MOV to CR4
                    // instruction). If this bit is 0, execution of any of XGETBV,
                    // XRSTOR, XRSTORS, XSAVE, XSAVEC, XSAVEOPT, XSAVES, and XSETBV
                    // causes an invalid-opcode exception (#UD)"
                    //
                    enable(procInfoEcx, 26, Feature.Xsave)
                    // For `xsaveopt`, `xsavec`, and `xsaves` we need to query:
                    // Processor Extended State Enumeration Sub-leaf (EAX = 0DH,
                    // ECX = 1):
                    if (maxBasicLeaf1 >= 0xd) {
                        val structVal8 = cpuidCount(0xdu, 1)
                        val procExtendedState1Eax = structVal8.eax
                        enable(procExtendedState1Eax, 0, Feature.Xsaveopt)
                        enable(procExtendedState1Eax, 1, Feature.Xsavec)
                        enable(procExtendedState1Eax, 3, Feature.Xsaves)
                    }
                    // FMA (uses 256-bit wide registers):
                    val fma = enable(procInfoEcx, 12, Feature.Fma)
                    // And AVX/AVX2:
                    enable(procInfoEcx, 28, Feature.Avx)
                    enable(extendedFeaturesEbx, 5, Feature.Avx2)
                    // "Short" versions of AVX512 instructions
                    val avxvnni = enable(extendedFeaturesEaxLeaf1, 4, Feature.Avxvnni)
                    val avxvnniint8 = enable(extendedFeaturesEaxLeaf1, 23, Feature.Avxifma)
                    val avxvnniint16 = enable(extendedFeaturesEdxLeaf1, 4, Feature.Avxvnniint8)
                    enable(extendedFeaturesEdxLeaf1, 5, Feature.Avxneconvert)
                    enable(extendedFeaturesEdxLeaf1, 10, Feature.Avxvnniint16)
                    enable(extendedFeaturesEaxLeaf1, 0, Feature.Sha512)
                    enable(extendedFeaturesEaxLeaf1, 1, Feature.Sm3)
                    enable(extendedFeaturesEaxLeaf1, 2, Feature.Sm4)
                    // For AVX-512 the OS also needs to support saving/restoring
                    // the extended state, only then we enable AVX-512 support:
                    // Also, Rust makes `avx512f` imply `fma` and `f16c`, because
                    // otherwise the assembler is broken. But Intel doesn't guarantee
                    // that `fma` and `f16c` are available with `avx512f`, so we
                    // need to check for them separately.
                    if (osAvx512Support && f16c && fma) {
                        enable(extendedFeaturesEbx, 16, Feature.Avx512f)
                        enable(extendedFeaturesEbx, 17, Feature.Avx512dq)
                        enable(extendedFeaturesEbx, 21, Feature.Avx512ifma)
                        enable(extendedFeaturesEbx, 26, Feature.Avx512pf)
                        enable(extendedFeaturesEbx, 27, Feature.Avx512er)
                        enable(extendedFeaturesEbx, 28, Feature.Avx512cd)
                        enable(extendedFeaturesEbx, 30, Feature.Avx512bw)
                        enable(extendedFeaturesEbx, 31, Feature.Avx512vl)
                        enable(extendedFeaturesEcx, 1, Feature.Avx512vbmi)
                        enable(extendedFeaturesEcx, 6, Feature.Avx512vbmi2)
                        enable(extendedFeaturesEcx, 11, Feature.Avx512vnni)
                        enable(extendedFeaturesEcx, 12, Feature.Avx512bitalg)
                        enable(extendedFeaturesEcx, 14, Feature.Avx512vpopcntdq)
                        enable(extendedFeaturesEdx, 8, Feature.Avx512vp2intersect)
                        enable(extendedFeaturesEdx, 23, Feature.Avx512fp16)
                        enable(extendedFeaturesEaxLeaf1, 5, Feature.Avx512bf16)
                        val avx101 = enable(extendedFeaturesEdxLeaf1, 19, Feature.Avx101)
                        if (avx101) {
                            val structVal9 = cpuid(0x24)
                            val ebx3 = structVal9.ebx
                            val avx10Version = ebx3 and 0xff
                            // AVX10.2 supports masked versions of dot-product instructions available in avxvnni etc,
                            // so it doesn't make sense to have it without the unmasked versions
                            if (avx10Version >= 2 && avxvnni && avxvnniint8 && avxvnniint16) {
                                value.set(Feature.Avx102.toUInt())
                            }
                        }
                    }
                }
            }
        }
    }
    // Unfortunately, some Skylake chips erroneously report support for BMI1 and
    // BMI2 without actual support. These chips don't support AVX, and it seems
    // that all Intel chips with non-erroneous support BMI do (I didn't check
    // other vendors), so we can disable these flags for chips that don't also
    // report support for AVX.
    //
    // It's possible this will pessimize future chips that do support BMI and
    // not AVX, but this seems minor compared to a hard crash you get when
    // executing an unsupported instruction (to put it another way, it's safe
    // for us to under-report CPU features, but not to over-report them). Still,
    // to limit any impact this may have in the future, we only do this for
    // Intel chips, as it's a bug only present in their chips.
    //
    // This bug is documented as `SKL052` in the errata section of this document:
    // http://www.intel.com/content/dam/www/public/us/en/documents/specification-updates/desktop-6th-gen-core-family-spec-update.pdf
    if (vendorId2 == "GenuineIntel".encodeToByteArray() && !value.test(Feature.Avx.toUInt())) {
        value.unset(Feature.Bmi1.toUInt())
        value.unset(Feature.Bmi2.toUInt())
    }
    return value
}
