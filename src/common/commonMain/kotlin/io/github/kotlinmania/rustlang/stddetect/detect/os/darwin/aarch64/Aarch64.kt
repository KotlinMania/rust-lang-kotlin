// port-lint: source detect/os/darwin/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.darwin.aarch64

import io.github.kotlinmania.rustlang.core.ffi.CStr
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache

// Run-time feature detection for aarch64 on Darwin (macOS/iOS/tvOS/watchOS/visionOS).
//
// <https://developer.apple.com/documentation/kernel/1387446-sysctlbyname/determiningInstructionSetCharacteristics>

public fun sysctlbyname(name: CStr): Boolean {
    val localStorageL13C4 = AddressableLocal(0, PrimitiveLayouts.I32)
    val enabled by localStorageL13C4
    val localStorageL14C4 = AddressableLocal(4, PrimitiveLayouts.USIZE64)
    val enabledPtr = (MutPtr(localStorageL13C4.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val ret = io.github.kotlinmania.libc.sysctlbyname(name.asPtr(), enabledPtr, RustMutableReference(localStorageL14C4.asMutPtr(), localStorageL14C4.layout), MutPtr(0L), 0)
    return when (ret) {
        0 -> enabled != 0
        else -> false
    }
}

/**
 * Try to read the features using sysctlbyname.
 */
internal fun detectFeatures(): Initializer {
    val value = Cache.Initializer.default()
    var enableFeature =
        { f, enable ->
            if (enable) {
                value.set(f.toUInt())
            }
        }
    // Armv8.0 features not using the standard identifiers
    val fp = sysctlbyname("hw.optional.floatingpoint")
    val asimd = sysctlbyname("hw.optional.AdvSIMD") || sysctlbyname("hw.optional.arm.AdvSIMD")
    val crcOld = sysctlbyname("hw.optional.armv8_crc32")
    // Armv8 and Armv9 features using the standard identifiers
    val aes = sysctlbyname("hw.optional.arm.FEAT_AES")
    val bf16 = sysctlbyname("hw.optional.arm.FEAT_BF16")
    val bti = sysctlbyname("hw.optional.arm.FEAT_BTI")
    val crc = sysctlbyname("hw.optional.arm.FEAT_CRC32")
    val cssc = sysctlbyname("hw.optional.arm.FEAT_CSSC")
    val dit = sysctlbyname("hw.optional.arm.FEAT_DIT")
    val dotprod = sysctlbyname("hw.optional.arm.FEAT_DotProd")
    val dpb = sysctlbyname("hw.optional.arm.FEAT_DPB")
    val dpb2 = sysctlbyname("hw.optional.arm.FEAT_DPB2")
    val ecv = sysctlbyname("hw.optional.arm.FEAT_ECV")
    val fcma = sysctlbyname("hw.optional.arm.FEAT_FCMA")
    val fhm = sysctlbyname("hw.optional.arm.FEAT_FHM")
    val flagm = sysctlbyname("hw.optional.arm.FEAT_FlagM")
    val flagm2 = sysctlbyname("hw.optional.arm.FEAT_FlagM2")
    val fp16 = sysctlbyname("hw.optional.arm.FEAT_FP16")
    val frintts = sysctlbyname("hw.optional.arm.FEAT_FRINTTS")
    val hbc = sysctlbyname("hw.optional.arm.FEAT_HBC")
    val i8mm = sysctlbyname("hw.optional.arm.FEAT_I8MM")
    val jsconv = sysctlbyname("hw.optional.arm.FEAT_JSCVT")
    val rcpc = sysctlbyname("hw.optional.arm.FEAT_LRCPC")
    val rcpc2 = sysctlbyname("hw.optional.arm.FEAT_LRCPC2")
    val lse = sysctlbyname("hw.optional.arm.FEAT_LSE")
    val lse2 = sysctlbyname("hw.optional.arm.FEAT_LSE2")
    val mte = sysctlbyname("hw.optional.arm.FEAT_MTE")
    val mte2 = sysctlbyname("hw.optional.arm.FEAT_MTE2")
    val pauth = sysctlbyname("hw.optional.arm.FEAT_PAuth")
    val pmull = sysctlbyname("hw.optional.arm.FEAT_PMULL")
    val rdm = sysctlbyname("hw.optional.arm.FEAT_RDM")
    val sb = sysctlbyname("hw.optional.arm.FEAT_SB")
    val sha1 = sysctlbyname("hw.optional.arm.FEAT_SHA1")
    val sha256 = sysctlbyname("hw.optional.arm.FEAT_SHA256")
    val sha3 = sysctlbyname("hw.optional.arm.FEAT_SHA3")
    val sha512 = sysctlbyname("hw.optional.arm.FEAT_SHA512")
    val sme = sysctlbyname("hw.optional.arm.FEAT_SME")
    val sme2 = sysctlbyname("hw.optional.arm.FEAT_SME2")
    val sme2p1 = sysctlbyname("hw.optional.arm.FEAT_SME2p1")
    val smeB16b16 = sysctlbyname("hw.optional.arm.FEAT_SME_B16B16")
    val smeF16f16 = sysctlbyname("hw.optional.arm.FEAT_SME_F16F16")
    val smeF64f64 = sysctlbyname("hw.optional.arm.FEAT_SME_F64F64")
    val smeI16i64 = sysctlbyname("hw.optional.arm.FEAT_SME_I16I64")
    val ssbs = sysctlbyname("hw.optional.arm.FEAT_SSBS")
    val sveB16b16 = sysctlbyname("hw.optional.arm.FEAT_SVE_B16B16")
    val wfxt = sysctlbyname("hw.optional.arm.FEAT_WFxT")
    // The following features are not exposed by `isAarch64FeatureDetected`,
    // but *are* reported by `sysctl`. They are here as documentation that they
    // exist, and may potentially be exposed later.
    //
    // let afp = _sysctlbyname(c"hw.optional.arm.FEAT_AFP");
    // let csv2 = _sysctlbyname(c"hw.optional.arm.FEAT_CSV2");
    // let csv3 = _sysctlbyname(c"hw.optional.arm.FEAT_CSV3");
    // let ebf16 = _sysctlbyname(c"hw.optional.arm.FEAT_EBF16");
    // let fpac = _sysctlbyname(c"hw.optional.arm.FEAT_FPAC");
    // let fpaccombine = _sysctlbyname(c"hw.optional.arm.FEAT_FPACCOMBINE");
    // let mteAsync = _sysctlbyname(c"hw.optional.arm.FEAT_MTE_ASYNC");
    // let mteCanonicalTags = _sysctlbyname(c"hw.optional.arm.FEAT_MTE_CANONICAL_TAGS");
    // let mteNoAddressTags = _sysctlbyname(c"hw.optional.arm.FEAT_MTE_NO_ADDRESS_TAGS");
    // let mteStoreOnly = _sysctlbyname(c"hw.optional.arm.FEAT_MTE_STORE_ONLY");
    // let mte3 = _sysctlbyname(c"hw.optional.arm.FEAT_MTE3");
    // let mte4 = _sysctlbyname(c"hw.optional.arm.FEAT_MTE4");
    // let pacimp = _sysctlbyname(c"hw.optional.arm.FEAT_PACIMP");
    // let pauth2 = _sysctlbyname(c"hw.optional.arm.FeatPauth2");
    // let rpres = _sysctlbyname(c"hw.optional.arm.FEAT_RPRES");
    // let specres = _sysctlbyname(c"hw.optional.arm.FEAT_SPECRES");
    // let specres2 = _sysctlbyname(c"hw.optional.arm.FEAT_SPECRES2");
    //
    // The following "features" are reported by `sysctl` but are mandatory parts
    // of SME or SME2, and so are not exposed separately by
    // `isAarch64FeatureDetected`.  They are here to document their
    // existence, in case they're needed in the future.
    //
    // let smeB16f32 = _sysctlbyname(c"hw.optional.arm.SME_B16F32");
    // let smeBi32i32 = _sysctlbyname(c"hw.optional.arm.SME_BI32I32");
    // let smeF16f32 = _sysctlbyname(c"hw.optional.arm.SME_F16F32");
    // let smeF32f32 = _sysctlbyname(c"hw.optional.arm.SME_F32F32");
    // let smeI16i32 = _sysctlbyname(c"hw.optional.arm.SME_I16I32");
    // let smeI8i32 = _sysctlbyname(c"hw.optional.arm.SME_I8I32");
    //
    enableFeature(Feature::aes, aes && pmull)
    enableFeature(Feature::asimd, asimd)
    enableFeature(Feature::bf16, bf16)
    enableFeature(Feature::bti, bti)
    enableFeature(Feature::crc, crcOld || crc)
    enableFeature(Feature::cssc, cssc)
    enableFeature(Feature::dit, dit)
    enableFeature(Feature::dotprod, dotprod)
    enableFeature(Feature::dpb, dpb)
    enableFeature(Feature::dpb2, dpb2)
    enableFeature(Feature::ecv, ecv)
    enableFeature(Feature::fcma, fcma)
    enableFeature(Feature::fhm, fhm)
    enableFeature(Feature::flagm, flagm)
    enableFeature(Feature::flagm2, flagm2)
    enableFeature(Feature::fp, fp)
    enableFeature(Feature::fp16, fp16)
    enableFeature(Feature::frintts, frintts)
    enableFeature(Feature::hbc, hbc)
    enableFeature(Feature::i8mm, i8mm)
    enableFeature(Feature::jsconv, jsconv)
    enableFeature(Feature::lse, lse)
    enableFeature(Feature::lse2, lse2)
    enableFeature(Feature::mte, mte && mte2)
    enableFeature(Feature::paca, pauth)
    enableFeature(Feature::pacg, pauth)
    enableFeature(Feature::pmull, aes && pmull)
    enableFeature(Feature::rcpc, rcpc)
    enableFeature(Feature::rcpc2, rcpc2)
    enableFeature(Feature::rdm, rdm)
    enableFeature(Feature::sb, sb)
    enableFeature(Feature::sha2, sha1 && sha256 && asimd)
    enableFeature(Feature::sha3, sha512 && sha3 && asimd)
    enableFeature(Feature::sme, sme)
    enableFeature(Feature::sme2, sme2)
    enableFeature(Feature::sme2p1, sme2p1)
    enableFeature(Feature::smeB16b16, smeB16b16)
    enableFeature(Feature::smeF16f16, smeF16f16)
    enableFeature(Feature::smeF64f64, smeF64f64)
    enableFeature(Feature::smeI16i64, smeI16i64)
    enableFeature(Feature::ssbs, ssbs)
    enableFeature(Feature::sveB16b16, sveB16b16)
    enableFeature(Feature::wfxt, wfxt)
    return value
}
