// port-lint: source types.rs
package io.github.kotlinmania.rustlang.libc.types

import io.github.kotlinmania.rustlang.core.ffi.CChar
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Ioctl

// Platform-agnostic support types.

// This is restricted to `Copy` types since that's a loose indicator that zeros is actually
// a valid bitpattern. There is no technical reason this is required, though, so it could be
// lifted in the future if it becomes a problem.

/**
 * A transparent wrapper over `MaybeUninit<T>` to represent uninitialized padding
 * while providing `Default`.
 */
public class Padding<T> internal constructor(
    valueCtorArg: MaybeUninit<T>,
) {
    public var value: MaybeUninit<T> = valueCtorArg
        internal set

    public var field0: MaybeUninit<T>
        get() = value
        set(v) {
            value = v
        }

    public constructor() : this(
        valueCtorArg = MaybeUninit.uninit(ObjectSlotLayout<T>()),
    )

    public fun clone(): Padding<T> = Padding(value)

    public fun fmt(f: StringBuilder) {
        // Taken from `MaybeUninit`s debug implementation
        // NB: there is no `.padFmt` so we can't use a simpler `formatArgs("Padding<{..}>").
        val fullName = "Padding"
        val prefixLen = (fullName.indexOf("Padding").takeIf { it >= 0 })!!
        f.append(fullName.substring(prefixLen))
    }

    /**
     * Do nothing when hashing to ignore the existence of padding fields.
     */
    public override fun hashCode(): Int = 0

    /**
     * Padding fields are all equal, regardless of what is inside them, so they do not affect anything.
     */
    public fun eq(other: Padding<T>): Boolean = true

    /**
     * Mark that `Padding` implements `Eq` so that it can be used in types that implement it.
     */

    public companion object {
        public operator fun <T> invoke(
            value: MaybeUninit<T> = MaybeUninit.uninit(ObjectSlotLayout<T>()),
        ): Padding<T> = Padding<T>(
            valueCtorArg = value,
        )

        public fun <T> default(): Padding<T> = Padding(MaybeUninit.zeroed(ObjectSlotLayout<T>()))

        /**
         * Create a `Padding` initialized with the given value.
         */
        public fun <T> new(value: T): Padding<T> = Padding(MaybeUninit.new(value, ObjectSlotLayout<T>()))
    }
}

/**
 * The default repr type used for C style enums in Rust.
 */
public typealias CEnumRepr = CInt

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u16CastShort(x: UShort): CShort {
    check(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) <= sizeOf<CShort>(typeLayoutT = ObjectSlotLayout<CShort>()))
    // Should always be true
    return x.toShort()
}

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u32CastInt(x: UInt): CInt {
    // May not be true on 16-bit platforms, but should be everywhere this is used.
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<CInt>(typeLayoutT = ObjectSlotLayout<CInt>()))
    return x.toInt()
}

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u32CastLong(x: UInt): CLong {
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<CLong>(typeLayoutT = ObjectSlotLayout<CLong>()))
    // Should always be true
    return x.toLong()
}

/**
 * Checked casting from `unsigned long` to `int`.
 */
public fun ulongCastInt(x: CUlong): CInt {
    check(x <= (Int.MAX_VALUE.toULong()))
    return x.toInt()
}

/**
 * Checked casting from `unsigned long` to `unsigned int`.
 */
public fun ulongCastUint(x: CUlong): CUint {
    check(x <= (UInt.MAX_VALUE.toULong()))
    return x.toUInt()
}

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u32CastIoctl(x: UInt): Ioctl {
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<Ioctl>(typeLayoutT = ObjectSlotLayout<Ioctl>()))
    // Should always be true
    return x.toULong()
}

public fun u8SliceCastCharSlice(x: List<UByte>): List<CChar> {
    check(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8) == sizeOf<CChar>(typeLayoutT = ObjectSlotLayout<CChar>()))
    val len = x.size
    val ptr = x.asPtr()
    // SAFETY: same repr, possibly just a sign cast
    return rawSliceList(ConstPtr((ptr).rawAddress), len.toInt(), ObjectSlotLayout<CChar>())
}

// Note(msrv): we can switch to `copyFromSlice` in 1.87.

/**
 * Replace bytes in an array with those from a slice. This is a polyfill for `[T].copyFromSlice`
 * in `const`.
 */
public fun <T> replaceArrayItems(dst: List<T>, src: List<T>, start: Int, n: Int): List<T> {
    var i = 0
    while (i < src.size) {
        dst[i + start] = src[i]
        i += 1
    }
    return dst
}

// Note(msrv): we can opt to use C-string literals directly in 1.77

/**
 * Constructs a compile time cstring literal from a byte array
 */
public fun cstr(bytes: List<UByte>): ConstPtr {
    check(!bytes.isEmpty() && bytes[bytes.size - 1] == 0u.toUByte())
    return ConstPtr((bytes.asPtr()).rawAddress)
}
