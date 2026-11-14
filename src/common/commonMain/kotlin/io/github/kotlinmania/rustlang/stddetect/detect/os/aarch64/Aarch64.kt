// port-lint: source detect/os/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.aarch64

import io.github.kotlinmania.rustlang.stddetect.detect.Feature
import io.github.kotlinmania.rustlang.stddetect.detect.cache.Cache

// Run-time feature detection for Aarch64 on any OS that emulates the mrs instruction.
//
// On FreeBSD >= 12.0, Linux >= 4.11 and other operating systems, it is possible to use
// privileged system registers from userspace to check CPU feature support.
//
// AArch64 system registers ID_AA64ISAR0_EL1, ID_AA64PFR0_EL1, ID_AA64ISAR1_EL1
// have bits dedicated to features like AdvSIMD, CRC32, AES, atomics (LSE), etc.
// Each part of the register indicates the level of support for a certain feature, e.g.
// when ID_AA64ISAR0_EL1\[7:4\] is >= 1, AES is supported; when it's >= 2, PMULL is supported.
//
// For proper support of [SoCs where different cores have different capabilities](https://medium.com/@jadr2ddude/a-big-little-problem-a-tale-of-big-little-gone-wrong-e7778ce744bb),
// the OS has to always report only the features supported by all cores, like [FreeBSD does](https://reviews.freebsd.org/D17137#393947).
//
// References:
//
// - [Zircon implementation](https://fuchsia.googlesource.com/zircon/+/master/kernel/arch/arm64/feature.cpp)
// - [Linux documentation](https://www.kernel.org/doc/Documentation/arm64/cpu-feature-registers.txt)
// - [ARM documentation](https://developer.arm.com/documentation/ddi0601/2022-12/AArch64-Registers?lang=en)

/**
 * Try to read the features from the system registers.
 *
 * This will cause SIGILL if the current OS is not trapping the mrs instruction.
 */
internal fun detectFeatures(): Initializer {
    // ID_AA64ISAR0_EL1 - Instruction Set Attribute Register 0
    val aa64isar0: ULong
    run {
        asm("mrs {}, ID_AA64ISAR0_EL1", out, reg, aa64isar0, options(pure, nomem, preservesFlags, nostack))
    }
    // ID_AA64ISAR1_EL1 - Instruction Set Attribute Register 1
    val aa64isar1: ULong
    run {
        asm("mrs {}, ID_AA64ISAR1_EL1", out, reg, aa64isar1, options(pure, nomem, preservesFlags, nostack))
    }
    // ID_AA64MMFR2_EL1 - AArch64 Memory Model Feature Register 2
    val aa64mmfr2: ULong
    run {
        asm("mrs {}, ID_AA64MMFR2_EL1", out, reg, aa64mmfr2, options(pure, nomem, preservesFlags, nostack))
    }
    // ID_AA64PFR0_EL1 - Processor Feature Register 0
    val aa64pfr0: ULong
    run {
        asm("mrs {}, ID_AA64PFR0_EL1", out, reg, aa64pfr0, options(pure, nomem, preservesFlags, nostack))
    }
    return parseSystemRegisters(aa64isar0, aa64isar1, aa64mmfr2, aa64pfr0)
}

internal fun parseSystemRegisters(aa64isar0: ULong, aa64isar1: ULong, aa64mmfr2: ULong, aa64pfr0: ULong?): Initializer {
    val value = Cache.Initializer.default()
    var enableFeature =
        { f, enable ->
            if (enable) {
                value.set(f.toUInt())
            }
        }
    // ID_AA64ISAR0_EL1 - Instruction Set Attribute Register 0
    enableFeature(Feature::pmull, bitsShift(aa64isar0, 7, 4) >= 2uL)
    enableFeature(Feature::lse, bitsShift(aa64isar0, 23, 20) >= 2uL)
    enableFeature(Feature::crc, bitsShift(aa64isar0, 19, 16) >= 1uL)
    // ID_AA64PFR0_EL1 - Processor Feature Register 0
    if (aa64pfr0 != null) {
        val aa64pfr01 = aa64pfr0
        val fp = bitsShift(aa64pfr01, 19, 16) < 0xFuL
        val fphp = bitsShift(aa64pfr01, 19, 16) >= 1uL
        val asimd = bitsShift(aa64pfr01, 23, 20) < 0xFuL
        val asimdhp = bitsShift(aa64pfr01, 23, 20) >= 1uL
        enableFeature(Feature::fp, fp)
        enableFeature(Feature::fp16, fphp)
        // SIMD support requires float support - if half-floats are
        // supported, it also requires half-float support:
        enableFeature(Feature::asimd, fp && asimd && (fphp.inv() or asimdhp))
        // SIMD extensions require SIMD support:
        enableFeature(Feature::aes, asimd && bitsShift(aa64isar0, 7, 4) >= 2uL)
        val sha1 = bitsShift(aa64isar0, 11, 8) >= 1uL
        val sha2 = bitsShift(aa64isar0, 15, 12) >= 1uL
        enableFeature(Feature::sha2, asimd && sha1 && sha2)
        enableFeature(Feature::rdm, asimd && bitsShift(aa64isar0, 31, 28) >= 1uL)
        enableFeature(Feature::dotprod, asimd && bitsShift(aa64isar0, 47, 44) >= 1uL)
        enableFeature(Feature::sve, asimd && bitsShift(aa64pfr01, 35, 32) >= 1uL)
    }
    // ID_AA64ISAR1_EL1 - Instruction Set Attribute Register 1
    // Check for either APA or API field
    enableFeature(Feature::paca, bitsShift(aa64isar1, 11, 4) >= 1uL)
    enableFeature(Feature::rcpc, bitsShift(aa64isar1, 23, 20) >= 1uL)
    // Check for either GPA or GPI field
    enableFeature(Feature::pacg, bitsShift(aa64isar1, 31, 24) >= 1uL)
    // ID_AA64MMFR2_EL1 - AArch64 Memory Model Feature Register 2
    enableFeature(Feature::lse2, bitsShift(aa64mmfr2, 35, 32) >= 1uL)
    return value
}

public fun bitsShift(x: ULong, high: Int, low: Int): ULong = (x shr low) and ((1uL shl (high - low + 1)) - 1uL)
