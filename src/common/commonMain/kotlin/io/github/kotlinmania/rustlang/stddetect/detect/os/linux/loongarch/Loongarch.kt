// port-lint: source detect/os/linux/loongarch.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.loongarch

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.loongarch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Initializer
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for LoongArch on Linux.

/**
 * Try to read the features from the auxiliary vector.
 */
public fun detectFeatures(): Initializer {
    val value = Initializer.default()
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
        Unit
    }
    enableFeature(value, Feature.T32s, test(cpucfg1, 0u) || test(cpucfg1, 1u))
    enableFeature(value, Feature.Frecipe, test(cpucfg2, 25u))
    enableFeature(value, Feature.Div32, test(cpucfg2, 26u))
    enableFeature(value, Feature.LamBh, test(cpucfg2, 27u))
    enableFeature(value, Feature.Lamcas, test(cpucfg2, 28u))
    enableFeature(value, Feature.Scq, test(cpucfg2, 30u))
    enableFeature(value, Feature.LdSeqSa, test(cpucfg3, 23u))
    // The values are part of the platform-specific [asm/hwcap.h][hwcap]
    //
    // [hwcap]: https://github.com/torvalds/linux/blob/master/arch/loongarch/include/uapi/asm/hwcap.h
    when (val matchVal = auxv()) {
        is Result.Ok<*, *> -> {
            val auxv = matchVal.okValue()
            enableFeature(value, Feature.F, test(cpucfg2, 1u) && test(auxv.hwcap, 3u))
            enableFeature(value, Feature.D, test(cpucfg2, 2u) && test(auxv.hwcap, 3u))
            enableFeature(value, Feature.Lsx, test(auxv.hwcap, 4u))
            enableFeature(value, Feature.Lasx, test(auxv.hwcap, 5u))
            enableFeature(value, Feature.Lbt, test(auxv.hwcap, 10u) && test(auxv.hwcap, 11u) && test(auxv.hwcap, 12u))
            enableFeature(value, Feature.Lvz, test(auxv.hwcap, 9u))
            enableFeature(value, Feature.Ual, test(auxv.hwcap, 2u))
            return value
        }
        else -> {}
    }
    return value
}
