// port-lint: source detect/os/openbsd/aarch64.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.openbsd.aarch64

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.fuchsia.SizeT
import io.github.kotlinmania.libc.sysctl
import io.github.kotlinmania.libc.unix.nto.CTL_MACHDEP
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.stddetect.detect.os.aarch64.parseSystemRegisters
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.s390x.cache

// Run-time feature detection for Aarch64 on OpenBSD.
//
// OpenBSD doesn't trap the mrs instruction, but exposes the system registers through sysctl.
// https://github.com/openbsd/src/commit/d335af936b9d7dd9cf655cae1ce19560c45de6c8
// https://github.com/golang/go/commit/cd54ef1f61945459486e9eea2f016d99ef1da925

// Defined in machine/cpu.h.
// https://github.com/openbsd/src/blob/72ccc03bd11da614f31f7ff76e3f6fce99bc1c79/sys/arch/arm64/include/cpu.h#L25-L40
public val CPU_ID_AA64ISAR0: CInt = 2

public val CPU_ID_AA64ISAR1: CInt = 3

public val CPU_ID_AA64MMFR2: CInt = 7

public val CPU_ID_AA64PFR0: CInt = 8

/**
 * Try to read the features from the system registers.
 */
internal fun detectFeatures(): cache.Initializer {
    // ID_AA64ISAR0_EL1 and ID_AA64ISAR1_EL1 are supported on OpenBSD 7.1+.
    // https://github.com/openbsd/src/commit/d335af936b9d7dd9cf655cae1ce19560c45de6c8
    // Others are supported on OpenBSD 7.3+.
    // https://github.com/openbsd/src/commit/c7654cd65262d532212f65123ee3905ba200365c
    // sysctl returns an unsupported error if operation is not supported,
    // so we can safely use this function on older versions of OpenBSD.
    val aa64isar0 = (sysctl64(listOf(CTL_MACHDEP, CPU_ID_AA64ISAR0)) ?: 0uL)
    val aa64isar1 = (sysctl64(listOf(CTL_MACHDEP, CPU_ID_AA64ISAR1)) ?: 0uL)
    val aa64mmfr2 = (sysctl64(listOf(CTL_MACHDEP, CPU_ID_AA64MMFR2)) ?: 0uL)
    // Do not use unwrapOr(0) because in fp and asimd fields, 0 indicates that
    // the feature is available.
    val aa64pfr0 = sysctl64(listOf(CTL_MACHDEP, CPU_ID_AA64PFR0))
    return parseSystemRegisters(aa64isar0, aa64isar1, aa64mmfr2, aa64pfr0)
}

public fun sysctl64(mib: List<CInt>): ULong? {
    val OUT_LEN: SizeT = sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64).toULong()
    val out = MaybeUninit.uninit(PrimitiveLayouts.U64)
    val outLen = OUT_LEN
    val res = sysctl(mib.asPtr(), mib.size.toUInt(), mutPtrFromRustCast(out.asMutPtr()), outLen, MutPtr(0L), 0)
    if (res == -1 || outLen != OUT_LEN) {
        return null
    }
    // SAFETY: we've checked that sysctl was successful and `out` was filled.
    return out.assumeInit()
}
