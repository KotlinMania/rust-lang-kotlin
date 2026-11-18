// port-lint: source detect/os/windows/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.windows.aarch64

import io.github.kotlinmania.rustlang.stddetect.detect.arch.aarch64.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Initializer

// Run-time feature detection for Aarch64 on Windows.

public typealias Dword = UInt

public typealias Bool = Int

/**
 * Try to read the features using IsProcessorFeaturePresent.
 */
public fun detectFeatures(): Initializer {
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
    fun isProcessorFeaturePresent(processorFeature: Dword): Bool = 0
    val value = Initializer.default()
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
            enableFeature(Feature.Fp, IsProcessorFeaturePresent(PF_ARM_VFP_32_REGISTERS_AVAILABLE) != FALSE)
            enableFeature(Feature.Asimd, IsProcessorFeaturePresent(PF_ARM_NEON_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Crc, IsProcessorFeaturePresent(PF_ARM_V8_CRC32_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Lse, IsProcessorFeaturePresent(PF_ARM_V81_ATOMIC_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Dotprod, IsProcessorFeaturePresent(PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Jsconv, IsProcessorFeaturePresent(PF_ARM_V83_JSCVT_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Rcpc, IsProcessorFeaturePresent(PF_ARM_V83_LRCPC_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve, IsProcessorFeaturePresent(PF_ARM_SVE_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2, IsProcessorFeaturePresent(PF_ARM_SVE2_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2p1, IsProcessorFeaturePresent(PF_ARM_SVE2_1_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2Aes, IsProcessorFeaturePresent(PF_ARM_SVE_AES_INSTRUCTIONS_AVAILABLE) != FALSE && IsProcessorFeaturePresent(PF_ARM_SVE_PMULL128_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2Bitperm, IsProcessorFeaturePresent(PF_ARM_SVE_BITPERM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.SveB16b16, IsProcessorFeaturePresent(PF_ARM_SVE_B16B16_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2Sha3, IsProcessorFeaturePresent(PF_ARM_SVE_SHA3_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Sve2Sm4, IsProcessorFeaturePresent(PF_ARM_SVE_SM4_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.F32mm, IsProcessorFeaturePresent(PF_ARM_SVE_F32MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.F64mm, IsProcessorFeaturePresent(PF_ARM_SVE_F64MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Lse2, IsProcessorFeaturePresent(PF_ARM_LSE2_AVAILABLE) != FALSE)
            enableFeature(Feature.Fp16, IsProcessorFeaturePresent(PF_ARM_V82_FP16_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.I8mm, IsProcessorFeaturePresent(PF_ARM_V82_I8MM_INSTRUCTIONS_AVAILABLE) != FALSE)
            enableFeature(Feature.Bf16, IsProcessorFeaturePresent(PF_ARM_V86_BF16_INSTRUCTIONS_AVAILABLE) != FALSE)
            // stdarch `sha3` is FEAT_SHA3 + FEAT_SHA512 together; Windows
            // exposes them as two separate flags.
            enableFeature(Feature.Sha3, IsProcessorFeaturePresent(PF_ARM_SHA3_INSTRUCTIONS_AVAILABLE) != FALSE && IsProcessorFeaturePresent(PF_ARM_SHA512_INSTRUCTIONS_AVAILABLE) != FALSE)
            // No PF_ARM_RDM_* constant exists. Derive FEAT_RDM from FeatDotProd:
            // DotProd is an optional v8.2-A feature only present on cores that
            // implement at least v8.1-A; v8.1-A with AdvSIMD mandates FEAT_RDM
            // (Arm ARM K.a §D17.2.91), and AdvSIMD is universal on Windows-on-ARM.
            // Same inference shipped in .NET 10 (dotnet/runtime PR 109493).
            enableFeature(Feature.Rdm, IsProcessorFeaturePresent(PF_ARM_V82_DP_INSTRUCTIONS_AVAILABLE) != FALSE)
            // PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE means aes, sha1, sha2 and
            // pmull support
            val crypto = IsProcessorFeaturePresent(PF_ARM_V8_CRYPTO_INSTRUCTIONS_AVAILABLE) != FALSE
            enableFeature(Feature.Aes, crypto)
            enableFeature(Feature.Pmull, crypto)
            enableFeature(Feature.Sha2, crypto)
        }
    }
    return value
}
