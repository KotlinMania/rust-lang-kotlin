// port-lint: source detect/os/freebsd/auxvec.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.freebsd.auxvec

import io.github.kotlinmania.libc.AtHwcap
import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUlong
import io.github.kotlinmania.libc.elfAuxInfo
import io.github.kotlinmania.libc.unix.linuxlike.AT_HWCAP2
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

// Parses ELF auxiliary vectors.

/**
 * Cache HWCAP bitfields of the ELF Auxiliary Vector.
 *
 * If an entry cannot be read all the bits in the bitfield are set to zero.
 * This should be interpreted as all the features being disabled.
 */
internal open class AuxVec(
    public var hwcap: Int,
    public var hwcap2: Int,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<AuxVec> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<AuxVec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): AuxVec =
                    AuxVec(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: AuxVec) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.hwcap)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.hwcap2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AuxVec =
                    AuxVec(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AuxVec) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.hwcap)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.hwcap2)
                }
            }
    }
}

/**
 * ELF Auxiliary Vector
 *
 * The auxiliary vector is a memory region in a running ELF program's stack
 * composed of (key: Int, value: Int) pairs.
 *
 * The keys used in the aux vector are platform dependent. For FreeBSD, they are
 * defined in [sys/elfCommon.h][elfCommonH]. The hardware capabilities of a given
 * CPU can be queried with the  `AT_HWCAP` and `AT_HWCAP2` keys.
 *
 * Note that run-time feature detection is not invoked for features that can
 * be detected at compile-time.
 *
 * [elfCommon.h]: https://svnweb.freebsd.org/base/release/12.0.0/sys/sys/elfCommon.h?revision=341707
 */
internal fun auxv(): Result<AuxVec, Unit> {
    val hwcap = archauxv(AtHwcap)
    val hwcap2 = archauxv(AT_HWCAP2)
    // Zero could indicate that no features were detected, but it's also used to
    // indicate an error. In particular, on many platforms AT_HWCAP2 will be
    // legitimately zero, since it contains the most recent feature flags.
    if (hwcap != 0 || hwcap2 != 0) {
        return Result.Ok(AuxVec(hwcap = hwcap, hwcap2 = hwcap2))
    }
    return Result.Err(Unit)
}

/**
 * Tries to read the `key` from the auxiliary vector.
 */
public fun archauxv(key: CInt): Int {
    val OUT_LEN: CInt = sizeOf<CUlong>(typeLayoutT = ObjectSlotLayout<CUlong>()).toInt()
    val localStorageL50C4 = AddressableLocal(0uL, ObjectSlotLayout<CUlong>())
    var out by localStorageL50C4
    run {
        // elfAuxInfo is available on FreeBSD 12.0+ and 11.4+:
        // https://github.com/freebsd/freebsd-src/commit/0b08ae2120cdd08c20a2b806e2fcef4d0a36c470
        // https://github.com/freebsd/freebsd-src/blob/release/11.4.0/sys/sys/auxv.h
        // FreeBSD 11 support in std has been removed in Rust 1.75 (https://github.com/rust-lang/rust/pull/114521),
        // so we can safely use this function.
        val res = elfAuxInfo(key, (MutPtr(localStorageL50C4.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, OUT_LEN)
        // If elfAuxInfo fails, `out` will be left at zero (which is the proper default value).
        check(res == 0 || out == 0uL)
    }
    return out.toInt()
}
