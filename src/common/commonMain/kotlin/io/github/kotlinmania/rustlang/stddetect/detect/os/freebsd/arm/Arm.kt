// port-lint: source detect/os/freebsd/arm.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.freebsd.arm

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache
import io.github.kotlinmania.rustlang.stddetect.detect.os.freebsd.auxvec.auxv

// Run-time feature detection for ARM on FreeBSD

// Defined in machine/elf.h.
// https://github.com/freebsd/freebsd-src/blob/deb63adf945d446ed91a9d84124c71f15ae571d1/sys/arm/include/elf.h
public val HWCAP_NEON: Int = 0x00001000

public val HWCAP2_AES: Int = 0x00000001

public val HWCAP2_PMULL: Int = 0x00000002

public val HWCAP2_SHA1: Int = 0x00000004

public val HWCAP2_SHA2: Int = 0x00000008

public val HWCAP2_CRC32: Int = 0x00000010

/**
 * Try to read the features from the auxiliary vector
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
            enableFeature(value, Feature::neon, auxv.hwcap and HWCAP_NEON != 0)
            enableFeature(value, Feature::pmull, auxv.hwcap2 and HWCAP2_PMULL != 0)
            enableFeature(value, Feature::crc, auxv.hwcap2 and HWCAP2_CRC32 != 0)
            enableFeature(value, Feature::aes, auxv.hwcap2 and HWCAP2_AES != 0)
            // SHA2 requires SHA1 & SHA2 features
            val sha1 = auxv.hwcap2 and HWCAP2_SHA1 != 0
            val sha2 = auxv.hwcap2 and HWCAP2_SHA2 != 0
            enableFeature(value, Feature::sha2, sha1 && sha2)
            return value
        }
        else -> {}
    }
    return value
}
