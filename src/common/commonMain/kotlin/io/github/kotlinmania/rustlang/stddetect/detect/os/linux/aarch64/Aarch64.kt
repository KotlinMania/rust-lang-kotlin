// port-lint: source detect/os/linux/aarch64.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.aarch64

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.systemPropertyGet
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.PROP_VALUE_MAX
import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.AuxVec
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache

// Run-time feature detection for Aarch64 on Linux.

/**
 * Try to read the features from the auxiliary vector.
 */
public fun detectFeatures(): cache.Initializer {
    val isExynos9810 = run {
        // Samsung Exynos 9810 has a bug that big and little cores have different
        // ISAs. And on older Android (pre-9), the kernel incorrectly reports
        // that features available only on some cores are available on all cores.
        // https://reviews.llvm.org/D114523
        val localStorageL14C8 = AddressableArrayLocal.fromUByteArray(UByteArray(PROP_VALUE_MAX.toInt()) { 0u.toUByte() })
        val arch by localStorageL14C8
        val len = systemPropertyGet("ro.arch".asPtr(), (localStorageL14C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
        // On Exynos, ro.arch is not available on Android 12+, but it is fine
        // because Android 9+ includes the fix.
        len > 0 && arch.startsWith("exynos9810".encodeToByteArray())
    }
    when (val matchVal = auxv()) {
        is Result.Ok<*, *> -> {
            val auxv = matchVal.value
            val hwcap: AtHwcap = auxv.into()
            return hwcap.cache(isExynos9810)
        }
        else -> {}
    }
    return cache.Initializer.default()
}

/**
 * These values are part of the platform-specific [asm/hwcap.h][hwcap] .
 *
 * The names match those used for cpuinfo.
 *
 * [hwcap]: https://github.com/torvalds/linux/blob/master/arch/arm64/include/uapi/asm/hwcap.h
 */
public open class AtHwcap(
    // AT_HWCAP
    public var fp: Boolean,
    public var asimd: Boolean,
    // evtstrm: No LLVM support.
    public var aes: Boolean,
    public var pmull: Boolean,
    public var sha1: Boolean,
    public var sha2: Boolean,
    public var crc32: Boolean,
    public var atomics: Boolean,
    public var fphp: Boolean,
    public var asimdhp: Boolean,
    // cpuid: No LLVM support.
    public var asimdrdm: Boolean,
    public var jscvt: Boolean,
    public var fcma: Boolean,
    public var lrcpc: Boolean,
    public var dcpop: Boolean,
    public var sha3: Boolean,
    public var sm3: Boolean,
    public var sm4: Boolean,
    public var asimddp: Boolean,
    public var sha512: Boolean,
    public var sve: Boolean,
    public var fhm: Boolean,
    public var dit: Boolean,
    public var uscat: Boolean,
    public var ilrcpc: Boolean,
    public var flagm: Boolean,
    public var ssbs: Boolean,
    public var sb: Boolean,
    public var paca: Boolean,
    public var pacg: Boolean,
    // AT_HWCAP2
    public var dcpodp: Boolean,
    public var sve2: Boolean,
    public var sveaes: Boolean,
    public var svepmull: Boolean,
    public var svebitperm: Boolean,
    public var svesha3: Boolean,
    public var svesm4: Boolean,
    public var flagm2: Boolean,
    public var frint: Boolean,
    // svei8mm: See i8mm feature.
    public var svef32mm: Boolean,
    public var svef64mm: Boolean,
    // svebf16: See bf16 feature.
    public var i8mm: Boolean,
    public var bf16: Boolean,
    // dgh: No LLVM support.
    public var rng: Boolean,
    public var bti: Boolean,
    public var mte: Boolean,
    public var ecv: Boolean,
    // afp: bool,
    // rpres: bool,
    // mte3: bool,
    public var sme: Boolean,
    public var smei16i64: Boolean,
    public var smef64f64: Boolean,
    // smei8i32: bool,
    // smef16f32: bool,
    // smeb16f32: bool,
    // smef32f32: bool,
    public var smefa64: Boolean,
    public var wfxt: Boolean,
    // ebf16: bool,
    // sveebf16: bool,
    public var cssc: Boolean,
    // rprfm: bool,
    public var sve2p1: Boolean,
    public var sme2: Boolean,
    public var sme2p1: Boolean,
    // smei16i32: bool,
    // smebi32i32: bool,
    public var smeb16b16: Boolean,
    public var smef16f16: Boolean,
    public var mops: Boolean,
    public var hbc: Boolean,
    public var sveb16b16: Boolean,
    public var lrcpc3: Boolean,
    public var lse128: Boolean,
    public var fpmr: Boolean,
    public var lut: Boolean,
    public var faminmax: Boolean,
    public var f8cvt: Boolean,
    public var f8fma: Boolean,
    public var f8dp4: Boolean,
    public var f8dp2: Boolean,
    public var f8e4m3: Boolean,
    public var f8e5m2: Boolean,
    public var smelutv2: Boolean,
    public var smef8f16: Boolean,
    public var smef8f32: Boolean,
    public var smesf8fma: Boolean,
    public var smesf8dp4: Boolean,
    public var smesf8dp2: Boolean,
) {
    /**
     * Initializes the cache from the feature -bits.
     *
     * The feature dependencies here come directly from LLVM's feature definitions:
     * <https://github.com/llvm/llvm-project/blob/main/llvm/lib/Target/AArch64/AArch64.td>
     */
    public open fun cache(isExynos9810: Boolean): cache.Initializer {
        val value = cache.Initializer.default()
        run {
            var enableFeature =
                { f, enable ->
                    if (enable) {
                        value.set(f.toUInt())
                    }
                }
            // Samsung Exynos 9810 has a bug that big and little cores have different
            // ISAs. And on older Android (pre-9), the kernel incorrectly reports
            // that features available only on some cores are available on all cores.
            // So, only check features that are known to be available on exynos-m3:
            // $ rustc --print cfg --target aarch64-linux-android -C target-cpu=exynos-m3 | grep targetFeature
            // See also https://github.com/rust-lang/stdarch/pull/1378#discussionR1103748342.
            if (isExynos9810) {
                enableFeature(Feature::fp, this.fp)
                enableFeature(Feature::crc, this.crc32)
                // ASIMD support requires float support - if half-floats are
                // supported, it also requires half-float support:
                val asimd = this.fp && this.asimd && (this.fphp.inv() or this.asimdhp)
                enableFeature(Feature::asimd, asimd)
                // Cryptographic extensions require ASIMD
                // AES also covers FEAT_PMULL
                enableFeature(Feature::aes, this.aes && this.pmull && asimd)
                enableFeature(Feature::sha2, this.sha1 && this.sha2 && asimd)
                return value
            }
            enableFeature(Feature::fp, this.fp)
            // Half-float support requires float support
            enableFeature(Feature::fp16, this.fp && this.fphp)
            // FHM (fp16fml in LLVM) requires half float support
            enableFeature(Feature::fhm, this.fphp && this.fhm)
            enableFeature(Feature::pmull, this.pmull)
            enableFeature(Feature::crc, this.crc32)
            enableFeature(Feature::lse, this.atomics)
            enableFeature(Feature::lse2, this.uscat)
            enableFeature(Feature::lse128, this.lse128 && this.atomics)
            enableFeature(Feature::rcpc, this.lrcpc)
            // RCPC2 (rcpc-immo in LLVM) requires RCPC support
            val rcpc2 = this.ilrcpc && this.lrcpc
            enableFeature(Feature::rcpc2, rcpc2)
            enableFeature(Feature::rcpc3, this.lrcpc3 && rcpc2)
            enableFeature(Feature::dit, this.dit)
            enableFeature(Feature::flagm, this.flagm)
            enableFeature(Feature::flagm2, this.flagm2)
            enableFeature(Feature::ssbs, this.ssbs)
            enableFeature(Feature::sb, this.sb)
            enableFeature(Feature::paca, this.paca)
            enableFeature(Feature::pacg, this.pacg)
            // enableFeature(Feature.pauthLr, self.pauthlr);
            enableFeature(Feature::dpb, this.dcpop)
            enableFeature(Feature::dpb2, this.dcpodp)
            enableFeature(Feature::rand, this.rng)
            enableFeature(Feature::bti, this.bti)
            enableFeature(Feature::mte, this.mte)
            // jsconv requires float support
            enableFeature(Feature::jsconv, this.jscvt && this.fp)
            enableFeature(Feature::rdm, this.asimdrdm)
            enableFeature(Feature::dotprod, this.asimddp)
            enableFeature(Feature::frintts, this.frint)
            // FEAT_I8MM & FEAT_BF16 also include optional SVE components which linux exposes
            // separately. We ignore that distinction here.
            enableFeature(Feature::i8mm, this.i8mm)
            enableFeature(Feature::bf16, this.bf16)
            // ASIMD support requires float support - if half-floats are
            // supported, it also requires half-float support:
            val asimd1 = this.fp && this.asimd && (this.fphp.inv() or this.asimdhp)
            enableFeature(Feature::asimd, asimd1)
            // ASIMD extensions require ASIMD support:
            enableFeature(Feature::fcma, this.fcma && asimd1)
            enableFeature(Feature::sve, this.sve && asimd1)
            // SVE extensions require SVE & ASIMD
            enableFeature(Feature::f32mm, this.svef32mm && this.sve && asimd1)
            enableFeature(Feature::f64mm, this.svef64mm && this.sve && asimd1)
            // Cryptographic extensions require ASIMD
            enableFeature(Feature::aes, this.aes && asimd1)
            enableFeature(Feature::sha2, this.sha1 && this.sha2 && asimd1)
            // SHA512/SHA3 require SHA1 & SHA256
            enableFeature(Feature::sha3, this.sha512 && this.sha3 && this.sha1 && this.sha2 && asimd1)
            enableFeature(Feature::sm4, this.sm3 && this.sm4 && asimd1)
            // SVE2 requires SVE
            val sve2 = this.sve2 && this.sve && asimd1
            enableFeature(Feature::sve2, sve2)
            enableFeature(Feature::sve2p1, this.sve2p1 && sve2)
            // SVE2 extensions require SVE2 and crypto features
            enableFeature(Feature::sve2Aes, this.sveaes && this.svepmull && sve2 && this.aes)
            enableFeature(Feature::sve2Sm4, this.svesm4 && sve2 && this.sm3 && this.sm4)
            enableFeature(Feature::sve2Sha3, this.svesha3 && sve2 && this.sha512 && this.sha3 && this.sha1 && this.sha2)
            enableFeature(Feature::sve2Bitperm, this.svebitperm && this.sve2)
            enableFeature(Feature::sveB16b16, this.bf16 && this.sveb16b16)
            enableFeature(Feature::hbc, this.hbc)
            enableFeature(Feature::mops, this.mops)
            enableFeature(Feature::ecv, this.ecv)
            enableFeature(Feature::lut, this.lut)
            enableFeature(Feature::cssc, this.cssc)
            enableFeature(Feature::fpmr, this.fpmr)
            enableFeature(Feature::faminmax, this.faminmax)
            val fp8 = this.f8cvt && this.faminmax && this.lut && this.bf16
            enableFeature(Feature::fp8, fp8)
            val fp8fma = this.f8fma && fp8
            enableFeature(Feature::fp8fma, fp8fma)
            val fp8dot4 = this.f8dp4 && fp8fma
            enableFeature(Feature::fp8dot4, fp8dot4)
            enableFeature(Feature::fp8dot2, this.f8dp2 && fp8dot4)
            enableFeature(Feature::wfxt, this.wfxt)
            val sme = this.sme && this.bf16
            enableFeature(Feature::sme, sme)
            enableFeature(Feature::smeI16i64, this.smei16i64 && sme)
            enableFeature(Feature::smeF64f64, this.smef64f64 && sme)
            enableFeature(Feature::smeFa64, this.smefa64 && sme && sve2)
            val sme2 = this.sme2 && sme
            enableFeature(Feature::sme2, sme2)
            enableFeature(Feature::sme2p1, this.sme2p1 && sme2)
            enableFeature(Feature::smeB16b16, sme2 && this.bf16 && this.sveb16b16 && this.smeb16b16)
            enableFeature(Feature::smeF16f16, this.smef16f16 && sme2)
            enableFeature(Feature::smeLutv2, this.smelutv2)
            val smeF8f32 = this.smef8f32 && sme2 && fp8
            enableFeature(Feature::smeF8f32, smeF8f32)
            enableFeature(Feature::smeF8f16, this.smef8f16 && smeF8f32)
            val ssveFp8fma = this.smesf8fma && sme2 && fp8
            enableFeature(Feature::ssveFp8fma, ssveFp8fma)
            val ssveFp8dot4 = this.smesf8dp4 && ssveFp8fma
            enableFeature(Feature::ssveFp8dot4, ssveFp8dot4)
            enableFeature(Feature::ssveFp8dot2, this.smesf8dp2 && ssveFp8dot4)
        }
        return value
    }

    public companion object {
        public fun typeLayout(): TypeLayout<AtHwcap> =
            object : TypeLayout<AtHwcap> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 78L
                override fun readNative(addr: Long): AtHwcap =
                    AtHwcap(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 3L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 4L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 5L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 6L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 7L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 8L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 9L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 10L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 11L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 12L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 13L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 14L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 15L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 16L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 17L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 18L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 19L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 20L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 21L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 22L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 23L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 24L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 25L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 26L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 27L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 28L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 29L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 30L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 31L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 32L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 33L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 34L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 35L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 36L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 37L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 38L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 39L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 40L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 41L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 42L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 43L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 44L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 45L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 46L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 47L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 48L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 49L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 50L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 51L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 52L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 53L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 54L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 55L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 56L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 57L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 58L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 59L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 60L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 61L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 62L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 63L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 64L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 65L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 66L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 67L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 68L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 69L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 70L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 71L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 72L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 73L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 74L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 75L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 76L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 77L),
                    )
                override fun writeNative(addr: Long, value: AtHwcap) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.fp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.asimd)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.aes)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.pmull)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.sha1)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.sha2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 6L, value.crc32)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 7L, value.atomics)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 8L, value.fphp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 9L, value.asimdhp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 10L, value.asimdrdm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 11L, value.jscvt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 12L, value.fcma)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 13L, value.lrcpc)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 14L, value.dcpop)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 15L, value.sha3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 16L, value.sm3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 17L, value.sm4)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 18L, value.asimddp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 19L, value.sha512)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 20L, value.sve)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 21L, value.fhm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 22L, value.dit)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 23L, value.uscat)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 24L, value.ilrcpc)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 25L, value.flagm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 26L, value.ssbs)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 27L, value.sb)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 28L, value.paca)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 29L, value.pacg)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 30L, value.dcpodp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 31L, value.sve2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 32L, value.sveaes)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 33L, value.svepmull)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 34L, value.svebitperm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 35L, value.svesha3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 36L, value.svesm4)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 37L, value.flagm2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 38L, value.frint)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 39L, value.svef32mm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 40L, value.svef64mm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 41L, value.i8mm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 42L, value.bf16)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 43L, value.rng)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 44L, value.bti)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 45L, value.mte)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 46L, value.ecv)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 47L, value.sme)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 48L, value.smei16i64)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 49L, value.smef64f64)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 50L, value.smefa64)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 51L, value.wfxt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 52L, value.cssc)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 53L, value.sve2p1)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 54L, value.sme2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 55L, value.sme2p1)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 56L, value.smeb16b16)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 57L, value.smef16f16)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 58L, value.mops)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 59L, value.hbc)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 60L, value.sveb16b16)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 61L, value.lrcpc3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 62L, value.lse128)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 63L, value.fpmr)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 64L, value.lut)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 65L, value.faminmax)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 66L, value.f8cvt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 67L, value.f8fma)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 68L, value.f8dp4)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 69L, value.f8dp2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 70L, value.f8e4m3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 71L, value.f8e5m2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 72L, value.smelutv2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 73L, value.smef8f16)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 74L, value.smef8f32)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 75L, value.smesf8fma)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 76L, value.smesf8dp4)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 77L, value.smesf8dp2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AtHwcap =
                    AtHwcap(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 7),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 9),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 11),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 13),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 15),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 17),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 19),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 21),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 22),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 23),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 25),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 26),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 27),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 29),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 30),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 31),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 33),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 34),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 35),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 37),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 38),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 39),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 41),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 42),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 43),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 45),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 46),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 47),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 49),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 50),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 51),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 53),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 54),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 55),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 57),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 58),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 59),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 61),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 62),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 63),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 65),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 66),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 67),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 68),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 69),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 70),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 71),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 73),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 74),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 75),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 77),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AtHwcap) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.fp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.asimd)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.aes)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.pmull)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.sha1)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.sha2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 6, value.crc32)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 7, value.atomics)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 8, value.fphp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 9, value.asimdhp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 10, value.asimdrdm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 11, value.jscvt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 12, value.fcma)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 13, value.lrcpc)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 14, value.dcpop)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 15, value.sha3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 16, value.sm3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 17, value.sm4)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 18, value.asimddp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 19, value.sha512)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 20, value.sve)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 21, value.fhm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 22, value.dit)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 23, value.uscat)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 24, value.ilrcpc)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 25, value.flagm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 26, value.ssbs)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 27, value.sb)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 28, value.paca)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 29, value.pacg)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 30, value.dcpodp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 31, value.sve2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 32, value.sveaes)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 33, value.svepmull)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 34, value.svebitperm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 35, value.svesha3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 36, value.svesm4)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 37, value.flagm2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 38, value.frint)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 39, value.svef32mm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 40, value.svef64mm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 41, value.i8mm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 42, value.bf16)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 43, value.rng)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 44, value.bti)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 45, value.mte)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 46, value.ecv)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 47, value.sme)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 48, value.smei16i64)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 49, value.smef64f64)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 50, value.smefa64)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 51, value.wfxt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 52, value.cssc)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 53, value.sve2p1)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 54, value.sme2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 55, value.sme2p1)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 56, value.smeb16b16)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 57, value.smef16f16)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 58, value.mops)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 59, value.hbc)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 60, value.sveb16b16)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 61, value.lrcpc3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 62, value.lse128)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 63, value.fpmr)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 64, value.lut)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 65, value.faminmax)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 66, value.f8cvt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 67, value.f8fma)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 68, value.f8dp4)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 69, value.f8dp2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 70, value.f8e4m3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 71, value.f8e5m2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 72, value.smelutv2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 73, value.smef8f16)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 74, value.smef8f32)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 75, value.smesf8fma)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 76, value.smesf8dp4)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 77, value.smesf8dp2)
                }
            }

        /**
         * Reads AtHwcap from the auxiliary vector.
         */
        public fun from(auxv: AuxVec): AtHwcap {
            val cap = AtHwcap.default().copy(fp = test(auxv.hwcap, 0u), asimd = test(auxv.hwcap, 1u), aes = test(auxv.hwcap, 3u), pmull = test(auxv.hwcap, 4u), sha1 = test(auxv.hwcap, 5u), sha2 = test(auxv.hwcap, 6u), crc32 = test(auxv.hwcap, 7u), atomics = test(auxv.hwcap, 8u), fphp = test(auxv.hwcap, 9u), asimdhp = test(auxv.hwcap, 10u), asimdrdm = test(auxv.hwcap, 12u), jscvt = test(auxv.hwcap, 13u), fcma = test(auxv.hwcap, 14u), lrcpc = test(auxv.hwcap, 15u), dcpop = test(auxv.hwcap, 16u), sha3 = test(auxv.hwcap, 17u), sm3 = test(auxv.hwcap, 18u), sm4 = test(auxv.hwcap, 19u), asimddp = test(auxv.hwcap, 20u), sha512 = test(auxv.hwcap, 21u), sve = test(auxv.hwcap, 22u), fhm = test(auxv.hwcap, 23u), dit = test(auxv.hwcap, 24u), uscat = test(auxv.hwcap, 25u), ilrcpc = test(auxv.hwcap, 26u), flagm = test(auxv.hwcap, 27u), ssbs = test(auxv.hwcap, 28u), sb = test(auxv.hwcap, 29u), paca = test(auxv.hwcap, 30u), pacg = test(auxv.hwcap, 31u), dcpodp = test(auxv.hwcap2, 0u), sve2 = test(auxv.hwcap2, 1u), sveaes = test(auxv.hwcap2, 2u), svepmull = test(auxv.hwcap2, 3u), svebitperm = test(auxv.hwcap2, 4u), svesha3 = test(auxv.hwcap2, 5u), svesm4 = test(auxv.hwcap2, 6u), flagm2 = test(auxv.hwcap2, 7u), frint = test(auxv.hwcap2, 8u), svef32mm = test(auxv.hwcap2, 10u), svef64mm = test(auxv.hwcap2, 11u), i8mm = test(auxv.hwcap2, 13u), bf16 = test(auxv.hwcap2, 14u), rng = test(auxv.hwcap2, 16u), bti = test(auxv.hwcap2, 17u), mte = test(auxv.hwcap2, 18u), ecv = test(auxv.hwcap2, 19u), sme = test(auxv.hwcap2, 23u), smei16i64 = test(auxv.hwcap2, 24u), smef64f64 = test(auxv.hwcap2, 25u), smefa64 = test(auxv.hwcap2, 30u), wfxt = test(auxv.hwcap2, 31u))
            // Hardware capabilities from bits 32 to 63 should only
            // be tested on LP64 targets with 64 bits `Int`.
            // On ILP32 targets like `aarch64 - unknown - linux - gnuIlp32`,
            // these hardware capabilities will default to `false`.
            // https://github.com/rust-lang/rust/issues/146230
            run {
                // cap.ebf16: bit.test(auxv.hwcap2, 32);
                // cap.sveebf16: bit.test(auxv.hwcap2, 33);
                cap.cssc = test(auxv.hwcap2, 34u)
                // cap.rprfm: bit.test(auxv.hwcap2, 35);
                cap.sve2p1 = test(auxv.hwcap2, 36u)
                cap.sme2 = test(auxv.hwcap2, 37u)
                cap.sme2p1 = test(auxv.hwcap2, 38u)
                // cap.smei16i32 = bit.test(auxv.hwcap2, 39);
                // cap.smebi32i32 = bit.test(auxv.hwcap2, 40);
                cap.smeb16b16 = test(auxv.hwcap2, 41u)
                cap.smef16f16 = test(auxv.hwcap2, 42u)
                cap.mops = test(auxv.hwcap2, 43u)
                cap.hbc = test(auxv.hwcap2, 44u)
                cap.sveb16b16 = test(auxv.hwcap2, 45u)
                cap.lrcpc3 = test(auxv.hwcap2, 46u)
                cap.lse128 = test(auxv.hwcap2, 47u)
                cap.fpmr = test(auxv.hwcap2, 48u)
                cap.lut = test(auxv.hwcap2, 49u)
                cap.faminmax = test(auxv.hwcap2, 50u)
                cap.f8cvt = test(auxv.hwcap2, 51u)
                cap.f8fma = test(auxv.hwcap2, 52u)
                cap.f8dp4 = test(auxv.hwcap2, 53u)
                cap.f8dp2 = test(auxv.hwcap2, 54u)
                cap.f8e4m3 = test(auxv.hwcap2, 55u)
                cap.f8e5m2 = test(auxv.hwcap2, 56u)
                cap.smelutv2 = test(auxv.hwcap2, 57u)
                cap.smef8f16 = test(auxv.hwcap2, 58u)
                cap.smef8f32 = test(auxv.hwcap2, 59u)
                cap.smesf8fma = test(auxv.hwcap2, 60u)
                cap.smesf8dp4 = test(auxv.hwcap2, 61u)
                cap.smesf8dp2 = test(auxv.hwcap2, 62u)
                // cap.pauthlr = bit.test(auxv.hwcap2, ??);
            }
            return cap
        }
    }
}
