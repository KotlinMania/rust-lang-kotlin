// port-lint: source types.rs
package io.github.kotlinmania.rustlang.libc.types

import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
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
public open class Padding<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout(),
) {
    public var field0: MaybeUninit<T>
        get() = value
        set(v) {
            value = v
        }

    public var value: MaybeUninit<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: MaybeUninit<T>, fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Padding<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Padding<T>(ptr, layout) }, { it.value })

    public open fun clone(): Padding<T> = Padding<T>(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        // Taken from `MaybeUninit`s debug implementation
        // NB: there is no `.padFmt` so we can't use a simpler `formatArgs("Padding<{..}>").
        val fullName = "Padding"
        val prefixLen = (fullName.indexOf("Padding").takeIf { it >= 0 })!!
        return run { f.append(fullName.substring(prefixLen)); Result.Ok(Unit) }
    }

    /**
     * Do nothing when hashing to ignore the existence of padding fields.
     */
    public override fun hashCode(): Int = 0

    /**
     * Padding fields are all equal, regardless of what is inside them, so they do not affect anything.
     */
    public open fun eq(other: Padding<T>): Boolean = true

    /**
     * Mark that `Padding` implements `Eq` so that it can be used in types that implement it.
     */

    public companion object {
        public operator fun <T> invoke(value: MaybeUninit<T>, fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout()): Padding<T> = Padding<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout()): Padding<T> = Padding<T>(fieldStorage, fieldLayout)

        public fun <T> default(fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout()): Padding<T> = Padding(MaybeUninit.zeroed(UnresolvedTypeLayout<T>()), fieldLayout = fieldLayout)

        /**
         * Create a `Padding` initialized with the given value.
         */
        public fun <T> new(value: T, fieldLayout: TypeLayout<MaybeUninit<T>> = UnresolvedTypeLayout()): Padding<T> = Padding(MaybeUninit.new(value, UnresolvedTypeLayout<T>()), fieldLayout = fieldLayout)
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
    check(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) <= sizeOf<CShort>(typeLayoutT = UnresolvedTypeLayout<CShort>()))
    // Should always be true
    return x.toShort()
}

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u32CastInt(x: UInt): CInt {
    // May not be true on 16-bit platforms, but should be everywhere this is used.
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<CInt>(typeLayoutT = UnresolvedTypeLayout<CInt>()))
    return x.toInt()
}

/**
 * Used to avoid `overflowingLiterals` when the value is in-range for the unsigned number but
 * out-of-range for signed.
 */
public fun u32CastLong(x: UInt): CLong {
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<CLong>(typeLayoutT = UnresolvedTypeLayout<CLong>()))
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
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<Ioctl>(typeLayoutT = UnresolvedTypeLayout<Ioctl>()))
    // Should always be true
    return x.toULong()
}

public fun u8SliceCastCharSlice(x: List<UByte>): List<CChar> {
    check(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8) == sizeOf<CChar>(typeLayoutT = UnresolvedTypeLayout<CChar>()))
    val len = x.size
    val ptr = x.asPtr()
    // SAFETY: same repr, possibly just a sign cast
    return linearSliceView(ConstPtr((ptr).rawAddress), len.toInt(), UnresolvedTypeLayout<CChar>())
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
