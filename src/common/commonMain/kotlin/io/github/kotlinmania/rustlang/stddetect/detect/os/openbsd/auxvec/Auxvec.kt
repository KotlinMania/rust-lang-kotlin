// port-lint: source detect/os/openbsd/auxvec.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.openbsd.auxvec

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
 * The keys used in the aux vector are platform dependent. For OpenBSD, they are
 * defined in [machine/elf.h][elfh]. The hardware capabilities of a given CPU
 * can be queried with the  `AT_HWCAP` and `AT_HWCAP2` keys.
 *
 * Note that run-time feature detection is not invoked for features that can
 * be detected at compile-time.
 *
 * [elf.h]: https://github.com/openbsd/src/blob/master/sys/arch/arm64/include/elf.h
 * [elf.h]: https://github.com/openbsd/src/blob/master/sys/arch/powerpc64/include/elf.h
 */
internal fun auxv(): Result<AuxVec, Unit> {
    val hwcap = archauxv(AtHwcap)
    val hwcap2 = archauxv(AT_HWCAP2)
    // Zero could indicate that no features were detected, but it's also used to
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
    val localStorageL46C4 = AddressableLocal(0uL, ObjectSlotLayout<CUlong>())
    var out by localStorageL46C4
    run {
        val res = elfAuxInfo(key, (MutPtr(localStorageL46C4.asMutPtr().rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, OUT_LEN)
        // If elfAuxInfo fails, `out` will be left at zero (which is the proper default value).
        check(res == 0 || out == 0uL)
    }
    return out.toInt()
}
