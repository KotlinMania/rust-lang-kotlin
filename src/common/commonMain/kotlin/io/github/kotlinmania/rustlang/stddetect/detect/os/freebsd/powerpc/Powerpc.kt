// port-lint: source detect/os/freebsd/powerpc.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.freebsd.powerpc

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.freebsd.auxvec.auxv

// Run-time feature detection for PowerPC on FreeBSD.
//
// Constants for both 32- and 64-bit come from
// <https://github.com/freebsd/freebsd-src/blob/main/sys/powerpc/include/cpu.h>.

public val PPC_FEATURE_HAS_ALTIVEC: Int = 0x10000000

public val PPC_FEATURE_HAS_VSX: Int = 0x00000080

/**
 * ISA 2.07, typically power8
 */
public val PPC_FEATURE2_ARCH_2_07: Int = 0x80000000.toInt()

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
            enableFeature(value, Feature::altivec, auxv.hwcap and PPC_FEATURE_HAS_ALTIVEC != 0)
            enableFeature(value, Feature::vsx, auxv.hwcap and PPC_FEATURE_HAS_VSX != 0)
            enableFeature(value, Feature::power8, auxv.hwcap2 and PPC_FEATURE2_ARCH_2_07 != 0)
            return value
        }
        else -> {}
    }
    return value
}
