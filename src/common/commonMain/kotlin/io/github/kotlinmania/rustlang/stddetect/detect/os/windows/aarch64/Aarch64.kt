// port-lint: source detect/os/windows/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.windows.aarch64

import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache

// Run-time feature detection for Aarch64 on Windows.

public typealias Dword = UInt

public typealias Bool = Int

/**
 * Try to read the features using IsProcessorFeaturePresent.
 */
internal fun detectFeatures(): cache.Initializer {
    val FALSE: Bool = 0
    // The following Microsoft documents isn't updated for aarch64.
    // https://docs.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-isprocessorfeaturepresent
    // These are defined in winnt.h of Windows SDK
    val PF_ARM_VFP_32_REGISTERS_AVAILABLE: UInt = 18u
    val PF_ARM_NEON_INSTRUCTIONS_AVAILABLE: UInt = 19u
    val PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE: UInt = 30u
    val PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE: UInt = 31u
    val PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE: UInt = 34u
    val PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE: UInt = 43u
    val PF_ARM_V83_JSCVT_INSTRUCTIONS_AVAILABLE: UInt = 44u
    val PF_ARM_V83_LRCPC_INSTRUCTIONS_AVAILABLE: UInt = 45u
    val PF_ARM_SVE_INSTRUCTIONS_AVAILABLE: UInt = 46u
    val PF_ARM_SVE2_INSTRUCTIONS_AVAILABLE: UInt = 47u
    val PF_ARM_SVE2_1_INSTRUCTIONS_AVAILABLE: UInt = 48u
    val PF_ARM_SVE_AES_INSTRUCTIONS_AVAILABLE: UInt = 49u
    val PF_ARM_SVE_PMULL128_INSTRUCTIONS_AVAILABLE: UInt = 50u
    val PF_ARM_SVE_BITPERM_INSTRUCTIONS_AVAILABLE: UInt = 51u
    // const PF_ARM_SVE_BF16_INSTRUCTIONS_AVAILABLE: u32 = 52;
    // const PF_ARM_SVE_EBF16_INSTRUCTIONS_AVAILABLE: u32 = 53;
    val PF_ARM_SVE_B16B16_INSTRUCTIONS_AVAILABLE: UInt = 54u
    val PF_ARM_SVE_SHA3_INSTRUCTIONS_AVAILABLE: UInt = 55u
    val PF_ARM_SVE_SM4_INSTRUCTIONS_AVAILABLE: UInt = 56u
    // const PF_ARM_SVE_I8MM_INSTRUCTIONS_AVAILABLE: u32 = 57;
    val PF_ARM_SVE_F32MM_INSTRUCTIONS_AVAILABLE: UInt = 58u
    val PF_ARM_SVE_F64MM_INSTRUCTIONS_AVAILABLE: UInt = 59u
    val PF_ARM_LSE2_AVAILABLE: UInt = 62u
    val PF_ARM_SHA3_INSTRUCTIONS_AVAILABLE: UInt = 64u
    val PF_ARM_SHA512_INSTRUCTIONS_AVAILABLE: UInt = 65u
    val PF_ARM_V82_I8MM_INSTRUCTIONS_AVAILABLE: UInt = 66u
    val PF_ARM_V82_FP16_INSTRUCTIONS_AVAILABLE: UInt = 67u
    val PF_ARM_V86_BF16_INSTRUCTIONS_AVAILABLE: UInt = 68u
    fun isProcessorFeaturePresent(processorFeature: Dword): Bool = null as Bool
    val value = cache.Initializer.default()
    run {
        var enableFeature =
            { f, enable ->
                if (enable) {
                    value.set(f.toUInt())
                }
            }
        // Some features may be supported on the current CPU but have no
        // detection path through the Win32 API; those report `false`.
        // SAFETY: `IsProcessorFeaturePresent` is a Win32 entry point taking a
        // `DWORD` by value and returning a `BOOL`. No pointer parameters,
        // no out-parameters, no thread-safety constraints.
        run {
            enableFeature(Feature::fp, IsProcessorFeaturePresent(PF_ARM_VFP_32_REGISTERS_AVAILABLE) != FALSE)
            enableFeature(Feature::asimd, IsProcessorFeaturePresent(PF_ARM_NEON_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::crc, IsProcessorFeaturePresent(PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::lse, IsProcessorFeaturePresent(PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::dotprod, IsProcessorFeaturePresent(PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::jsconv, IsProcessorFeaturePresent(PF_ARM_V83_JSCVT_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::rcpc, IsProcessorFeaturePresent(PF_ARM_V83_LRCPC_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve, IsProcessorFeaturePresent(PF_ARM_SVE_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2, IsProcessorFeaturePresent(PF_ARM_SVE2_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2p1, IsProcessorFeaturePresent(PF_ARM_SVE2_1_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2Aes, IsProcessorFeaturePresent(PF_ARM_SVE_AES_INSTRUCTIONS_AVAILABLE) != FALSE && IsProcessorFeaturePresent(PF_ARM_SVE_PMULL128_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2Bitperm, IsProcessorFeaturePresent(PF_ARM_SVE_BITPERM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sveB16b16, IsProcessorFeaturePresent(PF_ARM_SVE_B16B16_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2Sha3, IsProcessorFeaturePresent(PF_ARM_SVE_SHA3_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::sve2Sm4, IsProcessorFeaturePresent(PF_ARM_SVE_SM4_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::f32mm, IsProcessorFeaturePresent(PF_ARM_SVE_F32MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::f64mm, IsProcessorFeaturePresent(PF_ARM_SVE_F64MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::lse2, IsProcessorFeaturePresent(PF_ARM_LSE2_AVAILABLE) != FALSE)
            enableFeature(Feature::fp16, IsProcessorFeaturePresent(PF_ARM_V82_FP16_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::i8mm, IsProcessorFeaturePresent(PF_ARM_V82_I8MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature::bf16, IsProcessorFeaturePresent(PF_ARM_V86_BF16_INSTRUCTIONS_AVAILABLE) != FALSE)
            // stdarch `sha3` is FEAT_SHA3 + FEAT_SHA512 together; Windows
            // exposes them as two separate flags.
            enableFeature(Feature::sha3, IsProcessorFeaturePresent(PF_ARM_SHA3_INSTRUCTIONS_AVAILABLE) != FALSE && IsProcessorFeaturePresent(PF_ARM_SHA512_INSTRUCTIONS_AVAILABLE) != FALSE)
            // No PF_ARM_RDM_* constant exists. Derive FEAT_RDM from FeatDotProd:
            // DotProd is an optional v8.2-A feature only present on cores that
            // implement at least v8.1-A; v8.1-A with AdvSIMD mandates FEAT_RDM
            // (Arm ARM K.a §D17.2.91), and AdvSIMD is universal on Windows-on-ARM.
            // Same inference shipped in .NET 10 (dotnet/runtime PR 109493).
            enableFeature(Feature::rdm, IsProcessorFeaturePresent(PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE) != FALSE)
            // PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE means aes, sha1, sha2 and
            // pmull support
            val crypto = IsProcessorFeaturePresent(PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE) != FALSE
            enableFeature(Feature::aes, crypto)
            enableFeature(Feature::pmull, crypto)
            enableFeature(Feature::sha2, crypto)
        }
    }
    return value
}
