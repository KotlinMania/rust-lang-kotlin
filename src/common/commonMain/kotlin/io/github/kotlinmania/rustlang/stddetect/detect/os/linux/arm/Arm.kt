// port-lint: source detect/os/linux/arm.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.arm

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for ARM on Linux.

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
    // The values are part of the platform-specific [asm/hwcap.h][hwcap]
    //
    // [hwcap]: https://github.com/torvalds/linux/blob/master/arch/arm/include/uapi/asm/hwcap.h
    when (val matchVal = auxv()) {
        is Ok -> {
            val auxv = matchVal.value
            enableFeature(value, Feature::i8mm, test(auxv.hwcap, 27u))
            enableFeature(value, Feature::dotprod, test(auxv.hwcap, 24u))
            enableFeature(value, Feature::neon, test(auxv.hwcap, 12u))
            enableFeature(value, Feature::pmull, test(auxv.hwcap2, 1u))
            enableFeature(value, Feature::crc, test(auxv.hwcap2, 4u))
            enableFeature(value, Feature::aes, test(auxv.hwcap2, 0u))
            // SHA2 requires SHA1 & SHA2 features
            enableFeature(value, Feature::sha2, test(auxv.hwcap2, 2u) && test(auxv.hwcap2, 3u))
            return value
        }
        else -> {}
    }
    return value
}
