// port-lint: source detect/os/openbsd/powerpc.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.openbsd.powerpc

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.openbsd.auxvec.auxv

// Run-time feature detection for PowerPC on OpenBSD.
//
// Constants for 32-bit are located at
// <https://github.com/openbsd/src/blob/master/sys/arch/powerpc/include/elf.h>, and for 64-bit
// <https://github.com/openbsd/src/blob/master/sys/arch/powerpc64/include/elf.h>.

// Note that most constants are the same for 32- and 64-bit, but not all are
public val PPC_FEATURE_HAS_ALTIVEC: Int = 0x10000000

public val PPC_FEATURE_HAS_VSX: Int = 0x00000080

/**
 * ISA 2.07, typically power8
 */
public val PPC_FEATURE2_ARCH_2_07: Int = 0x80000000.toInt()

internal fun detectFeatures(): cache.Initializer {
    val value = cache.Initializer.default()
    val enableFeature =
        { value1: cache.Initializer, f, enable ->
            if (enable) {
                value1.set(f.toUInt())
            }
        }
    when (val matchVal = auxv()) {
        is Ok -> {
            val auxv = matchVal.value
            enableFeature(value, Feature::altivec, auxv.hwcap and PPC_FEATURE_HAS_ALTIVEC != 0)
            enableFeature(value, Feature::vsx, auxv.hwcap and PPC_FEATURE_HAS_VSX != 0)
            enableFeature(value, Feature::power8, auxv.hwcap2 and PPC_FEATURE2_ARCH_2_07 != 0)
            return value
        }
        else -> {}
    }
    return value
}
