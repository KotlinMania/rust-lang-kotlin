// port-lint: source detect/os/linux/mips.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.mips

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv

// Run-time feature detection for MIPS on Linux.

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
    // [hwcap]: https://github.com/torvalds/linux/blob/master/arch/mips/include/uapi/asm/hwcap.h
    when (val matchVal = auxv()) {
        is Ok -> {
            val auxv = matchVal.value
            enableFeature(value, Feature::msa, test(auxv.hwcap, 1u))
            return value
        }
        else -> {}
    }
    return value
}
