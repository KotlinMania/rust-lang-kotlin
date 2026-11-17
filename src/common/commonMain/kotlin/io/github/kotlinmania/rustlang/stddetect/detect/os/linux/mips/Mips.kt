// port-lint: source detect/os/linux/mips.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.mips

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.arch.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.bit.test
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec.auxv
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache

// Run-time feature detection for MIPS on Linux.

/**
 * Try to read the features from the auxiliary vector.
 */
public fun detectFeatures(): cache.Initializer {
    val value = cache.Initializer.default()
    val enableFeature =
        { value1: cache.Initializer, f, enable ->
            if (enable) {
                value1.set(f.toUInt())
            }
        }
    // The values are part of the platform-specific [asm/hwcap.h][hwcap]
    //
    // [hwcap]: https://github.com/torvalds/linux/blob/master/arch/mips/include/uapi/asm/hwcap.h
    when (val matchVal = auxv()) {
        is Result.Ok<*, *> -> {
            val auxv = matchVal.value
            enableFeature(value, Feature::msa, test(auxv.hwcap, 1u))
            return value
        }
        else -> {}
    }
    return value
}
