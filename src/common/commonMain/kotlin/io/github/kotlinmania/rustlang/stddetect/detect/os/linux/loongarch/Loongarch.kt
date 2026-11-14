// port-lint: source detect/os/linux/loongarch.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.loongarch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for LoongArch on Linux.

/**
 * Try to read the features from the auxiliary vector.
 */
internal fun detectFeatures(): Initializer {
    val value = Cache.Initializer.default()
    val enableFeature =
        { value1: Initializer, feature, enable ->
            if (enable) {
                value1.set(feature.toUInt())
            }
        }
    // The values are part of the platform-specific [cpucfg]
    //
    // [cpucfg]: LoongArch Reference Manual Volume 1: Basic Architecture v1.1
    val cpucfg1: Int
    val cpucfg2: Int
    val cpucfg3: Int
    run {
        asm("cpucfg {}, {}", "cpucfg {}, {}", "cpucfg {}, {}", out, reg, cpucfg1, reg, 1, out, reg, cpucfg2, reg, 2, out, reg, cpucfg3, reg, 3, options(pure, nomem, preservesFlags, nostack))
    }
    enableFeature(value, Feature.v32s, test(cpucfg1, 0u) || test(cpucfg1, 1u))
    enableFeature(value, Feature::frecipe, test(cpucfg2, 25u))
    enableFeature(value, Feature::div32, test(cpucfg2, 26u))
    enableFeature(value, Feature::lamBh, test(cpucfg2, 27u))
    enableFeature(value, Feature::lamcas, test(cpucfg2, 28u))
    enableFeature(value, Feature::scq, test(cpucfg2, 30u))
    enableFeature(value, Feature::ldSeqSa, test(cpucfg3, 23u))
    // The values are part of the platform-specific [asm/hwcap.h][hwcap]
    //
    // [hwcap]: https://github.com/torvalds/linux/blob/master/arch/loongarch/include/uapi/asm/hwcap.h
    when (val matchVal = auxv()) {
        is Ok -> {
            val auxv = matchVal.value
            enableFeature(value, Feature::f, test(cpucfg2, 1u) && test(auxv.hwcap, 3u))
            enableFeature(value, Feature::d, test(cpucfg2, 2u) && test(auxv.hwcap, 3u))
            enableFeature(value, Feature::lsx, test(auxv.hwcap, 4u))
            enableFeature(value, Feature::lasx, test(auxv.hwcap, 5u))
            enableFeature(value, Feature::lbt, test(auxv.hwcap, 10u) && test(auxv.hwcap, 11u) && test(auxv.hwcap, 12u))
            enableFeature(value, Feature::lvz, test(auxv.hwcap, 9u))
            enableFeature(value, Feature::ual, test(auxv.hwcap, 2u))
            return value
        }
        else -> {}
    }
    return value
}
