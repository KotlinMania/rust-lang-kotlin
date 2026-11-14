// port-lint: source detect/os/linux/powerpc.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.powerpc

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for PowerPC on Linux.
//
// Constants for both 32- and 64-bit come from
// <https://github.com/torvalds/linux/blob/master/arch/powerpc/include/uapi/asm/cputable.h>.

public val PPC_FEATURE_HAS_ALTIVEC: Int = 0x10000000

public val PPC_FEATURE_HAS_VSX: Int = 0x00000080

/**
 * ISA 2.07, typically power8
 */
public val PPC_FEATURE2_ARCH_2_07: Int = 0x80000000.toInt()

/**
 * ISA 3.00, typically power9
 */
public val PPC_FEATURE2_ARCH_3_00: Int = 0x00800000

/**
 * Try to read the features from the auxiliary vector.
 */
internal fun detectFeatures(): Initializer {
    val value = Cache.Initializer.default()
    val enableFeature =
        { value1: Initializer, f, enable ->
            if (enable) {
                value1.set(f.toUInt())
            }
        }
    when (val matchVal = auxv()) {
        is Ok -> {
            val auxv = matchVal.value
            // note: the PowerPC values are the mask to do the test (instead of the
            // index of the bit to test like in ARM and Aarch64)
            enableFeature(value, Feature::altivec, auxv.hwcap and PPC_FEATURE_HAS_ALTIVEC != 0)
            enableFeature(value, Feature::vsx, auxv.hwcap and PPC_FEATURE_HAS_VSX != 0)
            // Note(powerpcTargetFeature): this code assumes the ISA hwcaps imply vsx/altivec, but
            // this is not a correct assumption. See rust-lang/compiler-team#1046 for
            // a possible resolution.
            val power8Features = auxv.hwcap2 and PPC_FEATURE2_ARCH_2_07 != 0
            enableFeature(value, Feature::power8, power8Features)
            enableFeature(value, Feature::power8Altivec, power8Features)
            enableFeature(value, Feature::power8Crypto, power8Features)
            enableFeature(value, Feature::power8Vector, power8Features)
            val power9Features = auxv.hwcap2 and PPC_FEATURE2_ARCH_3_00 != 0
            enableFeature(value, Feature::power9, power9Features)
            enableFeature(value, Feature::power9Altivec, power9Features)
            enableFeature(value, Feature::power9Vector, power9Features)
            return value
        }
        else -> {}
    }
    return value
}
