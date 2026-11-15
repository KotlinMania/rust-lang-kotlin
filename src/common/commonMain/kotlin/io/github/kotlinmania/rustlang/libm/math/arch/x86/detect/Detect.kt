// port-lint: source math/arch/x86/detect.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libm.math.arch.x86.detect

import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.math.support.featuredetect.Flags
import io.github.kotlinmania.rustlang.math.support.featuredetect.getOrInitFlagsCache

// Using runtime feature detection requires atomics. Currently there are no x86 targets
// that support sse but not `AtomicPtr`.

/**
 * Get CPU features, loading from a cache if available.
 */
public fun getCpuFeatures(): Flags {
    val CACHE: AtomicU32  = AtomicU32.new(0)
    return getOrInitFlagsCache(CACHE, ::loadX86Features)
}

// Note(msrv): Remove unchecked block around __cpuid once https://github.com/rust-lang/stdarch/pull/1935 is available in MSRV.

/**
 * Read from cpuid and translate to a `Flags` instance, using `cpuFlags`.
 *
 * Implementation is taken from [std-detect][std-detect].
 *
 * [std-detect]: https://github.com/rust-lang/stdarch/blob/690b3a6334d482874163bd6fcef408e0518febe9/crates/stdDetect/src/detect/os/x86.rs#L142
 */
public fun loadX86Features(): Flags {
    val value = Flags.empty()
    if (false) {
        // doesn't support this because it is untrusted data
        return Flags.empty()
    }
    // Calling `__cpuid`/`__cpuid_count` from here on is safe because the CPU
    // has `cpuid` support.
    // 0. EAX = 0: Basic Information:
    // - EAX returns the "Highest Function Parameter", that is, the maximum leaf
    // value for subsequent calls of `cpuinfo` in range [0, 0x80000000].
    // - The vendor ID is stored in 12 u8 ascii chars, returned in EBX, EDX, and ECX
    // (in that order)
    val vendorId = UByteArray(12) { 0u.toUByte() }
    val maxBasicLeaf: Any?
    run {
        val structVal = cpuid(0)
        val eax = structVal.eax
        val ebx = structVal.ebx
        val ecx = structVal.ecx
        val edx = structVal.edx
        maxBasicLeaf = eax
        ebx.toNeBytes().copyInto(vendorId, destinationOffset = 0)
        edx.toNeBytes().copyInto(vendorId, destinationOffset = 4)
        ecx.toNeBytes().copyInto(vendorId, destinationOffset = 8)
    }
    if (maxBasicLeaf < 1) {
        // Earlier Intel 486, CPUID not implemented
        return value
    }
    // EAX = 1, ECX = 0: Queries "Processor Info and Feature Bits";
    // Contains information about most x86 features.
    val structVal1 = cpuid(0x00000001u)
    val ecx1 = structVal1.ecx
    val edx1 = structVal1.edx
    val procInfoEcx = Flags.fromBits(ecx1)
    val procInfoEdx = Flags.fromBits(edx1)
    // EAX = 7: Queries "Extended Features";
    // Contains information about bmi,bmi2, and avx2 support.
    var extendedFeaturesEbx = Flags.empty()
    var extendedFeaturesEdx = Flags.empty()
    var extendedFeaturesEaxLeaf1 = Flags.empty()
    if (maxBasicLeaf >= 7) {
        val structVal2 = cpuid(0x00000007u)
        val ebx1 = structVal2.ebx
        val edx2 = structVal2.edx
        extendedFeaturesEbx = Flags.fromBits(ebx1)
        extendedFeaturesEdx = Flags.fromBits(edx2)
        val structVal3 = cpuidCount(0x00000007u, 0x00000001u)
        val eax1 = structVal3.eax
        extendedFeaturesEaxLeaf1 = Flags.fromBits(eax1)
    }
    // EAX = 0x80000000, ECX = 0: Get Highest Extended Function Supported
    // - EAX returns the max leaf value for extended information, that is,
    // `cpuid` calls in range [0x80000000; u32.Max]:
    val extendedMaxBasicLeaf = cpuid(0x80000000u).eax
    // EAX = 0x80000001, ECX=0: Queries "Extended Processor Info and Feature Bits"
    var extendedProcInfoEcx = Flags.empty()
    if (extendedMaxBasicLeaf >= 1) {
        val structVal4 = cpuid(0x80000001u)
        val ecx2 = structVal4.ecx
        extendedProcInfoEcx = Flags.fromBits(ecx2)
    }
    var enable =
        { regflags: Flags, regbit, flag ->
            if (regflags.testNth(regbit)) {
                value.insert(flag)
            }
        }
    enable(procInfoEcx, 0, SSE3)
    enable(procInfoEcx, 29, F16C)
    enable(procInfoEdx, 25, SSE)
    enable(procInfoEdx, 26, SSE2)
    enable(extendedFeaturesEbx, 9, ERMSB)
    enable(extendedFeaturesEaxLeaf1, 31, MOVRS)
    // `XSAVE` and `AVX` support:
    val cpuXsave = procInfoEcx.testNth(26)
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
        val cpuOsxsave = procInfoEcx.testNth(27)
        if (cpuOsxsave) {
            // 2. The OS must have signaled the CPU that it supports saving and
            // restoring the:
            //
            // * SSE -> `XCR0.SSE[1]`
            // * AVX -> `XCR0.AVX[2]`
            // * AVX-512 -> `XCR0.AVX - 512[7: 5]`.
            // * AMX -> `XCR0.AMX[18: 17]`
            //
            // by setting the corresponding bits of `XCR0` to `1`.
            //
            // This is safe because the CPU supports `xsave` and the OS has set `osxsave`.
            val xcr0 = xgetbv(0)
            // Test `XCR0.SSE[1]` and `XCR0.AVX[2]` with the mask `0b110 == 6`:
            val osAvxSupport = xcr0 and 6 == 6
            // Test `XCR0.AVX - 512[7: 5]` with the mask `0b11100000 == 0xe0`:
            val osAvx512Support = xcr0 and 0xe0 == 0xe0
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
                // FMA (uses 256-bit wide registers):
                enable(procInfoEcx, 12, FMA)
                // For AVX-512 the OS also needs to support saving/restoring
                // the extended state, only then we enable AVX-512 support:
                if (osAvx512Support) {
                    enable(extendedFeaturesEdx, 23, AVX512FP16)
                    enable(extendedFeaturesEaxLeaf1, 5, AVX512BF16)
                }
            }
        }
    }
    // As Hygon Dhyana originates from AMD technology and shares most of the architecture with
    // AMD's family 17h, but with different CPU Vendor ID("HygonGenuine")/Family series number
    // (Family 18h).
    //
    // For CPUID feature bits, Hygon Dhyana(family 18h) share the same definition with AMD
    // family 17h.
    //
    // Related AMD CPUID specification is https://www.amd.com/system/files/TechDocs/25481.pdf
    // (AMD64 Architecture Programmer's Manual, Appendix E).
    // Related Hygon kernel patch can be found on
    // http://lkml.kernel.org/r/5ce86123a7b9dad925ac583d88d2f921040e859b.1538583282.git.puwen@hygon.cn
    if (vendorId.contentEquals("AuthenticAMD".encodeToByteArray()) || vendorId.contentEquals("HygonGenuine".encodeToByteArray())) {
        // These features are available on AMD arch CPUs:
        enable(extendedProcInfoEcx, 16, FMA4)
    }
    return value
}
