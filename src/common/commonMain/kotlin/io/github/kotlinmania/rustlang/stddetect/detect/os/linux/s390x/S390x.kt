// port-lint: source detect/os/linux/s390x.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.s390x.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.AuxVec
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for s390x on Linux.

/**
 * Try to read the features from the auxiliary vector
 */
public fun detectFeatures(): Initializer {
    val optHwcap: AtHwcap? = auxv().ok()?.let(Into::into)
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
        /**
         * Reads AtHwcap from the auxiliary vector.
         */
        public fun from(auxv: AuxVec): AtHwcap = AtHwcap(esan3 = test(auxv.hwcap, 0u), zarch = test(auxv.hwcap, 1u), stfle = test(auxv.hwcap, 2u), msa = test(auxv.hwcap, 3u), ldisp = test(auxv.hwcap, 4u), eimm = test(auxv.hwcap, 5u), dfp = test(auxv.hwcap, 6u), hpage = test(auxv.hwcap, 7u), etf3eh = test(auxv.hwcap, 8u), highGprs = test(auxv.hwcap, 9u), te = test(auxv.hwcap, 10u), vxrs = test(auxv.hwcap, 11u), vxrsBcd = test(auxv.hwcap, 12u), vxrsExt = test(auxv.hwcap, 13u), gs = test(auxv.hwcap, 14u), vxrsExt2 = test(auxv.hwcap, 15u), vxrsPde = test(auxv.hwcap, 16u), sort = test(auxv.hwcap, 17u), dflt = test(auxv.hwcap, 18u), vxrsPde2 = test(auxv.hwcap, 19u), nnpa = test(auxv.hwcap, 20u), pciMio = test(auxv.hwcap, 21u), sie = test(auxv.hwcap, 22u))
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
    val value = Initializer.default()
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
            enableIfSet(129, Feature.Vector)
            enableIfSet(135, Feature.VectorEnhancements1)
            enableIfSet(148, Feature.VectorEnhancements2)
            enableIfSet(198, Feature.VectorEnhancements3)
            enableIfSet(134, Feature.VectorPackedDecimal)
            enableIfSet(152, Feature.VectorPackedDecimalEnhancement)
            enableIfSet(192, Feature.VectorPackedDecimalEnhancement2)
            enableIfSet(199, Feature.VectorPackedDecimalEnhancement3)
            enableIfSet(165, Feature.NnpAssist)
        }
        // others
        enableIfSet(76, Feature.MessageSecurityAssistExtension3)
        enableIfSet(77, Feature.MessageSecurityAssistExtension4)
        enableIfSet(57, Feature.MessageSecurityAssistExtension5)
        enableIfSet(146, Feature.MessageSecurityAssistExtension8)
        enableIfSet(155, Feature.MessageSecurityAssistExtension9)
        enableIfSet(86, Feature.MessageSecurityAssistExtension12)
        enableIfSet(58, Feature.MiscellaneousExtensions2)
        enableIfSet(61, Feature.MiscellaneousExtensions3)
        enableIfSet(84, Feature.MiscellaneousExtensions4)
        enableIfSet(45, Feature.HighWord)
        enableIfSet(73, Feature.TransactionalExecution)
        enableIfSet(133, Feature.GuardedStorage)
        enableIfSet(150, Feature.EnhancedSort)
        enableIfSet(151, Feature.DeflateConversion)
        enableIfSet(201, Feature.ConcurrentFunctions)
    }
    return value
}
