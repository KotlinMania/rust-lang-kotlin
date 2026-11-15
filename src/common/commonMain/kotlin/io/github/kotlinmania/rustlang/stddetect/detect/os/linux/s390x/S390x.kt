// port-lint: source detect/os/linux/s390x.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.detect.arch.Feature
import io.github.kotlinmania.rustlang.detect.bit.test
import io.github.kotlinmania.rustlang.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec

// Run-time feature detection for s390x on Linux.

/**
 * Try to read the features from the auxiliary vector
 */
public fun detectFeatures(): Initializer {
    val optHwcap: AtHwcap? = auxvec.auxv().ok()?.let(Into::into)
    val facilities = ExtendedFacilityList.new()
    return cache(optHwcap, facilities)
}

public open class AtHwcap(
    public var esan3: Boolean,
    public var zarch: Boolean,
    public var stfle: Boolean,
    public var msa: Boolean,
    public var ldisp: Boolean,
    public var eimm: Boolean,
    public var dfp: Boolean,
    public var hpage: Boolean,
    public var etf3eh: Boolean,
    public var highGprs: Boolean,
    public var te: Boolean,
    public var vxrs: Boolean,
    public var vxrsBcd: Boolean,
    public var vxrsExt: Boolean,
    public var gs: Boolean,
    public var vxrsExt2: Boolean,
    public var vxrsPde: Boolean,
    public var sort: Boolean,
    public var dflt: Boolean,
    public var vxrsPde2: Boolean,
    public var nnpa: Boolean,
    public var pciMio: Boolean,
    public var sie: Boolean,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<AtHwcap> =
            object : TypeLayout<AtHwcap> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 23L
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
                    )
                override fun writeNative(addr: Long, value: AtHwcap) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.esan3)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.zarch)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.stfle)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.msa)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.ldisp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.eimm)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 6L, value.dfp)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 7L, value.hpage)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 8L, value.etf3eh)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 9L, value.highGprs)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 10L, value.te)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 11L, value.vxrs)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 12L, value.vxrsBcd)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 13L, value.vxrsExt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 14L, value.gs)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 15L, value.vxrsExt2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 16L, value.vxrsPde)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 17L, value.sort)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 18L, value.dflt)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 19L, value.vxrsPde2)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 20L, value.nnpa)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 21L, value.pciMio)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 22L, value.sie)
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
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AtHwcap) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.esan3)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.zarch)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.stfle)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.msa)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.ldisp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.eimm)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 6, value.dfp)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 7, value.hpage)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 8, value.etf3eh)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 9, value.highGprs)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 10, value.te)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 11, value.vxrs)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 12, value.vxrsBcd)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 13, value.vxrsExt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 14, value.gs)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 15, value.vxrsExt2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 16, value.vxrsPde)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 17, value.sort)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 18, value.dflt)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 19, value.vxrsPde2)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 20, value.nnpa)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 21, value.pciMio)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 22, value.sie)
                }
            }

        /**
         * Reads AtHwcap from the auxiliary vector.
         */
        public fun from(auxv: auxvec.AuxVec): AtHwcap = AtHwcap(esan3 = test(auxv.hwcap, 0u), zarch = test(auxv.hwcap, 1u), stfle = test(auxv.hwcap, 2u), msa = test(auxv.hwcap, 3u), ldisp = test(auxv.hwcap, 4u), eimm = test(auxv.hwcap, 5u), dfp = test(auxv.hwcap, 6u), hpage = test(auxv.hwcap, 7u), etf3eh = test(auxv.hwcap, 8u), highGprs = test(auxv.hwcap, 9u), te = test(auxv.hwcap, 10u), vxrs = test(auxv.hwcap, 11u), vxrsBcd = test(auxv.hwcap, 12u), vxrsExt = test(auxv.hwcap, 13u), gs = test(auxv.hwcap, 14u), vxrsExt2 = test(auxv.hwcap, 15u), vxrsPde = test(auxv.hwcap, 16u), sort = test(auxv.hwcap, 17u), dflt = test(auxv.hwcap, 18u), vxrsPde2 = test(auxv.hwcap, 19u), nnpa = test(auxv.hwcap, 20u), pciMio = test(auxv.hwcap, 21u), sie = test(auxv.hwcap, 22u))
    }
}

public open class ExtendedFacilityList(
    public var value: ULongArray,
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun getBit(n: Int): Boolean {
        // NOTE: bits are numbered from the left.
        return this.value[n / 64] and (1uL shl (63 - (n % 64))) != 0uL
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ExtendedFacilityList> =
            object : TypeLayout<ExtendedFacilityList> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): ExtendedFacilityList =
                    ExtendedFacilityList(
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ExtendedFacilityList) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExtendedFacilityList =
                    ExtendedFacilityList(
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExtendedFacilityList) {
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset, value.value)
                }
            }

        public fun new(): ExtendedFacilityList {
            val result: ULongArray  = ULongArray(4) { 0uL }
            // SAFETY: rust/llvm only support s390x version with the `stfle` instruction.
            run {
                Unit
            }
            return ExtendedFacilityList(result)
        }
    }
}

/**
 * Initializes the cache from the feature bits.
 *
 * These values are part of the platform-specific [asm/elf.h][kernel], and are a selection of the
 * fields found in the [Facility Indications].
 *
 * [Facility Indications]: https://www.ibm.com/support/pages/sites/default/files/2021-05/SA22-7871-10.pdf#page=63
 * [kernel]: https://github.com/torvalds/linux/blob/b62cef9a5c673f1b8083159f5dc03c1c5daced2f/arch/s390/include/asm/elf.h#L129
 */
public fun cache(hwcap: AtHwcap?, facilities: ExtendedFacilityList): Initializer {
    val value = Cache.Initializer.default()
    run {
        var enableIfSet =
            { bitIndex, f ->
                if (facilities.getBit(bitIndex)) {
                    value.set(f.toUInt())
                }
            }
        // We use HWCAP for `vector` because it requires both hardware and kernel support.
        if (hwcap.vxrs == true) {
            // vector and related
            enableIfSet(129, Feature::vector)
            enableIfSet(135, Feature::vectorEnhancements1)
            enableIfSet(148, Feature::vectorEnhancements2)
            enableIfSet(198, Feature::vectorEnhancements3)
            enableIfSet(134, Feature::vectorPackedDecimal)
            enableIfSet(152, Feature::vectorPackedDecimalEnhancement)
            enableIfSet(192, Feature::vectorPackedDecimalEnhancement2)
            enableIfSet(199, Feature::vectorPackedDecimalEnhancement3)
            enableIfSet(165, Feature::nnpAssist)
        }
        // others
        enableIfSet(76, Feature::messageSecurityAssistExtension3)
        enableIfSet(77, Feature::messageSecurityAssistExtension4)
        enableIfSet(57, Feature::messageSecurityAssistExtension5)
        enableIfSet(146, Feature::messageSecurityAssistExtension8)
        enableIfSet(155, Feature::messageSecurityAssistExtension9)
        enableIfSet(86, Feature::messageSecurityAssistExtension12)
        enableIfSet(58, Feature::miscellaneousExtensions2)
        enableIfSet(61, Feature::miscellaneousExtensions3)
        enableIfSet(84, Feature::miscellaneousExtensions4)
        enableIfSet(45, Feature::highWord)
        enableIfSet(73, Feature::transactionalExecution)
        enableIfSet(133, Feature::guardedStorage)
        enableIfSet(150, Feature::enhancedSort)
        enableIfSet(151, Feature::deflateConversion)
        enableIfSet(201, Feature::concurrentFunctions)
    }
    return value
}
