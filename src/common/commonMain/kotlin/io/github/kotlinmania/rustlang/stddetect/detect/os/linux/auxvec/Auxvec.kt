// port-lint: source detect/os/linux/auxvec.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.linux.auxvec

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.chunks
import io.github.kotlinmania.rustlang.stddetect.detect.os.linux.readFile

// Parses ELF auxiliary vectors.

public fun interface FnCallback {
    public operator fun invoke(arg0: CUlong): CUlong
}

public typealias F = FnCallback

public val AT_NULL: Int = 0

/**
 * Key to access the CPU Hardware capabilities bitfield.
 */
public val AT_HWCAP: Int = 16

/**
 * Key to access the CPU Hardware capabilities 2 bitfield.
 */
public val AT_HWCAP2: Int = 26

/**
 * Cache HWCAP bitfields of the ELF Auxiliary Vector.
 *
 * If an entry cannot be read all the bits in the bitfield are set to zero.
 * This should be interpreted as all the features being disabled.
 */
public open class AuxVec(
    public var hwcap: Int,
    public var hwcap2: Int,
) {
    public open fun clone(): AuxVec = AuxVec(hwcap, hwcap2)

    public companion object {
        public fun typeLayout(): TypeLayout<AuxVec> =
            object : TypeLayout<AuxVec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): AuxVec =
                    AuxVec(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: AuxVec) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.hwcap)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.hwcap2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AuxVec =
                    AuxVec(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AuxVec) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.hwcap)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.hwcap2)
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
 * The keys used in the aux vector are platform dependent. For Linux, they are
 * defined in [linux/auxvec.h][auxvecH]. The hardware capabilities of a given
 * CPU can be queried with the  `AT_HWCAP` and `AT_HWCAP2` keys.
 *
 * There is no perfect way of reading the auxiliary vector.
 *
 * - If [getauxval] is linked to the binary we use it, and otherwise it will
 *   try to read `/ proc / self / auxv`.
 * - If that fails, this function returns an error.
 *
 * Note that run-time feature detection is not invoked for features that can
 * be detected at compile-time.
 *
 *  Note: We always directly use `getauxval` on `* - linux - ( gnu, musl, ohos ) *` and
 * `* - android *` targets rather than `dlsym` it because we can safely assume
 * `getauxval` is linked to the binary.
 * - `* - linux - gnu *` targets ([since Rust 1.64](https://blog.rust-lang.org/2022/08/01/Increasing-glibc-kernel-requirements.html))
 *   have glibc requirements higher than [glibc 2.16 that added `getauxval`](https://sourceware.org/legacy-ml/libc-announce/2012/msg00000.html).
 * - `* - linux - musl *` targets ([at least since Rust 1.15](https://github.com/rust-lang/rust/blob/1.15.0/src/ci/docker/x8664-musl/build-musl.sh#L15))
 *   use musl newer than [musl 1.1.0 that added `getauxval`](https://git.musl-libc.org/cgit/musl/tree/WHATSNEW?h=v1.1.0#n1197)
 * - `* - linux - ohos *` targets use a [fork of musl 1.2](https://gitee.com/openharmony/docs/blob/master/en/application-dev/reference/native-lib/musl.md)
 * - `* - android *` targets ([since Rust 1.68](https://blog.rust-lang.org/2023/01/09/android-ndk-update-r25.html))
 *   have the minimum supported API level higher than [Android 4.3 (API level 18) that added `getauxval`](https://github.com/aosp-mirror/platformBionic/blob/d3ebc2f7c49a9893b114124d4a6b315f3a328764/libc/include/sys/auxv.h#L49).
 *
 * For more information about when `getauxval` is available check the great
 * [`auxv` crate documentation][auxvDocs].
 *
 * [auxvecH]: https://github.com/torvalds/linux/blob/master/include/uapi/linux/auxvec.h
 * [auxvDocs]: https://docs.rs/auxv/0.3.3/auxv/
 * [getauxval]: https://man7.org/linux/man-pages/man3/getauxval.3.html
 */
public fun auxv(): Result<AuxVec, Unit> {
    // Try to call a getauxval function.
    when (val matchVal = getauxval(AT_HWCAP)) {
        is Result.Ok<*, *> -> {
            val hwcap = matchVal.value
            // Targets with only AT_HWCAP:
            if (hwcap != 0) {
                return Result.Ok<AuxVec, Unit>(AuxVec(hwcap = hwcap))
            }
            // Targets with AT_HWCAP and AT_HWCAP2:
            when (val matchVal1 = getauxval(AT_HWCAP2)) {
                is Result.Ok<*, *> -> {
                    val hwcap2 = matchVal1.value
                    // Zero could indicate that no features were detected, but it's also used to indicate
                    // an error. In particular, on many platforms AT_HWCAP2 will be legitimately zero,
                    // since it contains the most recent feature flags. Use the fallback only if no
                    // features were detected at all.
                    if (hwcap != 0 || hwcap2 != 0) {
                        return Result.Ok<AuxVec, Unit>(AuxVec(hwcap = hwcap, hwcap2 = hwcap2))
                    }
                }
                else -> {}
            }
            // Intentionnaly not used
        }
        else -> {}
    }
    // If calling getauxval fails, try to read the auxiliary vector from
    // its file:
    return auxvFromFile("/proc/self/auxv").mapErr({ Unit })
}

/**
 * Tries to read the `key` from the auxiliary vector by calling the
 * `getauxval` function. If the function is not linked, this function return `Err`.
 */
public fun getauxval(key: Int): Result<Int, Unit> {
    val ffiGetauxval: F = io.github.kotlinmania.rustlang.libc.getauxval
    return Result.Ok<Int, Unit>(ffiGetauxval(key.toULong()).toInt())
}

/**
 * Tries to read the auxiliary vector from the `file`. If this fails, this
 * function returns `Err`.
 */
public fun auxvFromFile(file: CStr): Result<AuxVec, String> {
    val file1 = when (val tryResult = readFile(file)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<AuxVec, String>(tryResult.error) }
    return auxvFromFileBytes(file1)
}

/**
 * Read auxiliary vector from a slice of bytes.
 */
public fun auxvFromFileBytes(bytes: List<UByte>): Result<AuxVec, String> {
    // See <https://github.com/torvalds/linux/blob/v5.15/include/uapi/linux/auxvec.h>.
    //
    // The auxiliary vector contains at most 34 (key,value) fields: from
    // `AT_MINSIGSTKSZ` to `AT_NULL`, but its number may increase.
    val len = bytes.size
    val buf = mutableListOf(0, 1 + len / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
    run {
        copyNonoverlapping(bytes.asPtr(), mutPtrFromRustCast(buf.asMutPtr()), len, typeLayoutT = PrimitiveLayouts.U8)
    }
    return auxvFromBuf(buf)
}

/**
 * Tries to interpret the `buffer` as an auxiliary vector. If that fails, this
 * function returns `Err`.
 */
public fun auxvFromBuf(buf: List<Int>): Result<AuxVec, String> {
    // Targets with only AT_HWCAP:
    for (el in buf.chunks(2)) {
        when (el[0]) {
            AT_NULL -> { break }
            AT_HWCAP -> { return Result.Ok<AuxVec, String>(AuxVec(hwcap = el[1])) }
            else -> Unit
        }
    }
    // Targets with AT_HWCAP and AT_HWCAP2:
    run {
        var hwcap = null
        // For some platforms, AT_HWCAP2 was added recently, so let it default to zero.
        var hwcap2 = 0
        for (el1 in buf.chunks(2)) {
            when (el1[0]) {
                AT_NULL -> { break }
                AT_HWCAP -> hwcap = el1[1]
                AT_HWCAP2 -> hwcap2 = el1[1]
                else -> Unit
            }
        }
        if (hwcap != null) {
            val hwcap1 = hwcap
            return Result.Ok<AuxVec, String>(AuxVec(hwcap = hwcap1, hwcap2 = hwcap2))
        }
    }
    // Suppress unused variable
    return Result.Err<AuxVec, String>("hwcap not found")
}
